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

import com.liferay.petra.string.StringPool;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.kaleo.exception.NoSuchNodeException;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoNodeModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNodePersistence;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the kaleo node service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodePersistence
 * @see com.liferay.portal.workflow.kaleo.service.persistence.KaleoNodeUtil
 * @generated
 */
@ProviderType
public class KaleoNodePersistenceImpl extends BasePersistenceImpl<KaleoNode>
	implements KaleoNodePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link KaleoNodeUtil} to access the kaleo node persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = KaleoNodeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, KaleoNodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, KaleoNodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, KaleoNodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, KaleoNodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			KaleoNodeModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the kaleo nodes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo nodes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator,
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

		List<KaleoNode> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoNode>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoNode kaleoNode : list) {
					if ((companyId != kaleoNode.getCompanyId())) {
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

			query.append(_SQL_SELECT_KALEONODE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<KaleoNode>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoNode>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByCompanyId_First(long companyId,
		OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchNodeException(msg.toString());
	}

	/**
	 * Returns the first kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByCompanyId_First(long companyId,
		OrderByComparator<KaleoNode> orderByComparator) {
		List<KaleoNode> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByCompanyId_Last(long companyId,
		OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = fetchByCompanyId_Last(companyId, orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchNodeException(msg.toString());
	}

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByCompanyId_Last(long companyId,
		OrderByComparator<KaleoNode> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoNode> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo nodes before and after the current kaleo node in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoNodeId the primary key of the current kaleo node
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode[] findByCompanyId_PrevAndNext(long kaleoNodeId,
		long companyId, OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = findByPrimaryKey(kaleoNodeId);

		Session session = null;

		try {
			session = openSession();

			KaleoNode[] array = new KaleoNodeImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, kaleoNode,
					companyId, orderByComparator, true);

			array[1] = kaleoNode;

			array[2] = getByCompanyId_PrevAndNext(session, kaleoNode,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoNode getByCompanyId_PrevAndNext(Session session,
		KaleoNode kaleoNode, long companyId,
		OrderByComparator<KaleoNode> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEONODE_WHERE);

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
			query.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoNode);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoNode> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo nodes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoNode kaleoNode : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(kaleoNode);
		}
	}

	/**
	 * Returns the number of kaleo nodes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo nodes
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEONODE_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "kaleoNode.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEODEFINITIONVERSIONID =
		new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, KaleoNodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByKaleoDefinitionVersionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONVERSIONID =
		new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, KaleoNodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByKaleoDefinitionVersionId",
			new String[] { Long.class.getName() },
			KaleoNodeModelImpl.KALEODEFINITIONVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KALEODEFINITIONVERSIONID =
		new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoDefinitionVersionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {
		return findByKaleoDefinitionVersionId(kaleoDefinitionVersionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {
		return findByKaleoDefinitionVersionId(kaleoDefinitionVersionId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator) {
		return findByKaleoDefinitionVersionId(kaleoDefinitionVersionId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONVERSIONID;
			finderArgs = new Object[] { kaleoDefinitionVersionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_KALEODEFINITIONVERSIONID;
			finderArgs = new Object[] {
					kaleoDefinitionVersionId,
					
					start, end, orderByComparator
				};
		}

		List<KaleoNode> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoNode>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoNode kaleoNode : list) {
					if ((kaleoDefinitionVersionId != kaleoNode.getKaleoDefinitionVersionId())) {
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

			query.append(_SQL_SELECT_KALEONODE_WHERE);

			query.append(_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoDefinitionVersionId);

				if (!pagination) {
					list = (List<KaleoNode>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoNode>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = fetchByKaleoDefinitionVersionId_First(kaleoDefinitionVersionId,
				orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoDefinitionVersionId=");
		msg.append(kaleoDefinitionVersionId);

		msg.append("}");

		throw new NoSuchNodeException(msg.toString());
	}

	/**
	 * Returns the first kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator) {
		List<KaleoNode> list = findByKaleoDefinitionVersionId(kaleoDefinitionVersionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = fetchByKaleoDefinitionVersionId_Last(kaleoDefinitionVersionId,
				orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("kaleoDefinitionVersionId=");
		msg.append(kaleoDefinitionVersionId);

		msg.append("}");

		throw new NoSuchNodeException(msg.toString());
	}

	/**
	 * Returns the last kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator) {
		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoNode> list = findByKaleoDefinitionVersionId(kaleoDefinitionVersionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo nodes before and after the current kaleo node in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoNodeId the primary key of the current kaleo node
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode[] findByKaleoDefinitionVersionId_PrevAndNext(
		long kaleoNodeId, long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = findByPrimaryKey(kaleoNodeId);

		Session session = null;

		try {
			session = openSession();

			KaleoNode[] array = new KaleoNodeImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(session,
					kaleoNode, kaleoDefinitionVersionId, orderByComparator, true);

			array[1] = kaleoNode;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(session,
					kaleoNode, kaleoDefinitionVersionId, orderByComparator,
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

	protected KaleoNode getByKaleoDefinitionVersionId_PrevAndNext(
		Session session, KaleoNode kaleoNode, long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_KALEONODE_WHERE);

		query.append(_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

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
			query.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoNode);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoNode> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo nodes where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		for (KaleoNode kaleoNode : findByKaleoDefinitionVersionId(
				kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(kaleoNode);
		}
	}

	/**
	 * Returns the number of kaleo nodes where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo nodes
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KALEODEFINITIONVERSIONID;

		Object[] finderArgs = new Object[] { kaleoDefinitionVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_KALEONODE_WHERE);

			query.append(_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(kaleoDefinitionVersionId);

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

	private static final String _FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2 =
		"kaleoNode.kaleoDefinitionVersionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_KDVI = new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, KaleoNodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_KDVI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_KDVI =
		new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, KaleoNodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_KDVI",
			new String[] { Long.class.getName(), Long.class.getName() },
			KaleoNodeModelImpl.COMPANYID_COLUMN_BITMASK |
			KaleoNodeModelImpl.KALEODEFINITIONVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_KDVI = new FinderPath(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_KDVI",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByC_KDVI(long companyId,
		long kaleoDefinitionVersionId) {
		return findByC_KDVI(companyId, kaleoDefinitionVersionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByC_KDVI(long companyId,
		long kaleoDefinitionVersionId, int start, int end) {
		return findByC_KDVI(companyId, kaleoDefinitionVersionId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByC_KDVI(long companyId,
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator) {
		return findByC_KDVI(companyId, kaleoDefinitionVersionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching kaleo nodes
	 */
	@Override
	public List<KaleoNode> findByC_KDVI(long companyId,
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoNode> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_KDVI;
			finderArgs = new Object[] { companyId, kaleoDefinitionVersionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_KDVI;
			finderArgs = new Object[] {
					companyId, kaleoDefinitionVersionId,
					
					start, end, orderByComparator
				};
		}

		List<KaleoNode> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoNode>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoNode kaleoNode : list) {
					if ((companyId != kaleoNode.getCompanyId()) ||
							(kaleoDefinitionVersionId != kaleoNode.getKaleoDefinitionVersionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_KALEONODE_WHERE);

			query.append(_FINDER_COLUMN_C_KDVI_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_KDVI_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(kaleoDefinitionVersionId);

				if (!pagination) {
					list = (List<KaleoNode>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoNode>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByC_KDVI_First(long companyId,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = fetchByC_KDVI_First(companyId,
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", kaleoDefinitionVersionId=");
		msg.append(kaleoDefinitionVersionId);

		msg.append("}");

		throw new NoSuchNodeException(msg.toString());
	}

	/**
	 * Returns the first kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByC_KDVI_First(long companyId,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator) {
		List<KaleoNode> list = findByC_KDVI(companyId,
				kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node
	 * @throws NoSuchNodeException if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode findByC_KDVI_Last(long companyId,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = fetchByC_KDVI_Last(companyId,
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoNode != null) {
			return kaleoNode;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", kaleoDefinitionVersionId=");
		msg.append(kaleoDefinitionVersionId);

		msg.append("}");

		throw new NoSuchNodeException(msg.toString());
	}

	/**
	 * Returns the last kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node, or <code>null</code> if a matching kaleo node could not be found
	 */
	@Override
	public KaleoNode fetchByC_KDVI_Last(long companyId,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator) {
		int count = countByC_KDVI(companyId, kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoNode> list = findByC_KDVI(companyId,
				kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo nodes before and after the current kaleo node in the ordered set where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoNodeId the primary key of the current kaleo node
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode[] findByC_KDVI_PrevAndNext(long kaleoNodeId,
		long companyId, long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = findByPrimaryKey(kaleoNodeId);

		Session session = null;

		try {
			session = openSession();

			KaleoNode[] array = new KaleoNodeImpl[3];

			array[0] = getByC_KDVI_PrevAndNext(session, kaleoNode, companyId,
					kaleoDefinitionVersionId, orderByComparator, true);

			array[1] = kaleoNode;

			array[2] = getByC_KDVI_PrevAndNext(session, kaleoNode, companyId,
					kaleoDefinitionVersionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoNode getByC_KDVI_PrevAndNext(Session session,
		KaleoNode kaleoNode, long companyId, long kaleoDefinitionVersionId,
		OrderByComparator<KaleoNode> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_KALEONODE_WHERE);

		query.append(_FINDER_COLUMN_C_KDVI_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_KDVI_KALEODEFINITIONVERSIONID_2);

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
			query.append(KaleoNodeModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(kaleoNode);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<KaleoNode> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByC_KDVI(long companyId, long kaleoDefinitionVersionId) {
		for (KaleoNode kaleoNode : findByC_KDVI(companyId,
				kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(kaleoNode);
		}
	}

	/**
	 * Returns the number of kaleo nodes where companyId = &#63; and kaleoDefinitionVersionId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo nodes
	 */
	@Override
	public int countByC_KDVI(long companyId, long kaleoDefinitionVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_KDVI;

		Object[] finderArgs = new Object[] { companyId, kaleoDefinitionVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_KALEONODE_WHERE);

			query.append(_FINDER_COLUMN_C_KDVI_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_KDVI_KALEODEFINITIONVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(kaleoDefinitionVersionId);

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

	private static final String _FINDER_COLUMN_C_KDVI_COMPANYID_2 = "kaleoNode.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_KDVI_KALEODEFINITIONVERSIONID_2 =
		"kaleoNode.kaleoDefinitionVersionId = ?";

	public KaleoNodePersistenceImpl() {
		setModelClass(KaleoNode.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");
			dbColumnNames.put("initial", "initial_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the kaleo node in the entity cache if it is enabled.
	 *
	 * @param kaleoNode the kaleo node
	 */
	@Override
	public void cacheResult(KaleoNode kaleoNode) {
		entityCache.putResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeImpl.class, kaleoNode.getPrimaryKey(), kaleoNode);

		kaleoNode.resetOriginalValues();
	}

	/**
	 * Caches the kaleo nodes in the entity cache if it is enabled.
	 *
	 * @param kaleoNodes the kaleo nodes
	 */
	@Override
	public void cacheResult(List<KaleoNode> kaleoNodes) {
		for (KaleoNode kaleoNode : kaleoNodes) {
			if (entityCache.getResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
						KaleoNodeImpl.class, kaleoNode.getPrimaryKey()) == null) {
				cacheResult(kaleoNode);
			}
			else {
				kaleoNode.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all kaleo nodes.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoNodeImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the kaleo node.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoNode kaleoNode) {
		entityCache.removeResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeImpl.class, kaleoNode.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<KaleoNode> kaleoNodes) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (KaleoNode kaleoNode : kaleoNodes) {
			entityCache.removeResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
				KaleoNodeImpl.class, kaleoNode.getPrimaryKey());
		}
	}

	/**
	 * Creates a new kaleo node with the primary key. Does not add the kaleo node to the database.
	 *
	 * @param kaleoNodeId the primary key for the new kaleo node
	 * @return the new kaleo node
	 */
	@Override
	public KaleoNode create(long kaleoNodeId) {
		KaleoNode kaleoNode = new KaleoNodeImpl();

		kaleoNode.setNew(true);
		kaleoNode.setPrimaryKey(kaleoNodeId);

		kaleoNode.setCompanyId(companyProvider.getCompanyId());

		return kaleoNode;
	}

	/**
	 * Removes the kaleo node with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node that was removed
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode remove(long kaleoNodeId) throws NoSuchNodeException {
		return remove((Serializable)kaleoNodeId);
	}

	/**
	 * Removes the kaleo node with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo node
	 * @return the kaleo node that was removed
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode remove(Serializable primaryKey) throws NoSuchNodeException {
		Session session = null;

		try {
			session = openSession();

			KaleoNode kaleoNode = (KaleoNode)session.get(KaleoNodeImpl.class,
					primaryKey);

			if (kaleoNode == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNodeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(kaleoNode);
		}
		catch (NoSuchNodeException nsee) {
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
	protected KaleoNode removeImpl(KaleoNode kaleoNode) {
		kaleoNode = toUnwrappedModel(kaleoNode);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoNode)) {
				kaleoNode = (KaleoNode)session.get(KaleoNodeImpl.class,
						kaleoNode.getPrimaryKeyObj());
			}

			if (kaleoNode != null) {
				session.delete(kaleoNode);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (kaleoNode != null) {
			clearCache(kaleoNode);
		}

		return kaleoNode;
	}

	@Override
	public KaleoNode updateImpl(KaleoNode kaleoNode) {
		kaleoNode = toUnwrappedModel(kaleoNode);

		boolean isNew = kaleoNode.isNew();

		KaleoNodeModelImpl kaleoNodeModelImpl = (KaleoNodeModelImpl)kaleoNode;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (kaleoNode.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoNode.setCreateDate(now);
			}
			else {
				kaleoNode.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!kaleoNodeModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoNode.setModifiedDate(now);
			}
			else {
				kaleoNode.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (kaleoNode.isNew()) {
				session.save(kaleoNode);

				kaleoNode.setNew(false);
			}
			else {
				kaleoNode = (KaleoNode)session.merge(kaleoNode);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!KaleoNodeModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { kaleoNodeModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] { kaleoNodeModelImpl.getKaleoDefinitionVersionId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEODEFINITIONVERSIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONVERSIONID,
				args);

			args = new Object[] {
					kaleoNodeModelImpl.getCompanyId(),
					kaleoNodeModelImpl.getKaleoDefinitionVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_KDVI, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_KDVI,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((kaleoNodeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoNodeModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { kaleoNodeModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((kaleoNodeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONVERSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoNodeModelImpl.getOriginalKaleoDefinitionVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEODEFINITIONVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONVERSIONID,
					args);

				args = new Object[] {
						kaleoNodeModelImpl.getKaleoDefinitionVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_KALEODEFINITIONVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KALEODEFINITIONVERSIONID,
					args);
			}

			if ((kaleoNodeModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_KDVI.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						kaleoNodeModelImpl.getOriginalCompanyId(),
						kaleoNodeModelImpl.getOriginalKaleoDefinitionVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_KDVI, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_KDVI,
					args);

				args = new Object[] {
						kaleoNodeModelImpl.getCompanyId(),
						kaleoNodeModelImpl.getKaleoDefinitionVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_KDVI, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_KDVI,
					args);
			}
		}

		entityCache.putResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
			KaleoNodeImpl.class, kaleoNode.getPrimaryKey(), kaleoNode, false);

		kaleoNode.resetOriginalValues();

		return kaleoNode;
	}

	protected KaleoNode toUnwrappedModel(KaleoNode kaleoNode) {
		if (kaleoNode instanceof KaleoNodeImpl) {
			return kaleoNode;
		}

		KaleoNodeImpl kaleoNodeImpl = new KaleoNodeImpl();

		kaleoNodeImpl.setNew(kaleoNode.isNew());
		kaleoNodeImpl.setPrimaryKey(kaleoNode.getPrimaryKey());

		kaleoNodeImpl.setKaleoNodeId(kaleoNode.getKaleoNodeId());
		kaleoNodeImpl.setGroupId(kaleoNode.getGroupId());
		kaleoNodeImpl.setCompanyId(kaleoNode.getCompanyId());
		kaleoNodeImpl.setUserId(kaleoNode.getUserId());
		kaleoNodeImpl.setUserName(kaleoNode.getUserName());
		kaleoNodeImpl.setCreateDate(kaleoNode.getCreateDate());
		kaleoNodeImpl.setModifiedDate(kaleoNode.getModifiedDate());
		kaleoNodeImpl.setKaleoDefinitionVersionId(kaleoNode.getKaleoDefinitionVersionId());
		kaleoNodeImpl.setName(kaleoNode.getName());
		kaleoNodeImpl.setMetadata(kaleoNode.getMetadata());
		kaleoNodeImpl.setDescription(kaleoNode.getDescription());
		kaleoNodeImpl.setType(kaleoNode.getType());
		kaleoNodeImpl.setInitial(kaleoNode.isInitial());
		kaleoNodeImpl.setTerminal(kaleoNode.isTerminal());

		return kaleoNodeImpl;
	}

	/**
	 * Returns the kaleo node with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo node
	 * @return the kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode findByPrimaryKey(Serializable primaryKey)
		throws NoSuchNodeException {
		KaleoNode kaleoNode = fetchByPrimaryKey(primaryKey);

		if (kaleoNode == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNodeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return kaleoNode;
	}

	/**
	 * Returns the kaleo node with the primary key or throws a {@link NoSuchNodeException} if it could not be found.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node
	 * @throws NoSuchNodeException if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode findByPrimaryKey(long kaleoNodeId)
		throws NoSuchNodeException {
		return findByPrimaryKey((Serializable)kaleoNodeId);
	}

	/**
	 * Returns the kaleo node with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo node
	 * @return the kaleo node, or <code>null</code> if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
				KaleoNodeImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		KaleoNode kaleoNode = (KaleoNode)serializable;

		if (kaleoNode == null) {
			Session session = null;

			try {
				session = openSession();

				kaleoNode = (KaleoNode)session.get(KaleoNodeImpl.class,
						primaryKey);

				if (kaleoNode != null) {
					cacheResult(kaleoNode);
				}
				else {
					entityCache.putResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
						KaleoNodeImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
					KaleoNodeImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return kaleoNode;
	}

	/**
	 * Returns the kaleo node with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoNodeId the primary key of the kaleo node
	 * @return the kaleo node, or <code>null</code> if a kaleo node with the primary key could not be found
	 */
	@Override
	public KaleoNode fetchByPrimaryKey(long kaleoNodeId) {
		return fetchByPrimaryKey((Serializable)kaleoNodeId);
	}

	@Override
	public Map<Serializable, KaleoNode> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, KaleoNode> map = new HashMap<Serializable, KaleoNode>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			KaleoNode kaleoNode = fetchByPrimaryKey(primaryKey);

			if (kaleoNode != null) {
				map.put(primaryKey, kaleoNode);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
					KaleoNodeImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (KaleoNode)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_KALEONODE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (KaleoNode kaleoNode : (List<KaleoNode>)q.list()) {
				map.put(kaleoNode.getPrimaryKeyObj(), kaleoNode);

				cacheResult(kaleoNode);

				uncachedPrimaryKeys.remove(kaleoNode.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(KaleoNodeModelImpl.ENTITY_CACHE_ENABLED,
					KaleoNodeImpl.class, primaryKey, nullModel);
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
	 * Returns all the kaleo nodes.
	 *
	 * @return the kaleo nodes
	 */
	@Override
	public List<KaleoNode> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @return the range of kaleo nodes
	 */
	@Override
	public List<KaleoNode> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo nodes
	 */
	@Override
	public List<KaleoNode> findAll(int start, int end,
		OrderByComparator<KaleoNode> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo nodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoNodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo nodes
	 * @param end the upper bound of the range of kaleo nodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of kaleo nodes
	 */
	@Override
	public List<KaleoNode> findAll(int start, int end,
		OrderByComparator<KaleoNode> orderByComparator,
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

		List<KaleoNode> list = null;

		if (retrieveFromCache) {
			list = (List<KaleoNode>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_KALEONODE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_KALEONODE;

				if (pagination) {
					sql = sql.concat(KaleoNodeModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<KaleoNode>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<KaleoNode>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the kaleo nodes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoNode kaleoNode : findAll()) {
			remove(kaleoNode);
		}
	}

	/**
	 * Returns the number of kaleo nodes.
	 *
	 * @return the number of kaleo nodes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_KALEONODE);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoNodeModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo node persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(KaleoNodeImpl.class.getName());
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
	private static final String _SQL_SELECT_KALEONODE = "SELECT kaleoNode FROM KaleoNode kaleoNode";
	private static final String _SQL_SELECT_KALEONODE_WHERE_PKS_IN = "SELECT kaleoNode FROM KaleoNode kaleoNode WHERE kaleoNodeId IN (";
	private static final String _SQL_SELECT_KALEONODE_WHERE = "SELECT kaleoNode FROM KaleoNode kaleoNode WHERE ";
	private static final String _SQL_COUNT_KALEONODE = "SELECT COUNT(kaleoNode) FROM KaleoNode kaleoNode";
	private static final String _SQL_COUNT_KALEONODE_WHERE = "SELECT COUNT(kaleoNode) FROM KaleoNode kaleoNode WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoNode.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No KaleoNode exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No KaleoNode exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(KaleoNodePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type", "initial"
			});
}