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

import com.liferay.commerce.data.integration.exception.NoSuchDataIntegrationProcessLogException;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLog;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcessLogTable;
import com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessLogImpl;
import com.liferay.commerce.data.integration.model.impl.CommerceDataIntegrationProcessLogModelImpl;
import com.liferay.commerce.data.integration.service.persistence.CommerceDataIntegrationProcessLogPersistence;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce data integration process log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceDataIntegrationProcessLogPersistenceImpl
	extends BasePersistenceImpl<CommerceDataIntegrationProcessLog>
	implements CommerceDataIntegrationProcessLogPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceDataIntegrationProcessLogUtil</code> to access the commerce data integration process log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceDataIntegrationProcessLogImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCDataIntegrationProcessId;
	private FinderPath
		_finderPathWithoutPaginationFindByCDataIntegrationProcessId;
	private FinderPath _finderPathCountByCDataIntegrationProcessId;

	/**
	 * Returns all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @return the matching commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(long CDataIntegrationProcessId) {

		return findByCDataIntegrationProcessId(
			CDataIntegrationProcessId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @return the range of matching commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(
			long CDataIntegrationProcessId, int start, int end) {

		return findByCDataIntegrationProcessId(
			CDataIntegrationProcessId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(
			long CDataIntegrationProcessId, int start, int end,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator) {

		return findByCDataIntegrationProcessId(
			CDataIntegrationProcessId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog>
		findByCDataIntegrationProcessId(
			long CDataIntegrationProcessId, int start, int end,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCDataIntegrationProcessId;
				finderArgs = new Object[] {CDataIntegrationProcessId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCDataIntegrationProcessId;
			finderArgs = new Object[] {
				CDataIntegrationProcessId, start, end, orderByComparator
			};
		}

		List<CommerceDataIntegrationProcessLog> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceDataIntegrationProcessLog>)finderCache.getResult(
					finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDataIntegrationProcessLog
						commerceDataIntegrationProcessLog : list) {

					if (CDataIntegrationProcessId !=
							commerceDataIntegrationProcessLog.
								getCDataIntegrationProcessId()) {

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

			sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESSLOG_WHERE);

			sb.append(
				_FINDER_COLUMN_CDATAINTEGRATIONPROCESSID_CDATAINTEGRATIONPROCESSID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceDataIntegrationProcessLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CDataIntegrationProcessId);

				list = (List<CommerceDataIntegrationProcessLog>)QueryUtil.list(
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
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog
			findByCDataIntegrationProcessId_First(
				long CDataIntegrationProcessId,
				OrderByComparator<CommerceDataIntegrationProcessLog>
					orderByComparator)
		throws NoSuchDataIntegrationProcessLogException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			fetchByCDataIntegrationProcessId_First(
				CDataIntegrationProcessId, orderByComparator);

		if (commerceDataIntegrationProcessLog != null) {
			return commerceDataIntegrationProcessLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CDataIntegrationProcessId=");
		sb.append(CDataIntegrationProcessId);

		sb.append("}");

		throw new NoSuchDataIntegrationProcessLogException(sb.toString());
	}

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog
		fetchByCDataIntegrationProcessId_First(
			long CDataIntegrationProcessId,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator) {

		List<CommerceDataIntegrationProcessLog> list =
			findByCDataIntegrationProcessId(
				CDataIntegrationProcessId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog
			findByCDataIntegrationProcessId_Last(
				long CDataIntegrationProcessId,
				OrderByComparator<CommerceDataIntegrationProcessLog>
					orderByComparator)
		throws NoSuchDataIntegrationProcessLogException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			fetchByCDataIntegrationProcessId_Last(
				CDataIntegrationProcessId, orderByComparator);

		if (commerceDataIntegrationProcessLog != null) {
			return commerceDataIntegrationProcessLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CDataIntegrationProcessId=");
		sb.append(CDataIntegrationProcessId);

		sb.append("}");

		throw new NoSuchDataIntegrationProcessLogException(sb.toString());
	}

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog
		fetchByCDataIntegrationProcessId_Last(
			long CDataIntegrationProcessId,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator) {

		int count = countByCDataIntegrationProcessId(CDataIntegrationProcessId);

		if (count == 0) {
			return null;
		}

		List<CommerceDataIntegrationProcessLog> list =
			findByCDataIntegrationProcessId(
				CDataIntegrationProcessId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce data integration process logs before and after the current commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63;.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the current commerce data integration process log
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog[]
			findByCDataIntegrationProcessId_PrevAndNext(
				long commerceDataIntegrationProcessLogId,
				long CDataIntegrationProcessId,
				OrderByComparator<CommerceDataIntegrationProcessLog>
					orderByComparator)
		throws NoSuchDataIntegrationProcessLogException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			findByPrimaryKey(commerceDataIntegrationProcessLogId);

		Session session = null;

		try {
			session = openSession();

			CommerceDataIntegrationProcessLog[] array =
				new CommerceDataIntegrationProcessLogImpl[3];

			array[0] = getByCDataIntegrationProcessId_PrevAndNext(
				session, commerceDataIntegrationProcessLog,
				CDataIntegrationProcessId, orderByComparator, true);

			array[1] = commerceDataIntegrationProcessLog;

			array[2] = getByCDataIntegrationProcessId_PrevAndNext(
				session, commerceDataIntegrationProcessLog,
				CDataIntegrationProcessId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceDataIntegrationProcessLog
		getByCDataIntegrationProcessId_PrevAndNext(
			Session session,
			CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog,
			long CDataIntegrationProcessId,
			OrderByComparator<CommerceDataIntegrationProcessLog>
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

		sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESSLOG_WHERE);

		sb.append(
			_FINDER_COLUMN_CDATAINTEGRATIONPROCESSID_CDATAINTEGRATIONPROCESSID_2);

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
			sb.append(CommerceDataIntegrationProcessLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CDataIntegrationProcessId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDataIntegrationProcessLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDataIntegrationProcessLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce data integration process logs where CDataIntegrationProcessId = &#63; from the database.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 */
	@Override
	public void removeByCDataIntegrationProcessId(
		long CDataIntegrationProcessId) {

		for (CommerceDataIntegrationProcessLog
				commerceDataIntegrationProcessLog :
					findByCDataIntegrationProcessId(
						CDataIntegrationProcessId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceDataIntegrationProcessLog);
		}
	}

	/**
	 * Returns the number of commerce data integration process logs where CDataIntegrationProcessId = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @return the number of matching commerce data integration process logs
	 */
	@Override
	public int countByCDataIntegrationProcessId(
		long CDataIntegrationProcessId) {

		FinderPath finderPath = _finderPathCountByCDataIntegrationProcessId;

		Object[] finderArgs = new Object[] {CDataIntegrationProcessId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESSLOG_WHERE);

			sb.append(
				_FINDER_COLUMN_CDATAINTEGRATIONPROCESSID_CDATAINTEGRATIONPROCESSID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CDataIntegrationProcessId);

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
		_FINDER_COLUMN_CDATAINTEGRATIONPROCESSID_CDATAINTEGRATIONPROCESSID_2 =
			"commerceDataIntegrationProcessLog.CDataIntegrationProcessId = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @return the matching commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status) {

		return findByC_S(
			CDataIntegrationProcessId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @return the range of matching commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status, int start, int end) {

		return findByC_S(CDataIntegrationProcessId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcessLog>
			orderByComparator) {

		return findByC_S(
			CDataIntegrationProcessId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog> findByC_S(
		long CDataIntegrationProcessId, int status, int start, int end,
		OrderByComparator<CommerceDataIntegrationProcessLog> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {CDataIntegrationProcessId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				CDataIntegrationProcessId, status, start, end, orderByComparator
			};
		}

		List<CommerceDataIntegrationProcessLog> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceDataIntegrationProcessLog>)finderCache.getResult(
					finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDataIntegrationProcessLog
						commerceDataIntegrationProcessLog : list) {

					if ((CDataIntegrationProcessId !=
							commerceDataIntegrationProcessLog.
								getCDataIntegrationProcessId()) ||
						(status !=
							commerceDataIntegrationProcessLog.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESSLOG_WHERE);

			sb.append(_FINDER_COLUMN_C_S_CDATAINTEGRATIONPROCESSID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceDataIntegrationProcessLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CDataIntegrationProcessId);

				queryPos.add(status);

				list = (List<CommerceDataIntegrationProcessLog>)QueryUtil.list(
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
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog findByC_S_First(
			long CDataIntegrationProcessId, int status,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator)
		throws NoSuchDataIntegrationProcessLogException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			fetchByC_S_First(
				CDataIntegrationProcessId, status, orderByComparator);

		if (commerceDataIntegrationProcessLog != null) {
			return commerceDataIntegrationProcessLog;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CDataIntegrationProcessId=");
		sb.append(CDataIntegrationProcessId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchDataIntegrationProcessLogException(sb.toString());
	}

	/**
	 * Returns the first commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog fetchByC_S_First(
		long CDataIntegrationProcessId, int status,
		OrderByComparator<CommerceDataIntegrationProcessLog>
			orderByComparator) {

		List<CommerceDataIntegrationProcessLog> list = findByC_S(
			CDataIntegrationProcessId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a matching commerce data integration process log could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog findByC_S_Last(
			long CDataIntegrationProcessId, int status,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator)
		throws NoSuchDataIntegrationProcessLogException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			fetchByC_S_Last(
				CDataIntegrationProcessId, status, orderByComparator);

		if (commerceDataIntegrationProcessLog != null) {
			return commerceDataIntegrationProcessLog;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CDataIntegrationProcessId=");
		sb.append(CDataIntegrationProcessId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchDataIntegrationProcessLogException(sb.toString());
	}

	/**
	 * Returns the last commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce data integration process log, or <code>null</code> if a matching commerce data integration process log could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog fetchByC_S_Last(
		long CDataIntegrationProcessId, int status,
		OrderByComparator<CommerceDataIntegrationProcessLog>
			orderByComparator) {

		int count = countByC_S(CDataIntegrationProcessId, status);

		if (count == 0) {
			return null;
		}

		List<CommerceDataIntegrationProcessLog> list = findByC_S(
			CDataIntegrationProcessId, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce data integration process logs before and after the current commerce data integration process log in the ordered set where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the current commerce data integration process log
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog[] findByC_S_PrevAndNext(
			long commerceDataIntegrationProcessLogId,
			long CDataIntegrationProcessId, int status,
			OrderByComparator<CommerceDataIntegrationProcessLog>
				orderByComparator)
		throws NoSuchDataIntegrationProcessLogException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			findByPrimaryKey(commerceDataIntegrationProcessLogId);

		Session session = null;

		try {
			session = openSession();

			CommerceDataIntegrationProcessLog[] array =
				new CommerceDataIntegrationProcessLogImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, commerceDataIntegrationProcessLog,
				CDataIntegrationProcessId, status, orderByComparator, true);

			array[1] = commerceDataIntegrationProcessLog;

			array[2] = getByC_S_PrevAndNext(
				session, commerceDataIntegrationProcessLog,
				CDataIntegrationProcessId, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceDataIntegrationProcessLog getByC_S_PrevAndNext(
		Session session,
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog,
		long CDataIntegrationProcessId, int status,
		OrderByComparator<CommerceDataIntegrationProcessLog> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESSLOG_WHERE);

		sb.append(_FINDER_COLUMN_C_S_CDATAINTEGRATIONPROCESSID_2);

		sb.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			sb.append(CommerceDataIntegrationProcessLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CDataIntegrationProcessId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDataIntegrationProcessLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDataIntegrationProcessLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63; from the database.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long CDataIntegrationProcessId, int status) {
		for (CommerceDataIntegrationProcessLog
				commerceDataIntegrationProcessLog :
					findByC_S(
						CDataIntegrationProcessId, status, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceDataIntegrationProcessLog);
		}
	}

	/**
	 * Returns the number of commerce data integration process logs where CDataIntegrationProcessId = &#63; and status = &#63;.
	 *
	 * @param CDataIntegrationProcessId the c data integration process ID
	 * @param status the status
	 * @return the number of matching commerce data integration process logs
	 */
	@Override
	public int countByC_S(long CDataIntegrationProcessId, int status) {
		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {CDataIntegrationProcessId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESSLOG_WHERE);

			sb.append(_FINDER_COLUMN_C_S_CDATAINTEGRATIONPROCESSID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CDataIntegrationProcessId);

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_C_S_CDATAINTEGRATIONPROCESSID_2 =
		"commerceDataIntegrationProcessLog.CDataIntegrationProcessId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_STATUS_2 =
		"commerceDataIntegrationProcessLog.status = ?";

	public CommerceDataIntegrationProcessLogPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceDataIntegrationProcessLogId",
			"CDataIntegrationProcessLogId");
		dbColumnNames.put("output", "output_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceDataIntegrationProcessLog.class);

		setModelImplClass(CommerceDataIntegrationProcessLogImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceDataIntegrationProcessLogTable.INSTANCE);
	}

	/**
	 * Caches the commerce data integration process log in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcessLog the commerce data integration process log
	 */
	@Override
	public void cacheResult(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		entityCache.putResult(
			CommerceDataIntegrationProcessLogImpl.class,
			commerceDataIntegrationProcessLog.getPrimaryKey(),
			commerceDataIntegrationProcessLog);
	}

	/**
	 * Caches the commerce data integration process logs in the entity cache if it is enabled.
	 *
	 * @param commerceDataIntegrationProcessLogs the commerce data integration process logs
	 */
	@Override
	public void cacheResult(
		List<CommerceDataIntegrationProcessLog>
			commerceDataIntegrationProcessLogs) {

		for (CommerceDataIntegrationProcessLog
				commerceDataIntegrationProcessLog :
					commerceDataIntegrationProcessLogs) {

			if (entityCache.getResult(
					CommerceDataIntegrationProcessLogImpl.class,
					commerceDataIntegrationProcessLog.getPrimaryKey()) ==
						null) {

				cacheResult(commerceDataIntegrationProcessLog);
			}
		}
	}

	/**
	 * Clears the cache for all commerce data integration process logs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDataIntegrationProcessLogImpl.class);

		finderCache.clearCache(CommerceDataIntegrationProcessLogImpl.class);
	}

	/**
	 * Clears the cache for the commerce data integration process log.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		entityCache.removeResult(
			CommerceDataIntegrationProcessLogImpl.class,
			commerceDataIntegrationProcessLog);
	}

	@Override
	public void clearCache(
		List<CommerceDataIntegrationProcessLog>
			commerceDataIntegrationProcessLogs) {

		for (CommerceDataIntegrationProcessLog
				commerceDataIntegrationProcessLog :
					commerceDataIntegrationProcessLogs) {

			entityCache.removeResult(
				CommerceDataIntegrationProcessLogImpl.class,
				commerceDataIntegrationProcessLog);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceDataIntegrationProcessLogImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceDataIntegrationProcessLogImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new commerce data integration process log with the primary key. Does not add the commerce data integration process log to the database.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key for the new commerce data integration process log
	 * @return the new commerce data integration process log
	 */
	@Override
	public CommerceDataIntegrationProcessLog create(
		long commerceDataIntegrationProcessLogId) {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			new CommerceDataIntegrationProcessLogImpl();

		commerceDataIntegrationProcessLog.setNew(true);
		commerceDataIntegrationProcessLog.setPrimaryKey(
			commerceDataIntegrationProcessLogId);

		commerceDataIntegrationProcessLog.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceDataIntegrationProcessLog;
	}

	/**
	 * Removes the commerce data integration process log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the commerce data integration process log
	 * @return the commerce data integration process log that was removed
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog remove(
			long commerceDataIntegrationProcessLogId)
		throws NoSuchDataIntegrationProcessLogException {

		return remove((Serializable)commerceDataIntegrationProcessLogId);
	}

	/**
	 * Removes the commerce data integration process log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce data integration process log
	 * @return the commerce data integration process log that was removed
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog remove(Serializable primaryKey)
		throws NoSuchDataIntegrationProcessLogException {

		Session session = null;

		try {
			session = openSession();

			CommerceDataIntegrationProcessLog
				commerceDataIntegrationProcessLog =
					(CommerceDataIntegrationProcessLog)session.get(
						CommerceDataIntegrationProcessLogImpl.class,
						primaryKey);

			if (commerceDataIntegrationProcessLog == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDataIntegrationProcessLogException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceDataIntegrationProcessLog);
		}
		catch (NoSuchDataIntegrationProcessLogException noSuchEntityException) {
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
	protected CommerceDataIntegrationProcessLog removeImpl(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDataIntegrationProcessLog)) {
				commerceDataIntegrationProcessLog =
					(CommerceDataIntegrationProcessLog)session.get(
						CommerceDataIntegrationProcessLogImpl.class,
						commerceDataIntegrationProcessLog.getPrimaryKeyObj());
			}

			if (commerceDataIntegrationProcessLog != null) {
				session.delete(commerceDataIntegrationProcessLog);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceDataIntegrationProcessLog != null) {
			clearCache(commerceDataIntegrationProcessLog);
		}

		return commerceDataIntegrationProcessLog;
	}

	@Override
	public CommerceDataIntegrationProcessLog updateImpl(
		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog) {

		boolean isNew = commerceDataIntegrationProcessLog.isNew();

		if (!(commerceDataIntegrationProcessLog instanceof
				CommerceDataIntegrationProcessLogModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceDataIntegrationProcessLog.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceDataIntegrationProcessLog);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDataIntegrationProcessLog proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDataIntegrationProcessLog implementation " +
					commerceDataIntegrationProcessLog.getClass());
		}

		CommerceDataIntegrationProcessLogModelImpl
			commerceDataIntegrationProcessLogModelImpl =
				(CommerceDataIntegrationProcessLogModelImpl)
					commerceDataIntegrationProcessLog;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
			(commerceDataIntegrationProcessLog.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceDataIntegrationProcessLog.setCreateDate(now);
			}
			else {
				commerceDataIntegrationProcessLog.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceDataIntegrationProcessLogModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDataIntegrationProcessLog.setModifiedDate(now);
			}
			else {
				commerceDataIntegrationProcessLog.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceDataIntegrationProcessLog);
			}
			else {
				commerceDataIntegrationProcessLog =
					(CommerceDataIntegrationProcessLog)session.merge(
						commerceDataIntegrationProcessLog);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceDataIntegrationProcessLogImpl.class,
			commerceDataIntegrationProcessLogModelImpl, false, true);

		if (isNew) {
			commerceDataIntegrationProcessLog.setNew(false);
		}

		commerceDataIntegrationProcessLog.resetOriginalValues();

		return commerceDataIntegrationProcessLog;
	}

	/**
	 * Returns the commerce data integration process log with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce data integration process log
	 * @return the commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchDataIntegrationProcessLogException {

		CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog =
			fetchByPrimaryKey(primaryKey);

		if (commerceDataIntegrationProcessLog == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDataIntegrationProcessLogException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceDataIntegrationProcessLog;
	}

	/**
	 * Returns the commerce data integration process log with the primary key or throws a <code>NoSuchDataIntegrationProcessLogException</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the commerce data integration process log
	 * @return the commerce data integration process log
	 * @throws NoSuchDataIntegrationProcessLogException if a commerce data integration process log with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog findByPrimaryKey(
			long commerceDataIntegrationProcessLogId)
		throws NoSuchDataIntegrationProcessLogException {

		return findByPrimaryKey(
			(Serializable)commerceDataIntegrationProcessLogId);
	}

	/**
	 * Returns the commerce data integration process log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDataIntegrationProcessLogId the primary key of the commerce data integration process log
	 * @return the commerce data integration process log, or <code>null</code> if a commerce data integration process log with the primary key could not be found
	 */
	@Override
	public CommerceDataIntegrationProcessLog fetchByPrimaryKey(
		long commerceDataIntegrationProcessLogId) {

		return fetchByPrimaryKey(
			(Serializable)commerceDataIntegrationProcessLogId);
	}

	/**
	 * Returns all the commerce data integration process logs.
	 *
	 * @return the commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce data integration process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @return the range of commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog> findAll(
		int start, int end,
		OrderByComparator<CommerceDataIntegrationProcessLog>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce data integration process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDataIntegrationProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce data integration process logs
	 * @param end the upper bound of the range of commerce data integration process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce data integration process logs
	 */
	@Override
	public List<CommerceDataIntegrationProcessLog> findAll(
		int start, int end,
		OrderByComparator<CommerceDataIntegrationProcessLog> orderByComparator,
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

		List<CommerceDataIntegrationProcessLog> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceDataIntegrationProcessLog>)finderCache.getResult(
					finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESSLOG);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESSLOG;

				sql = sql.concat(
					CommerceDataIntegrationProcessLogModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceDataIntegrationProcessLog>)QueryUtil.list(
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
	 * Removes all the commerce data integration process logs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDataIntegrationProcessLog
				commerceDataIntegrationProcessLog : findAll()) {

			remove(commerceDataIntegrationProcessLog);
		}
	}

	/**
	 * Returns the number of commerce data integration process logs.
	 *
	 * @return the number of commerce data integration process logs
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
					_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESSLOG);

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
		return "CDataIntegrationProcessLogId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESSLOG;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceDataIntegrationProcessLogModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce data integration process log persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDataIntegrationProcessLogPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceDataIntegrationProcessLogModelArgumentsResolver(),
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

		_finderPathWithPaginationFindByCDataIntegrationProcessId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCDataIntegrationProcessId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"CDataIntegrationProcessId"}, true);

		_finderPathWithoutPaginationFindByCDataIntegrationProcessId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCDataIntegrationProcessId",
				new String[] {Long.class.getName()},
				new String[] {"CDataIntegrationProcessId"}, true);

		_finderPathCountByCDataIntegrationProcessId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCDataIntegrationProcessId",
			new String[] {Long.class.getName()},
			new String[] {"CDataIntegrationProcessId"}, false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CDataIntegrationProcessId", "status"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CDataIntegrationProcessId", "status"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CDataIntegrationProcessId", "status"}, false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceDataIntegrationProcessLogImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESSLOG =
		"SELECT commerceDataIntegrationProcessLog FROM CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog";

	private static final String
		_SQL_SELECT_COMMERCEDATAINTEGRATIONPROCESSLOG_WHERE =
			"SELECT commerceDataIntegrationProcessLog FROM CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog WHERE ";

	private static final String _SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESSLOG =
		"SELECT COUNT(commerceDataIntegrationProcessLog) FROM CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog";

	private static final String
		_SQL_COUNT_COMMERCEDATAINTEGRATIONPROCESSLOG_WHERE =
			"SELECT COUNT(commerceDataIntegrationProcessLog) FROM CommerceDataIntegrationProcessLog commerceDataIntegrationProcessLog WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceDataIntegrationProcessLog.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceDataIntegrationProcessLog exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceDataIntegrationProcessLog exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDataIntegrationProcessLogPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceDataIntegrationProcessLogId", "output"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommerceDataIntegrationProcessLogModelArgumentsResolver
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

			CommerceDataIntegrationProcessLogModelImpl
				commerceDataIntegrationProcessLogModelImpl =
					(CommerceDataIntegrationProcessLogModelImpl)baseModel;

			long columnBitmask =
				commerceDataIntegrationProcessLogModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceDataIntegrationProcessLogModelImpl, columnNames,
					original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceDataIntegrationProcessLogModelImpl.
							getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceDataIntegrationProcessLogModelImpl, columnNames,
					original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommerceDataIntegrationProcessLogImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommerceDataIntegrationProcessLogTable.INSTANCE.
				getTableName();
		}

		private Object[] _getValue(
			CommerceDataIntegrationProcessLogModelImpl
				commerceDataIntegrationProcessLogModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceDataIntegrationProcessLogModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						commerceDataIntegrationProcessLogModelImpl.
							getColumnValue(columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}