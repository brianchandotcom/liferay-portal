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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormInstanceImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskFormInstanceModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskFormInstancePersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the kaleo task form instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormInstancePersistence
 * @see com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskFormInstanceUtil
 * @generated
 */
@ProviderType
public class KaleoTaskFormInstancePersistenceImpl extends BasePersistenceImpl<KaleoTaskFormInstance>
	implements KaleoTaskFormInstancePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link KaleoTaskFormInstanceUtil} to access the kaleo task form instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = KaleoTaskFormInstanceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			KaleoTaskFormInstanceModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the kaleo task form instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo task form instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @return the range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<KaleoTaskFormInstance> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoTaskFormInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskFormInstance kaleoTaskFormInstance : list) {
					if ((companyId != kaleoTaskFormInstance.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByCompanyId_First(long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByCompanyId_First(long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		List<KaleoTaskFormInstance> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByCompanyId_Last(long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByCompanyId_Last(long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskFormInstance> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance[] findByCompanyId_PrevAndNext(
		long kaleoTaskFormInstanceId, long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = findByPrimaryKey(kaleoTaskFormInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskFormInstance[] array = new KaleoTaskFormInstanceImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					kaleoTaskFormInstance, companyId, orderByComparator, true);

			array[1] = kaleoTaskFormInstance;

			array[2] = getByCompanyId_PrevAndNext(session,
					kaleoTaskFormInstance, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoTaskFormInstance getByCompanyId_PrevAndNext(
		Session session, KaleoTaskFormInstance kaleoTaskFormInstance,
		long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoTaskFormInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoTaskFormInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task form instances where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoTaskFormInstance kaleoTaskFormInstance : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(kaleoTaskFormInstance);
		}
	}

	/**
	 * Returns the number of kaleo task form instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo task form instances
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEOTASKFORMINSTANCE_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "kaleoTaskFormInstance.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEODEFINITIONID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoDefinitionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByKaleoDefinitionId", new String[] { Long.class.getName() },
			KaleoTaskFormInstanceModelImpl.KALEODEFINITIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KALEODEFINITIONID = new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoDefinitionId", new String[] { Long.class.getName() });

	/**
	 * Returns all the kaleo task form instances where kaleoDefinitionId = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @return the matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId) {
		return findByKaleoDefinitionId(kaleoDefinitionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task form instances where kaleoDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @return the range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end) {
		return findByKaleoDefinitionId(kaleoDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where kaleoDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return findByKaleoDefinitionId(kaleoDefinitionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where kaleoDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONID;
			finderArgs = new Object[] { kaleoDefinitionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEODEFINITIONID;
			finderArgs = new Object[] {
					kaleoDefinitionId,
					
					start, end, orderByComparator
				};
		}

		List<KaleoTaskFormInstance> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoTaskFormInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskFormInstance kaleoTaskFormInstance : list) {
					if ((kaleoDefinitionId != kaleoTaskFormInstance.getKaleoDefinitionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEODEFINITIONID_KALEODEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoDefinitionId);

				if (!pagination) {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByKaleoDefinitionId_First(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByKaleoDefinitionId_First(kaleoDefinitionId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoDefinitionId=");
		msg.append(kaleoDefinitionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoDefinitionId_First(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		List<KaleoTaskFormInstance> list = findByKaleoDefinitionId(kaleoDefinitionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByKaleoDefinitionId_Last(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByKaleoDefinitionId_Last(kaleoDefinitionId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoDefinitionId=");
		msg.append(kaleoDefinitionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoDefinitionId_Last(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		int count = countByKaleoDefinitionId(kaleoDefinitionId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskFormInstance> list = findByKaleoDefinitionId(kaleoDefinitionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance[] findByKaleoDefinitionId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = findByPrimaryKey(kaleoTaskFormInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskFormInstance[] array = new KaleoTaskFormInstanceImpl[3];

			array[0] = getByKaleoDefinitionId_PrevAndNext(session,
					kaleoTaskFormInstance, kaleoDefinitionId,
					orderByComparator, true);

			array[1] = kaleoTaskFormInstance;

			array[2] = getByKaleoDefinitionId_PrevAndNext(session,
					kaleoTaskFormInstance, kaleoDefinitionId,
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

	protected KaleoTaskFormInstance getByKaleoDefinitionId_PrevAndNext(
		Session session, KaleoTaskFormInstance kaleoTaskFormInstance,
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

		query.append(_FINDER_COLUMN_KALEODEFINITIONID_KALEODEFINITIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(kaleoDefinitionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoTaskFormInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoTaskFormInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task form instances where kaleoDefinitionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 */
	@Override
	public void removeByKaleoDefinitionId(long kaleoDefinitionId) {
		for (KaleoTaskFormInstance kaleoTaskFormInstance : findByKaleoDefinitionId(
				kaleoDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(kaleoTaskFormInstance);
		}
	}

	/**
	 * Returns the number of kaleo task form instances where kaleoDefinitionId = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @return the number of matching kaleo task form instances
	 */
	@Override
	public int countByKaleoDefinitionId(long kaleoDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KALEODEFINITIONID;

		Object[] finderArgs = new Object[] { kaleoDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEODEFINITIONID_KALEODEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoDefinitionId);

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

	private static final String _FINDER_COLUMN_KALEODEFINITIONID_KALEODEFINITIONID_2 =
		"kaleoTaskFormInstance.kaleoDefinitionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEOINSTANCEID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoInstanceId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOINSTANCEID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKaleoInstanceId",
			new String[] { Long.class.getName() },
			KaleoTaskFormInstanceModelImpl.KALEOINSTANCEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KALEOINSTANCEID = new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoInstanceId", new String[] { Long.class.getName() });

	/**
	 * Returns all the kaleo task form instances where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId) {
		return findByKaleoInstanceId(kaleoInstanceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task form instances where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @return the range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {
		return findByKaleoInstanceId(kaleoInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return findByKaleoInstanceId(kaleoInstanceId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOINSTANCEID;
			finderArgs = new Object[] { kaleoInstanceId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEOINSTANCEID;
			finderArgs = new Object[] {
					kaleoInstanceId,
					
					start, end, orderByComparator
				};
		}

		List<KaleoTaskFormInstance> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoTaskFormInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskFormInstance kaleoTaskFormInstance : list) {
					if ((kaleoInstanceId != kaleoTaskFormInstance.getKaleoInstanceId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoInstanceId);

				if (!pagination) {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByKaleoInstanceId_First(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByKaleoInstanceId_First(kaleoInstanceId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoInstanceId=");
		msg.append(kaleoInstanceId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoInstanceId_First(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		List<KaleoTaskFormInstance> list = findByKaleoInstanceId(kaleoInstanceId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByKaleoInstanceId_Last(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByKaleoInstanceId_Last(kaleoInstanceId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoInstanceId=");
		msg.append(kaleoInstanceId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoInstanceId_Last(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		int count = countByKaleoInstanceId(kaleoInstanceId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskFormInstance> list = findByKaleoInstanceId(kaleoInstanceId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance[] findByKaleoInstanceId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = findByPrimaryKey(kaleoTaskFormInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskFormInstance[] array = new KaleoTaskFormInstanceImpl[3];

			array[0] = getByKaleoInstanceId_PrevAndNext(session,
					kaleoTaskFormInstance, kaleoInstanceId, orderByComparator,
					true);

			array[1] = kaleoTaskFormInstance;

			array[2] = getByKaleoInstanceId_PrevAndNext(session,
					kaleoTaskFormInstance, kaleoInstanceId, orderByComparator,
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

	protected KaleoTaskFormInstance getByKaleoInstanceId_PrevAndNext(
		Session session, KaleoTaskFormInstance kaleoTaskFormInstance,
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

		query.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(kaleoInstanceId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoTaskFormInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoTaskFormInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task form instances where kaleoInstanceId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 */
	@Override
	public void removeByKaleoInstanceId(long kaleoInstanceId) {
		for (KaleoTaskFormInstance kaleoTaskFormInstance : findByKaleoInstanceId(
				kaleoInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(kaleoTaskFormInstance);
		}
	}

	/**
	 * Returns the number of kaleo task form instances where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the number of matching kaleo task form instances
	 */
	@Override
	public int countByKaleoInstanceId(long kaleoInstanceId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KALEOINSTANCEID;

		Object[] finderArgs = new Object[] { kaleoInstanceId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoInstanceId);

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

	private static final String _FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2 =
		"kaleoTaskFormInstance.kaleoInstanceId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEOTASKID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoTaskId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKaleoTaskId",
			new String[] { Long.class.getName() },
			KaleoTaskFormInstanceModelImpl.KALEOTASKID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KALEOTASKID = new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKaleoTaskId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the kaleo task form instances where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @return the matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoTaskId(long kaleoTaskId) {
		return findByKaleoTaskId(kaleoTaskId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task form instances where kaleoTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @return the range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoTaskId(long kaleoTaskId,
		int start, int end) {
		return findByKaleoTaskId(kaleoTaskId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where kaleoTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoTaskId(long kaleoTaskId,
		int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return findByKaleoTaskId(kaleoTaskId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where kaleoTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoTaskId(long kaleoTaskId,
		int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKID;
			finderArgs = new Object[] { kaleoTaskId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEOTASKID;
			finderArgs = new Object[] { kaleoTaskId, start, end, orderByComparator };
		}

		List<KaleoTaskFormInstance> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoTaskFormInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskFormInstance kaleoTaskFormInstance : list) {
					if ((kaleoTaskId != kaleoTaskFormInstance.getKaleoTaskId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEOTASKID_KALEOTASKID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoTaskId);

				if (!pagination) {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByKaleoTaskId_First(long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByKaleoTaskId_First(kaleoTaskId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoTaskId=");
		msg.append(kaleoTaskId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoTaskId_First(long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		List<KaleoTaskFormInstance> list = findByKaleoTaskId(kaleoTaskId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByKaleoTaskId_Last(long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByKaleoTaskId_Last(kaleoTaskId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoTaskId=");
		msg.append(kaleoTaskId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoTaskId_Last(long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		int count = countByKaleoTaskId(kaleoTaskId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskFormInstance> list = findByKaleoTaskId(kaleoTaskId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	 * @param kaleoTaskId the kaleo task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance[] findByKaleoTaskId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = findByPrimaryKey(kaleoTaskFormInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskFormInstance[] array = new KaleoTaskFormInstanceImpl[3];

			array[0] = getByKaleoTaskId_PrevAndNext(session,
					kaleoTaskFormInstance, kaleoTaskId, orderByComparator, true);

			array[1] = kaleoTaskFormInstance;

			array[2] = getByKaleoTaskId_PrevAndNext(session,
					kaleoTaskFormInstance, kaleoTaskId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoTaskFormInstance getByKaleoTaskId_PrevAndNext(
		Session session, KaleoTaskFormInstance kaleoTaskFormInstance,
		long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

		query.append(_FINDER_COLUMN_KALEOTASKID_KALEOTASKID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(kaleoTaskId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoTaskFormInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoTaskFormInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task form instances where kaleoTaskId = &#63; from the database.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 */
	@Override
	public void removeByKaleoTaskId(long kaleoTaskId) {
		for (KaleoTaskFormInstance kaleoTaskFormInstance : findByKaleoTaskId(
				kaleoTaskId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(kaleoTaskFormInstance);
		}
	}

	/**
	 * Returns the number of kaleo task form instances where kaleoTaskId = &#63;.
	 *
	 * @param kaleoTaskId the kaleo task ID
	 * @return the number of matching kaleo task form instances
	 */
	@Override
	public int countByKaleoTaskId(long kaleoTaskId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KALEOTASKID;

		Object[] finderArgs = new Object[] { kaleoTaskId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEOTASKID_KALEOTASKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoTaskId);

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

	private static final String _FINDER_COLUMN_KALEOTASKID_KALEOTASKID_2 = "kaleoTaskFormInstance.kaleoTaskId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEOTASKINSTANCETOKENID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByKaleoTaskInstanceTokenId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKINSTANCETOKENID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByKaleoTaskInstanceTokenId",
			new String[] { Long.class.getName() },
			KaleoTaskFormInstanceModelImpl.KALEOTASKINSTANCETOKENID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KALEOTASKINSTANCETOKENID =
		new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoTaskInstanceTokenId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {
		return findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @return the range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end) {
		return findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKINSTANCETOKENID;
			finderArgs = new Object[] { kaleoTaskInstanceTokenId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEOTASKINSTANCETOKENID;
			finderArgs = new Object[] {
					kaleoTaskInstanceTokenId,
					
					start, end, orderByComparator
				};
		}

		List<KaleoTaskFormInstance> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoTaskFormInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskFormInstance kaleoTaskFormInstance : list) {
					if ((kaleoTaskInstanceTokenId != kaleoTaskFormInstance.getKaleoTaskInstanceTokenId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEOTASKINSTANCETOKENID_KALEOTASKINSTANCETOKENID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoTaskInstanceTokenId);

				if (!pagination) {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByKaleoTaskInstanceTokenId_First(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByKaleoTaskInstanceTokenId_First(kaleoTaskInstanceTokenId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoTaskInstanceTokenId=");
		msg.append(kaleoTaskInstanceTokenId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the first kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoTaskInstanceTokenId_First(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		List<KaleoTaskFormInstance> list = findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByKaleoTaskInstanceTokenId_Last(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByKaleoTaskInstanceTokenId_Last(kaleoTaskInstanceTokenId,
				orderByComparator);

		if (kaleoTaskFormInstance != null) {
			return kaleoTaskFormInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoTaskInstanceTokenId=");
		msg.append(kaleoTaskInstanceTokenId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTaskFormInstanceException(msg.toString());
	}

	/**
	 * Returns the last kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoTaskInstanceTokenId_Last(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		int count = countByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskFormInstance> list = findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance[] findByKaleoTaskInstanceTokenId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = findByPrimaryKey(kaleoTaskFormInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskFormInstance[] array = new KaleoTaskFormInstanceImpl[3];

			array[0] = getByKaleoTaskInstanceTokenId_PrevAndNext(session,
					kaleoTaskFormInstance, kaleoTaskInstanceTokenId,
					orderByComparator, true);

			array[1] = kaleoTaskFormInstance;

			array[2] = getByKaleoTaskInstanceTokenId_PrevAndNext(session,
					kaleoTaskFormInstance, kaleoTaskInstanceTokenId,
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

	protected KaleoTaskFormInstance getByKaleoTaskInstanceTokenId_PrevAndNext(
		Session session, KaleoTaskFormInstance kaleoTaskFormInstance,
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

		query.append(_FINDER_COLUMN_KALEOTASKINSTANCETOKENID_KALEOTASKINSTANCETOKENID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(kaleoTaskInstanceTokenId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoTaskFormInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoTaskFormInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63; from the database.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 */
	@Override
	public void removeByKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		for (KaleoTaskFormInstance kaleoTaskFormInstance : findByKaleoTaskInstanceTokenId(
				kaleoTaskInstanceTokenId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(kaleoTaskFormInstance);
		}
	}

	/**
	 * Returns the number of kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the number of matching kaleo task form instances
	 */
	@Override
	public int countByKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KALEOTASKINSTANCETOKENID;

		Object[] finderArgs = new Object[] { kaleoTaskInstanceTokenId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEOTASKINSTANCETOKENID_KALEOTASKINSTANCETOKENID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoTaskInstanceTokenId);

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

	private static final String _FINDER_COLUMN_KALEOTASKINSTANCETOKENID_KALEOTASKINSTANCETOKENID_2 =
		"kaleoTaskFormInstance.kaleoTaskInstanceTokenId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_KALEOTASKFORMID = new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByKaleoTaskFormId", new String[] { Long.class.getName() },
			KaleoTaskFormInstanceModelImpl.KALEOTASKFORMID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KALEOTASKFORMID = new FinderPath(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoTaskFormId", new String[] { Long.class.getName() });

	/**
	 * Returns the kaleo task form instance where kaleoTaskFormId = &#63; or throws a {@link NoSuchTaskFormInstanceException} if it could not be found.
	 *
	 * @param kaleoTaskFormId the kaleo task form ID
	 * @return the matching kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByKaleoTaskFormId(long kaleoTaskFormId)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByKaleoTaskFormId(kaleoTaskFormId);

		if (kaleoTaskFormInstance == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("kaleoTaskFormId=");
			msg.append(kaleoTaskFormId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchTaskFormInstanceException(msg.toString());
		}

		return kaleoTaskFormInstance;
	}

	/**
	 * Returns the kaleo task form instance where kaleoTaskFormId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoTaskFormId the kaleo task form ID
	 * @return the matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoTaskFormId(long kaleoTaskFormId) {
		return fetchByKaleoTaskFormId(kaleoTaskFormId, true);
	}

	/**
	 * Returns the kaleo task form instance where kaleoTaskFormId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoTaskFormId the kaleo task form ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByKaleoTaskFormId(long kaleoTaskFormId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { kaleoTaskFormId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_KALEOTASKFORMID,
					finderArgs, this);
		}

		if (result instanceof KaleoTaskFormInstance) {
			KaleoTaskFormInstance kaleoTaskFormInstance = (KaleoTaskFormInstance)result;

			if ((kaleoTaskFormId != kaleoTaskFormInstance.getKaleoTaskFormId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEOTASKFORMID_KALEOTASKFORMID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoTaskFormId);

				List<KaleoTaskFormInstance> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_KALEOTASKFORMID,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"KaleoTaskFormInstancePersistenceImpl.fetchByKaleoTaskFormId(long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KaleoTaskFormInstance kaleoTaskFormInstance = list.get(0);

					result = kaleoTaskFormInstance;

					cacheResult(kaleoTaskFormInstance);

					if ((kaleoTaskFormInstance.getKaleoTaskFormId() != kaleoTaskFormId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_KALEOTASKFORMID,
							finderArgs, kaleoTaskFormInstance);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_KALEOTASKFORMID,
					finderArgs);

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
			return (KaleoTaskFormInstance)result;
		}
	}

	/**
	 * Removes the kaleo task form instance where kaleoTaskFormId = &#63; from the database.
	 *
	 * @param kaleoTaskFormId the kaleo task form ID
	 * @return the kaleo task form instance that was removed
	 */
	@Override
	public KaleoTaskFormInstance removeByKaleoTaskFormId(long kaleoTaskFormId)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = findByKaleoTaskFormId(kaleoTaskFormId);

		return remove(kaleoTaskFormInstance);
	}

	/**
	 * Returns the number of kaleo task form instances where kaleoTaskFormId = &#63;.
	 *
	 * @param kaleoTaskFormId the kaleo task form ID
	 * @return the number of matching kaleo task form instances
	 */
	@Override
	public int countByKaleoTaskFormId(long kaleoTaskFormId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KALEOTASKFORMID;

		Object[] finderArgs = new Object[] { kaleoTaskFormId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEOTASKFORMINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_KALEOTASKFORMID_KALEOTASKFORMID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoTaskFormId);

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

	private static final String _FINDER_COLUMN_KALEOTASKFORMID_KALEOTASKFORMID_2 =
		"kaleoTaskFormInstance.kaleoTaskFormId = ?";

	public KaleoTaskFormInstancePersistenceImpl() {
		setModelClass(KaleoTaskFormInstance.class);
	}

	/**
	 * Caches the kaleo task form instance in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskFormInstance the kaleo task form instance
	 */
	@Override
	public void cacheResult(KaleoTaskFormInstance kaleoTaskFormInstance) {
		entityCache.putResult(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			kaleoTaskFormInstance.getPrimaryKey(), kaleoTaskFormInstance);

		finderCache.putResult(FINDER_PATH_FETCH_BY_KALEOTASKFORMID,
			new Object[] { kaleoTaskFormInstance.getKaleoTaskFormId() },
			kaleoTaskFormInstance);

		kaleoTaskFormInstance.resetOriginalValues();
	}

	/**
	 * Caches the kaleo task form instances in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskFormInstances the kaleo task form instances
	 */
	@Override
	public void cacheResult(List<KaleoTaskFormInstance> kaleoTaskFormInstances) {
		for (KaleoTaskFormInstance kaleoTaskFormInstance : kaleoTaskFormInstances) {
			if (entityCache.getResult(
						KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
						KaleoTaskFormInstanceImpl.class,
						kaleoTaskFormInstance.getPrimaryKey()) == null) {
				cacheResult(kaleoTaskFormInstance);
			}
			else {
				kaleoTaskFormInstance.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all kaleo task form instances.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoTaskFormInstanceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the kaleo task form instance.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoTaskFormInstance kaleoTaskFormInstance) {
		entityCache.removeResult(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			kaleoTaskFormInstance.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((KaleoTaskFormInstanceModelImpl)kaleoTaskFormInstance,
			true);
	}

	@Override
	public void clearCache(List<KaleoTaskFormInstance> kaleoTaskFormInstances) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (KaleoTaskFormInstance kaleoTaskFormInstance : kaleoTaskFormInstances) {
			entityCache.removeResult(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
				KaleoTaskFormInstanceImpl.class,
				kaleoTaskFormInstance.getPrimaryKey());

			clearUniqueFindersCache((KaleoTaskFormInstanceModelImpl)kaleoTaskFormInstance,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoTaskFormInstanceModelImpl kaleoTaskFormInstanceModelImpl) {
		Object[] args = new Object[] {
				kaleoTaskFormInstanceModelImpl.getKaleoTaskFormId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_KALEOTASKFORMID, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_KALEOTASKFORMID, args,
			kaleoTaskFormInstanceModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		KaleoTaskFormInstanceModelImpl kaleoTaskFormInstanceModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					kaleoTaskFormInstanceModelImpl.getKaleoTaskFormId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOTASKFORMID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_KALEOTASKFORMID, args);
		}

		if ((kaleoTaskFormInstanceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_KALEOTASKFORMID.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					kaleoTaskFormInstanceModelImpl.getOriginalKaleoTaskFormId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOTASKFORMID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_KALEOTASKFORMID, args);
		}
	}

	/**
	 * Creates a new kaleo task form instance with the primary key. Does not add the kaleo task form instance to the database.
	 *
	 * @param kaleoTaskFormInstanceId the primary key for the new kaleo task form instance
	 * @return the new kaleo task form instance
	 */
	@Override
	public KaleoTaskFormInstance create(long kaleoTaskFormInstanceId) {
		KaleoTaskFormInstance kaleoTaskFormInstance = new KaleoTaskFormInstanceImpl();

		kaleoTaskFormInstance.setNew(true);
		kaleoTaskFormInstance.setPrimaryKey(kaleoTaskFormInstanceId);

		kaleoTaskFormInstance.setCompanyId(companyProvider.getCompanyId());

		return kaleoTaskFormInstance;
	}

	/**
	 * Removes the kaleo task form instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	 * @return the kaleo task form instance that was removed
	 * @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance remove(long kaleoTaskFormInstanceId)
		throws NoSuchTaskFormInstanceException {
		return remove((Serializable)kaleoTaskFormInstanceId);
	}

	/**
	 * Removes the kaleo task form instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo task form instance
	 * @return the kaleo task form instance that was removed
	 * @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance remove(Serializable primaryKey)
		throws NoSuchTaskFormInstanceException {
		Session session = null;

		try {
			session = openSession();

			KaleoTaskFormInstance kaleoTaskFormInstance = (KaleoTaskFormInstance)session.get(KaleoTaskFormInstanceImpl.class,
					primaryKey);

			if (kaleoTaskFormInstance == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaskFormInstanceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(kaleoTaskFormInstance);
		}
		catch (NoSuchTaskFormInstanceException nsee) {
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
	protected KaleoTaskFormInstance removeImpl(
		KaleoTaskFormInstance kaleoTaskFormInstance) {
		kaleoTaskFormInstance = toUnwrappedModel(kaleoTaskFormInstance);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoTaskFormInstance)) {
				kaleoTaskFormInstance = (KaleoTaskFormInstance)session.get(KaleoTaskFormInstanceImpl.class,
						kaleoTaskFormInstance.getPrimaryKeyObj());
			}

			if (kaleoTaskFormInstance != null) {
				session.delete(kaleoTaskFormInstance);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (kaleoTaskFormInstance != null) {
			clearCache(kaleoTaskFormInstance);
		}

		return kaleoTaskFormInstance;
	}

	@Override
	public KaleoTaskFormInstance updateImpl(
		KaleoTaskFormInstance kaleoTaskFormInstance) {
		kaleoTaskFormInstance = toUnwrappedModel(kaleoTaskFormInstance);

		boolean isNew = kaleoTaskFormInstance.isNew();

		KaleoTaskFormInstanceModelImpl kaleoTaskFormInstanceModelImpl = (KaleoTaskFormInstanceModelImpl)kaleoTaskFormInstance;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (kaleoTaskFormInstance.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoTaskFormInstance.setCreateDate(now);
			}
			else {
				kaleoTaskFormInstance.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!kaleoTaskFormInstanceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoTaskFormInstance.setModifiedDate(now);
			}
			else {
				kaleoTaskFormInstance.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (kaleoTaskFormInstance.isNew()) {
				session.save(kaleoTaskFormInstance);

				kaleoTaskFormInstance.setNew(false);
			}
			else {
				kaleoTaskFormInstance = (KaleoTaskFormInstance)session.merge(kaleoTaskFormInstance);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !KaleoTaskFormInstanceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((kaleoTaskFormInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((kaleoTaskFormInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getOriginalKaleoDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEODEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONID,
					args);

				args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getKaleoDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEODEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONID,
					args);
			}

			if ((kaleoTaskFormInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOINSTANCEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getOriginalKaleoInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOINSTANCEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOINSTANCEID,
					args);

				args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getKaleoInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOINSTANCEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOINSTANCEID,
					args);
			}

			if ((kaleoTaskFormInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getOriginalKaleoTaskId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOTASKID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKID,
					args);

				args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getKaleoTaskId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOTASKID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKID,
					args);
			}

			if ((kaleoTaskFormInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKINSTANCETOKENID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getOriginalKaleoTaskInstanceTokenId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOTASKINSTANCETOKENID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKINSTANCETOKENID,
					args);

				args = new Object[] {
						kaleoTaskFormInstanceModelImpl.getKaleoTaskInstanceTokenId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEOTASKINSTANCETOKENID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEOTASKINSTANCETOKENID,
					args);
			}
		}

		entityCache.putResult(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
			KaleoTaskFormInstanceImpl.class,
			kaleoTaskFormInstance.getPrimaryKey(), kaleoTaskFormInstance, false);

		clearUniqueFindersCache(kaleoTaskFormInstanceModelImpl, false);
		cacheUniqueFindersCache(kaleoTaskFormInstanceModelImpl);

		kaleoTaskFormInstance.resetOriginalValues();

		return kaleoTaskFormInstance;
	}

	protected KaleoTaskFormInstance toUnwrappedModel(
		KaleoTaskFormInstance kaleoTaskFormInstance) {
		if (kaleoTaskFormInstance instanceof KaleoTaskFormInstanceImpl) {
			return kaleoTaskFormInstance;
		}

		KaleoTaskFormInstanceImpl kaleoTaskFormInstanceImpl = new KaleoTaskFormInstanceImpl();

		kaleoTaskFormInstanceImpl.setNew(kaleoTaskFormInstance.isNew());
		kaleoTaskFormInstanceImpl.setPrimaryKey(kaleoTaskFormInstance.getPrimaryKey());

		kaleoTaskFormInstanceImpl.setKaleoTaskFormInstanceId(kaleoTaskFormInstance.getKaleoTaskFormInstanceId());
		kaleoTaskFormInstanceImpl.setGroupId(kaleoTaskFormInstance.getGroupId());
		kaleoTaskFormInstanceImpl.setCompanyId(kaleoTaskFormInstance.getCompanyId());
		kaleoTaskFormInstanceImpl.setUserId(kaleoTaskFormInstance.getUserId());
		kaleoTaskFormInstanceImpl.setUserName(kaleoTaskFormInstance.getUserName());
		kaleoTaskFormInstanceImpl.setCreateDate(kaleoTaskFormInstance.getCreateDate());
		kaleoTaskFormInstanceImpl.setModifiedDate(kaleoTaskFormInstance.getModifiedDate());
		kaleoTaskFormInstanceImpl.setKaleoDefinitionId(kaleoTaskFormInstance.getKaleoDefinitionId());
		kaleoTaskFormInstanceImpl.setKaleoInstanceId(kaleoTaskFormInstance.getKaleoInstanceId());
		kaleoTaskFormInstanceImpl.setKaleoTaskId(kaleoTaskFormInstance.getKaleoTaskId());
		kaleoTaskFormInstanceImpl.setKaleoTaskInstanceTokenId(kaleoTaskFormInstance.getKaleoTaskInstanceTokenId());
		kaleoTaskFormInstanceImpl.setKaleoTaskFormId(kaleoTaskFormInstance.getKaleoTaskFormId());
		kaleoTaskFormInstanceImpl.setFormValues(kaleoTaskFormInstance.getFormValues());
		kaleoTaskFormInstanceImpl.setFormValueEntryGroupId(kaleoTaskFormInstance.getFormValueEntryGroupId());
		kaleoTaskFormInstanceImpl.setFormValueEntryId(kaleoTaskFormInstance.getFormValueEntryId());
		kaleoTaskFormInstanceImpl.setFormValueEntryUuid(kaleoTaskFormInstance.getFormValueEntryUuid());
		kaleoTaskFormInstanceImpl.setMetadata(kaleoTaskFormInstance.getMetadata());

		return kaleoTaskFormInstanceImpl;
	}

	/**
	 * Returns the kaleo task form instance with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo task form instance
	 * @return the kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaskFormInstanceException {
		KaleoTaskFormInstance kaleoTaskFormInstance = fetchByPrimaryKey(primaryKey);

		if (kaleoTaskFormInstance == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaskFormInstanceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return kaleoTaskFormInstance;
	}

	/**
	 * Returns the kaleo task form instance with the primary key or throws a {@link NoSuchTaskFormInstanceException} if it could not be found.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	 * @return the kaleo task form instance
	 * @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance findByPrimaryKey(long kaleoTaskFormInstanceId)
		throws NoSuchTaskFormInstanceException {
		return findByPrimaryKey((Serializable)kaleoTaskFormInstanceId);
	}

	/**
	 * Returns the kaleo task form instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo task form instance
	 * @return the kaleo task form instance, or <code>null</code> if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
				KaleoTaskFormInstanceImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		KaleoTaskFormInstance kaleoTaskFormInstance = (KaleoTaskFormInstance)serializable;

		if (kaleoTaskFormInstance == null) {
			Session session = null;

			try {
				session = openSession();

				kaleoTaskFormInstance = (KaleoTaskFormInstance)session.get(KaleoTaskFormInstanceImpl.class,
						primaryKey);

				if (kaleoTaskFormInstance != null) {
					cacheResult(kaleoTaskFormInstance);
				}
				else {
					entityCache.putResult(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
						KaleoTaskFormInstanceImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
					KaleoTaskFormInstanceImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return kaleoTaskFormInstance;
	}

	/**
	 * Returns the kaleo task form instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	 * @return the kaleo task form instance, or <code>null</code> if a kaleo task form instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskFormInstance fetchByPrimaryKey(long kaleoTaskFormInstanceId) {
		return fetchByPrimaryKey((Serializable)kaleoTaskFormInstanceId);
	}

	@Override
	public Map<Serializable, KaleoTaskFormInstance> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, KaleoTaskFormInstance> map = new HashMap<Serializable, KaleoTaskFormInstance>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			KaleoTaskFormInstance kaleoTaskFormInstance = fetchByPrimaryKey(primaryKey);

			if (kaleoTaskFormInstance != null) {
				map.put(primaryKey, kaleoTaskFormInstance);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
					KaleoTaskFormInstanceImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (KaleoTaskFormInstance)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (KaleoTaskFormInstance kaleoTaskFormInstance : (List<KaleoTaskFormInstance>)q.list()) {
				map.put(kaleoTaskFormInstance.getPrimaryKeyObj(),
					kaleoTaskFormInstance);

				cacheResult(kaleoTaskFormInstance);

				uncachedPrimaryKeys.remove(kaleoTaskFormInstance.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(KaleoTaskFormInstanceModelImpl.ENTITY_CACHE_ENABLED,
					KaleoTaskFormInstanceImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the kaleo task form instances.
	 *
	 * @return the kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task form instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @return the range of kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findAll(int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task form instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task form instances
	 * @param end the upper bound of the range of kaleo task form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of kaleo task form instances
	 */
	@Override
	public List<KaleoTaskFormInstance> findAll(int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<KaleoTaskFormInstance> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoTaskFormInstance>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_KALEOTASKFORMINSTANCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOTASKFORMINSTANCE;

				if (pagination) {
					sql = sql.concat(KaleoTaskFormInstanceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoTaskFormInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the kaleo task form instances from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoTaskFormInstance kaleoTaskFormInstance : findAll()) {
			remove(kaleoTaskFormInstance);
		}
	}

	/**
	 * Returns the number of kaleo task form instances.
	 *
	 * @return the number of kaleo task form instances
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_KALEOTASKFORMINSTANCE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoTaskFormInstanceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo task form instance persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(KaleoTaskFormInstanceImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_KALEOTASKFORMINSTANCE = "SELECT kaleoTaskFormInstance FROM KaleoTaskFormInstance kaleoTaskFormInstance";
	private static final String _SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE_PKS_IN = "SELECT kaleoTaskFormInstance FROM KaleoTaskFormInstance kaleoTaskFormInstance WHERE kaleoTaskFormInstanceId IN (";
	private static final String _SQL_SELECT_KALEOTASKFORMINSTANCE_WHERE = "SELECT kaleoTaskFormInstance FROM KaleoTaskFormInstance kaleoTaskFormInstance WHERE ";
	private static final String _SQL_COUNT_KALEOTASKFORMINSTANCE = "SELECT COUNT(kaleoTaskFormInstance) FROM KaleoTaskFormInstance kaleoTaskFormInstance";
	private static final String _SQL_COUNT_KALEOTASKFORMINSTANCE_WHERE = "SELECT COUNT(kaleoTaskFormInstance) FROM KaleoTaskFormInstance kaleoTaskFormInstance WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoTaskFormInstance.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No KaleoTaskFormInstance exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No KaleoTaskFormInstance exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(KaleoTaskFormInstancePersistenceImpl.class);
}