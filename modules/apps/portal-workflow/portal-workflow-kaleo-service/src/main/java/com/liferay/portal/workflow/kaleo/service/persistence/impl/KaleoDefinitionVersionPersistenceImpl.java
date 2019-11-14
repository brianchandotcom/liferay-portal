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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionVersionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionVersionImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionVersionModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoDefinitionVersionPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the kaleo definition version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = KaleoDefinitionVersionPersistence.class)
public class KaleoDefinitionVersionPersistenceImpl
	extends BasePersistenceImpl<KaleoDefinitionVersion>
	implements KaleoDefinitionVersionPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoDefinitionVersionUtil</code> to access the kaleo definition version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoDefinitionVersionImpl.class.getName();

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
	 * Returns all the kaleo definition versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
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

		List<KaleoDefinitionVersion> list = null;

		if (useFinderCache) {
			list = (List<KaleoDefinitionVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoDefinitionVersion kaleoDefinitionVersion : list) {
					if (companyId != kaleoDefinitionVersion.getCompanyId()) {
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

			query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<KaleoDefinitionVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {

		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (kaleoDefinitionVersion != null) {
			return kaleoDefinitionVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByCompanyId_First(
		long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		List<KaleoDefinitionVersion> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {

		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (kaleoDefinitionVersion != null) {
			return kaleoDefinitionVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoDefinitionVersion> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo definition versions before and after the current kaleo definition version in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the current kaleo definition version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion[] findByCompanyId_PrevAndNext(
			long kaleoDefinitionVersionId, long companyId,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {

		KaleoDefinitionVersion kaleoDefinitionVersion = findByPrimaryKey(
			kaleoDefinitionVersionId);

		Session session = null;

		try {
			session = openSession();

			KaleoDefinitionVersion[] array = new KaleoDefinitionVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoDefinitionVersion, companyId, orderByComparator,
				true);

			array[1] = kaleoDefinitionVersion;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoDefinitionVersion, companyId, orderByComparator,
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

	protected KaleoDefinitionVersion getByCompanyId_PrevAndNext(
		Session session, KaleoDefinitionVersion kaleoDefinitionVersion,
		long companyId,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

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
			query.append(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
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
						kaleoDefinitionVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<KaleoDefinitionVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo definition versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoDefinitionVersion kaleoDefinitionVersion :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoDefinitionVersion);
		}
	}

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo definition versions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"kaleoDefinitionVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_N;
	private FinderPath _finderPathWithoutPaginationFindByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByC_N(long companyId, String name) {
		return findByC_N(
			companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name, int start, int end) {

		return findByC_N(companyId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return findByC_N(companyId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findByC_N(
		long companyId, String name, int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_N;
				finderArgs = new Object[] {companyId, name};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_N;
			finderArgs = new Object[] {
				companyId, name, start, end, orderByComparator
			};
		}

		List<KaleoDefinitionVersion> list = null;

		if (useFinderCache) {
			list = (List<KaleoDefinitionVersion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoDefinitionVersion kaleoDefinitionVersion : list) {
					if ((companyId != kaleoDefinitionVersion.getCompanyId()) ||
						!name.equals(kaleoDefinitionVersion.getName())) {

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

			query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
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

				list = (List<KaleoDefinitionVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByC_N_First(
			long companyId, String name,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {

		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByC_N_First(
			companyId, name, orderByComparator);

		if (kaleoDefinitionVersion != null) {
			return kaleoDefinitionVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchDefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the first kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByC_N_First(
		long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		List<KaleoDefinitionVersion> list = findByC_N(
			companyId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByC_N_Last(
			long companyId, String name,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {

		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByC_N_Last(
			companyId, name, orderByComparator);

		if (kaleoDefinitionVersion != null) {
			return kaleoDefinitionVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", name=");
		msg.append(name);

		msg.append("}");

		throw new NoSuchDefinitionVersionException(msg.toString());
	}

	/**
	 * Returns the last kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByC_N_Last(
		long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		int count = countByC_N(companyId, name);

		if (count == 0) {
			return null;
		}

		List<KaleoDefinitionVersion> list = findByC_N(
			companyId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo definition versions before and after the current kaleo definition version in the ordered set where companyId = &#63; and name = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the current kaleo definition version
	 * @param companyId the company ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion[] findByC_N_PrevAndNext(
			long kaleoDefinitionVersionId, long companyId, String name,
			OrderByComparator<KaleoDefinitionVersion> orderByComparator)
		throws NoSuchDefinitionVersionException {

		name = Objects.toString(name, "");

		KaleoDefinitionVersion kaleoDefinitionVersion = findByPrimaryKey(
			kaleoDefinitionVersionId);

		Session session = null;

		try {
			session = openSession();

			KaleoDefinitionVersion[] array = new KaleoDefinitionVersionImpl[3];

			array[0] = getByC_N_PrevAndNext(
				session, kaleoDefinitionVersion, companyId, name,
				orderByComparator, true);

			array[1] = kaleoDefinitionVersion;

			array[2] = getByC_N_PrevAndNext(
				session, kaleoDefinitionVersion, companyId, name,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoDefinitionVersion getByC_N_PrevAndNext(
		Session session, KaleoDefinitionVersion kaleoDefinitionVersion,
		long companyId, String name,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
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

		query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

		query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			query.append(_FINDER_COLUMN_C_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_C_N_NAME_2);
		}

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
			query.append(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoDefinitionVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<KaleoDefinitionVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo definition versions where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 */
	@Override
	public void removeByC_N(long companyId, String name) {
		for (KaleoDefinitionVersion kaleoDefinitionVersion :
				findByC_N(
					companyId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoDefinitionVersion);
		}
	}

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching kaleo definition versions
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_KALEODEFINITIONVERSION_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"kaleoDefinitionVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"kaleoDefinitionVersion.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(kaleoDefinitionVersion.name IS NULL OR kaleoDefinitionVersion.name = '')";

	private FinderPath _finderPathFetchByC_N_V;
	private FinderPath _finderPathCountByC_N_V;

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or throws a <code>NoSuchDefinitionVersionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByC_N_V(
			long companyId, String name, String version)
		throws NoSuchDefinitionVersionException {

		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByC_N_V(
			companyId, name, version);

		if (kaleoDefinitionVersion == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDefinitionVersionException(msg.toString());
		}

		return kaleoDefinitionVersion;
	}

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByC_N_V(
		long companyId, String name, String version) {

		return fetchByC_N_V(companyId, name, version, true);
	}

	/**
	 * Returns the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo definition version, or <code>null</code> if a matching kaleo definition version could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByC_N_V(
		long companyId, String name, String version, boolean useFinderCache) {

		name = Objects.toString(name, "");
		version = Objects.toString(version, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_N_V, finderArgs, this);
		}

		if (result instanceof KaleoDefinitionVersion) {
			KaleoDefinitionVersion kaleoDefinitionVersion =
				(KaleoDefinitionVersion)result;

			if ((companyId != kaleoDefinitionVersion.getCompanyId()) ||
				!Objects.equals(name, kaleoDefinitionVersion.getName()) ||
				!Objects.equals(version, kaleoDefinitionVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_V_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_V_NAME_2);
			}

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_C_N_V_VERSION_2);
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

				if (bindVersion) {
					qPos.add(version);
				}

				List<KaleoDefinitionVersion> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N_V, finderArgs, list);
					}
				}
				else {
					KaleoDefinitionVersion kaleoDefinitionVersion = list.get(0);

					result = kaleoDefinitionVersion;

					cacheResult(kaleoDefinitionVersion);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByC_N_V, finderArgs);
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
			return (KaleoDefinitionVersion)result;
		}
	}

	/**
	 * Removes the kaleo definition version where companyId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the kaleo definition version that was removed
	 */
	@Override
	public KaleoDefinitionVersion removeByC_N_V(
			long companyId, String name, String version)
		throws NoSuchDefinitionVersionException {

		KaleoDefinitionVersion kaleoDefinitionVersion = findByC_N_V(
			companyId, name, version);

		return remove(kaleoDefinitionVersion);
	}

	/**
	 * Returns the number of kaleo definition versions where companyId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching kaleo definition versions
	 */
	@Override
	public int countByC_N_V(long companyId, String name, String version) {
		name = Objects.toString(name, "");
		version = Objects.toString(version, "");

		FinderPath finderPath = _finderPathCountByC_N_V;

		Object[] finderArgs = new Object[] {companyId, name, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_KALEODEFINITIONVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_N_V_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_V_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_V_NAME_2);
			}

			boolean bindVersion = false;

			if (version.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_C_N_V_VERSION_2);
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

				if (bindVersion) {
					qPos.add(version);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_V_COMPANYID_2 =
		"kaleoDefinitionVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_V_NAME_2 =
		"kaleoDefinitionVersion.name = ? AND ";

	private static final String _FINDER_COLUMN_C_N_V_NAME_3 =
		"(kaleoDefinitionVersion.name IS NULL OR kaleoDefinitionVersion.name = '') AND ";

	private static final String _FINDER_COLUMN_C_N_V_VERSION_2 =
		"kaleoDefinitionVersion.version = ?";

	private static final String _FINDER_COLUMN_C_N_V_VERSION_3 =
		"(kaleoDefinitionVersion.version IS NULL OR kaleoDefinitionVersion.version = '')";

	public KaleoDefinitionVersionPersistenceImpl() {
		setModelClass(KaleoDefinitionVersion.class);

		setModelImplClass(KaleoDefinitionVersionImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the kaleo definition version in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinitionVersion the kaleo definition version
	 */
	@Override
	public void cacheResult(KaleoDefinitionVersion kaleoDefinitionVersion) {
		entityCache.putResult(
			entityCacheEnabled, KaleoDefinitionVersionImpl.class,
			kaleoDefinitionVersion.getPrimaryKey(), kaleoDefinitionVersion);

		finderCache.putResult(
			_finderPathFetchByC_N_V,
			new Object[] {
				kaleoDefinitionVersion.getCompanyId(),
				kaleoDefinitionVersion.getName(),
				kaleoDefinitionVersion.getVersion()
			},
			kaleoDefinitionVersion);

		kaleoDefinitionVersion.resetOriginalValues();
	}

	/**
	 * Caches the kaleo definition versions in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinitionVersions the kaleo definition versions
	 */
	@Override
	public void cacheResult(
		List<KaleoDefinitionVersion> kaleoDefinitionVersions) {

		for (KaleoDefinitionVersion kaleoDefinitionVersion :
				kaleoDefinitionVersions) {

			if (entityCache.getResult(
					entityCacheEnabled, KaleoDefinitionVersionImpl.class,
					kaleoDefinitionVersion.getPrimaryKey()) == null) {

				cacheResult(kaleoDefinitionVersion);
			}
			else {
				kaleoDefinitionVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all kaleo definition versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoDefinitionVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the kaleo definition version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoDefinitionVersion kaleoDefinitionVersion) {
		entityCache.removeResult(
			entityCacheEnabled, KaleoDefinitionVersionImpl.class,
			kaleoDefinitionVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(KaleoDefinitionVersionModelImpl)kaleoDefinitionVersion, true);
	}

	@Override
	public void clearCache(
		List<KaleoDefinitionVersion> kaleoDefinitionVersions) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (KaleoDefinitionVersion kaleoDefinitionVersion :
				kaleoDefinitionVersions) {

			entityCache.removeResult(
				entityCacheEnabled, KaleoDefinitionVersionImpl.class,
				kaleoDefinitionVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(KaleoDefinitionVersionModelImpl)kaleoDefinitionVersion, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, KaleoDefinitionVersionImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoDefinitionVersionModelImpl kaleoDefinitionVersionModelImpl) {

		Object[] args = new Object[] {
			kaleoDefinitionVersionModelImpl.getCompanyId(),
			kaleoDefinitionVersionModelImpl.getName(),
			kaleoDefinitionVersionModelImpl.getVersion()
		};

		finderCache.putResult(
			_finderPathCountByC_N_V, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_N_V, args, kaleoDefinitionVersionModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		KaleoDefinitionVersionModelImpl kaleoDefinitionVersionModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				kaleoDefinitionVersionModelImpl.getCompanyId(),
				kaleoDefinitionVersionModelImpl.getName(),
				kaleoDefinitionVersionModelImpl.getVersion()
			};

			finderCache.removeResult(_finderPathCountByC_N_V, args);
			finderCache.removeResult(_finderPathFetchByC_N_V, args);
		}

		if ((kaleoDefinitionVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N_V.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				kaleoDefinitionVersionModelImpl.getOriginalCompanyId(),
				kaleoDefinitionVersionModelImpl.getOriginalName(),
				kaleoDefinitionVersionModelImpl.getOriginalVersion()
			};

			finderCache.removeResult(_finderPathCountByC_N_V, args);
			finderCache.removeResult(_finderPathFetchByC_N_V, args);
		}
	}

	/**
	 * Creates a new kaleo definition version with the primary key. Does not add the kaleo definition version to the database.
	 *
	 * @param kaleoDefinitionVersionId the primary key for the new kaleo definition version
	 * @return the new kaleo definition version
	 */
	@Override
	public KaleoDefinitionVersion create(long kaleoDefinitionVersionId) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			new KaleoDefinitionVersionImpl();

		kaleoDefinitionVersion.setNew(true);
		kaleoDefinitionVersion.setPrimaryKey(kaleoDefinitionVersionId);

		kaleoDefinitionVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoDefinitionVersion;
	}

	/**
	 * Removes the kaleo definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version that was removed
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion remove(long kaleoDefinitionVersionId)
		throws NoSuchDefinitionVersionException {

		return remove((Serializable)kaleoDefinitionVersionId);
	}

	/**
	 * Removes the kaleo definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo definition version
	 * @return the kaleo definition version that was removed
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion remove(Serializable primaryKey)
		throws NoSuchDefinitionVersionException {

		Session session = null;

		try {
			session = openSession();

			KaleoDefinitionVersion kaleoDefinitionVersion =
				(KaleoDefinitionVersion)session.get(
					KaleoDefinitionVersionImpl.class, primaryKey);

			if (kaleoDefinitionVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDefinitionVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoDefinitionVersion);
		}
		catch (NoSuchDefinitionVersionException nsee) {
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
	protected KaleoDefinitionVersion removeImpl(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoDefinitionVersion)) {
				kaleoDefinitionVersion = (KaleoDefinitionVersion)session.get(
					KaleoDefinitionVersionImpl.class,
					kaleoDefinitionVersion.getPrimaryKeyObj());
			}

			if (kaleoDefinitionVersion != null) {
				session.delete(kaleoDefinitionVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (kaleoDefinitionVersion != null) {
			clearCache(kaleoDefinitionVersion);
		}

		return kaleoDefinitionVersion;
	}

	@Override
	public KaleoDefinitionVersion updateImpl(
		KaleoDefinitionVersion kaleoDefinitionVersion) {

		boolean isNew = kaleoDefinitionVersion.isNew();

		if (!(kaleoDefinitionVersion instanceof
				KaleoDefinitionVersionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoDefinitionVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoDefinitionVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoDefinitionVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoDefinitionVersion implementation " +
					kaleoDefinitionVersion.getClass());
		}

		KaleoDefinitionVersionModelImpl kaleoDefinitionVersionModelImpl =
			(KaleoDefinitionVersionModelImpl)kaleoDefinitionVersion;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (kaleoDefinitionVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoDefinitionVersion.setCreateDate(now);
			}
			else {
				kaleoDefinitionVersion.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!kaleoDefinitionVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoDefinitionVersion.setModifiedDate(now);
			}
			else {
				kaleoDefinitionVersion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (kaleoDefinitionVersion.isNew()) {
				session.save(kaleoDefinitionVersion);

				kaleoDefinitionVersion.setNew(false);
			}
			else {
				kaleoDefinitionVersion = (KaleoDefinitionVersion)session.merge(
					kaleoDefinitionVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				kaleoDefinitionVersionModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				kaleoDefinitionVersionModelImpl.getCompanyId(),
				kaleoDefinitionVersionModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_N, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((kaleoDefinitionVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					kaleoDefinitionVersionModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {
					kaleoDefinitionVersionModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((kaleoDefinitionVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_N.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					kaleoDefinitionVersionModelImpl.getOriginalCompanyId(),
					kaleoDefinitionVersionModelImpl.getOriginalName()
				};

				finderCache.removeResult(_finderPathCountByC_N, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_N, args);

				args = new Object[] {
					kaleoDefinitionVersionModelImpl.getCompanyId(),
					kaleoDefinitionVersionModelImpl.getName()
				};

				finderCache.removeResult(_finderPathCountByC_N, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_N, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, KaleoDefinitionVersionImpl.class,
			kaleoDefinitionVersion.getPrimaryKey(), kaleoDefinitionVersion,
			false);

		clearUniqueFindersCache(kaleoDefinitionVersionModelImpl, false);
		cacheUniqueFindersCache(kaleoDefinitionVersionModelImpl);

		kaleoDefinitionVersion.resetOriginalValues();

		return kaleoDefinitionVersion;
	}

	/**
	 * Returns the kaleo definition version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo definition version
	 * @return the kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDefinitionVersionException {

		KaleoDefinitionVersion kaleoDefinitionVersion = fetchByPrimaryKey(
			primaryKey);

		if (kaleoDefinitionVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDefinitionVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoDefinitionVersion;
	}

	/**
	 * Returns the kaleo definition version with the primary key or throws a <code>NoSuchDefinitionVersionException</code> if it could not be found.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version
	 * @throws NoSuchDefinitionVersionException if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion findByPrimaryKey(
			long kaleoDefinitionVersionId)
		throws NoSuchDefinitionVersionException {

		return findByPrimaryKey((Serializable)kaleoDefinitionVersionId);
	}

	/**
	 * Returns the kaleo definition version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version, or <code>null</code> if a kaleo definition version with the primary key could not be found
	 */
	@Override
	public KaleoDefinitionVersion fetchByPrimaryKey(
		long kaleoDefinitionVersionId) {

		return fetchByPrimaryKey((Serializable)kaleoDefinitionVersionId);
	}

	/**
	 * Returns all the kaleo definition versions.
	 *
	 * @return the kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findAll(
		int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo definition versions
	 */
	@Override
	public List<KaleoDefinitionVersion> findAll(
		int start, int end,
		OrderByComparator<KaleoDefinitionVersion> orderByComparator,
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

		List<KaleoDefinitionVersion> list = null;

		if (useFinderCache) {
			list = (List<KaleoDefinitionVersion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_KALEODEFINITIONVERSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_KALEODEFINITIONVERSION;

				sql = sql.concat(KaleoDefinitionVersionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<KaleoDefinitionVersion>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
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
	 * Removes all the kaleo definition versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoDefinitionVersion kaleoDefinitionVersion : findAll()) {
			remove(kaleoDefinitionVersion);
		}
	}

	/**
	 * Returns the number of kaleo definition versions.
	 *
	 * @return the number of kaleo definition versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_KALEODEFINITIONVERSION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
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
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "kaleoDefinitionVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEODEFINITIONVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoDefinitionVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo definition version persistence.
	 */
	@Activate
	public void activate() {
		KaleoDefinitionVersionModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		KaleoDefinitionVersionModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			KaleoDefinitionVersionModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_N = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_N = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoDefinitionVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			KaleoDefinitionVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			KaleoDefinitionVersionModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_N = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByC_N_V = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			KaleoDefinitionVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			KaleoDefinitionVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			KaleoDefinitionVersionModelImpl.NAME_COLUMN_BITMASK |
			KaleoDefinitionVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByC_N_V = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoDefinitionVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion"),
			true);
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

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_KALEODEFINITIONVERSION =
		"SELECT kaleoDefinitionVersion FROM KaleoDefinitionVersion kaleoDefinitionVersion";

	private static final String _SQL_SELECT_KALEODEFINITIONVERSION_WHERE =
		"SELECT kaleoDefinitionVersion FROM KaleoDefinitionVersion kaleoDefinitionVersion WHERE ";

	private static final String _SQL_COUNT_KALEODEFINITIONVERSION =
		"SELECT COUNT(kaleoDefinitionVersion) FROM KaleoDefinitionVersion kaleoDefinitionVersion";

	private static final String _SQL_COUNT_KALEODEFINITIONVERSION_WHERE =
		"SELECT COUNT(kaleoDefinitionVersion) FROM KaleoDefinitionVersion kaleoDefinitionVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"kaleoDefinitionVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoDefinitionVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoDefinitionVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionVersionPersistenceImpl.class);

	static {
		try {
			Class.forName(KaleoPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}