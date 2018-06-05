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

import com.liferay.commerce.exception.NoSuchAvailabilityEstimateException;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.model.impl.CommerceAvailabilityEstimateImpl;
import com.liferay.commerce.model.impl.CommerceAvailabilityEstimateModelImpl;
import com.liferay.commerce.service.persistence.CommerceAvailabilityEstimatePersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
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
 * The persistence implementation for the commerce availability estimate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimatePersistence
 * @see com.liferay.commerce.service.persistence.CommerceAvailabilityEstimateUtil
 * @generated
 */
@ProviderType
public class CommerceAvailabilityEstimatePersistenceImpl
	extends BasePersistenceImpl<CommerceAvailabilityEstimate>
	implements CommerceAvailabilityEstimatePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceAvailabilityEstimateUtil} to access the commerce availability estimate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceAvailabilityEstimateImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceAvailabilityEstimateModelImpl.UUID_COLUMN_BITMASK |
			CommerceAvailabilityEstimateModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the commerce availability estimates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @return the range of matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByUuid(String uuid,
		int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<CommerceAvailabilityEstimate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAvailabilityEstimate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : list) {
					if (!Objects.equals(uuid,
								commerceAvailabilityEstimate.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<CommerceAvailabilityEstimate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAvailabilityEstimate>)QueryUtil.list(q,
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
	 * Returns the first commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate findByUuid_First(String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = fetchByUuid_First(uuid,
				orderByComparator);

		if (commerceAvailabilityEstimate != null) {
			return commerceAvailabilityEstimate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAvailabilityEstimateException(msg.toString());
	}

	/**
	 * Returns the first commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByUuid_First(String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		List<CommerceAvailabilityEstimate> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate findByUuid_Last(String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commerceAvailabilityEstimate != null) {
			return commerceAvailabilityEstimate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAvailabilityEstimateException(msg.toString());
	}

	/**
	 * Returns the last commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceAvailabilityEstimate> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce availability estimates before and after the current commerce availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the current commerce availability estimate
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate[] findByUuid_PrevAndNext(
		long commerceAvailabilityEstimateId, String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = findByPrimaryKey(commerceAvailabilityEstimateId);

		Session session = null;

		try {
			session = openSession();

			CommerceAvailabilityEstimate[] array = new CommerceAvailabilityEstimateImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					commerceAvailabilityEstimate, uuid, orderByComparator, true);

			array[1] = commerceAvailabilityEstimate;

			array[2] = getByUuid_PrevAndNext(session,
					commerceAvailabilityEstimate, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAvailabilityEstimate getByUuid_PrevAndNext(
		Session session,
		CommerceAvailabilityEstimate commerceAvailabilityEstimate, String uuid,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals("")) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

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
			query.append(CommerceAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAvailabilityEstimate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAvailabilityEstimate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce availability estimates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAvailabilityEstimate);
		}
	}

	/**
	 * Returns the number of commerce availability estimates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce availability estimates
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceAvailabilityEstimate.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceAvailabilityEstimate.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceAvailabilityEstimate.uuid IS NULL OR commerceAvailabilityEstimate.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceAvailabilityEstimateModelImpl.UUID_COLUMN_BITMASK |
			CommerceAvailabilityEstimateModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce availability estimate where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAvailabilityEstimateException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate findByUUID_G(String uuid, long groupId)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = fetchByUUID_G(uuid,
				groupId);

		if (commerceAvailabilityEstimate == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAvailabilityEstimateException(msg.toString());
		}

		return commerceAvailabilityEstimate;
	}

	/**
	 * Returns the commerce availability estimate where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce availability estimate where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceAvailabilityEstimate) {
			CommerceAvailabilityEstimate commerceAvailabilityEstimate = (CommerceAvailabilityEstimate)result;

			if (!Objects.equals(uuid, commerceAvailabilityEstimate.getUuid()) ||
					(groupId != commerceAvailabilityEstimate.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<CommerceAvailabilityEstimate> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceAvailabilityEstimate commerceAvailabilityEstimate = list.get(0);

					result = commerceAvailabilityEstimate;

					cacheResult(commerceAvailabilityEstimate);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

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
			return (CommerceAvailabilityEstimate)result;
		}
	}

	/**
	 * Removes the commerce availability estimate where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce availability estimate that was removed
	 */
	@Override
	public CommerceAvailabilityEstimate removeByUUID_G(String uuid, long groupId)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = findByUUID_G(uuid,
				groupId);

		return remove(commerceAvailabilityEstimate);
	}

	/**
	 * Returns the number of commerce availability estimates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce availability estimates
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceAvailabilityEstimate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceAvailabilityEstimate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceAvailabilityEstimate.uuid IS NULL OR commerceAvailabilityEstimate.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceAvailabilityEstimate.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceAvailabilityEstimateModelImpl.UUID_COLUMN_BITMASK |
			CommerceAvailabilityEstimateModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceAvailabilityEstimateModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @return the range of matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceAvailabilityEstimate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAvailabilityEstimate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : list) {
					if (!Objects.equals(uuid,
								commerceAvailabilityEstimate.getUuid()) ||
							(companyId != commerceAvailabilityEstimate.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceAvailabilityEstimate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAvailabilityEstimate>)QueryUtil.list(q,
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
	 * Returns the first commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commerceAvailabilityEstimate != null) {
			return commerceAvailabilityEstimate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAvailabilityEstimateException(msg.toString());
	}

	/**
	 * Returns the first commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		List<CommerceAvailabilityEstimate> list = findByUuid_C(uuid, companyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commerceAvailabilityEstimate != null) {
			return commerceAvailabilityEstimate;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAvailabilityEstimateException(msg.toString());
	}

	/**
	 * Returns the last commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceAvailabilityEstimate> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce availability estimates before and after the current commerce availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the current commerce availability estimate
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate[] findByUuid_C_PrevAndNext(
		long commerceAvailabilityEstimateId, String uuid, long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = findByPrimaryKey(commerceAvailabilityEstimateId);

		Session session = null;

		try {
			session = openSession();

			CommerceAvailabilityEstimate[] array = new CommerceAvailabilityEstimateImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commerceAvailabilityEstimate, uuid, companyId,
					orderByComparator, true);

			array[1] = commerceAvailabilityEstimate;

			array[2] = getByUuid_C_PrevAndNext(session,
					commerceAvailabilityEstimate, uuid, companyId,
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

	protected CommerceAvailabilityEstimate getByUuid_C_PrevAndNext(
		Session session,
		CommerceAvailabilityEstimate commerceAvailabilityEstimate, String uuid,
		long companyId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals("")) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(CommerceAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAvailabilityEstimate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAvailabilityEstimate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce availability estimates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAvailabilityEstimate);
		}
	}

	/**
	 * Returns the number of commerce availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce availability estimates
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceAvailabilityEstimate.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceAvailabilityEstimate.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceAvailabilityEstimate.uuid IS NULL OR commerceAvailabilityEstimate.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceAvailabilityEstimate.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceAvailabilityEstimateModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceAvailabilityEstimateModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce availability estimates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce availability estimates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @return the range of matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce availability estimates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce availability estimates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
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

		List<CommerceAvailabilityEstimate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAvailabilityEstimate>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : list) {
					if ((groupId != commerceAvailabilityEstimate.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceAvailabilityEstimate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAvailabilityEstimate>)QueryUtil.list(q,
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
	 * Returns the first commerce availability estimate in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate findByGroupId_First(long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceAvailabilityEstimate != null) {
			return commerceAvailabilityEstimate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchAvailabilityEstimateException(msg.toString());
	}

	/**
	 * Returns the first commerce availability estimate in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		List<CommerceAvailabilityEstimate> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce availability estimate in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate findByGroupId_Last(long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceAvailabilityEstimate != null) {
			return commerceAvailabilityEstimate;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchAvailabilityEstimateException(msg.toString());
	}

	/**
	 * Returns the last commerce availability estimate in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce availability estimate, or <code>null</code> if a matching commerce availability estimate could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceAvailabilityEstimate> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce availability estimates before and after the current commerce availability estimate in the ordered set where groupId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the current commerce availability estimate
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate[] findByGroupId_PrevAndNext(
		long commerceAvailabilityEstimateId, long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator)
		throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = findByPrimaryKey(commerceAvailabilityEstimateId);

		Session session = null;

		try {
			session = openSession();

			CommerceAvailabilityEstimate[] array = new CommerceAvailabilityEstimateImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceAvailabilityEstimate, groupId, orderByComparator,
					true);

			array[1] = commerceAvailabilityEstimate;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceAvailabilityEstimate, groupId, orderByComparator,
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

	protected CommerceAvailabilityEstimate getByGroupId_PrevAndNext(
		Session session,
		CommerceAvailabilityEstimate commerceAvailabilityEstimate,
		long groupId,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE);

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
			query.append(CommerceAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAvailabilityEstimate);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAvailabilityEstimate> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce availability estimates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAvailabilityEstimate);
		}
	}

	/**
	 * Returns the number of commerce availability estimates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce availability estimates
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEAVAILABILITYESTIMATE_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceAvailabilityEstimate.groupId = ?";

	public CommerceAvailabilityEstimatePersistenceImpl() {
		setModelClass(CommerceAvailabilityEstimate.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce availability estimate in the entity cache if it is enabled.
	 *
	 * @param commerceAvailabilityEstimate the commerce availability estimate
	 */
	@Override
	public void cacheResult(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		entityCache.putResult(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			commerceAvailabilityEstimate.getPrimaryKey(),
			commerceAvailabilityEstimate);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commerceAvailabilityEstimate.getUuid(),
				commerceAvailabilityEstimate.getGroupId()
			}, commerceAvailabilityEstimate);

		commerceAvailabilityEstimate.resetOriginalValues();
	}

	/**
	 * Caches the commerce availability estimates in the entity cache if it is enabled.
	 *
	 * @param commerceAvailabilityEstimates the commerce availability estimates
	 */
	@Override
	public void cacheResult(
		List<CommerceAvailabilityEstimate> commerceAvailabilityEstimates) {
		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : commerceAvailabilityEstimates) {
			if (entityCache.getResult(
						CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
						CommerceAvailabilityEstimateImpl.class,
						commerceAvailabilityEstimate.getPrimaryKey()) == null) {
				cacheResult(commerceAvailabilityEstimate);
			}
			else {
				commerceAvailabilityEstimate.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce availability estimates.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceAvailabilityEstimateImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce availability estimate.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		entityCache.removeResult(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			commerceAvailabilityEstimate.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceAvailabilityEstimateModelImpl)commerceAvailabilityEstimate,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceAvailabilityEstimate> commerceAvailabilityEstimates) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : commerceAvailabilityEstimates) {
			entityCache.removeResult(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
				CommerceAvailabilityEstimateImpl.class,
				commerceAvailabilityEstimate.getPrimaryKey());

			clearUniqueFindersCache((CommerceAvailabilityEstimateModelImpl)commerceAvailabilityEstimate,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceAvailabilityEstimateModelImpl commerceAvailabilityEstimateModelImpl) {
		Object[] args = new Object[] {
				commerceAvailabilityEstimateModelImpl.getUuid(),
				commerceAvailabilityEstimateModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceAvailabilityEstimateModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceAvailabilityEstimateModelImpl commerceAvailabilityEstimateModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceAvailabilityEstimateModelImpl.getUuid(),
					commerceAvailabilityEstimateModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceAvailabilityEstimateModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceAvailabilityEstimateModelImpl.getOriginalUuid(),
					commerceAvailabilityEstimateModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new commerce availability estimate with the primary key. Does not add the commerce availability estimate to the database.
	 *
	 * @param commerceAvailabilityEstimateId the primary key for the new commerce availability estimate
	 * @return the new commerce availability estimate
	 */
	@Override
	public CommerceAvailabilityEstimate create(
		long commerceAvailabilityEstimateId) {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = new CommerceAvailabilityEstimateImpl();

		commerceAvailabilityEstimate.setNew(true);
		commerceAvailabilityEstimate.setPrimaryKey(commerceAvailabilityEstimateId);

		String uuid = PortalUUIDUtil.generate();

		commerceAvailabilityEstimate.setUuid(uuid);

		commerceAvailabilityEstimate.setCompanyId(companyProvider.getCompanyId());

		return commerceAvailabilityEstimate;
	}

	/**
	 * Removes the commerce availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	 * @return the commerce availability estimate that was removed
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate remove(
		long commerceAvailabilityEstimateId)
		throws NoSuchAvailabilityEstimateException {
		return remove((Serializable)commerceAvailabilityEstimateId);
	}

	/**
	 * Removes the commerce availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce availability estimate
	 * @return the commerce availability estimate that was removed
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate remove(Serializable primaryKey)
		throws NoSuchAvailabilityEstimateException {
		Session session = null;

		try {
			session = openSession();

			CommerceAvailabilityEstimate commerceAvailabilityEstimate = (CommerceAvailabilityEstimate)session.get(CommerceAvailabilityEstimateImpl.class,
					primaryKey);

			if (commerceAvailabilityEstimate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAvailabilityEstimateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceAvailabilityEstimate);
		}
		catch (NoSuchAvailabilityEstimateException nsee) {
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
	protected CommerceAvailabilityEstimate removeImpl(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceAvailabilityEstimate)) {
				commerceAvailabilityEstimate = (CommerceAvailabilityEstimate)session.get(CommerceAvailabilityEstimateImpl.class,
						commerceAvailabilityEstimate.getPrimaryKeyObj());
			}

			if (commerceAvailabilityEstimate != null) {
				session.delete(commerceAvailabilityEstimate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceAvailabilityEstimate != null) {
			clearCache(commerceAvailabilityEstimate);
		}

		return commerceAvailabilityEstimate;
	}

	@Override
	public CommerceAvailabilityEstimate updateImpl(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {
		boolean isNew = commerceAvailabilityEstimate.isNew();

		if (!(commerceAvailabilityEstimate instanceof CommerceAvailabilityEstimateModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceAvailabilityEstimate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceAvailabilityEstimate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceAvailabilityEstimate proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceAvailabilityEstimate implementation " +
				commerceAvailabilityEstimate.getClass());
		}

		CommerceAvailabilityEstimateModelImpl commerceAvailabilityEstimateModelImpl =
			(CommerceAvailabilityEstimateModelImpl)commerceAvailabilityEstimate;

		if (Validator.isNull(commerceAvailabilityEstimate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceAvailabilityEstimate.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceAvailabilityEstimate.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceAvailabilityEstimate.setCreateDate(now);
			}
			else {
				commerceAvailabilityEstimate.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceAvailabilityEstimateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceAvailabilityEstimate.setModifiedDate(now);
			}
			else {
				commerceAvailabilityEstimate.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceAvailabilityEstimate.isNew()) {
				session.save(commerceAvailabilityEstimate);

				commerceAvailabilityEstimate.setNew(false);
			}
			else {
				commerceAvailabilityEstimate = (CommerceAvailabilityEstimate)session.merge(commerceAvailabilityEstimate);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceAvailabilityEstimateModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceAvailabilityEstimateModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceAvailabilityEstimateModelImpl.getUuid(),
					commerceAvailabilityEstimateModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					commerceAvailabilityEstimateModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceAvailabilityEstimateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAvailabilityEstimateModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						commerceAvailabilityEstimateModelImpl.getUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceAvailabilityEstimateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAvailabilityEstimateModelImpl.getOriginalUuid(),
						commerceAvailabilityEstimateModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceAvailabilityEstimateModelImpl.getUuid(),
						commerceAvailabilityEstimateModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceAvailabilityEstimateModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAvailabilityEstimateModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceAvailabilityEstimateModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAvailabilityEstimateImpl.class,
			commerceAvailabilityEstimate.getPrimaryKey(),
			commerceAvailabilityEstimate, false);

		clearUniqueFindersCache(commerceAvailabilityEstimateModelImpl, false);
		cacheUniqueFindersCache(commerceAvailabilityEstimateModelImpl);

		commerceAvailabilityEstimate.resetOriginalValues();

		return commerceAvailabilityEstimate;
	}

	/**
	 * Returns the commerce availability estimate with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce availability estimate
	 * @return the commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate findByPrimaryKey(
		Serializable primaryKey) throws NoSuchAvailabilityEstimateException {
		CommerceAvailabilityEstimate commerceAvailabilityEstimate = fetchByPrimaryKey(primaryKey);

		if (commerceAvailabilityEstimate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAvailabilityEstimateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceAvailabilityEstimate;
	}

	/**
	 * Returns the commerce availability estimate with the primary key or throws a {@link NoSuchAvailabilityEstimateException} if it could not be found.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	 * @return the commerce availability estimate
	 * @throws NoSuchAvailabilityEstimateException if a commerce availability estimate with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate findByPrimaryKey(
		long commerceAvailabilityEstimateId)
		throws NoSuchAvailabilityEstimateException {
		return findByPrimaryKey((Serializable)commerceAvailabilityEstimateId);
	}

	/**
	 * Returns the commerce availability estimate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce availability estimate
	 * @return the commerce availability estimate, or <code>null</code> if a commerce availability estimate with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
				CommerceAvailabilityEstimateImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceAvailabilityEstimate commerceAvailabilityEstimate = (CommerceAvailabilityEstimate)serializable;

		if (commerceAvailabilityEstimate == null) {
			Session session = null;

			try {
				session = openSession();

				commerceAvailabilityEstimate = (CommerceAvailabilityEstimate)session.get(CommerceAvailabilityEstimateImpl.class,
						primaryKey);

				if (commerceAvailabilityEstimate != null) {
					cacheResult(commerceAvailabilityEstimate);
				}
				else {
					entityCache.putResult(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
						CommerceAvailabilityEstimateImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAvailabilityEstimateImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceAvailabilityEstimate;
	}

	/**
	 * Returns the commerce availability estimate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAvailabilityEstimateId the primary key of the commerce availability estimate
	 * @return the commerce availability estimate, or <code>null</code> if a commerce availability estimate with the primary key could not be found
	 */
	@Override
	public CommerceAvailabilityEstimate fetchByPrimaryKey(
		long commerceAvailabilityEstimateId) {
		return fetchByPrimaryKey((Serializable)commerceAvailabilityEstimateId);
	}

	@Override
	public Map<Serializable, CommerceAvailabilityEstimate> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceAvailabilityEstimate> map = new HashMap<Serializable, CommerceAvailabilityEstimate>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceAvailabilityEstimate commerceAvailabilityEstimate = fetchByPrimaryKey(primaryKey);

			if (commerceAvailabilityEstimate != null) {
				map.put(primaryKey, commerceAvailabilityEstimate);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAvailabilityEstimateImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceAvailabilityEstimate)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE_PKS_IN);

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

			for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : (List<CommerceAvailabilityEstimate>)q.list()) {
				map.put(commerceAvailabilityEstimate.getPrimaryKeyObj(),
					commerceAvailabilityEstimate);

				cacheResult(commerceAvailabilityEstimate);

				uncachedPrimaryKeys.remove(commerceAvailabilityEstimate.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceAvailabilityEstimateModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAvailabilityEstimateImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the commerce availability estimates.
	 *
	 * @return the commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @return the range of commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findAll(int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAvailabilityEstimateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce availability estimates
	 * @param end the upper bound of the range of commerce availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce availability estimates
	 */
	@Override
	public List<CommerceAvailabilityEstimate> findAll(int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator,
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

		List<CommerceAvailabilityEstimate> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAvailabilityEstimate>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEAVAILABILITYESTIMATE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEAVAILABILITYESTIMATE;

				if (pagination) {
					sql = sql.concat(CommerceAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceAvailabilityEstimate>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAvailabilityEstimate>)QueryUtil.list(q,
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
	 * Removes all the commerce availability estimates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate : findAll()) {
			remove(commerceAvailabilityEstimate);
		}
	}

	/**
	 * Returns the number of commerce availability estimates.
	 *
	 * @return the number of commerce availability estimates
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEAVAILABILITYESTIMATE);

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
		return CommerceAvailabilityEstimateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce availability estimate persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceAvailabilityEstimateImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEAVAILABILITYESTIMATE = "SELECT commerceAvailabilityEstimate FROM CommerceAvailabilityEstimate commerceAvailabilityEstimate";
	private static final String _SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE_PKS_IN =
		"SELECT commerceAvailabilityEstimate FROM CommerceAvailabilityEstimate commerceAvailabilityEstimate WHERE commerceAvailabilityEstimateId IN (";
	private static final String _SQL_SELECT_COMMERCEAVAILABILITYESTIMATE_WHERE = "SELECT commerceAvailabilityEstimate FROM CommerceAvailabilityEstimate commerceAvailabilityEstimate WHERE ";
	private static final String _SQL_COUNT_COMMERCEAVAILABILITYESTIMATE = "SELECT COUNT(commerceAvailabilityEstimate) FROM CommerceAvailabilityEstimate commerceAvailabilityEstimate";
	private static final String _SQL_COUNT_COMMERCEAVAILABILITYESTIMATE_WHERE = "SELECT COUNT(commerceAvailabilityEstimate) FROM CommerceAvailabilityEstimate commerceAvailabilityEstimate WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceAvailabilityEstimate.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceAvailabilityEstimate exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceAvailabilityEstimate exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceAvailabilityEstimatePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}