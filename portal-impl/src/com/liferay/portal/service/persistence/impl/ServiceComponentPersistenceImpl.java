/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchServiceComponentException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.model.ServiceComponentTable;
import com.liferay.portal.kernel.service.persistence.ServiceComponentPersistence;
import com.liferay.portal.kernel.service.persistence.ServiceComponentUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.ServiceComponentImpl;
import com.liferay.portal.model.impl.ServiceComponentModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the service component service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ServiceComponentPersistenceImpl
	extends BasePersistenceImpl<ServiceComponent>
	implements ServiceComponentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ServiceComponentUtil</code> to access the service component persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ServiceComponentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByBuildNamespace;
	private FinderPath _finderPathWithoutPaginationFindByBuildNamespace;
	private FinderPath _finderPathCountByBuildNamespace;

	/**
	 * Returns all the service components where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @return the matching service components
	 */
	@Override
	public List<ServiceComponent> findByBuildNamespace(String buildNamespace) {
		return findByBuildNamespace(
			buildNamespace, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service components where buildNamespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>.
	 * </p>
	 *
	 * @param buildNamespace the build namespace
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @return the range of matching service components
	 */
	@Override
	public List<ServiceComponent> findByBuildNamespace(
		String buildNamespace, int start, int end) {

		return findByBuildNamespace(buildNamespace, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service components where buildNamespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>.
	 * </p>
	 *
	 * @param buildNamespace the build namespace
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service components
	 */
	@Override
	public List<ServiceComponent> findByBuildNamespace(
		String buildNamespace, int start, int end,
		OrderByComparator<ServiceComponent> orderByComparator) {

		return findByBuildNamespace(
			buildNamespace, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the service components where buildNamespace = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>.
	 * </p>
	 *
	 * @param buildNamespace the build namespace
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching service components
	 */
	@Override
	public List<ServiceComponent> findByBuildNamespace(
		String buildNamespace, int start, int end,
		OrderByComparator<ServiceComponent> orderByComparator,
		boolean useFinderCache) {

		buildNamespace = Objects.toString(buildNamespace, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByBuildNamespace;
				finderArgs = new Object[] {buildNamespace};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByBuildNamespace;
			finderArgs = new Object[] {
				buildNamespace, start, end, orderByComparator
			};
		}

		List<ServiceComponent> list = null;

		if (useFinderCache) {
			list = (List<ServiceComponent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ServiceComponent serviceComponent : list) {
					if (!buildNamespace.equals(
							serviceComponent.getBuildNamespace())) {

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

			sb.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

			boolean bindBuildNamespace = false;

			if (buildNamespace.isEmpty()) {
				sb.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
			}
			else {
				bindBuildNamespace = true;

				sb.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ServiceComponentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindBuildNamespace) {
					queryPos.add(buildNamespace);
				}

				list = (List<ServiceComponent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first service component in the ordered set where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service component
	 * @throws NoSuchServiceComponentException if a matching service component could not be found
	 */
	@Override
	public ServiceComponent findByBuildNamespace_First(
			String buildNamespace,
			OrderByComparator<ServiceComponent> orderByComparator)
		throws NoSuchServiceComponentException {

		ServiceComponent serviceComponent = fetchByBuildNamespace_First(
			buildNamespace, orderByComparator);

		if (serviceComponent != null) {
			return serviceComponent;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("buildNamespace=");
		sb.append(buildNamespace);

		sb.append("}");

		throw new NoSuchServiceComponentException(sb.toString());
	}

	/**
	 * Returns the first service component in the ordered set where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service component, or <code>null</code> if a matching service component could not be found
	 */
	@Override
	public ServiceComponent fetchByBuildNamespace_First(
		String buildNamespace,
		OrderByComparator<ServiceComponent> orderByComparator) {

		List<ServiceComponent> list = findByBuildNamespace(
			buildNamespace, 0, 1, orderByComparator);

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
	 * @throws NoSuchServiceComponentException if a matching service component could not be found
	 */
	@Override
	public ServiceComponent findByBuildNamespace_Last(
			String buildNamespace,
			OrderByComparator<ServiceComponent> orderByComparator)
		throws NoSuchServiceComponentException {

		ServiceComponent serviceComponent = fetchByBuildNamespace_Last(
			buildNamespace, orderByComparator);

		if (serviceComponent != null) {
			return serviceComponent;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("buildNamespace=");
		sb.append(buildNamespace);

		sb.append("}");

		throw new NoSuchServiceComponentException(sb.toString());
	}

	/**
	 * Returns the last service component in the ordered set where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service component, or <code>null</code> if a matching service component could not be found
	 */
	@Override
	public ServiceComponent fetchByBuildNamespace_Last(
		String buildNamespace,
		OrderByComparator<ServiceComponent> orderByComparator) {

		int count = countByBuildNamespace(buildNamespace);

		if (count == 0) {
			return null;
		}

		List<ServiceComponent> list = findByBuildNamespace(
			buildNamespace, count - 1, count, orderByComparator);

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
	 * @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	 */
	@Override
	public ServiceComponent[] findByBuildNamespace_PrevAndNext(
			long serviceComponentId, String buildNamespace,
			OrderByComparator<ServiceComponent> orderByComparator)
		throws NoSuchServiceComponentException {

		buildNamespace = Objects.toString(buildNamespace, "");

		ServiceComponent serviceComponent = findByPrimaryKey(
			serviceComponentId);

		Session session = null;

		try {
			session = openSession();

			ServiceComponent[] array = new ServiceComponentImpl[3];

			array[0] = getByBuildNamespace_PrevAndNext(
				session, serviceComponent, buildNamespace, orderByComparator,
				true);

			array[1] = serviceComponent;

			array[2] = getByBuildNamespace_PrevAndNext(
				session, serviceComponent, buildNamespace, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ServiceComponent getByBuildNamespace_PrevAndNext(
		Session session, ServiceComponent serviceComponent,
		String buildNamespace,
		OrderByComparator<ServiceComponent> orderByComparator,
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

		sb.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

		boolean bindBuildNamespace = false;

		if (buildNamespace.isEmpty()) {
			sb.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
		}
		else {
			bindBuildNamespace = true;

			sb.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
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
			sb.append(ServiceComponentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindBuildNamespace) {
			queryPos.add(buildNamespace);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						serviceComponent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ServiceComponent> list = query.list();

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
	 */
	@Override
	public void removeByBuildNamespace(String buildNamespace) {
		for (ServiceComponent serviceComponent :
				findByBuildNamespace(
					buildNamespace, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(serviceComponent);
		}
	}

	/**
	 * Returns the number of service components where buildNamespace = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @return the number of matching service components
	 */
	@Override
	public int countByBuildNamespace(String buildNamespace) {
		buildNamespace = Objects.toString(buildNamespace, "");

		FinderPath finderPath = _finderPathCountByBuildNamespace;

		Object[] finderArgs = new Object[] {buildNamespace};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_SERVICECOMPONENT_WHERE);

			boolean bindBuildNamespace = false;

			if (buildNamespace.isEmpty()) {
				sb.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3);
			}
			else {
				bindBuildNamespace = true;

				sb.append(_FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindBuildNamespace) {
					queryPos.add(buildNamespace);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_2 =
		"serviceComponent.buildNamespace = ?";

	private static final String _FINDER_COLUMN_BUILDNAMESPACE_BUILDNAMESPACE_3 =
		"(serviceComponent.buildNamespace IS NULL OR serviceComponent.buildNamespace = '')";

	private FinderPath _finderPathFetchByBNS_BNU;
	private FinderPath _finderPathCountByBNS_BNU;

	/**
	 * Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or throws a <code>NoSuchServiceComponentException</code> if it could not be found.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @return the matching service component
	 * @throws NoSuchServiceComponentException if a matching service component could not be found
	 */
	@Override
	public ServiceComponent findByBNS_BNU(
			String buildNamespace, long buildNumber)
		throws NoSuchServiceComponentException {

		ServiceComponent serviceComponent = fetchByBNS_BNU(
			buildNamespace, buildNumber);

		if (serviceComponent == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("buildNamespace=");
			sb.append(buildNamespace);

			sb.append(", buildNumber=");
			sb.append(buildNumber);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchServiceComponentException(sb.toString());
		}

		return serviceComponent;
	}

	/**
	 * Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @return the matching service component, or <code>null</code> if a matching service component could not be found
	 */
	@Override
	public ServiceComponent fetchByBNS_BNU(
		String buildNamespace, long buildNumber) {

		return fetchByBNS_BNU(buildNamespace, buildNumber, true);
	}

	/**
	 * Returns the service component where buildNamespace = &#63; and buildNumber = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching service component, or <code>null</code> if a matching service component could not be found
	 */
	@Override
	public ServiceComponent fetchByBNS_BNU(
		String buildNamespace, long buildNumber, boolean useFinderCache) {

		buildNamespace = Objects.toString(buildNamespace, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {buildNamespace, buildNumber};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByBNS_BNU, finderArgs, this);
		}

		if (result instanceof ServiceComponent) {
			ServiceComponent serviceComponent = (ServiceComponent)result;

			if (!Objects.equals(
					buildNamespace, serviceComponent.getBuildNamespace()) ||
				(buildNumber != serviceComponent.getBuildNumber())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_SERVICECOMPONENT_WHERE);

			boolean bindBuildNamespace = false;

			if (buildNamespace.isEmpty()) {
				sb.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_3);
			}
			else {
				bindBuildNamespace = true;

				sb.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_2);
			}

			sb.append(_FINDER_COLUMN_BNS_BNU_BUILDNUMBER_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindBuildNamespace) {
					queryPos.add(buildNamespace);
				}

				queryPos.add(buildNumber);

				List<ServiceComponent> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByBNS_BNU, finderArgs, list);
					}
				}
				else {
					ServiceComponent serviceComponent = list.get(0);

					result = serviceComponent;

					cacheResult(serviceComponent);
				}
			}
			catch (Exception exception) {
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
			return (ServiceComponent)result;
		}
	}

	/**
	 * Removes the service component where buildNamespace = &#63; and buildNumber = &#63; from the database.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @return the service component that was removed
	 */
	@Override
	public ServiceComponent removeByBNS_BNU(
			String buildNamespace, long buildNumber)
		throws NoSuchServiceComponentException {

		ServiceComponent serviceComponent = findByBNS_BNU(
			buildNamespace, buildNumber);

		return remove(serviceComponent);
	}

	/**
	 * Returns the number of service components where buildNamespace = &#63; and buildNumber = &#63;.
	 *
	 * @param buildNamespace the build namespace
	 * @param buildNumber the build number
	 * @return the number of matching service components
	 */
	@Override
	public int countByBNS_BNU(String buildNamespace, long buildNumber) {
		buildNamespace = Objects.toString(buildNamespace, "");

		FinderPath finderPath = _finderPathCountByBNS_BNU;

		Object[] finderArgs = new Object[] {buildNamespace, buildNumber};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_SERVICECOMPONENT_WHERE);

			boolean bindBuildNamespace = false;

			if (buildNamespace.isEmpty()) {
				sb.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_3);
			}
			else {
				bindBuildNamespace = true;

				sb.append(_FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_2);
			}

			sb.append(_FINDER_COLUMN_BNS_BNU_BUILDNUMBER_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindBuildNamespace) {
					queryPos.add(buildNamespace);
				}

				queryPos.add(buildNumber);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_2 =
		"serviceComponent.buildNamespace = ? AND ";

	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNAMESPACE_3 =
		"(serviceComponent.buildNamespace IS NULL OR serviceComponent.buildNamespace = '') AND ";

	private static final String _FINDER_COLUMN_BNS_BNU_BUILDNUMBER_2 =
		"serviceComponent.buildNumber = ?";

	public ServiceComponentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("data", "data_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ServiceComponent.class);

		setModelImplClass(ServiceComponentImpl.class);
		setModelPKClass(long.class);

		setTable(ServiceComponentTable.INSTANCE);
	}

	/**
	 * Caches the service component in the entity cache if it is enabled.
	 *
	 * @param serviceComponent the service component
	 */
	@Override
	public void cacheResult(ServiceComponent serviceComponent) {
		EntityCacheUtil.putResult(
			ServiceComponentImpl.class, serviceComponent.getPrimaryKey(),
			serviceComponent);

		FinderCacheUtil.putResult(
			_finderPathFetchByBNS_BNU,
			new Object[] {
				serviceComponent.getBuildNamespace(),
				serviceComponent.getBuildNumber()
			},
			serviceComponent);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the service components in the entity cache if it is enabled.
	 *
	 * @param serviceComponents the service components
	 */
	@Override
	public void cacheResult(List<ServiceComponent> serviceComponents) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (serviceComponents.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ServiceComponent serviceComponent : serviceComponents) {
			if (EntityCacheUtil.getResult(
					ServiceComponentImpl.class,
					serviceComponent.getPrimaryKey()) == null) {

				cacheResult(serviceComponent);
			}
		}
	}

	/**
	 * Clears the cache for all service components.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ServiceComponentImpl.class);

		FinderCacheUtil.clearCache(ServiceComponentImpl.class);
	}

	/**
	 * Clears the cache for the service component.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ServiceComponent serviceComponent) {
		EntityCacheUtil.removeResult(
			ServiceComponentImpl.class, serviceComponent);
	}

	@Override
	public void clearCache(List<ServiceComponent> serviceComponents) {
		for (ServiceComponent serviceComponent : serviceComponents) {
			EntityCacheUtil.removeResult(
				ServiceComponentImpl.class, serviceComponent);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(ServiceComponentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				ServiceComponentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ServiceComponentModelImpl serviceComponentModelImpl) {

		Object[] args = new Object[] {
			serviceComponentModelImpl.getBuildNamespace(),
			serviceComponentModelImpl.getBuildNumber()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByBNS_BNU, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByBNS_BNU, args, serviceComponentModelImpl);
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
	 * @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	 */
	@Override
	public ServiceComponent remove(long serviceComponentId)
		throws NoSuchServiceComponentException {

		return remove((Serializable)serviceComponentId);
	}

	/**
	 * Removes the service component with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the service component
	 * @return the service component that was removed
	 * @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	 */
	@Override
	public ServiceComponent remove(Serializable primaryKey)
		throws NoSuchServiceComponentException {

		Session session = null;

		try {
			session = openSession();

			ServiceComponent serviceComponent = (ServiceComponent)session.get(
				ServiceComponentImpl.class, primaryKey);

			if (serviceComponent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchServiceComponentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(serviceComponent);
		}
		catch (NoSuchServiceComponentException noSuchEntityException) {
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
	protected ServiceComponent removeImpl(ServiceComponent serviceComponent) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(serviceComponent)) {
				serviceComponent = (ServiceComponent)session.get(
					ServiceComponentImpl.class,
					serviceComponent.getPrimaryKeyObj());
			}

			if (serviceComponent != null) {
				session.delete(serviceComponent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
	public ServiceComponent updateImpl(ServiceComponent serviceComponent) {
		boolean isNew = serviceComponent.isNew();

		if (!(serviceComponent instanceof ServiceComponentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(serviceComponent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					serviceComponent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in serviceComponent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ServiceComponent implementation " +
					serviceComponent.getClass());
		}

		ServiceComponentModelImpl serviceComponentModelImpl =
			(ServiceComponentModelImpl)serviceComponent;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(serviceComponent);
			}
			else {
				serviceComponent = (ServiceComponent)session.merge(
					serviceComponent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			ServiceComponentImpl.class, serviceComponentModelImpl, false, true);

		cacheUniqueFindersCache(serviceComponentModelImpl);

		if (isNew) {
			serviceComponent.setNew(false);
		}

		serviceComponent.resetOriginalValues();

		return serviceComponent;
	}

	/**
	 * Returns the service component with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the service component
	 * @return the service component
	 * @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	 */
	@Override
	public ServiceComponent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchServiceComponentException {

		ServiceComponent serviceComponent = fetchByPrimaryKey(primaryKey);

		if (serviceComponent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchServiceComponentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return serviceComponent;
	}

	/**
	 * Returns the service component with the primary key or throws a <code>NoSuchServiceComponentException</code> if it could not be found.
	 *
	 * @param serviceComponentId the primary key of the service component
	 * @return the service component
	 * @throws NoSuchServiceComponentException if a service component with the primary key could not be found
	 */
	@Override
	public ServiceComponent findByPrimaryKey(long serviceComponentId)
		throws NoSuchServiceComponentException {

		return findByPrimaryKey((Serializable)serviceComponentId);
	}

	/**
	 * Returns the service component with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param serviceComponentId the primary key of the service component
	 * @return the service component, or <code>null</code> if a service component with the primary key could not be found
	 */
	@Override
	public ServiceComponent fetchByPrimaryKey(long serviceComponentId) {
		return fetchByPrimaryKey((Serializable)serviceComponentId);
	}

	/**
	 * Returns all the service components.
	 *
	 * @return the service components
	 */
	@Override
	public List<ServiceComponent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service components.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @return the range of service components
	 */
	@Override
	public List<ServiceComponent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the service components.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of service components
	 */
	@Override
	public List<ServiceComponent> findAll(
		int start, int end,
		OrderByComparator<ServiceComponent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the service components.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ServiceComponentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of service components
	 * @param end the upper bound of the range of service components (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of service components
	 */
	@Override
	public List<ServiceComponent> findAll(
		int start, int end,
		OrderByComparator<ServiceComponent> orderByComparator,
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

		List<ServiceComponent> list = null;

		if (useFinderCache) {
			list = (List<ServiceComponent>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_SERVICECOMPONENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_SERVICECOMPONENT;

				sql = sql.concat(ServiceComponentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ServiceComponent>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the service components from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ServiceComponent serviceComponent : findAll()) {
			remove(serviceComponent);
		}
	}

	/**
	 * Returns the number of service components.
	 *
	 * @return the number of service components
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_SERVICECOMPONENT);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "serviceComponentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SERVICECOMPONENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ServiceComponentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the service component persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByBuildNamespace = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByBuildNamespace",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"buildNamespace"}, true);

		_finderPathWithoutPaginationFindByBuildNamespace = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByBuildNamespace",
			new String[] {String.class.getName()},
			new String[] {"buildNamespace"}, true);

		_finderPathCountByBuildNamespace = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBuildNamespace",
			new String[] {String.class.getName()},
			new String[] {"buildNamespace"}, false);

		_finderPathFetchByBNS_BNU = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBNS_BNU",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"buildNamespace", "buildNumber"}, true);

		_finderPathCountByBNS_BNU = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBNS_BNU",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"buildNamespace", "buildNumber"}, false);

		ServiceComponentUtil.setPersistence(this);
	}

	public void destroy() {
		ServiceComponentUtil.setPersistence(null);

		EntityCacheUtil.removeCache(ServiceComponentImpl.class.getName());
	}

	private static final String _SQL_SELECT_SERVICECOMPONENT =
		"SELECT serviceComponent FROM ServiceComponent serviceComponent";

	private static final String _SQL_SELECT_SERVICECOMPONENT_WHERE =
		"SELECT serviceComponent FROM ServiceComponent serviceComponent WHERE ";

	private static final String _SQL_COUNT_SERVICECOMPONENT =
		"SELECT COUNT(serviceComponent) FROM ServiceComponent serviceComponent";

	private static final String _SQL_COUNT_SERVICECOMPONENT_WHERE =
		"SELECT COUNT(serviceComponent) FROM ServiceComponent serviceComponent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "serviceComponent.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ServiceComponent exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ServiceComponent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceComponentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"data"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}