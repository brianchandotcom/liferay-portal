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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.impl.CommerceShipmentImpl;
import com.liferay.commerce.model.impl.CommerceShipmentModelImpl;
import com.liferay.commerce.service.persistence.CommerceShipmentPersistence;

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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce shipment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentPersistence
 * @see com.liferay.commerce.service.persistence.CommerceShipmentUtil
 * @generated
 */
@ProviderType
public class CommerceShipmentPersistenceImpl extends BasePersistenceImpl<CommerceShipment>
	implements CommerceShipmentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceShipmentUtil} to access the commerce shipment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceShipmentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceShipmentModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceShipmentModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce shipments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceShipment> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceShipment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<CommerceShipment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShipment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShipment commerceShipment : list) {
					if ((groupId != commerceShipment.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPMENT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShipmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceShipment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShipment>)QueryUtil.list(q,
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
	 * Returns the first commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment findByGroupId_First(long groupId,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceShipment != null) {
			return commerceShipment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchShipmentException(msg.toString());
	}

	/**
	 * Returns the first commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceShipment> orderByComparator) {
		List<CommerceShipment> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment findByGroupId_Last(long groupId,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceShipment != null) {
			return commerceShipment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchShipmentException(msg.toString());
	}

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceShipment> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceShipment> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipments before and after the current commerce shipment in the ordered set where groupId = &#63;.
	 *
	 * @param commerceShipmentId the primary key of the current commerce shipment
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment[] findByGroupId_PrevAndNext(
		long commerceShipmentId, long groupId,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = findByPrimaryKey(commerceShipmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceShipment[] array = new CommerceShipmentImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, commerceShipment,
					groupId, orderByComparator, true);

			array[1] = commerceShipment;

			array[2] = getByGroupId_PrevAndNext(session, commerceShipment,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShipment getByGroupId_PrevAndNext(Session session,
		CommerceShipment commerceShipment, long groupId,
		OrderByComparator<CommerceShipment> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCESHIPMENT_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(CommerceShipmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShipment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShipment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipments where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceShipment commerceShipment : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceShipment);
		}
	}

	/**
	 * Returns the number of commerce shipments where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce shipments
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCESHIPMENT_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceShipment.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SITEGROUPID =
		new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBySiteGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITEGROUPID =
		new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySiteGroupId",
			new String[] { Long.class.getName() },
			CommerceShipmentModelImpl.SITEGROUPID_COLUMN_BITMASK |
			CommerceShipmentModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SITEGROUPID = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySiteGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce shipments where siteGroupId = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @return the matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findBySiteGroupId(long siteGroupId) {
		return findBySiteGroupId(siteGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipments where siteGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param siteGroupId the site group ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findBySiteGroupId(long siteGroupId,
		int start, int end) {
		return findBySiteGroupId(siteGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipments where siteGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param siteGroupId the site group ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findBySiteGroupId(long siteGroupId,
		int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator) {
		return findBySiteGroupId(siteGroupId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce shipments where siteGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param siteGroupId the site group ID
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findBySiteGroupId(long siteGroupId,
		int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITEGROUPID;
			finderArgs = new Object[] { siteGroupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SITEGROUPID;
			finderArgs = new Object[] { siteGroupId, start, end, orderByComparator };
		}

		List<CommerceShipment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShipment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShipment commerceShipment : list) {
					if ((siteGroupId != commerceShipment.getSiteGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPMENT_WHERE);

			query.append(_FINDER_COLUMN_SITEGROUPID_SITEGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShipmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(siteGroupId);

				if (!pagination) {
					list = (List<CommerceShipment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShipment>)QueryUtil.list(q,
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
	 * Returns the first commerce shipment in the ordered set where siteGroupId = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment findBySiteGroupId_First(long siteGroupId,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = fetchBySiteGroupId_First(siteGroupId,
				orderByComparator);

		if (commerceShipment != null) {
			return commerceShipment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("siteGroupId=");
		msg.append(siteGroupId);

		msg.append("}");

		throw new NoSuchShipmentException(msg.toString());
	}

	/**
	 * Returns the first commerce shipment in the ordered set where siteGroupId = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment fetchBySiteGroupId_First(long siteGroupId,
		OrderByComparator<CommerceShipment> orderByComparator) {
		List<CommerceShipment> list = findBySiteGroupId(siteGroupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipment in the ordered set where siteGroupId = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment findBySiteGroupId_Last(long siteGroupId,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = fetchBySiteGroupId_Last(siteGroupId,
				orderByComparator);

		if (commerceShipment != null) {
			return commerceShipment;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("siteGroupId=");
		msg.append(siteGroupId);

		msg.append("}");

		throw new NoSuchShipmentException(msg.toString());
	}

	/**
	 * Returns the last commerce shipment in the ordered set where siteGroupId = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment fetchBySiteGroupId_Last(long siteGroupId,
		OrderByComparator<CommerceShipment> orderByComparator) {
		int count = countBySiteGroupId(siteGroupId);

		if (count == 0) {
			return null;
		}

		List<CommerceShipment> list = findBySiteGroupId(siteGroupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipments before and after the current commerce shipment in the ordered set where siteGroupId = &#63;.
	 *
	 * @param commerceShipmentId the primary key of the current commerce shipment
	 * @param siteGroupId the site group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment[] findBySiteGroupId_PrevAndNext(
		long commerceShipmentId, long siteGroupId,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = findByPrimaryKey(commerceShipmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceShipment[] array = new CommerceShipmentImpl[3];

			array[0] = getBySiteGroupId_PrevAndNext(session, commerceShipment,
					siteGroupId, orderByComparator, true);

			array[1] = commerceShipment;

			array[2] = getBySiteGroupId_PrevAndNext(session, commerceShipment,
					siteGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShipment getBySiteGroupId_PrevAndNext(Session session,
		CommerceShipment commerceShipment, long siteGroupId,
		OrderByComparator<CommerceShipment> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCESHIPMENT_WHERE);

		query.append(_FINDER_COLUMN_SITEGROUPID_SITEGROUPID_2);

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
			query.append(CommerceShipmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(siteGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShipment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShipment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipments where siteGroupId = &#63; from the database.
	 *
	 * @param siteGroupId the site group ID
	 */
	@Override
	public void removeBySiteGroupId(long siteGroupId) {
		for (CommerceShipment commerceShipment : findBySiteGroupId(
				siteGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceShipment);
		}
	}

	/**
	 * Returns the number of commerce shipments where siteGroupId = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @return the number of matching commerce shipments
	 */
	@Override
	public int countBySiteGroupId(long siteGroupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SITEGROUPID;

		Object[] finderArgs = new Object[] { siteGroupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCESHIPMENT_WHERE);

			query.append(_FINDER_COLUMN_SITEGROUPID_SITEGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(siteGroupId);

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

	private static final String _FINDER_COLUMN_SITEGROUPID_SITEGROUPID_2 = "commerceShipment.siteGroupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			CommerceShipmentModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceShipmentModelImpl.STATUS_COLUMN_BITMASK |
			CommerceShipmentModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByG_S(long groupId, int status) {
		return findByG_S(groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByG_S(long groupId, int status,
		int start, int end) {
		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByG_S(long groupId, int status,
		int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator) {
		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByG_S(long groupId, int status,
		int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] { groupId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S;
			finderArgs = new Object[] {
					groupId, status,
					
					start, end, orderByComparator
				};
		}

		List<CommerceShipment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShipment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShipment commerceShipment : list) {
					if ((groupId != commerceShipment.getGroupId()) ||
							(status != commerceShipment.getStatus())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPMENT_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShipmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<CommerceShipment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShipment>)QueryUtil.list(q,
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
	 * Returns the first commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment findByG_S_First(long groupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = fetchByG_S_First(groupId, status,
				orderByComparator);

		if (commerceShipment != null) {
			return commerceShipment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchShipmentException(msg.toString());
	}

	/**
	 * Returns the first commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment fetchByG_S_First(long groupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator) {
		List<CommerceShipment> list = findByG_S(groupId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment findByG_S_Last(long groupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = fetchByG_S_Last(groupId, status,
				orderByComparator);

		if (commerceShipment != null) {
			return commerceShipment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchShipmentException(msg.toString());
	}

	/**
	 * Returns the last commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment fetchByG_S_Last(long groupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator) {
		int count = countByG_S(groupId, status);

		if (count == 0) {
			return null;
		}

		List<CommerceShipment> list = findByG_S(groupId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipments before and after the current commerce shipment in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param commerceShipmentId the primary key of the current commerce shipment
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment[] findByG_S_PrevAndNext(long commerceShipmentId,
		long groupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = findByPrimaryKey(commerceShipmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceShipment[] array = new CommerceShipmentImpl[3];

			array[0] = getByG_S_PrevAndNext(session, commerceShipment, groupId,
					status, orderByComparator, true);

			array[1] = commerceShipment;

			array[2] = getByG_S_PrevAndNext(session, commerceShipment, groupId,
					status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShipment getByG_S_PrevAndNext(Session session,
		CommerceShipment commerceShipment, long groupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCESHIPMENT_WHERE);

		query.append(_FINDER_COLUMN_G_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			query.append(CommerceShipmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShipment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShipment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipments where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (CommerceShipment commerceShipment : findByG_S(groupId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceShipment);
		}
	}

	/**
	 * Returns the number of commerce shipments where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching commerce shipments
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_S;

		Object[] finderArgs = new Object[] { groupId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCESHIPMENT_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "commerceShipment.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STATUS_2 = "commerceShipment.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_S_S = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByS_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_S = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED,
			CommerceShipmentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			CommerceShipmentModelImpl.SITEGROUPID_COLUMN_BITMASK |
			CommerceShipmentModelImpl.STATUS_COLUMN_BITMASK |
			CommerceShipmentModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_S_S = new FinderPath(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the commerce shipments where siteGroupId = &#63; and status = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @return the matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByS_S(long siteGroupId, int status) {
		return findByS_S(siteGroupId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipments where siteGroupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByS_S(long siteGroupId, int status,
		int start, int end) {
		return findByS_S(siteGroupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipments where siteGroupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByS_S(long siteGroupId, int status,
		int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator) {
		return findByS_S(siteGroupId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce shipments where siteGroupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce shipments
	 */
	@Override
	public List<CommerceShipment> findByS_S(long siteGroupId, int status,
		int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_S;
			finderArgs = new Object[] { siteGroupId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_S_S;
			finderArgs = new Object[] {
					siteGroupId, status,
					
					start, end, orderByComparator
				};
		}

		List<CommerceShipment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShipment>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShipment commerceShipment : list) {
					if ((siteGroupId != commerceShipment.getSiteGroupId()) ||
							(status != commerceShipment.getStatus())) {
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

			query.append(_SQL_SELECT_COMMERCESHIPMENT_WHERE);

			query.append(_FINDER_COLUMN_S_S_SITEGROUPID_2);

			query.append(_FINDER_COLUMN_S_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceShipmentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(siteGroupId);

				qPos.add(status);

				if (!pagination) {
					list = (List<CommerceShipment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShipment>)QueryUtil.list(q,
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
	 * Returns the first commerce shipment in the ordered set where siteGroupId = &#63; and status = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment findByS_S_First(long siteGroupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = fetchByS_S_First(siteGroupId,
				status, orderByComparator);

		if (commerceShipment != null) {
			return commerceShipment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("siteGroupId=");
		msg.append(siteGroupId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchShipmentException(msg.toString());
	}

	/**
	 * Returns the first commerce shipment in the ordered set where siteGroupId = &#63; and status = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment fetchByS_S_First(long siteGroupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator) {
		List<CommerceShipment> list = findByS_S(siteGroupId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipment in the ordered set where siteGroupId = &#63; and status = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment
	 * @throws NoSuchShipmentException if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment findByS_S_Last(long siteGroupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = fetchByS_S_Last(siteGroupId,
				status, orderByComparator);

		if (commerceShipment != null) {
			return commerceShipment;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("siteGroupId=");
		msg.append(siteGroupId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchShipmentException(msg.toString());
	}

	/**
	 * Returns the last commerce shipment in the ordered set where siteGroupId = &#63; and status = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipment, or <code>null</code> if a matching commerce shipment could not be found
	 */
	@Override
	public CommerceShipment fetchByS_S_Last(long siteGroupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator) {
		int count = countByS_S(siteGroupId, status);

		if (count == 0) {
			return null;
		}

		List<CommerceShipment> list = findByS_S(siteGroupId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipments before and after the current commerce shipment in the ordered set where siteGroupId = &#63; and status = &#63;.
	 *
	 * @param commerceShipmentId the primary key of the current commerce shipment
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment[] findByS_S_PrevAndNext(long commerceShipmentId,
		long siteGroupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = findByPrimaryKey(commerceShipmentId);

		Session session = null;

		try {
			session = openSession();

			CommerceShipment[] array = new CommerceShipmentImpl[3];

			array[0] = getByS_S_PrevAndNext(session, commerceShipment,
					siteGroupId, status, orderByComparator, true);

			array[1] = commerceShipment;

			array[2] = getByS_S_PrevAndNext(session, commerceShipment,
					siteGroupId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShipment getByS_S_PrevAndNext(Session session,
		CommerceShipment commerceShipment, long siteGroupId, int status,
		OrderByComparator<CommerceShipment> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCESHIPMENT_WHERE);

		query.append(_FINDER_COLUMN_S_S_SITEGROUPID_2);

		query.append(_FINDER_COLUMN_S_S_STATUS_2);

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
			query.append(CommerceShipmentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(siteGroupId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceShipment);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceShipment> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipments where siteGroupId = &#63; and status = &#63; from the database.
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 */
	@Override
	public void removeByS_S(long siteGroupId, int status) {
		for (CommerceShipment commerceShipment : findByS_S(siteGroupId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceShipment);
		}
	}

	/**
	 * Returns the number of commerce shipments where siteGroupId = &#63; and status = &#63;.
	 *
	 * @param siteGroupId the site group ID
	 * @param status the status
	 * @return the number of matching commerce shipments
	 */
	@Override
	public int countByS_S(long siteGroupId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_S_S;

		Object[] finderArgs = new Object[] { siteGroupId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCESHIPMENT_WHERE);

			query.append(_FINDER_COLUMN_S_S_SITEGROUPID_2);

			query.append(_FINDER_COLUMN_S_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(siteGroupId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_S_S_SITEGROUPID_2 = "commerceShipment.siteGroupId = ? AND ";
	private static final String _FINDER_COLUMN_S_S_STATUS_2 = "commerceShipment.status = ?";

	public CommerceShipmentPersistenceImpl() {
		setModelClass(CommerceShipment.class);
	}

	/**
	 * Caches the commerce shipment in the entity cache if it is enabled.
	 *
	 * @param commerceShipment the commerce shipment
	 */
	@Override
	public void cacheResult(CommerceShipment commerceShipment) {
		entityCache.putResult(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentImpl.class, commerceShipment.getPrimaryKey(),
			commerceShipment);

		commerceShipment.resetOriginalValues();
	}

	/**
	 * Caches the commerce shipments in the entity cache if it is enabled.
	 *
	 * @param commerceShipments the commerce shipments
	 */
	@Override
	public void cacheResult(List<CommerceShipment> commerceShipments) {
		for (CommerceShipment commerceShipment : commerceShipments) {
			if (entityCache.getResult(
						CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShipmentImpl.class,
						commerceShipment.getPrimaryKey()) == null) {
				cacheResult(commerceShipment);
			}
			else {
				commerceShipment.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipments.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShipmentImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce shipment.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceShipment commerceShipment) {
		entityCache.removeResult(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentImpl.class, commerceShipment.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceShipment> commerceShipments) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceShipment commerceShipment : commerceShipments) {
			entityCache.removeResult(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShipmentImpl.class, commerceShipment.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce shipment with the primary key. Does not add the commerce shipment to the database.
	 *
	 * @param commerceShipmentId the primary key for the new commerce shipment
	 * @return the new commerce shipment
	 */
	@Override
	public CommerceShipment create(long commerceShipmentId) {
		CommerceShipment commerceShipment = new CommerceShipmentImpl();

		commerceShipment.setNew(true);
		commerceShipment.setPrimaryKey(commerceShipmentId);

		commerceShipment.setCompanyId(companyProvider.getCompanyId());

		return commerceShipment;
	}

	/**
	 * Removes the commerce shipment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShipmentId the primary key of the commerce shipment
	 * @return the commerce shipment that was removed
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment remove(long commerceShipmentId)
		throws NoSuchShipmentException {
		return remove((Serializable)commerceShipmentId);
	}

	/**
	 * Removes the commerce shipment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce shipment
	 * @return the commerce shipment that was removed
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment remove(Serializable primaryKey)
		throws NoSuchShipmentException {
		Session session = null;

		try {
			session = openSession();

			CommerceShipment commerceShipment = (CommerceShipment)session.get(CommerceShipmentImpl.class,
					primaryKey);

			if (commerceShipment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShipmentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceShipment);
		}
		catch (NoSuchShipmentException nsee) {
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
	protected CommerceShipment removeImpl(CommerceShipment commerceShipment) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShipment)) {
				commerceShipment = (CommerceShipment)session.get(CommerceShipmentImpl.class,
						commerceShipment.getPrimaryKeyObj());
			}

			if (commerceShipment != null) {
				session.delete(commerceShipment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceShipment != null) {
			clearCache(commerceShipment);
		}

		return commerceShipment;
	}

	@Override
	public CommerceShipment updateImpl(CommerceShipment commerceShipment) {
		boolean isNew = commerceShipment.isNew();

		if (!(commerceShipment instanceof CommerceShipmentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceShipment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceShipment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceShipment proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceShipment implementation " +
				commerceShipment.getClass());
		}

		CommerceShipmentModelImpl commerceShipmentModelImpl = (CommerceShipmentModelImpl)commerceShipment;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceShipment.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceShipment.setCreateDate(now);
			}
			else {
				commerceShipment.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceShipmentModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceShipment.setModifiedDate(now);
			}
			else {
				commerceShipment.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceShipment.isNew()) {
				session.save(commerceShipment);

				commerceShipment.setNew(false);
			}
			else {
				commerceShipment = (CommerceShipment)session.merge(commerceShipment);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceShipmentModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { commerceShipmentModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { commerceShipmentModelImpl.getSiteGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SITEGROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITEGROUPID,
				args);

			args = new Object[] {
					commerceShipmentModelImpl.getGroupId(),
					commerceShipmentModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
				args);

			args = new Object[] {
					commerceShipmentModelImpl.getSiteGroupId(),
					commerceShipmentModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_S_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_S,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceShipmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShipmentModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceShipmentModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceShipmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITEGROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShipmentModelImpl.getOriginalSiteGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SITEGROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITEGROUPID,
					args);

				args = new Object[] { commerceShipmentModelImpl.getSiteGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SITEGROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SITEGROUPID,
					args);
			}

			if ((commerceShipmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShipmentModelImpl.getOriginalGroupId(),
						commerceShipmentModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);

				args = new Object[] {
						commerceShipmentModelImpl.getGroupId(),
						commerceShipmentModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S,
					args);
			}

			if ((commerceShipmentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceShipmentModelImpl.getOriginalSiteGroupId(),
						commerceShipmentModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_S_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_S,
					args);

				args = new Object[] {
						commerceShipmentModelImpl.getSiteGroupId(),
						commerceShipmentModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_S_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_S,
					args);
			}
		}

		entityCache.putResult(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
			CommerceShipmentImpl.class, commerceShipment.getPrimaryKey(),
			commerceShipment, false);

		commerceShipment.resetOriginalValues();

		return commerceShipment;
	}

	/**
	 * Returns the commerce shipment with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipment
	 * @return the commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchShipmentException {
		CommerceShipment commerceShipment = fetchByPrimaryKey(primaryKey);

		if (commerceShipment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShipmentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceShipment;
	}

	/**
	 * Returns the commerce shipment with the primary key or throws a {@link NoSuchShipmentException} if it could not be found.
	 *
	 * @param commerceShipmentId the primary key of the commerce shipment
	 * @return the commerce shipment
	 * @throws NoSuchShipmentException if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment findByPrimaryKey(long commerceShipmentId)
		throws NoSuchShipmentException {
		return findByPrimaryKey((Serializable)commerceShipmentId);
	}

	/**
	 * Returns the commerce shipment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipment
	 * @return the commerce shipment, or <code>null</code> if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
				CommerceShipmentImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceShipment commerceShipment = (CommerceShipment)serializable;

		if (commerceShipment == null) {
			Session session = null;

			try {
				session = openSession();

				commerceShipment = (CommerceShipment)session.get(CommerceShipmentImpl.class,
						primaryKey);

				if (commerceShipment != null) {
					cacheResult(commerceShipment);
				}
				else {
					entityCache.putResult(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
						CommerceShipmentImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShipmentImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceShipment;
	}

	/**
	 * Returns the commerce shipment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShipmentId the primary key of the commerce shipment
	 * @return the commerce shipment, or <code>null</code> if a commerce shipment with the primary key could not be found
	 */
	@Override
	public CommerceShipment fetchByPrimaryKey(long commerceShipmentId) {
		return fetchByPrimaryKey((Serializable)commerceShipmentId);
	}

	@Override
	public Map<Serializable, CommerceShipment> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceShipment> map = new HashMap<Serializable, CommerceShipment>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceShipment commerceShipment = fetchByPrimaryKey(primaryKey);

			if (commerceShipment != null) {
				map.put(primaryKey, commerceShipment);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShipmentImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceShipment)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCESHIPMENT_WHERE_PKS_IN);

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

			for (CommerceShipment commerceShipment : (List<CommerceShipment>)q.list()) {
				map.put(commerceShipment.getPrimaryKeyObj(), commerceShipment);

				cacheResult(commerceShipment);

				uncachedPrimaryKeys.remove(commerceShipment.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceShipmentModelImpl.ENTITY_CACHE_ENABLED,
					CommerceShipmentImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce shipments.
	 *
	 * @return the commerce shipments
	 */
	@Override
	public List<CommerceShipment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @return the range of commerce shipments
	 */
	@Override
	public List<CommerceShipment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipments
	 */
	@Override
	public List<CommerceShipment> findAll(int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceShipmentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipments
	 * @param end the upper bound of the range of commerce shipments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce shipments
	 */
	@Override
	public List<CommerceShipment> findAll(int start, int end,
		OrderByComparator<CommerceShipment> orderByComparator,
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

		List<CommerceShipment> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceShipment>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCESHIPMENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPMENT;

				if (pagination) {
					sql = sql.concat(CommerceShipmentModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceShipment>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceShipment>)QueryUtil.list(q,
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
	 * Removes all the commerce shipments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceShipment commerceShipment : findAll()) {
			remove(commerceShipment);
		}
	}

	/**
	 * Returns the number of commerce shipments.
	 *
	 * @return the number of commerce shipments
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCESHIPMENT);

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
		return CommerceShipmentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipment persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceShipmentImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCESHIPMENT = "SELECT commerceShipment FROM CommerceShipment commerceShipment";
	private static final String _SQL_SELECT_COMMERCESHIPMENT_WHERE_PKS_IN = "SELECT commerceShipment FROM CommerceShipment commerceShipment WHERE commerceShipmentId IN (";
	private static final String _SQL_SELECT_COMMERCESHIPMENT_WHERE = "SELECT commerceShipment FROM CommerceShipment commerceShipment WHERE ";
	private static final String _SQL_COUNT_COMMERCESHIPMENT = "SELECT COUNT(commerceShipment) FROM CommerceShipment commerceShipment";
	private static final String _SQL_COUNT_COMMERCESHIPMENT_WHERE = "SELECT COUNT(commerceShipment) FROM CommerceShipment commerceShipment WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceShipment.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceShipment exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceShipment exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceShipmentPersistenceImpl.class);
}