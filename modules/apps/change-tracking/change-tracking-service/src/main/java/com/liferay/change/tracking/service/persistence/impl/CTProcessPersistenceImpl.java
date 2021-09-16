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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchProcessException;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.model.CTProcessTable;
import com.liferay.change.tracking.model.impl.CTProcessImpl;
import com.liferay.change.tracking.model.impl.CTProcessModelImpl;
import com.liferay.change.tracking.service.persistence.CTProcessPersistence;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
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
 * The persistence implementation for the ct process service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {CTProcessPersistence.class, BasePersistence.class})
public class CTProcessPersistenceImpl
	extends BasePersistenceImpl<CTProcess> implements CTProcessPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTProcessUtil</code> to access the ct process persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTProcessImpl.class.getName();

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
	 * Returns all the ct processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct processes
	 */
	@Override
	public List<CTProcess> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of matching ct processes
	 */
	@Override
	public List<CTProcess> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct processes
	 */
	@Override
	public List<CTProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTProcess> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct processes
	 */
	@Override
	public List<CTProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CTProcess> orderByComparator,
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

		List<CTProcess> list = null;

		if (useFinderCache) {
			list = (List<CTProcess>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTProcess ctProcess : list) {
					if (companyId != ctProcess.getCompanyId()) {
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

			sb.append(_SQL_SELECT_CTPROCESS_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTProcessModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CTProcess>)QueryUtil.list(
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
	 * Returns the first ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	@Override
	public CTProcess findByCompanyId_First(
			long companyId, OrderByComparator<CTProcess> orderByComparator)
		throws NoSuchProcessException {

		CTProcess ctProcess = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (ctProcess != null) {
			return ctProcess;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchProcessException(sb.toString());
	}

	/**
	 * Returns the first ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	@Override
	public CTProcess fetchByCompanyId_First(
		long companyId, OrderByComparator<CTProcess> orderByComparator) {

		List<CTProcess> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	@Override
	public CTProcess findByCompanyId_Last(
			long companyId, OrderByComparator<CTProcess> orderByComparator)
		throws NoSuchProcessException {

		CTProcess ctProcess = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (ctProcess != null) {
			return ctProcess;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchProcessException(sb.toString());
	}

	/**
	 * Returns the last ct process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	@Override
	public CTProcess fetchByCompanyId_Last(
		long companyId, OrderByComparator<CTProcess> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CTProcess> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct processes before and after the current ct process in the ordered set where companyId = &#63;.
	 *
	 * @param ctProcessId the primary key of the current ct process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	@Override
	public CTProcess[] findByCompanyId_PrevAndNext(
			long ctProcessId, long companyId,
			OrderByComparator<CTProcess> orderByComparator)
		throws NoSuchProcessException {

		CTProcess ctProcess = findByPrimaryKey(ctProcessId);

		Session session = null;

		try {
			session = openSession();

			CTProcess[] array = new CTProcessImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, ctProcess, companyId, orderByComparator, true);

			array[1] = ctProcess;

			array[2] = getByCompanyId_PrevAndNext(
				session, ctProcess, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTProcess getByCompanyId_PrevAndNext(
		Session session, CTProcess ctProcess, long companyId,
		OrderByComparator<CTProcess> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTPROCESS_WHERE);

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
			sb.append(CTProcessModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctProcess)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTProcess> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct processes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CTProcess ctProcess :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctProcess);
		}
	}

	/**
	 * Returns the number of ct processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct processes
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTPROCESS_WHERE);

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
		"ctProcess.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCtCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByCtCollectionId;
	private FinderPath _finderPathCountByCtCollectionId;

	/**
	 * Returns all the ct processes where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct processes
	 */
	@Override
	public List<CTProcess> findByCtCollectionId(long ctCollectionId) {
		return findByCtCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct processes where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of matching ct processes
	 */
	@Override
	public List<CTProcess> findByCtCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCtCollectionId(ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct processes where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct processes
	 */
	@Override
	public List<CTProcess> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTProcess> orderByComparator) {

		return findByCtCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct processes where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct processes
	 */
	@Override
	public List<CTProcess> findByCtCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTProcess> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCtCollectionId;
				finderArgs = new Object[] {ctCollectionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCtCollectionId;
			finderArgs = new Object[] {
				ctCollectionId, start, end, orderByComparator
			};
		}

		List<CTProcess> list = null;

		if (useFinderCache) {
			list = (List<CTProcess>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CTProcess ctProcess : list) {
					if (ctCollectionId != ctProcess.getCtCollectionId()) {
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

			sb.append(_SQL_SELECT_CTPROCESS_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CTProcessModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

				list = (List<CTProcess>)QueryUtil.list(
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
	 * Returns the first ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	@Override
	public CTProcess findByCtCollectionId_First(
			long ctCollectionId, OrderByComparator<CTProcess> orderByComparator)
		throws NoSuchProcessException {

		CTProcess ctProcess = fetchByCtCollectionId_First(
			ctCollectionId, orderByComparator);

		if (ctProcess != null) {
			return ctProcess;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchProcessException(sb.toString());
	}

	/**
	 * Returns the first ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	@Override
	public CTProcess fetchByCtCollectionId_First(
		long ctCollectionId, OrderByComparator<CTProcess> orderByComparator) {

		List<CTProcess> list = findByCtCollectionId(
			ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process
	 * @throws NoSuchProcessException if a matching ct process could not be found
	 */
	@Override
	public CTProcess findByCtCollectionId_Last(
			long ctCollectionId, OrderByComparator<CTProcess> orderByComparator)
		throws NoSuchProcessException {

		CTProcess ctProcess = fetchByCtCollectionId_Last(
			ctCollectionId, orderByComparator);

		if (ctProcess != null) {
			return ctProcess;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("ctCollectionId=");
		sb.append(ctCollectionId);

		sb.append("}");

		throw new NoSuchProcessException(sb.toString());
	}

	/**
	 * Returns the last ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct process, or <code>null</code> if a matching ct process could not be found
	 */
	@Override
	public CTProcess fetchByCtCollectionId_Last(
		long ctCollectionId, OrderByComparator<CTProcess> orderByComparator) {

		int count = countByCtCollectionId(ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTProcess> list = findByCtCollectionId(
			ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct processes before and after the current ct process in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctProcessId the primary key of the current ct process
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	@Override
	public CTProcess[] findByCtCollectionId_PrevAndNext(
			long ctProcessId, long ctCollectionId,
			OrderByComparator<CTProcess> orderByComparator)
		throws NoSuchProcessException {

		CTProcess ctProcess = findByPrimaryKey(ctProcessId);

		Session session = null;

		try {
			session = openSession();

			CTProcess[] array = new CTProcessImpl[3];

			array[0] = getByCtCollectionId_PrevAndNext(
				session, ctProcess, ctCollectionId, orderByComparator, true);

			array[1] = ctProcess;

			array[2] = getByCtCollectionId_PrevAndNext(
				session, ctProcess, ctCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTProcess getByCtCollectionId_PrevAndNext(
		Session session, CTProcess ctProcess, long ctCollectionId,
		OrderByComparator<CTProcess> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CTPROCESS_WHERE);

		sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

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
			sb.append(CTProcessModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctProcess)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CTProcess> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct processes where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCtCollectionId(long ctCollectionId) {
		for (CTProcess ctProcess :
				findByCtCollectionId(
					ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctProcess);
		}
	}

	/**
	 * Returns the number of ct processes where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct processes
	 */
	@Override
	public int countByCtCollectionId(long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByCtCollectionId;

		Object[] finderArgs = new Object[] {ctCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CTPROCESS_WHERE);

			sb.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ctCollectionId);

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

	private static final String _FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2 =
		"ctProcess.ctCollectionId = ?";

	public CTProcessPersistenceImpl() {
		setModelClass(CTProcess.class);

		setModelImplClass(CTProcessImpl.class);
		setModelPKClass(long.class);

		setTable(CTProcessTable.INSTANCE);
	}

	/**
	 * Caches the ct process in the entity cache if it is enabled.
	 *
	 * @param ctProcess the ct process
	 */
	@Override
	public void cacheResult(CTProcess ctProcess) {
		entityCache.putResult(
			CTProcessImpl.class, ctProcess.getPrimaryKey(), ctProcess);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the ct processes in the entity cache if it is enabled.
	 *
	 * @param ctProcesses the ct processes
	 */
	@Override
	public void cacheResult(List<CTProcess> ctProcesses) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (ctProcesses.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CTProcess ctProcess : ctProcesses) {
			if (entityCache.getResult(
					CTProcessImpl.class, ctProcess.getPrimaryKey()) == null) {

				cacheResult(ctProcess);
			}
		}
	}

	/**
	 * Clears the cache for all ct processes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTProcessImpl.class);

		finderCache.clearCache(CTProcessImpl.class);
	}

	/**
	 * Clears the cache for the ct process.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTProcess ctProcess) {
		entityCache.removeResult(CTProcessImpl.class, ctProcess);
	}

	@Override
	public void clearCache(List<CTProcess> ctProcesses) {
		for (CTProcess ctProcess : ctProcesses) {
			entityCache.removeResult(CTProcessImpl.class, ctProcess);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CTProcessImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CTProcessImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new ct process with the primary key. Does not add the ct process to the database.
	 *
	 * @param ctProcessId the primary key for the new ct process
	 * @return the new ct process
	 */
	@Override
	public CTProcess create(long ctProcessId) {
		CTProcess ctProcess = new CTProcessImpl();

		ctProcess.setNew(true);
		ctProcess.setPrimaryKey(ctProcessId);

		ctProcess.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctProcess;
	}

	/**
	 * Removes the ct process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process that was removed
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	@Override
	public CTProcess remove(long ctProcessId) throws NoSuchProcessException {
		return remove((Serializable)ctProcessId);
	}

	/**
	 * Removes the ct process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct process
	 * @return the ct process that was removed
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	@Override
	public CTProcess remove(Serializable primaryKey)
		throws NoSuchProcessException {

		Session session = null;

		try {
			session = openSession();

			CTProcess ctProcess = (CTProcess)session.get(
				CTProcessImpl.class, primaryKey);

			if (ctProcess == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProcessException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctProcess);
		}
		catch (NoSuchProcessException noSuchEntityException) {
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
	protected CTProcess removeImpl(CTProcess ctProcess) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctProcess)) {
				ctProcess = (CTProcess)session.get(
					CTProcessImpl.class, ctProcess.getPrimaryKeyObj());
			}

			if (ctProcess != null) {
				session.delete(ctProcess);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ctProcess != null) {
			clearCache(ctProcess);
		}

		return ctProcess;
	}

	@Override
	public CTProcess updateImpl(CTProcess ctProcess) {
		boolean isNew = ctProcess.isNew();

		if (!(ctProcess instanceof CTProcessModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctProcess.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctProcess);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctProcess proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTProcess implementation " +
					ctProcess.getClass());
		}

		CTProcessModelImpl ctProcessModelImpl = (CTProcessModelImpl)ctProcess;

		if (isNew && (ctProcess.getCreateDate() == null)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Date date = new Date();

			if (serviceContext == null) {
				ctProcess.setCreateDate(date);
			}
			else {
				ctProcess.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(ctProcess);
			}
			else {
				ctProcess = (CTProcess)session.merge(ctProcess);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CTProcessImpl.class, ctProcessModelImpl, false, true);

		if (isNew) {
			ctProcess.setNew(false);
		}

		ctProcess.resetOriginalValues();

		return ctProcess;
	}

	/**
	 * Returns the ct process with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct process
	 * @return the ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	@Override
	public CTProcess findByPrimaryKey(Serializable primaryKey)
		throws NoSuchProcessException {

		CTProcess ctProcess = fetchByPrimaryKey(primaryKey);

		if (ctProcess == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProcessException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctProcess;
	}

	/**
	 * Returns the ct process with the primary key or throws a <code>NoSuchProcessException</code> if it could not be found.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process
	 * @throws NoSuchProcessException if a ct process with the primary key could not be found
	 */
	@Override
	public CTProcess findByPrimaryKey(long ctProcessId)
		throws NoSuchProcessException {

		return findByPrimaryKey((Serializable)ctProcessId);
	}

	/**
	 * Returns the ct process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process, or <code>null</code> if a ct process with the primary key could not be found
	 */
	@Override
	public CTProcess fetchByPrimaryKey(long ctProcessId) {
		return fetchByPrimaryKey((Serializable)ctProcessId);
	}

	/**
	 * Returns all the ct processes.
	 *
	 * @return the ct processes
	 */
	@Override
	public List<CTProcess> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of ct processes
	 */
	@Override
	public List<CTProcess> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct processes
	 */
	@Override
	public List<CTProcess> findAll(
		int start, int end, OrderByComparator<CTProcess> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct processes
	 */
	@Override
	public List<CTProcess> findAll(
		int start, int end, OrderByComparator<CTProcess> orderByComparator,
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

		List<CTProcess> list = null;

		if (useFinderCache) {
			list = (List<CTProcess>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CTPROCESS);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CTPROCESS;

				sql = sql.concat(CTProcessModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CTProcess>)QueryUtil.list(
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
	 * Removes all the ct processes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTProcess ctProcess : findAll()) {
			remove(ctProcess);
		}
	}

	/**
	 * Returns the number of ct processes.
	 *
	 * @return the number of ct processes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CTPROCESS);

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
		return "ctProcessId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTPROCESS;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTProcessModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct process persistence.
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

		_finderPathWithPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCtCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"ctCollectionId"}, true);

		_finderPathWithoutPaginationFindByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, true);

		_finderPathCountByCtCollectionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCtCollectionId",
			new String[] {Long.class.getName()},
			new String[] {"ctCollectionId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTProcessImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CTPROCESS =
		"SELECT ctProcess FROM CTProcess ctProcess";

	private static final String _SQL_SELECT_CTPROCESS_WHERE =
		"SELECT ctProcess FROM CTProcess ctProcess WHERE ";

	private static final String _SQL_COUNT_CTPROCESS =
		"SELECT COUNT(ctProcess) FROM CTProcess ctProcess";

	private static final String _SQL_COUNT_CTPROCESS_WHERE =
		"SELECT COUNT(ctProcess) FROM CTProcess ctProcess WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctProcess.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTProcess exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTProcess exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTProcessPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CTProcessModelArgumentsResolver _ctProcessModelArgumentsResolver;

}