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

package com.liferay.commerce.data.integration.service.persistence.impl;

import com.liferay.commerce.data.integration.exception.NoSuchDataIntegrationProcessException;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessImpl;
import com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessModelImpl;
import com.liferay.commerce.data.integration.service.persistence.CommerceDataIntegrationProcessPersistence;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
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
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce data integration process service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceDataIntegrationProcessPersistenceImpl
	extends BasePersistenceImpl<CommerceDataIntegrationProcess>
	implements CommerceDataIntegrationProcessPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceDataIntegrationProcessUtil</code> to access the commerce data integration process persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceDataIntegrationProcessImpl.class.getName();

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
	 * Returns all the commerce data integration processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId) {

		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce data integration processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
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

		List<CommerceDataIntegrationProcess> list = null;

		if (useFinderCache) {
			list = (List<CommerceDataIntegrationProcess>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDataIntegrationProcess
						commerceDataIntegrationProcess : list) {

					if (companyId !=
							commerceDataIntegrationProcess.getCompanyId()) {

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

			sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceDataIntegrationProcessModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceDataIntegrationProcess>)QueryUtil.list(
					query, getDialect(), start, end);

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
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (commerceDataIntegrationProcess != null) {
			return commerceDataIntegrationProcess;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDataIntegrationProcessException(sb.toString());
	}

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		List<CommerceDataIntegrationProcess> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (commerceDataIntegrationProcess != null) {
			return commerceDataIntegrationProcess;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDataIntegrationProcessException(sb.toString());
	}

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceDataIntegrationProcess> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set where companyId = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess[] findByCompanyId_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			findByPrimaryKey(commerceDataIntegrationProcessId);

		Session session = null;

		try {
			session = openSession();

			CommerceDataIntegrationProcess[] array =
				new CommerceDataIntegrationProcessImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceDataIntegrationProcess, companyId,
				orderByComparator, true);

			array[1] = commerceDataIntegrationProcess;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceDataIntegrationProcess, companyId,
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

	protected CommerceDataIntegrationProcess getByCompanyId_PrevAndNext(
		Session session,
		CommerceDataIntegrationProcess commerceDataIntegrationProcess,
		long companyId,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

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
			sb.append(CommerceDataIntegrationProcessModelImpl.ORDER_BY_JPQL);
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
						commerceDataIntegrationProcess)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDataIntegrationProcess> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce data integration processes that the user has permission to view
	 */
	@Override
	public List<CommerceDataIntegrationProcess> filterFindByCompanyId(
		long companyId) {

		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes that the user has permission to view
	 */
	@Override
	public List<CommerceDataIntegrationProcess> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes that the user has permission to view
	 */
	@Override
	public List<CommerceDataIntegrationProcess> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					CommerceDataIntegrationProcessModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceDataIntegrationProcessModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDataIntegrationProcess.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS,
					CommerceDataIntegrationProcessImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE,
					CommerceDataIntegrationProcessImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<CommerceDataIntegrationProcess>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set of commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess[] filterFindByCompanyId_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				commerceDataIntegrationProcessId, companyId, orderByComparator);
		}

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			findByPrimaryKey(commerceDataIntegrationProcessId);

		Session session = null;

		try {
			session = openSession();

			CommerceDataIntegrationProcess[] array =
				new CommerceDataIntegrationProcessImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, commerceDataIntegrationProcess, companyId,
				orderByComparator, true);

			array[1] = commerceDataIntegrationProcess;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, commerceDataIntegrationProcess, companyId,
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

	protected CommerceDataIntegrationProcess filterGetByCompanyId_PrevAndNext(
		Session session,
		CommerceDataIntegrationProcess commerceDataIntegrationProcess,
		long companyId,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					CommerceDataIntegrationProcessModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceDataIntegrationProcessModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDataIntegrationProcess.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceDataIntegrationProcessImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceDataIntegrationProcessImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDataIntegrationProcess)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDataIntegrationProcess> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce data integration processes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceDataIntegrationProcess commerceDataIntegrationProcess :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceDataIntegrationProcess);
		}
	}

	/**
	 * Returns the number of commerce data integration processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce data integration processes
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

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
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of commerce data integration processes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce data integration processes that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDataIntegrationProcess.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"commerceDataIntegrationProcess.companyId = ?";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the commerce data integration process where companyId = &#63; and name = &#63; or throws a <code>NoSuchDataIntegrationProcessException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess findByC_N(long companyId, String name)
		throws NoSuchDataIntegrationProcessException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			fetchByC_N(companyId, name);

		if (commerceDataIntegrationProcess == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDataIntegrationProcessException(sb.toString());
		}

		return commerceDataIntegrationProcess;
	}

	/**
	 * Returns the commerce data integration process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess fetchByC_N(
		long companyId, String name) {

		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the commerce data integration process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_N, finderArgs, this);
		}

		if (result instanceof CommerceDataIntegrationProcess) {
			CommerceDataIntegrationProcess commerceDataIntegrationProcess =
				(CommerceDataIntegrationProcess)result;

			if ((companyId != commerceDataIntegrationProcess.getCompanyId()) ||
				!Objects.equals(
					name, commerceDataIntegrationProcess.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				List<CommerceDataIntegrationProcess> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					CommerceDataIntegrationProcess
						commerceDataIntegrationProcess = list.get(0);

					result = commerceDataIntegrationProcess;

					cacheResult(commerceDataIntegrationProcess);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_N, finderArgs);
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
			return (CommerceDataIntegrationProcess)result;
		}
	}

	/**
	 * Removes the commerce data integration process where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the commerce data integration process that was removed
	 */
	@Override
	public CommerceDataIntegrationProcess removeByC_N(
			long companyId, String name)
		throws NoSuchDataIntegrationProcessException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			findByC_N(companyId, name);

		return remove(commerceDataIntegrationProcess);
	}

	/**
	 * Returns the number of commerce data integration processes where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching commerce data integration processes
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"commerceDataIntegrationProcess.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"commerceDataIntegrationProcess.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(commerceDataIntegrationProcess.name IS NULL OR commerceDataIntegrationProcess.name = '')";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithoutPaginationFindByC_T;
	private FinderPath _finderPathCountByC_T;

	/**
	 * Returns all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type) {

		return findByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type, int start, int end) {

		return findByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return findByC_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_T;
				finderArgs = new Object[] {companyId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_T;
			finderArgs = new Object[] {
				companyId, type, start, end, orderByComparator
			};
		}

		List<CommerceDataIntegrationProcess> list = null;

		if (useFinderCache) {
			list = (List<CommerceDataIntegrationProcess>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDataIntegrationProcess
						commerceDataIntegrationProcess : list) {

					if ((companyId !=
							commerceDataIntegrationProcess.getCompanyId()) ||
						!type.equals(
							commerceDataIntegrationProcess.getType())) {

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

			sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceDataIntegrationProcessModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceDataIntegrationProcess>)QueryUtil.list(
					query, getDialect(), start, end);

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
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess findByC_T_First(
			long companyId, String type,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			fetchByC_T_First(companyId, type, orderByComparator);

		if (commerceDataIntegrationProcess != null) {
			return commerceDataIntegrationProcess;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchDataIntegrationProcessException(sb.toString());
	}

	/**
	 * Returns the first commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess fetchByC_T_First(
		long companyId, String type,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		List<CommerceDataIntegrationProcess> list = findByC_T(
			companyId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess findByC_T_Last(
			long companyId, String type,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			fetchByC_T_Last(companyId, type, orderByComparator);

		if (commerceDataIntegrationProcess != null) {
			return commerceDataIntegrationProcess;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchDataIntegrationProcessException(sb.toString());
	}

	/**
	 * Returns the last commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process, or <code>null</code> if a matching commerce data integration process could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess fetchByC_T_Last(
		long companyId, String type,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		int count = countByC_T(companyId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceDataIntegrationProcess> list = findByC_T(
			companyId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess[] findByC_T_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId, String type,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException {

		type = Objects.toString(type, "");

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			findByPrimaryKey(commerceDataIntegrationProcessId);

		Session session = null;

		try {
			session = openSession();

			CommerceDataIntegrationProcess[] array =
				new CommerceDataIntegrationProcessImpl[3];

			array[0] = getByC_T_PrevAndNext(
				session, commerceDataIntegrationProcess, companyId, type,
				orderByComparator, true);

			array[1] = commerceDataIntegrationProcess;

			array[2] = getByC_T_PrevAndNext(
				session, commerceDataIntegrationProcess, companyId, type,
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

	protected CommerceDataIntegrationProcess getByC_T_PrevAndNext(
		Session session,
		CommerceDataIntegrationProcess commerceDataIntegrationProcess,
		long companyId, String type,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2);
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
			sb.append(CommerceDataIntegrationProcessModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(
						commerceDataIntegrationProcess)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDataIntegrationProcess> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce data integration processes that the user has permission to view
	 */
	@Override
	public List<CommerceDataIntegrationProcess> filterFindByC_T(
		long companyId, String type) {

		return filterFindByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of matching commerce data integration processes that the user has permission to view
	 */
	@Override
	public List<CommerceDataIntegrationProcess> filterFindByC_T(
		long companyId, String type, int start, int end) {

		return filterFindByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes that the user has permissions to view where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration processes that the user has permission to view
	 */
	@Override
	public List<CommerceDataIntegrationProcess> filterFindByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T(companyId, type, start, end, orderByComparator);
		}

		type = Objects.toString(type, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					CommerceDataIntegrationProcessModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceDataIntegrationProcessModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDataIntegrationProcess.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS,
					CommerceDataIntegrationProcessImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE,
					CommerceDataIntegrationProcessImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindType) {
				queryPos.add(type);
			}

			return (List<CommerceDataIntegrationProcess>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the commerce data integration processes before and after the current commerce data integration process in the ordered set of commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the current commerce data integration process
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess[] filterFindByC_T_PrevAndNext(
			long commerceDataIntegrationProcessId, long companyId, String type,
			OrderByComparator<CommerceDataIntegrationProcess> orderByComparator)
		throws NoSuchDataIntegrationProcessException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_T_PrevAndNext(
				commerceDataIntegrationProcessId, companyId, type,
				orderByComparator);
		}

		type = Objects.toString(type, "");

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			findByPrimaryKey(commerceDataIntegrationProcessId);

		Session session = null;

		try {
			session = openSession();

			CommerceDataIntegrationProcess[] array =
				new CommerceDataIntegrationProcessImpl[3];

			array[0] = filterGetByC_T_PrevAndNext(
				session, commerceDataIntegrationProcess, companyId, type,
				orderByComparator, true);

			array[1] = commerceDataIntegrationProcess;

			array[2] = filterGetByC_T_PrevAndNext(
				session, commerceDataIntegrationProcess, companyId, type,
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

	protected CommerceDataIntegrationProcess filterGetByC_T_PrevAndNext(
		Session session,
		CommerceDataIntegrationProcess commerceDataIntegrationProcess,
		long companyId, String type,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(
					CommerceDataIntegrationProcessModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(CommerceDataIntegrationProcessModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDataIntegrationProcess.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, CommerceDataIntegrationProcessImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, CommerceDataIntegrationProcessImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDataIntegrationProcess)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDataIntegrationProcess> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce data integration processes where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_T(long companyId, String type) {
		for (CommerceDataIntegrationProcess commerceDataIntegrationProcess :
				findByC_T(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceDataIntegrationProcess);
		}
	}

	/**
	 * Returns the number of commerce data integration processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce data integration processes
	 */
	@Override
	public int countByC_T(long companyId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByC_T;

		Object[] finderArgs = new Object[] {companyId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

			sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_T_TYPE_2);
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
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of commerce data integration processes that the user has permission to view where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce data integration processes that the user has permission to view
	 */
	@Override
	public int filterCountByC_T(long companyId, String type) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_T(companyId, type);
		}

		type = Objects.toString(type, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESS_WHERE);

		sb.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_T_TYPE_3_SQL);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_T_TYPE_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), CommerceDataIntegrationProcess.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			if (bindType) {
				queryPos.add(type);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 =
		"commerceDataIntegrationProcess.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TYPE_2 =
		"commerceDataIntegrationProcess.type = ?";

	private static final String _FINDER_COLUMN_C_T_TYPE_3 =
		"(commerceDataIntegrationProcess.type IS NULL OR commerceDataIntegrationProcess.type = '')";

	private static final String _FINDER_COLUMN_C_T_TYPE_2_SQL =
		"commerceDataIntegrationProcess.type_ = ?";

	private static final String _FINDER_COLUMN_C_T_TYPE_3_SQL =
		"(commerceDataIntegrationProcess.type_ IS NULL OR commerceDataIntegrationProcess.type_ = '')";

	public CommerceDataIntegrationProcessPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceDataIntegrationProcessId", "CDataIntegrationProcessId");
		dbColumnNames.put("type", "type_");
		dbColumnNames.put("system", "system_");
		dbColumnNames.put("active", "active_");

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

		setModelClass(CommerceDataIntegrationProcess.class);
	}

	/**
	 * Caches the commerce data integration process in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcess the commerce data integration process
	 */
	@Override
	public void cacheResult(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		entityCache.putResult(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class,
			commerceDataIntegrationProcess.getPrimaryKey(),
			commerceDataIntegrationProcess);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				commerceDataIntegrationProcess.getCompanyId(),
				commerceDataIntegrationProcess.getName()
			},
			commerceDataIntegrationProcess);

		commerceDataIntegrationProcess.resetOriginalValues();
	}

	/**
	 * Caches the commerce data integration processes in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcesses the commerce data integration processes
	 */
	@Override
	public void cacheResult(
		List<CommerceDataIntegrationProcess> commerceDataIntegrationProcesses) {

		for (CommerceDataIntegrationProcess commerceDataIntegrationProcess :
				commerceDataIntegrationProcesses) {

			if (entityCache.getResult(
					CommerceDataIntegrationProcessModelImpl.
						ENTITY_CACHE_ENABLED,
					CommerceDataIntegrationProcessImpl.class,
					commerceDataIntegrationProcess.getPrimaryKey()) == null) {

				cacheResult(commerceDataIntegrationProcess);
			}
			else {
				commerceDataIntegrationProcess.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce data integration processes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDataIntegrationProcessImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce data integration process.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		entityCache.removeResult(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class,
			commerceDataIntegrationProcess.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(CommerceDataIntegrationProcessModelImpl)
				commerceDataIntegrationProcess,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceDataIntegrationProcess> commerceDataIntegrationProcesses) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceDataIntegrationProcess commerceDataIntegrationProcess :
				commerceDataIntegrationProcesses) {

			entityCache.removeResult(
				CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDataIntegrationProcessImpl.class,
				commerceDataIntegrationProcess.getPrimaryKey());

			clearUniqueFindersCache(
				(CommerceDataIntegrationProcessModelImpl)
					commerceDataIntegrationProcess,
				true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDataIntegrationProcessImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceDataIntegrationProcessModelImpl
			commerceDataIntegrationProcessModelImpl) {

		Object[] args = new Object[] {
			commerceDataIntegrationProcessModelImpl.getCompanyId(),
			commerceDataIntegrationProcessModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByC_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_N, args,
			commerceDataIntegrationProcessModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceDataIntegrationProcessModelImpl
			commerceDataIntegrationProcessModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				commerceDataIntegrationProcessModelImpl.getCompanyId(),
				commerceDataIntegrationProcessModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}

		if ((commerceDataIntegrationProcessModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				commerceDataIntegrationProcessModelImpl.getOriginalCompanyId(),
				commerceDataIntegrationProcessModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}
	}

	/**
	 * Creates a new commerce data integration process with the primary key. Does not add the commerce data integration process to the database.
	 *
	 * @param commerceDataIntegrationProcessId the primary key for the new commerce data integration process
	 * @return the new commerce data integration process
	 */
	@Override
	public CommerceDataIntegrationProcess create(
		long commerceDataIntegrationProcessId) {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			new CommerceDataIntegrationProcessImpl();

		commerceDataIntegrationProcess.setNew(true);
		commerceDataIntegrationProcess.setPrimaryKey(
			commerceDataIntegrationProcessId);

		commerceDataIntegrationProcess.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceDataIntegrationProcess;
	}

	/**
	 * Removes the commerce data integration process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process that was removed
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess remove(
			long commerceDataIntegrationProcessId)
		throws NoSuchDataIntegrationProcessException {

		return remove((Serializable)commerceDataIntegrationProcessId);
	}

	/**
	 * Removes the commerce data integration process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce data integration process
	 * @return the commerce data integration process that was removed
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess remove(Serializable primaryKey)
		throws NoSuchDataIntegrationProcessException {

		Session session = null;

		try {
			session = openSession();

			CommerceDataIntegrationProcess commerceDataIntegrationProcess =
				(CommerceDataIntegrationProcess)session.get(
					CommerceDataIntegrationProcessImpl.class, primaryKey);

			if (commerceDataIntegrationProcess == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDataIntegrationProcessException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceDataIntegrationProcess);
		}
		catch (NoSuchDataIntegrationProcessException noSuchEntityException) {
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
	protected CommerceDataIntegrationProcess removeImpl(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDataIntegrationProcess)) {
				commerceDataIntegrationProcess =
					(CommerceDataIntegrationProcess)session.get(
						CommerceDataIntegrationProcessImpl.class,
						commerceDataIntegrationProcess.getPrimaryKeyObj());
			}

			if (commerceDataIntegrationProcess != null) {
				session.delete(commerceDataIntegrationProcess);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceDataIntegrationProcess != null) {
			clearCache(commerceDataIntegrationProcess);
		}

		return commerceDataIntegrationProcess;
	}

	@Override
	public CommerceDataIntegrationProcess updateImpl(
		CommerceDataIntegrationProcess commerceDataIntegrationProcess) {

		boolean isNew = commerceDataIntegrationProcess.isNew();

		if (!(commerceDataIntegrationProcess instanceof
				CommerceDataIntegrationProcessModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceDataIntegrationProcess.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceDataIntegrationProcess);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDataIntegrationProcess proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDataIntegrationProcess implementation " +
					commerceDataIntegrationProcess.getClass());
		}

		CommerceDataIntegrationProcessModelImpl
			commerceDataIntegrationProcessModelImpl =
				(CommerceDataIntegrationProcessModelImpl)
					commerceDataIntegrationProcess;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceDataIntegrationProcess.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDataIntegrationProcess.setCreateDate(now);
			}
			else {
				commerceDataIntegrationProcess.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceDataIntegrationProcessModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDataIntegrationProcess.setModifiedDate(now);
			}
			else {
				commerceDataIntegrationProcess.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceDataIntegrationProcess.isNew()) {
				session.save(commerceDataIntegrationProcess);

				commerceDataIntegrationProcess.setNew(false);
			}
			else {
				commerceDataIntegrationProcess =
					(CommerceDataIntegrationProcess)session.merge(
						commerceDataIntegrationProcess);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceDataIntegrationProcessModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				commerceDataIntegrationProcessModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				commerceDataIntegrationProcessModelImpl.getCompanyId(),
				commerceDataIntegrationProcessModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByC_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_T, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((commerceDataIntegrationProcessModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceDataIntegrationProcessModelImpl.
						getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {
					commerceDataIntegrationProcessModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((commerceDataIntegrationProcessModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					commerceDataIntegrationProcessModelImpl.
						getOriginalCompanyId(),
					commerceDataIntegrationProcessModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByC_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_T, args);

				args = new Object[] {
					commerceDataIntegrationProcessModelImpl.getCompanyId(),
					commerceDataIntegrationProcessModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByC_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_T, args);
			}
		}

		entityCache.putResult(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class,
			commerceDataIntegrationProcess.getPrimaryKey(),
			commerceDataIntegrationProcess, false);

		clearUniqueFindersCache(commerceDataIntegrationProcessModelImpl, false);
		cacheUniqueFindersCache(commerceDataIntegrationProcessModelImpl);

		commerceDataIntegrationProcess.resetOriginalValues();

		return commerceDataIntegrationProcess;
	}

	/**
	 * Returns the commerce data integration process with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce data integration process
	 * @return the commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchDataIntegrationProcessException {

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			fetchByPrimaryKey(primaryKey);

		if (commerceDataIntegrationProcess == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDataIntegrationProcessException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceDataIntegrationProcess;
	}

	/**
	 * Returns the commerce data integration process with the primary key or throws a <code>NoSuchDataIntegrationProcessException</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process
	 * @throws NoSuchDataIntegrationProcessException if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess findByPrimaryKey(
			long commerceDataIntegrationProcessId)
		throws NoSuchDataIntegrationProcessException {

		return findByPrimaryKey((Serializable)commerceDataIntegrationProcessId);
	}

	/**
	 * Returns the commerce data integration process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce data integration process
	 * @return the commerce data integration process, or <code>null</code> if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess fetchByPrimaryKey(
		Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceDataIntegrationProcess commerceDataIntegrationProcess =
			(CommerceDataIntegrationProcess)serializable;

		if (commerceDataIntegrationProcess == null) {
			Session session = null;

			try {
				session = openSession();

				commerceDataIntegrationProcess =
					(CommerceDataIntegrationProcess)session.get(
						CommerceDataIntegrationProcessImpl.class, primaryKey);

				if (commerceDataIntegrationProcess != null) {
					cacheResult(commerceDataIntegrationProcess);
				}
				else {
					entityCache.putResult(
						CommerceDataIntegrationProcessModelImpl.
							ENTITY_CACHE_ENABLED,
						CommerceDataIntegrationProcessImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					CommerceDataIntegrationProcessModelImpl.
						ENTITY_CACHE_ENABLED,
					CommerceDataIntegrationProcessImpl.class, primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceDataIntegrationProcess;
	}

	/**
	 * Returns the commerce data integration process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessId the primary key of the commerce data integration process
	 * @return the commerce data integration process, or <code>null</code> if a commerce data integration process with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcess fetchByPrimaryKey(
		long commerceDataIntegrationProcessId) {

		return fetchByPrimaryKey(
			(Serializable)commerceDataIntegrationProcessId);
	}

	@Override
	public Map<Serializable, CommerceDataIntegrationProcess> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceDataIntegrationProcess> map =
			new HashMap<Serializable, CommerceDataIntegrationProcess>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceDataIntegrationProcess commerceDataIntegrationProcess =
				fetchByPrimaryKey(primaryKey);

			if (commerceDataIntegrationProcess != null) {
				map.put(primaryKey, commerceDataIntegrationProcess);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDataIntegrationProcessImpl.class, primaryKey);

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
						(CommerceDataIntegrationProcess)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE_PKS_IN);

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

			for (CommerceDataIntegrationProcess commerceDataIntegrationProcess :
					(List<CommerceDataIntegrationProcess>)query.list()) {

				map.put(
					commerceDataIntegrationProcess.getPrimaryKeyObj(),
					commerceDataIntegrationProcess);

				cacheResult(commerceDataIntegrationProcess);

				uncachedPrimaryKeys.remove(
					commerceDataIntegrationProcess.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					CommerceDataIntegrationProcessModelImpl.
						ENTITY_CACHE_ENABLED,
					CommerceDataIntegrationProcessImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the commerce data integration processes.
	 *
	 * @return the commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @return the range of commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findAll(
		int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce data integration processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration processes
	 * @param end the upper bound of the range of commerce data integration processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce data integration processes
	 */
	@Override
	public List<CommerceDataIntegrationProcess> findAll(
		int start, int end,
		OrderByComparator<CommerceDataIntegrationProcess> orderByComparator,
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

		List<CommerceDataIntegrationProcess> list = null;

		if (useFinderCache) {
			list = (List<CommerceDataIntegrationProcess>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS;

				sql = sql.concat(
					CommerceDataIntegrationProcessModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceDataIntegrationProcess>)QueryUtil.list(
					query, getDialect(), start, end);

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
	 * Removes all the commerce data integration processes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDataIntegrationProcess commerceDataIntegrationProcess :
				findAll()) {

			remove(commerceDataIntegrationProcess);
		}
	}

	/**
	 * Returns the number of commerce data integration processes.
	 *
	 * @return the number of commerce data integration processes
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
					_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESS);

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
		return CommerceDataIntegrationProcessModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce data integration process persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			CommerceDataIntegrationProcessModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceDataIntegrationProcessModelImpl.
				MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] {Long.class.getName()});

		_finderPathFetchByC_N = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			CommerceDataIntegrationProcessModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceDataIntegrationProcessModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_N = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByC_T = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_T = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			CommerceDataIntegrationProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T",
			new String[] {Long.class.getName(), String.class.getName()},
			CommerceDataIntegrationProcessModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceDataIntegrationProcessModelImpl.TYPE_COLUMN_BITMASK |
			CommerceDataIntegrationProcessModelImpl.
				MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByC_T = new FinderPath(
			CommerceDataIntegrationProcessModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDataIntegrationProcessModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceDataIntegrationProcessImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS =
		"SELECT commerceDataIntegrationProcess FROM CommerceDataIntegrationProcess commerceDataIntegrationProcess";

	private static final String
		_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE_PKS_IN =
			"SELECT commerceDataIntegrationProcess FROM CommerceDataIntegrationProcess commerceDataIntegrationProcess WHERE CDataIntegrationProcessId IN (";

	private static final String
		_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE =
			"SELECT commerceDataIntegrationProcess FROM CommerceDataIntegrationProcess commerceDataIntegrationProcess WHERE ";

	private static final String _SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESS =
		"SELECT COUNT(commerceDataIntegrationProcess) FROM CommerceDataIntegrationProcess commerceDataIntegrationProcess";

	private static final String
		_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESS_WHERE =
			"SELECT COUNT(commerceDataIntegrationProcess) FROM CommerceDataIntegrationProcess commerceDataIntegrationProcess WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"commerceDataIntegrationProcess.CDataIntegrationProcessId";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_WHERE =
			"SELECT DISTINCT {commerceDataIntegrationProcess.*} FROM CDataIntegrationProcess commerceDataIntegrationProcess WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {CDataIntegrationProcess.*} FROM (SELECT DISTINCT commerceDataIntegrationProcess.CDataIntegrationProcessId FROM CDataIntegrationProcess commerceDataIntegrationProcess WHERE ";

	private static final String
		_FILTER_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESS_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN CDataIntegrationProcess ON TEMP_TABLE.CDataIntegrationProcessId = CDataIntegrationProcess.CDataIntegrationProcessId";

	private static final String
		_FILTER_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESS_WHERE =
			"SELECT COUNT(DISTINCT commerceDataIntegrationProcess.CDataIntegrationProcessId) AS COUNT_VALUE FROM CDataIntegrationProcess commerceDataIntegrationProcess WHERE ";

	private static final String _FILTER_ENTITY_ALIAS =
		"commerceDataIntegrationProcess";

	private static final String _FILTER_ENTITY_TABLE =
		"CDataIntegrationProcess";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceDataIntegrationProcess.";

	private static final String _ORDER_BY_ENTITY_TABLE =
		"CDataIntegrationProcess.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceDataIntegrationProcess exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceDataIntegrationProcess exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDataIntegrationProcessPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"commerceDataIntegrationProcessId", "type", "system", "active"
		});

}