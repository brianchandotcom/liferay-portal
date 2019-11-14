/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.resiliency.spi.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.resiliency.spi.exception.NoSuchDefinitionException;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;
import com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionImpl;
import com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionModelImpl;
import com.liferay.portal.resiliency.spi.service.persistence.SPIDefinitionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the spi definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Michael C. Han
 * @generated
 */
public class SPIDefinitionPersistenceImpl
	extends BasePersistenceImpl<SPIDefinition>
	implements SPIDefinitionPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SPIDefinitionUtil</code> to access the spi definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SPIDefinitionImpl.class.getName();

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
	 * Returns all the spi definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator,
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

		List<SPIDefinition> list = null;

		if (useFinderCache) {
			list = (List<SPIDefinition>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SPIDefinition spiDefinition : list) {
					if (companyId != spiDefinition.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SPIDefinition>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByCompanyId_First(
			long companyId, OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (spiDefinition != null) {
			return spiDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDefinitionException(msg.toString());
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByCompanyId_First(
		long companyId, OrderByComparator<SPIDefinition> orderByComparator) {

		List<SPIDefinition> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByCompanyId_Last(
			long companyId, OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (spiDefinition != null) {
			return spiDefinition;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDefinitionException(msg.toString());
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByCompanyId_Last(
		long companyId, OrderByComparator<SPIDefinition> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SPIDefinition> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set where companyId = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition[] findByCompanyId_PrevAndNext(
			long spiDefinitionId, long companyId,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = findByPrimaryKey(spiDefinitionId);

		Session session = null;

		try {
			session = openSession();

			SPIDefinition[] array = new SPIDefinitionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, spiDefinition, companyId, orderByComparator, true);

			array[1] = spiDefinition;

			array[2] = getByCompanyId_PrevAndNext(
				session, spiDefinition, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SPIDefinition getByCompanyId_PrevAndNext(
		Session session, SPIDefinition spiDefinition, long companyId,
		OrderByComparator<SPIDefinition> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						spiDefinition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SPIDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SPIDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<SPIDefinition>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set of spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition[] filterFindByCompanyId_PrevAndNext(
			long spiDefinitionId, long companyId,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				spiDefinitionId, companyId, orderByComparator);
		}

		SPIDefinition spiDefinition = findByPrimaryKey(spiDefinitionId);

		Session session = null;

		try {
			session = openSession();

			SPIDefinition[] array = new SPIDefinitionImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, spiDefinition, companyId, orderByComparator, true);

			array[1] = spiDefinition;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, spiDefinition, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SPIDefinition filterGetByCompanyId_PrevAndNext(
		Session session, SPIDefinition spiDefinition, long companyId,
		OrderByComparator<SPIDefinition> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SPIDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						spiDefinition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SPIDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the spi definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SPIDefinition spiDefinition :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(spiDefinition);
		}
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SPIDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SPIDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"spiDefinition.companyId = ?";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByC_N(long companyId, String name)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = fetchByC_N(companyId, name);

		if (spiDefinition == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDefinitionException(msg.toString());
		}

		return spiDefinition;
	}

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the spi definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByC_N, finderArgs, this);
		}

		if (result instanceof SPIDefinition) {
			SPIDefinition spiDefinition = (SPIDefinition)result;

			if ((companyId != spiDefinition.getCompanyId()) ||
				!Objects.equals(name, spiDefinition.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				List<SPIDefinition> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {companyId, name};
							}

							_log.warn(
								"SPIDefinitionPersistenceImpl.fetchByC_N(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SPIDefinition spiDefinition = list.get(0);

					result = spiDefinition;

					cacheResult(spiDefinition);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByC_N, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SPIDefinition)result;
		}
	}

	/**
	 * Removes the spi definition where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the spi definition that was removed
	 */
	@Override
	public SPIDefinition removeByC_N(long companyId, String name)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = findByC_N(companyId, name);

		return remove(spiDefinition);
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"spiDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"spiDefinition.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(spiDefinition.name IS NULL OR spiDefinition.name = '')";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;
	private FinderPath _finderPathWithPaginationCountByC_S;

	/**
	 * Returns all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int status) {
		return findByC_S(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(
		long companyId, int status, int start, int end) {

		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return findByC_S(
			companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {companyId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				companyId, status, start, end, orderByComparator
			};
		}

		List<SPIDefinition> list = null;

		if (useFinderCache) {
			list = (List<SPIDefinition>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SPIDefinition spiDefinition : list) {
					if ((companyId != spiDefinition.getCompanyId()) ||
						(status != spiDefinition.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				list = (List<SPIDefinition>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByC_S_First(
			long companyId, int status,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = fetchByC_S_First(
			companyId, status, orderByComparator);

		if (spiDefinition != null) {
			return spiDefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchDefinitionException(msg.toString());
	}

	/**
	 * Returns the first spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByC_S_First(
		long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator) {

		List<SPIDefinition> list = findByC_S(
			companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByC_S_Last(
			long companyId, int status,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = fetchByC_S_Last(
			companyId, status, orderByComparator);

		if (spiDefinition != null) {
			return spiDefinition;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchDefinitionException(msg.toString());
	}

	/**
	 * Returns the last spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByC_S_Last(
		long companyId, int status,
		OrderByComparator<SPIDefinition> orderByComparator) {

		int count = countByC_S(companyId, status);

		if (count == 0) {
			return null;
		}

		List<SPIDefinition> list = findByC_S(
			companyId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition[] findByC_S_PrevAndNext(
			long spiDefinitionId, long companyId, int status,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = findByPrimaryKey(spiDefinitionId);

		Session session = null;

		try {
			session = openSession();

			SPIDefinition[] array = new SPIDefinitionImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, spiDefinition, companyId, status, orderByComparator,
				true);

			array[1] = spiDefinition;

			array[2] = getByC_S_PrevAndNext(
				session, spiDefinition, companyId, status, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SPIDefinition getByC_S_PrevAndNext(
		Session session, SPIDefinition spiDefinition, long companyId,
		int status, OrderByComparator<SPIDefinition> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						spiDefinition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SPIDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(long companyId, int status) {
		return filterFindByC_S(
			companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(
		long companyId, int status, int start, int end) {

		return filterFindByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions that the user has permissions to view where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(
		long companyId, int status, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_S(companyId, status, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SPIDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(status);

			return (List<SPIDefinition>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the spi definitions before and after the current spi definition in the ordered set of spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param spiDefinitionId the primary key of the current spi definition
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition[] filterFindByC_S_PrevAndNext(
			long spiDefinitionId, long companyId, int status,
			OrderByComparator<SPIDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_S_PrevAndNext(
				spiDefinitionId, companyId, status, orderByComparator);
		}

		SPIDefinition spiDefinition = findByPrimaryKey(spiDefinitionId);

		Session session = null;

		try {
			session = openSession();

			SPIDefinition[] array = new SPIDefinitionImpl[3];

			array[0] = filterGetByC_S_PrevAndNext(
				session, spiDefinition, companyId, status, orderByComparator,
				true);

			array[1] = spiDefinition;

			array[2] = filterGetByC_S_PrevAndNext(
				session, spiDefinition, companyId, status, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SPIDefinition filterGetByC_S_PrevAndNext(
		Session session, SPIDefinition spiDefinition, long companyId,
		int status, OrderByComparator<SPIDefinition> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					query.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SPIDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						spiDefinition)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SPIDefinition> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(long companyId, int[] statuses) {
		return filterFindByC_S(
			companyId, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(
		long companyId, int[] statuses, int start, int end) {

		return filterFindByC_S(companyId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions that the user has permission to view
	 */
	@Override
	public List<SPIDefinition> filterFindByC_S(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_S(
				companyId, statuses, start, end, orderByComparator);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SPIDEFINITION_WHERE);
		}
		else {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		if (statuses.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_C_S_STATUS_7);

			query.append(StringUtil.merge(statuses));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(
				_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SPIDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SPIDefinitionImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SPIDefinitionImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<SPIDefinition>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(long companyId, int[] statuses) {
		return findByC_S(
			companyId, statuses, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(
		long companyId, int[] statuses, int start, int end) {

		return findByC_S(companyId, statuses, start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return findByC_S(
			companyId, statuses, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the spi definitions where companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching spi definitions
	 */
	@Override
	public List<SPIDefinition> findByC_S(
		long companyId, int[] statuses, int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator,
		boolean useFinderCache) {

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		if (statuses.length == 1) {
			return findByC_S(
				companyId, statuses[0], start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					companyId, StringUtil.merge(statuses)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				companyId, StringUtil.merge(statuses), start, end,
				orderByComparator
			};
		}

		List<SPIDefinition> list = null;

		if (useFinderCache) {
			list = (List<SPIDefinition>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByC_S, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SPIDefinition spiDefinition : list) {
					if ((companyId != spiDefinition.getCompanyId()) ||
						!ArrayUtil.contains(
							statuses, spiDefinition.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			if (statuses.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_S_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SPIDefinition>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByC_S, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathWithPaginationFindByC_S, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the spi definitions where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
		for (SPIDefinition spiDefinition :
				findByC_S(
					companyId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(spiDefinition);
		}
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {companyId, status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of spi definitions where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByC_S(long companyId, int[] statuses) {
		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		Object[] finderArgs = new Object[] {
			companyId, StringUtil.merge(statuses)
		};

		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathWithPaginationCountByC_S, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			if (statuses.length > 0) {
				query.append("(");

				query.append(_FINDER_COLUMN_C_S_STATUS_7);

				query.append(StringUtil.merge(statuses));

				query.append(")");

				query.append(")");
			}

			query.setStringAt(
				removeConjunction(query.stringAt(query.index() - 1)),
				query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathWithPaginationCountByC_S, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathWithPaginationCountByC_S, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_S(long companyId, int status) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_S(companyId, status);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SPIDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SPIDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(status);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of spi definitions that the user has permission to view where companyId = &#63; and status = any &#63;.
	 *
	 * @param companyId the company ID
	 * @param statuses the statuses
	 * @return the number of matching spi definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_S(long companyId, int[] statuses) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_S(companyId, statuses);
		}

		if (statuses == null) {
			statuses = new int[0];
		}
		else if (statuses.length > 1) {
			statuses = ArrayUtil.sortedUnique(statuses);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_SPIDEFINITION_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		if (statuses.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_C_S_STATUS_7);

			query.append(StringUtil.merge(statuses));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			query.toString(), SPIDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"spiDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_STATUS_2 =
		"spiDefinition.status = ?";

	private static final String _FINDER_COLUMN_C_S_STATUS_7 =
		"spiDefinition.status IN (";

	private FinderPath _finderPathFetchByCA_CP;
	private FinderPath _finderPathCountByCA_CP;

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the matching spi definition
	 * @throws NoSuchDefinitionException if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition findByCA_CP(String connectorAddress, int connectorPort)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = fetchByCA_CP(
			connectorAddress, connectorPort);

		if (spiDefinition == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("connectorAddress=");
			msg.append(connectorAddress);

			msg.append(", connectorPort=");
			msg.append(connectorPort);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDefinitionException(msg.toString());
		}

		return spiDefinition;
	}

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByCA_CP(
		String connectorAddress, int connectorPort) {

		return fetchByCA_CP(connectorAddress, connectorPort, true);
	}

	/**
	 * Returns the spi definition where connectorAddress = &#63; and connectorPort = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching spi definition, or <code>null</code> if a matching spi definition could not be found
	 */
	@Override
	public SPIDefinition fetchByCA_CP(
		String connectorAddress, int connectorPort, boolean useFinderCache) {

		connectorAddress = Objects.toString(connectorAddress, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {connectorAddress, connectorPort};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByCA_CP, finderArgs, this);
		}

		if (result instanceof SPIDefinition) {
			SPIDefinition spiDefinition = (SPIDefinition)result;

			if (!Objects.equals(
					connectorAddress, spiDefinition.getConnectorAddress()) ||
				(connectorPort != spiDefinition.getConnectorPort())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SPIDEFINITION_WHERE);

			boolean bindConnectorAddress = false;

			if (connectorAddress.isEmpty()) {
				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_3);
			}
			else {
				bindConnectorAddress = true;

				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_2);
			}

			query.append(_FINDER_COLUMN_CA_CP_CONNECTORPORT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConnectorAddress) {
					qPos.add(connectorAddress);
				}

				qPos.add(connectorPort);

				List<SPIDefinition> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByCA_CP, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									connectorAddress, connectorPort
								};
							}

							_log.warn(
								"SPIDefinitionPersistenceImpl.fetchByCA_CP(String, int, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					SPIDefinition spiDefinition = list.get(0);

					result = spiDefinition;

					cacheResult(spiDefinition);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByCA_CP, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SPIDefinition)result;
		}
	}

	/**
	 * Removes the spi definition where connectorAddress = &#63; and connectorPort = &#63; from the database.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the spi definition that was removed
	 */
	@Override
	public SPIDefinition removeByCA_CP(
			String connectorAddress, int connectorPort)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = findByCA_CP(
			connectorAddress, connectorPort);

		return remove(spiDefinition);
	}

	/**
	 * Returns the number of spi definitions where connectorAddress = &#63; and connectorPort = &#63;.
	 *
	 * @param connectorAddress the connector address
	 * @param connectorPort the connector port
	 * @return the number of matching spi definitions
	 */
	@Override
	public int countByCA_CP(String connectorAddress, int connectorPort) {
		connectorAddress = Objects.toString(connectorAddress, "");

		FinderPath finderPath = _finderPathCountByCA_CP;

		Object[] finderArgs = new Object[] {connectorAddress, connectorPort};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SPIDEFINITION_WHERE);

			boolean bindConnectorAddress = false;

			if (connectorAddress.isEmpty()) {
				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_3);
			}
			else {
				bindConnectorAddress = true;

				query.append(_FINDER_COLUMN_CA_CP_CONNECTORADDRESS_2);
			}

			query.append(_FINDER_COLUMN_CA_CP_CONNECTORPORT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConnectorAddress) {
					qPos.add(connectorAddress);
				}

				qPos.add(connectorPort);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CA_CP_CONNECTORADDRESS_2 =
		"spiDefinition.connectorAddress = ? AND ";

	private static final String _FINDER_COLUMN_CA_CP_CONNECTORADDRESS_3 =
		"(spiDefinition.connectorAddress IS NULL OR spiDefinition.connectorAddress = '') AND ";

	private static final String _FINDER_COLUMN_CA_CP_CONNECTORPORT_2 =
		"spiDefinition.connectorPort = ?";

	public SPIDefinitionPersistenceImpl() {
		setModelClass(SPIDefinition.class);

		setModelImplClass(SPIDefinitionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the spi definition in the entity cache if it is enabled.
	 *
	 * @param spiDefinition the spi definition
	 */
	@Override
	public void cacheResult(SPIDefinition spiDefinition) {
		EntityCacheUtil.putResult(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionImpl.class, spiDefinition.getPrimaryKey(),
			spiDefinition);

		FinderCacheUtil.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				spiDefinition.getCompanyId(), spiDefinition.getName()
			},
			spiDefinition);

		FinderCacheUtil.putResult(
			_finderPathFetchByCA_CP,
			new Object[] {
				spiDefinition.getConnectorAddress(),
				spiDefinition.getConnectorPort()
			},
			spiDefinition);

		spiDefinition.resetOriginalValues();
	}

	/**
	 * Caches the spi definitions in the entity cache if it is enabled.
	 *
	 * @param spiDefinitions the spi definitions
	 */
	@Override
	public void cacheResult(List<SPIDefinition> spiDefinitions) {
		for (SPIDefinition spiDefinition : spiDefinitions) {
			if (EntityCacheUtil.getResult(
					SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
					SPIDefinitionImpl.class, spiDefinition.getPrimaryKey()) ==
						null) {

				cacheResult(spiDefinition);
			}
			else {
				spiDefinition.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all spi definitions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(SPIDefinitionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the spi definition.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SPIDefinition spiDefinition) {
		EntityCacheUtil.removeResult(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionImpl.class, spiDefinition.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SPIDefinitionModelImpl)spiDefinition, true);
	}

	@Override
	public void clearCache(List<SPIDefinition> spiDefinitions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SPIDefinition spiDefinition : spiDefinitions) {
			EntityCacheUtil.removeResult(
				SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
				SPIDefinitionImpl.class, spiDefinition.getPrimaryKey());

			clearUniqueFindersCache(
				(SPIDefinitionModelImpl)spiDefinition, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
				SPIDefinitionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SPIDefinitionModelImpl spiDefinitionModelImpl) {

		Object[] args = new Object[] {
			spiDefinitionModelImpl.getCompanyId(),
			spiDefinitionModelImpl.getName()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByC_N, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByC_N, args, spiDefinitionModelImpl, false);

		args = new Object[] {
			spiDefinitionModelImpl.getConnectorAddress(),
			spiDefinitionModelImpl.getConnectorPort()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByCA_CP, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByCA_CP, args, spiDefinitionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SPIDefinitionModelImpl spiDefinitionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				spiDefinitionModelImpl.getCompanyId(),
				spiDefinitionModelImpl.getName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_N, args);
		}

		if ((spiDefinitionModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				spiDefinitionModelImpl.getOriginalCompanyId(),
				spiDefinitionModelImpl.getOriginalName()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_N, args);
			FinderCacheUtil.removeResult(_finderPathFetchByC_N, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				spiDefinitionModelImpl.getConnectorAddress(),
				spiDefinitionModelImpl.getConnectorPort()
			};

			FinderCacheUtil.removeResult(_finderPathCountByCA_CP, args);
			FinderCacheUtil.removeResult(_finderPathFetchByCA_CP, args);
		}

		if ((spiDefinitionModelImpl.getColumnBitmask() &
			 _finderPathFetchByCA_CP.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				spiDefinitionModelImpl.getOriginalConnectorAddress(),
				spiDefinitionModelImpl.getOriginalConnectorPort()
			};

			FinderCacheUtil.removeResult(_finderPathCountByCA_CP, args);
			FinderCacheUtil.removeResult(_finderPathFetchByCA_CP, args);
		}
	}

	/**
	 * Creates a new spi definition with the primary key. Does not add the spi definition to the database.
	 *
	 * @param spiDefinitionId the primary key for the new spi definition
	 * @return the new spi definition
	 */
	@Override
	public SPIDefinition create(long spiDefinitionId) {
		SPIDefinition spiDefinition = new SPIDefinitionImpl();

		spiDefinition.setNew(true);
		spiDefinition.setPrimaryKey(spiDefinitionId);

		spiDefinition.setCompanyId(CompanyThreadLocal.getCompanyId());

		return spiDefinition;
	}

	/**
	 * Removes the spi definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition that was removed
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition remove(long spiDefinitionId)
		throws NoSuchDefinitionException {

		return remove((Serializable)spiDefinitionId);
	}

	/**
	 * Removes the spi definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the spi definition
	 * @return the spi definition that was removed
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition remove(Serializable primaryKey)
		throws NoSuchDefinitionException {

		Session session = null;

		try {
			session = openSession();

			SPIDefinition spiDefinition = (SPIDefinition)session.get(
				SPIDefinitionImpl.class, primaryKey);

			if (spiDefinition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDefinitionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(spiDefinition);
		}
		catch (NoSuchDefinitionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected SPIDefinition removeImpl(SPIDefinition spiDefinition) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(spiDefinition)) {
				spiDefinition = (SPIDefinition)session.get(
					SPIDefinitionImpl.class, spiDefinition.getPrimaryKeyObj());
			}

			if (spiDefinition != null) {
				session.delete(spiDefinition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (spiDefinition != null) {
			clearCache(spiDefinition);
		}

		return spiDefinition;
	}

	@Override
	public SPIDefinition updateImpl(SPIDefinition spiDefinition) {
		boolean isNew = spiDefinition.isNew();

		if (!(spiDefinition instanceof SPIDefinitionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(spiDefinition.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					spiDefinition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in spiDefinition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SPIDefinition implementation " +
					spiDefinition.getClass());
		}

		SPIDefinitionModelImpl spiDefinitionModelImpl =
			(SPIDefinitionModelImpl)spiDefinition;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (spiDefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				spiDefinition.setCreateDate(now);
			}
			else {
				spiDefinition.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!spiDefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				spiDefinition.setModifiedDate(now);
			}
			else {
				spiDefinition.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (spiDefinition.isNew()) {
				session.save(spiDefinition);

				spiDefinition.setNew(false);
			}
			else {
				spiDefinition = (SPIDefinition)session.merge(spiDefinition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!SPIDefinitionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				spiDefinitionModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				spiDefinitionModelImpl.getCompanyId(),
				spiDefinitionModelImpl.getStatus()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_S, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((spiDefinitionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					spiDefinitionModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {spiDefinitionModelImpl.getCompanyId()};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((spiDefinitionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					spiDefinitionModelImpl.getOriginalCompanyId(),
					spiDefinitionModelImpl.getOriginalStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_S, args);

				args = new Object[] {
					spiDefinitionModelImpl.getCompanyId(),
					spiDefinitionModelImpl.getStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_S, args);
			}
		}

		EntityCacheUtil.putResult(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionImpl.class, spiDefinition.getPrimaryKey(),
			spiDefinition, false);

		clearUniqueFindersCache(spiDefinitionModelImpl, false);
		cacheUniqueFindersCache(spiDefinitionModelImpl);

		spiDefinition.resetOriginalValues();

		return spiDefinition;
	}

	/**
	 * Returns the spi definition with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the spi definition
	 * @return the spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDefinitionException {

		SPIDefinition spiDefinition = fetchByPrimaryKey(primaryKey);

		if (spiDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDefinitionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return spiDefinition;
	}

	/**
	 * Returns the spi definition with the primary key or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition
	 * @throws NoSuchDefinitionException if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition findByPrimaryKey(long spiDefinitionId)
		throws NoSuchDefinitionException {

		return findByPrimaryKey((Serializable)spiDefinitionId);
	}

	/**
	 * Returns the spi definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param spiDefinitionId the primary key of the spi definition
	 * @return the spi definition, or <code>null</code> if a spi definition with the primary key could not be found
	 */
	@Override
	public SPIDefinition fetchByPrimaryKey(long spiDefinitionId) {
		return fetchByPrimaryKey((Serializable)spiDefinitionId);
	}

	/**
	 * Returns all the spi definitions.
	 *
	 * @return the spi definitions
	 */
	@Override
	public List<SPIDefinition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @return the range of spi definitions
	 */
	@Override
	public List<SPIDefinition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of spi definitions
	 */
	@Override
	public List<SPIDefinition> findAll(
		int start, int end,
		OrderByComparator<SPIDefinition> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the spi definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SPIDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of spi definitions
	 * @param end the upper bound of the range of spi definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of spi definitions
	 */
	@Override
	public List<SPIDefinition> findAll(
		int start, int end, OrderByComparator<SPIDefinition> orderByComparator,
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

		List<SPIDefinition> list = null;

		if (useFinderCache) {
			list = (List<SPIDefinition>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SPIDEFINITION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SPIDEFINITION;

				sql = sql.concat(SPIDefinitionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<SPIDefinition>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the spi definitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SPIDefinition spiDefinition : findAll()) {
			remove(spiDefinition);
		}
	}

	/**
	 * Returns the number of spi definitions.
	 *
	 * @return the number of spi definitions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SPIDEFINITION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "spiDefinitionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SPIDEFINITION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SPIDefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the spi definition persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			SPIDefinitionModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathFetchByC_N = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			SPIDefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			SPIDefinitionModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_N = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByC_S = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			SPIDefinitionModelImpl.COMPANYID_COLUMN_BITMASK |
			SPIDefinitionModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByC_S = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationCountByC_S = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathFetchByCA_CP = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED,
			SPIDefinitionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByCA_CP",
			new String[] {String.class.getName(), Integer.class.getName()},
			SPIDefinitionModelImpl.CONNECTORADDRESS_COLUMN_BITMASK |
			SPIDefinitionModelImpl.CONNECTORPORT_COLUMN_BITMASK);

		_finderPathCountByCA_CP = new FinderPath(
			SPIDefinitionModelImpl.ENTITY_CACHE_ENABLED,
			SPIDefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCA_CP",
			new String[] {String.class.getName(), Integer.class.getName()});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SPIDefinitionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SPIDEFINITION =
		"SELECT spiDefinition FROM SPIDefinition spiDefinition";

	private static final String _SQL_SELECT_SPIDEFINITION_WHERE =
		"SELECT spiDefinition FROM SPIDefinition spiDefinition WHERE ";

	private static final String _SQL_COUNT_SPIDEFINITION =
		"SELECT COUNT(spiDefinition) FROM SPIDefinition spiDefinition";

	private static final String _SQL_COUNT_SPIDEFINITION_WHERE =
		"SELECT COUNT(spiDefinition) FROM SPIDefinition spiDefinition WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"spiDefinition.spiDefinitionId";

	private static final String _FILTER_SQL_SELECT_SPIDEFINITION_WHERE =
		"SELECT DISTINCT {spiDefinition.*} FROM SPIDefinition spiDefinition WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {SPIDefinition.*} FROM (SELECT DISTINCT spiDefinition.spiDefinitionId FROM SPIDefinition spiDefinition WHERE ";

	private static final String
		_FILTER_SQL_SELECT_SPIDEFINITION_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN SPIDefinition ON TEMP_TABLE.spiDefinitionId = SPIDefinition.spiDefinitionId";

	private static final String _FILTER_SQL_COUNT_SPIDEFINITION_WHERE =
		"SELECT COUNT(DISTINCT spiDefinition.spiDefinitionId) AS COUNT_VALUE FROM SPIDefinition spiDefinition WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "spiDefinition";

	private static final String _FILTER_ENTITY_TABLE = "SPIDefinition";

	private static final String _ORDER_BY_ENTITY_ALIAS = "spiDefinition.";

	private static final String _ORDER_BY_ENTITY_TABLE = "SPIDefinition.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SPIDefinition exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SPIDefinition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SPIDefinitionPersistenceImpl.class);

}