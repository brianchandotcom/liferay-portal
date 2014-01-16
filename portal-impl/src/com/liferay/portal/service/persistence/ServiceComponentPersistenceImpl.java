/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchServiceComponentException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.ServiceComponent;
import com.liferay.portal.model.impl.ServiceComponentImpl;
import com.liferay.portal.model.impl.ServiceComponentModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the service component service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponentPersistence
 * @see ServiceComponentUtil
 * @generated
 */
public class ServiceComponentPersistenceImpl extends BasePersistenceImpl<ServiceComponent>
	implements ServiceComponentPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ServiceComponentUtil} to access the service component persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ServiceComponentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			ServiceComponentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			ServiceComponentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_BUILDNAMESPACE =
		new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			ServiceComponentImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByBuildNamespace",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BUILDNAMESPACE =
		new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			ServiceComponentImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByBuildNamespace",
			new String[] { String.class.getName() },
			ServiceComponentModelImpl.BUILDNAMESPACE_COLUMN_BITMASK |
			ServiceComponentModelImpl.BUILDNUMBER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_BUILDNAMESPACE = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBuildNamespace",
			new String[] { String.class.getName() });

	/**
	 * Returns all the service components where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @return the matching service components
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ServiceComponent> findByBuildNamespace(String buildNamespace)
		throws SystemException {
		return findByBuildNamespace(buildNamespace, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service components where buildNamespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param buildNamespace the build namespace
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @return the range of matching service components
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ServiceComponent> findByBuildNamespace(String buildNamespace,
		int start, int end) throws SystemException {
		return findByBuildNamespace(buildNamespace, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service components where buildNamespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param buildNamespace the build namespace
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service components
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ServiceComponent> findByBuildNamespace(String buildNamespace,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BUILDNAMESPACE;
			finderArgs = new Object[] { buildNamespace };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_BUILDNAMESPACE;
			finderArgs = new Object[] {
					buildNamespace,
					
					start, end, orderByComparator
				};
		}

		List<ServiceComponent> list = (List<ServiceComponent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ServiceComponent serviceComponent : list) {
				if (!Validator.equals(buildNamespace,
							serviceComponent.getBuildNamespace())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

			boolean bindBuildNamespace = false;

			if (buildNamespace == null) {
				query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_1);
			}
			else if (buildNamespace.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
			}
			else {
				bindBuildNamespace = true;

				query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ServiceComponentModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBuildNamespace) {
					qPos.add(buildNamespace);
				}

				if (!pagination) {
					list = (List<ServiceComponent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ServiceComponent>(list);
				}
				else {
					list = (List<ServiceComponent>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first service component in the ordered set where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service component
	 * @throws com.liferay.portal.NoSuchServiceComponentException if a matching service component could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent findByBuildNamespace_First(String buildNamespace,
		OrderByComparator orderByComparator)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = fetchByBuildNamespace_First(buildNamespace,
				orderByComparator);

		if (serviceComponent != null) {
			return serviceComponent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("buildNamespace=");
		msg.append(buildNamespace);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceComponentException(msg.toString());
	}

	/**
	 * Returns the first service component in the ordered set where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service component, or <code>null</code> if a matching service component could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent fetchByBuildNamespace_First(String buildNamespace,
		OrderByComparator orderByComparator) throws SystemException {
		List<ServiceComponent> list = findByBuildNamespace(buildNamespace, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last service component in the ordered set where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service component
	 * @throws com.liferay.portal.NoSuchServiceComponentException if a matching service component could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent findByBuildNamespace_Last(String buildNamespace,
		OrderByComparator orderByComparator)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = fetchByBuildNamespace_Last(buildNamespace,
				orderByComparator);

		if (serviceComponent != null) {
			return serviceComponent;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("buildNamespace=");
		msg.append(buildNamespace);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceComponentException(msg.toString());
	}

	/**
	 * Returns the last service component in the ordered set where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service component, or <code>null</code> if a matching service component could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent fetchByBuildNamespace_Last(String buildNamespace,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByBuildNamespace(buildNamespace);

		if (count == 0) {
			return null;
		}

		List<ServiceComponent> list = findByBuildNamespace(buildNamespace,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the service components before and after the current service component in the ordered set where buildNamespace = &#63;.
	 *
	 * @param serviceComponentId the primary key of the current service component
	 * @param buildNamespace the build namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service component
	 * @throws com.liferay.portal.NoSuchServiceComponentException if a service component with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent[] findByBuildNamespace_PrevAndNext(
		long serviceComponentId, String buildNamespace,
		OrderByComparator orderByComparator)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = findByPrimaryKey(serviceComponentId);

		Session session = null;

		try {
			session = openSession();

			ServiceComponent[] array = new ServiceComponentImpl[3];

			array[0] = getByBuildNamespace_PrevAndNext(session,
					serviceComponent, buildNamespace, orderByComparator, true);

			array[1] = serviceComponent;

			array[2] = getByBuildNamespace_PrevAndNext(session,
					serviceComponent, buildNamespace, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ServiceComponent getByBuildNamespace_PrevAndNext(
		Session session, ServiceComponent serviceComponent,
		String buildNamespace, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

		boolean bindBuildNamespace = false;

		if (buildNamespace == null) {
			query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_1);
		}
		else if (buildNamespace.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
		}
		else {
			bindBuildNamespace = true;

			query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
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
			query.append(ServiceComponentModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindBuildNamespace) {
			qPos.add(buildNamespace);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceComponent);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceComponent> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the service components where buildNamespace = &#63; from the database.
	 *
	 * @param buildNamespace the build namespace
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByBuildNamespace(String buildNamespace)
		throws SystemException {
		for (ServiceComponent serviceComponent : findByBuildNamespace(
				buildNamespace, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(serviceComponent);
		}
	}

	/**
	 * Returns the number of service components where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @return the number of matching service components
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByBuildNamespace(String buildNamespace)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_BUILDNAMESPACE;

		Object[] finderArgs = new Object[] { buildNamespace };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SERVICECOMPONENT_WHERE);

			boolean bindBuildNamespace = false;

			if (buildNamespace == null) {
				query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_1);
			}
			else if (buildNamespace.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
			}
			else {
				bindBuildNamespace = true;

				query.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBuildNamespace) {
					qPos.add(buildNamespace);
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

	private static final String _FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_1 = "serviceComponent.buildNamespace IS NULL";
	private static final String _FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2 = "serviceComponent.buildNamespace = ?";
	private static final String _FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3 = "(serviceComponent.buildNamespace IS NULL OR serviceComponent.buildNamespace = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_BNS_BNU = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED,
			ServiceComponentImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByBNS_BNU",
			new String[] { String.class.getName(), Long.class.getName() },
			ServiceComponentModelImpl.BUILDNAMESPACE_COLUMN_BITMASK |
			ServiceComponentModelImpl.BUILDNUMBER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_BNS_BNU = new FinderPath(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBNS_BNU",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or throws a {@link com.liferay.portal.NoSuchServiceComponentException} if it could not be found.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @return the matching service component
	 * @throws com.liferay.portal.NoSuchServiceComponentException if a matching service component could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent findByBNS_BNU(String buildNamespace,
		long buildNumber)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = fetchByBNS_BNU(buildNamespace,
				buildNumber);

		if (serviceComponent == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("buildNamespace=");
			msg.append(buildNamespace);

			msg.append(", buildNumber=");
			msg.append(buildNumber);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchServiceComponentException(msg.toString());
		}

		return serviceComponent;
	}

	/**
	 * Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @return the matching service component, or <code>null</code> if a matching service component could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent fetchByBNS_BNU(String buildNamespace,
		long buildNumber) throws SystemException {
		return fetchByBNS_BNU(buildNamespace, buildNumber, true);
	}

	/**
	 * Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching service component, or <code>null</code> if a matching service component could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent fetchByBNS_BNU(String buildNamespace,
		long buildNumber, boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { buildNamespace, buildNumber };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_BNS_BNU,
					finderArgs, this);
		}

		if (result instanceof ServiceComponent) {
			ServiceComponent serviceComponent = (ServiceComponent)result;

			if (!Validator.equals(buildNamespace,
						serviceComponent.getBuildNamespace()) ||
					(buildNumber != serviceComponent.getBuildNumber())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

			boolean bindBuildNamespace = false;

			if (buildNamespace == null) {
				query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_1);
			}
			else if (buildNamespace.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_3);
			}
			else {
				bindBuildNamespace = true;

				query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_2);
			}

			query.append(_FINDER_COLUMN_BNS_BNU_BUILDNUMBER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBuildNamespace) {
					qPos.add(buildNamespace);
				}

				qPos.add(buildNumber);

				List<ServiceComponent> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU,
						finderArgs, list);
				}
				else {
					ServiceComponent serviceComponent = list.get(0);

					result = serviceComponent;

					cacheResult(serviceComponent);

					if ((serviceComponent.getBuildNamespace() == null) ||
							!serviceComponent.getBuildNamespace()
												 .equals(buildNamespace) ||
							(serviceComponent.getBuildNumber() != buildNumber)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU,
							finderArgs, serviceComponent);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_BNS_BNU,
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
			return (ServiceComponent)result;
		}
	}

	/**
	 * Removes the service component where buildNamespace = &#63; and buildNumber = &#63; from the database.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @return the service component that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent removeByBNS_BNU(String buildNamespace,
		long buildNumber)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = findByBNS_BNU(buildNamespace,
				buildNumber);

		return remove(serviceComponent);
	}

	/**
	 * Returns the number of service components where buildNamespace = &#63; and buildNumber = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @return the number of matching service components
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByBNS_BNU(String buildNamespace, long buildNumber)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_BNS_BNU;

		Object[] finderArgs = new Object[] { buildNamespace, buildNumber };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SERVICECOMPONENT_WHERE);

			boolean bindBuildNamespace = false;

			if (buildNamespace == null) {
				query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_1);
			}
			else if (buildNamespace.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_3);
			}
			else {
				bindBuildNamespace = true;

				query.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_2);
			}

			query.append(_FINDER_COLUMN_BNS_BNU_BUILDNUMBER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindBuildNamespace) {
					qPos.add(buildNamespace);
				}

				qPos.add(buildNumber);

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

	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_1 = "serviceComponent.buildNamespace IS NULL AND ";
	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_2 = "serviceComponent.buildNamespace = ? AND ";
	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_3 = "(serviceComponent.buildNamespace IS NULL OR serviceComponent.buildNamespace = '') AND ";
	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNUMBER_2 = "serviceComponent.buildNumber = ?";

	public ServiceComponentPersistenceImpl() {
		setModelClass(ServiceComponent.class);
	}

	/**
	 * Caches the service component in the entity cache if it is enabled.
	 *
	 * @param serviceComponent the service component
	 */
	@Override
	public void cacheResult(ServiceComponent serviceComponent) {
		EntityCacheUtil.putResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentImpl.class, serviceComponent.getPrimaryKey(),
			serviceComponent);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU,
			new Object[] {
				serviceComponent.getBuildNamespace(),
				serviceComponent.getBuildNumber()
			}, serviceComponent);

		serviceComponent.resetOriginalValues();
	}

	/**
	 * Caches the service components in the entity cache if it is enabled.
	 *
	 * @param serviceComponents the service components
	 */
	@Override
	public void cacheResult(List<ServiceComponent> serviceComponents) {
		for (ServiceComponent serviceComponent : serviceComponents) {
			if (EntityCacheUtil.getResult(
						ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
						ServiceComponentImpl.class,
						serviceComponent.getPrimaryKey()) == null) {
				cacheResult(serviceComponent);
			}
			else {
				serviceComponent.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all service components.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(ServiceComponentImpl.class.getName());
		}

		EntityCacheUtil.clearCache(ServiceComponentImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the service component.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ServiceComponent serviceComponent) {
		EntityCacheUtil.removeResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentImpl.class, serviceComponent.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(serviceComponent);
	}

	@Override
	public void clearCache(List<ServiceComponent> serviceComponents) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ServiceComponent serviceComponent : serviceComponents) {
			EntityCacheUtil.removeResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
				ServiceComponentImpl.class, serviceComponent.getPrimaryKey());

			clearUniqueFindersCache(serviceComponent);
		}
	}

	protected void cacheUniqueFindersCache(ServiceComponent serviceComponent) {
		if (serviceComponent.isNew()) {
			Object[] args = new Object[] {
					serviceComponent.getBuildNamespace(),
					serviceComponent.getBuildNumber()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_BNS_BNU, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU, args,
				serviceComponent);
		}
		else {
			ServiceComponentModelImpl serviceComponentModelImpl = (ServiceComponentModelImpl)serviceComponent;

			if ((serviceComponentModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_BNS_BNU.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceComponent.getBuildNamespace(),
						serviceComponent.getBuildNumber()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_BNS_BNU, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_BNS_BNU, args,
					serviceComponent);
			}
		}
	}

	protected void clearUniqueFindersCache(ServiceComponent serviceComponent) {
		ServiceComponentModelImpl serviceComponentModelImpl = (ServiceComponentModelImpl)serviceComponent;

		Object[] args = new Object[] {
				serviceComponent.getBuildNamespace(),
				serviceComponent.getBuildNumber()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_BNS_BNU, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_BNS_BNU, args);

		if ((serviceComponentModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_BNS_BNU.getColumnBitmask()) != 0) {
			args = new Object[] {
					serviceComponentModelImpl.getOriginalBuildNamespace(),
					serviceComponentModelImpl.getOriginalBuildNumber()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_BNS_BNU, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_BNS_BNU, args);
		}
	}

	/**
	 * Creates a new service component with the primary key. Does not add the service component to the database.
	 *
	 * @param serviceComponentId the primary key for the new service component
	 * @return the new service component
	 */
	@Override
	public ServiceComponent create(long serviceComponentId) {
		ServiceComponent serviceComponent = new ServiceComponentImpl();

		serviceComponent.setNew(true);
		serviceComponent.setPrimaryKey(serviceComponentId);

		return serviceComponent;
	}

	/**
	 * Removes the service component with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param serviceComponentId the primary key of the service component
	 * @return the service component that was removed
	 * @throws com.liferay.portal.NoSuchServiceComponentException if a service component with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent remove(long serviceComponentId)
		throws NoSuchServiceComponentException, SystemException {
		return remove((Serializable)serviceComponentId);
	}

	/**
	 * Removes the service component with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the service component
	 * @return the service component that was removed
	 * @throws com.liferay.portal.NoSuchServiceComponentException if a service component with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent remove(Serializable primaryKey)
		throws NoSuchServiceComponentException, SystemException {
		Session session = null;

		try {
			session = openSession();

			ServiceComponent serviceComponent = (ServiceComponent)session.get(ServiceComponentImpl.class,
					primaryKey);

			if (serviceComponent == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchServiceComponentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(serviceComponent);
		}
		catch (NoSuchServiceComponentException nsee) {
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
	protected ServiceComponent removeImpl(ServiceComponent serviceComponent)
		throws SystemException {
		serviceComponent = toUnwrappedModel(serviceComponent);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(serviceComponent)) {
				serviceComponent = (ServiceComponent)session.get(ServiceComponentImpl.class,
						serviceComponent.getPrimaryKeyObj());
			}

			if (serviceComponent != null) {
				session.delete(serviceComponent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (serviceComponent != null) {
			clearCache(serviceComponent);
		}

		return serviceComponent;
	}

	@Override
	public ServiceComponent updateImpl(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws SystemException {
		serviceComponent = toUnwrappedModel(serviceComponent);

		boolean isNew = serviceComponent.isNew();

		ServiceComponentModelImpl serviceComponentModelImpl = (ServiceComponentModelImpl)serviceComponent;

		Session session = null;

		try {
			session = openSession();

			if (serviceComponent.isNew()) {
				session.save(serviceComponent);

				serviceComponent.setNew(false);
			}
			else {
				session.merge(serviceComponent);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ServiceComponentModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((serviceComponentModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BUILDNAMESPACE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceComponentModelImpl.getOriginalBuildNamespace()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_BUILDNAMESPACE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BUILDNAMESPACE,
					args);

				args = new Object[] {
						serviceComponentModelImpl.getBuildNamespace()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_BUILDNAMESPACE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_BUILDNAMESPACE,
					args);
			}
		}

		EntityCacheUtil.putResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
			ServiceComponentImpl.class, serviceComponent.getPrimaryKey(),
			serviceComponent);

		clearUniqueFindersCache(serviceComponent);
		cacheUniqueFindersCache(serviceComponent);

		serviceComponent.resetOriginalValues();

		return serviceComponent;
	}

	protected ServiceComponent toUnwrappedModel(
		ServiceComponent serviceComponent) {
		if (serviceComponent instanceof ServiceComponentImpl) {
			return serviceComponent;
		}

		ServiceComponentImpl serviceComponentImpl = new ServiceComponentImpl();

		serviceComponentImpl.setNew(serviceComponent.isNew());
		serviceComponentImpl.setPrimaryKey(serviceComponent.getPrimaryKey());

		serviceComponentImpl.setServiceComponentId(serviceComponent.getServiceComponentId());
		serviceComponentImpl.setBuildNamespace(serviceComponent.getBuildNamespace());
		serviceComponentImpl.setBuildNumber(serviceComponent.getBuildNumber());
		serviceComponentImpl.setBuildDate(serviceComponent.getBuildDate());
		serviceComponentImpl.setData(serviceComponent.getData());
		serviceComponentImpl.setMvccVersion(serviceComponent.getMvccVersion());

		return serviceComponentImpl;
	}

	/**
	 * Returns the service component with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the service component
	 * @return the service component
	 * @throws com.liferay.portal.NoSuchServiceComponentException if a service component with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchServiceComponentException, SystemException {
		ServiceComponent serviceComponent = fetchByPrimaryKey(primaryKey);

		if (serviceComponent == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchServiceComponentException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return serviceComponent;
	}

	/**
	 * Returns the service component with the primary key or throws a {@link com.liferay.portal.NoSuchServiceComponentException} if it could not be found.
	 *
	 * @param serviceComponentId the primary key of the service component
	 * @return the service component
	 * @throws com.liferay.portal.NoSuchServiceComponentException if a service component with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent findByPrimaryKey(long serviceComponentId)
		throws NoSuchServiceComponentException, SystemException {
		return findByPrimaryKey((Serializable)serviceComponentId);
	}

	/**
	 * Returns the service component with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the service component
	 * @return the service component, or <code>null</code> if a service component with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		ServiceComponent serviceComponent = (ServiceComponent)EntityCacheUtil.getResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
				ServiceComponentImpl.class, primaryKey);

		if (serviceComponent == _nullServiceComponent) {
			return null;
		}

		if (serviceComponent == null) {
			Session session = null;

			try {
				session = openSession();

				serviceComponent = (ServiceComponent)session.get(ServiceComponentImpl.class,
						primaryKey);

				if (serviceComponent != null) {
					cacheResult(serviceComponent);
				}
				else {
					EntityCacheUtil.putResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
						ServiceComponentImpl.class, primaryKey,
						_nullServiceComponent);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ServiceComponentModelImpl.ENTITY_CACHE_ENABLED,
					ServiceComponentImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return serviceComponent;
	}

	/**
	 * Returns the service component with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param serviceComponentId the primary key of the service component
	 * @return the service component, or <code>null</code> if a service component with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public ServiceComponent fetchByPrimaryKey(long serviceComponentId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)serviceComponentId);
	}

	/**
	 * Returns all the service components.
	 *
	 * @return the service components
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ServiceComponent> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service components.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @return the range of service components
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ServiceComponent> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the service components.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ServiceComponentModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of service components
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<ServiceComponent> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<ServiceComponent> list = (List<ServiceComponent>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SERVICECOMPONENT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SERVICECOMPONENT;

				if (pagination) {
					sql = sql.concat(ServiceComponentModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ServiceComponent>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<ServiceComponent>(list);
				}
				else {
					list = (List<ServiceComponent>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the service components from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (ServiceComponent serviceComponent : findAll()) {
			remove(serviceComponent);
		}
	}

	/**
	 * Returns the number of service components.
	 *
	 * @return the number of service components
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SERVICECOMPONENT);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
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
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the service component persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.ServiceComponent")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<ServiceComponent>> listenersList = new ArrayList<ModelListener<ServiceComponent>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<ServiceComponent>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ServiceComponentImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SERVICECOMPONENT = "SELECT serviceComponent FROM ServiceComponent serviceComponent";
	private static final String _SQL_SELECT_SERVICECOMPONENT_WHERE = "SELECT serviceComponent FROM ServiceComponent serviceComponent WHERE ";
	private static final String _SQL_COUNT_SERVICECOMPONENT = "SELECT COUNT(serviceComponent) FROM ServiceComponent serviceComponent";
	private static final String _SQL_COUNT_SERVICECOMPONENT_WHERE = "SELECT COUNT(serviceComponent) FROM ServiceComponent serviceComponent WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "serviceComponent.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ServiceComponent exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ServiceComponent exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(ServiceComponentPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"data"
			});
	private static ServiceComponent _nullServiceComponent = new ServiceComponentImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ServiceComponent> toCacheModel() {
				return _nullServiceComponentCacheModel;
			}
		};

	private static CacheModel<ServiceComponent> _nullServiceComponentCacheModel = new CacheModel<ServiceComponent>() {
			@Override
			public ServiceComponent toEntityModel() {
				return _nullServiceComponent;
			}
		};
}