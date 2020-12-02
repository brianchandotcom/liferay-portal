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

package com.liferay.app.builder.service.persistence.impl;

import com.liferay.app.builder.exception.NoSuchAppDeploymentException;
import com.liferay.app.builder.model.AppBuilderAppDeployment;
import com.liferay.app.builder.model.AppBuilderAppDeploymentTable;
import com.liferay.app.builder.model.impl.AppBuilderAppDeploymentImpl;
import com.liferay.app.builder.model.impl.AppBuilderAppDeploymentModelImpl;
import com.liferay.app.builder.service.persistence.AppBuilderAppDeploymentPersistence;
import com.liferay.app.builder.service.persistence.impl.constants.AppBuilderPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the app builder app deployment service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {AppBuilderAppDeploymentPersistence.class, BasePersistence.class}
)
public class AppBuilderAppDeploymentPersistenceImpl
	extends BasePersistenceImpl<AppBuilderAppDeployment>
	implements AppBuilderAppDeploymentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AppBuilderAppDeploymentUtil</code> to access the app builder app deployment persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AppBuilderAppDeploymentImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAppBuilderAppId;
	private FinderPath _finderPathWithoutPaginationFindByAppBuilderAppId;
	private FinderPath _finderPathCountByAppBuilderAppId;

	/**
	 * Returns all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder app deployments
	 */
	@Override
	public List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId) {

		return findByAppBuilderAppId(
			appBuilderAppId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @return the range of matching app builder app deployments
	 */
	@Override
	public List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end) {

		return findByAppBuilderAppId(appBuilderAppId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app deployments
	 */
	@Override
	public List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator) {

		return findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app deployments
	 */
	@Override
	public List<AppBuilderAppDeployment> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAppBuilderAppId;
				finderArgs = new Object[] {appBuilderAppId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAppBuilderAppId;
			finderArgs = new Object[] {
				appBuilderAppId, start, end, orderByComparator
			};
		}

		List<AppBuilderAppDeployment> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppDeployment>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderAppDeployment appBuilderAppDeployment : list) {
					if (appBuilderAppId !=
							appBuilderAppDeployment.getAppBuilderAppId()) {

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

			sb.append(_SQL_SELECT_APPBUILDERAPPDEPLOYMENT_WHERE);

			sb.append(_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppDeploymentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				list = (List<AppBuilderAppDeployment>)QueryUtil.list(
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
	 * Returns the first app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app deployment
	 * @throws NoSuchAppDeploymentException if a matching app builder app deployment could not be found
	 */
	@Override
	public AppBuilderAppDeployment findByAppBuilderAppId_First(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppDeployment> orderByComparator)
		throws NoSuchAppDeploymentException {

		AppBuilderAppDeployment appBuilderAppDeployment =
			fetchByAppBuilderAppId_First(appBuilderAppId, orderByComparator);

		if (appBuilderAppDeployment != null) {
			return appBuilderAppDeployment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append("}");

		throw new NoSuchAppDeploymentException(sb.toString());
	}

	/**
	 * Returns the first app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	@Override
	public AppBuilderAppDeployment fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator) {

		List<AppBuilderAppDeployment> list = findByAppBuilderAppId(
			appBuilderAppId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app deployment
	 * @throws NoSuchAppDeploymentException if a matching app builder app deployment could not be found
	 */
	@Override
	public AppBuilderAppDeployment findByAppBuilderAppId_Last(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppDeployment> orderByComparator)
		throws NoSuchAppDeploymentException {

		AppBuilderAppDeployment appBuilderAppDeployment =
			fetchByAppBuilderAppId_Last(appBuilderAppId, orderByComparator);

		if (appBuilderAppDeployment != null) {
			return appBuilderAppDeployment;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append("}");

		throw new NoSuchAppDeploymentException(sb.toString());
	}

	/**
	 * Returns the last app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	@Override
	public AppBuilderAppDeployment fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator) {

		int count = countByAppBuilderAppId(appBuilderAppId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderAppDeployment> list = findByAppBuilderAppId(
			appBuilderAppId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder app deployments before and after the current app builder app deployment in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the current app builder app deployment
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app deployment
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDeployment[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderAppDeploymentId, long appBuilderAppId,
			OrderByComparator<AppBuilderAppDeployment> orderByComparator)
		throws NoSuchAppDeploymentException {

		AppBuilderAppDeployment appBuilderAppDeployment = findByPrimaryKey(
			appBuilderAppDeploymentId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppDeployment[] array =
				new AppBuilderAppDeploymentImpl[3];

			array[0] = getByAppBuilderAppId_PrevAndNext(
				session, appBuilderAppDeployment, appBuilderAppId,
				orderByComparator, true);

			array[1] = appBuilderAppDeployment;

			array[2] = getByAppBuilderAppId_PrevAndNext(
				session, appBuilderAppDeployment, appBuilderAppId,
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

	protected AppBuilderAppDeployment getByAppBuilderAppId_PrevAndNext(
		Session session, AppBuilderAppDeployment appBuilderAppDeployment,
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPPDEPLOYMENT_WHERE);

		sb.append(_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2);

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
			sb.append(AppBuilderAppDeploymentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(appBuilderAppId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderAppDeployment)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderAppDeployment> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder app deployments where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	@Override
	public void removeByAppBuilderAppId(long appBuilderAppId) {
		for (AppBuilderAppDeployment appBuilderAppDeployment :
				findByAppBuilderAppId(
					appBuilderAppId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderAppDeployment);
		}
	}

	/**
	 * Returns the number of app builder app deployments where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder app deployments
	 */
	@Override
	public int countByAppBuilderAppId(long appBuilderAppId) {
		FinderPath finderPath = _finderPathCountByAppBuilderAppId;

		Object[] finderArgs = new Object[] {appBuilderAppId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPPDEPLOYMENT_WHERE);

			sb.append(_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

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
		_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2 =
			"appBuilderAppDeployment.appBuilderAppId = ?";

	private FinderPath _finderPathFetchByA_T;
	private FinderPath _finderPathCountByA_T;

	/**
	 * Returns the app builder app deployment where appBuilderAppId = &#63; and type = &#63; or throws a <code>NoSuchAppDeploymentException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the matching app builder app deployment
	 * @throws NoSuchAppDeploymentException if a matching app builder app deployment could not be found
	 */
	@Override
	public AppBuilderAppDeployment findByA_T(long appBuilderAppId, String type)
		throws NoSuchAppDeploymentException {

		AppBuilderAppDeployment appBuilderAppDeployment = fetchByA_T(
			appBuilderAppId, type);

		if (appBuilderAppDeployment == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("appBuilderAppId=");
			sb.append(appBuilderAppId);

			sb.append(", type=");
			sb.append(type);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAppDeploymentException(sb.toString());
		}

		return appBuilderAppDeployment;
	}

	/**
	 * Returns the app builder app deployment where appBuilderAppId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	@Override
	public AppBuilderAppDeployment fetchByA_T(
		long appBuilderAppId, String type) {

		return fetchByA_T(appBuilderAppId, type, true);
	}

	/**
	 * Returns the app builder app deployment where appBuilderAppId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app deployment, or <code>null</code> if a matching app builder app deployment could not be found
	 */
	@Override
	public AppBuilderAppDeployment fetchByA_T(
		long appBuilderAppId, String type, boolean useFinderCache) {

		type = Objects.toString(type, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {appBuilderAppId, type};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByA_T, finderArgs);
		}

		if (result instanceof AppBuilderAppDeployment) {
			AppBuilderAppDeployment appBuilderAppDeployment =
				(AppBuilderAppDeployment)result;

			if ((appBuilderAppId !=
					appBuilderAppDeployment.getAppBuilderAppId()) ||
				!Objects.equals(type, appBuilderAppDeployment.getType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_APPBUILDERAPPDEPLOYMENT_WHERE);

			sb.append(_FINDER_COLUMN_A_T_APPBUILDERAPPID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_A_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				if (bindType) {
					queryPos.add(type);
				}

				List<AppBuilderAppDeployment> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByA_T, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									appBuilderAppId, type
								};
							}

							_log.warn(
								"AppBuilderAppDeploymentPersistenceImpl.fetchByA_T(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AppBuilderAppDeployment appBuilderAppDeployment = list.get(
						0);

					result = appBuilderAppDeployment;

					cacheResult(appBuilderAppDeployment);
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
			return (AppBuilderAppDeployment)result;
		}
	}

	/**
	 * Removes the app builder app deployment where appBuilderAppId = &#63; and type = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the app builder app deployment that was removed
	 */
	@Override
	public AppBuilderAppDeployment removeByA_T(
			long appBuilderAppId, String type)
		throws NoSuchAppDeploymentException {

		AppBuilderAppDeployment appBuilderAppDeployment = findByA_T(
			appBuilderAppId, type);

		return remove(appBuilderAppDeployment);
	}

	/**
	 * Returns the number of app builder app deployments where appBuilderAppId = &#63; and type = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param type the type
	 * @return the number of matching app builder app deployments
	 */
	@Override
	public int countByA_T(long appBuilderAppId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByA_T;

		Object[] finderArgs = new Object[] {appBuilderAppId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPPDEPLOYMENT_WHERE);

			sb.append(_FINDER_COLUMN_A_T_APPBUILDERAPPID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_A_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				if (bindType) {
					queryPos.add(type);
				}

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

	private static final String _FINDER_COLUMN_A_T_APPBUILDERAPPID_2 =
		"appBuilderAppDeployment.appBuilderAppId = ? AND ";

	private static final String _FINDER_COLUMN_A_T_TYPE_2 =
		"appBuilderAppDeployment.type = ?";

	private static final String _FINDER_COLUMN_A_T_TYPE_3 =
		"(appBuilderAppDeployment.type IS NULL OR appBuilderAppDeployment.type = '')";

	public AppBuilderAppDeploymentPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("settings", "settings_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(AppBuilderAppDeployment.class);

		setModelImplClass(AppBuilderAppDeploymentImpl.class);
		setModelPKClass(long.class);

		setTable(AppBuilderAppDeploymentTable.INSTANCE);
	}

	/**
	 * Caches the app builder app deployment in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDeployment the app builder app deployment
	 */
	@Override
	public void cacheResult(AppBuilderAppDeployment appBuilderAppDeployment) {
		entityCache.putResult(
			AppBuilderAppDeploymentImpl.class,
			appBuilderAppDeployment.getPrimaryKey(), appBuilderAppDeployment);

		finderCache.putResult(
			_finderPathFetchByA_T,
			new Object[] {
				appBuilderAppDeployment.getAppBuilderAppId(),
				appBuilderAppDeployment.getType()
			},
			appBuilderAppDeployment);
	}

	/**
	 * Caches the app builder app deployments in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDeployments the app builder app deployments
	 */
	@Override
	public void cacheResult(
		List<AppBuilderAppDeployment> appBuilderAppDeployments) {

		for (AppBuilderAppDeployment appBuilderAppDeployment :
				appBuilderAppDeployments) {

			if (entityCache.getResult(
					AppBuilderAppDeploymentImpl.class,
					appBuilderAppDeployment.getPrimaryKey()) == null) {

				cacheResult(appBuilderAppDeployment);
			}
		}
	}

	/**
	 * Clears the cache for all app builder app deployments.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AppBuilderAppDeploymentImpl.class);

		finderCache.clearCache(AppBuilderAppDeploymentImpl.class);
	}

	/**
	 * Clears the cache for the app builder app deployment.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AppBuilderAppDeployment appBuilderAppDeployment) {
		entityCache.removeResult(
			AppBuilderAppDeploymentImpl.class, appBuilderAppDeployment);
	}

	@Override
	public void clearCache(
		List<AppBuilderAppDeployment> appBuilderAppDeployments) {

		for (AppBuilderAppDeployment appBuilderAppDeployment :
				appBuilderAppDeployments) {

			entityCache.removeResult(
				AppBuilderAppDeploymentImpl.class, appBuilderAppDeployment);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AppBuilderAppDeploymentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AppBuilderAppDeploymentImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AppBuilderAppDeploymentModelImpl appBuilderAppDeploymentModelImpl) {

		Object[] args = new Object[] {
			appBuilderAppDeploymentModelImpl.getAppBuilderAppId(),
			appBuilderAppDeploymentModelImpl.getType()
		};

		finderCache.putResult(_finderPathCountByA_T, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByA_T, args, appBuilderAppDeploymentModelImpl);
	}

	/**
	 * Creates a new app builder app deployment with the primary key. Does not add the app builder app deployment to the database.
	 *
	 * @param appBuilderAppDeploymentId the primary key for the new app builder app deployment
	 * @return the new app builder app deployment
	 */
	@Override
	public AppBuilderAppDeployment create(long appBuilderAppDeploymentId) {
		AppBuilderAppDeployment appBuilderAppDeployment =
			new AppBuilderAppDeploymentImpl();

		appBuilderAppDeployment.setNew(true);
		appBuilderAppDeployment.setPrimaryKey(appBuilderAppDeploymentId);

		appBuilderAppDeployment.setCompanyId(CompanyThreadLocal.getCompanyId());

		return appBuilderAppDeployment;
	}

	/**
	 * Removes the app builder app deployment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment that was removed
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDeployment remove(long appBuilderAppDeploymentId)
		throws NoSuchAppDeploymentException {

		return remove((Serializable)appBuilderAppDeploymentId);
	}

	/**
	 * Removes the app builder app deployment with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the app builder app deployment
	 * @return the app builder app deployment that was removed
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDeployment remove(Serializable primaryKey)
		throws NoSuchAppDeploymentException {

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppDeployment appBuilderAppDeployment =
				(AppBuilderAppDeployment)session.get(
					AppBuilderAppDeploymentImpl.class, primaryKey);

			if (appBuilderAppDeployment == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAppDeploymentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(appBuilderAppDeployment);
		}
		catch (NoSuchAppDeploymentException noSuchEntityException) {
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
	protected AppBuilderAppDeployment removeImpl(
		AppBuilderAppDeployment appBuilderAppDeployment) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(appBuilderAppDeployment)) {
				appBuilderAppDeployment = (AppBuilderAppDeployment)session.get(
					AppBuilderAppDeploymentImpl.class,
					appBuilderAppDeployment.getPrimaryKeyObj());
			}

			if (appBuilderAppDeployment != null) {
				session.delete(appBuilderAppDeployment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (appBuilderAppDeployment != null) {
			clearCache(appBuilderAppDeployment);
		}

		return appBuilderAppDeployment;
	}

	@Override
	public AppBuilderAppDeployment updateImpl(
		AppBuilderAppDeployment appBuilderAppDeployment) {

		boolean isNew = appBuilderAppDeployment.isNew();

		if (!(appBuilderAppDeployment instanceof
				AppBuilderAppDeploymentModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(appBuilderAppDeployment.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					appBuilderAppDeployment);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in appBuilderAppDeployment proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AppBuilderAppDeployment implementation " +
					appBuilderAppDeployment.getClass());
		}

		AppBuilderAppDeploymentModelImpl appBuilderAppDeploymentModelImpl =
			(AppBuilderAppDeploymentModelImpl)appBuilderAppDeployment;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(appBuilderAppDeployment);
			}
			else {
				appBuilderAppDeployment =
					(AppBuilderAppDeployment)session.merge(
						appBuilderAppDeployment);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AppBuilderAppDeploymentImpl.class, appBuilderAppDeploymentModelImpl,
			false, true);

		cacheUniqueFindersCache(appBuilderAppDeploymentModelImpl);

		if (isNew) {
			appBuilderAppDeployment.setNew(false);
		}

		appBuilderAppDeployment.resetOriginalValues();

		return appBuilderAppDeployment;
	}

	/**
	 * Returns the app builder app deployment with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the app builder app deployment
	 * @return the app builder app deployment
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDeployment findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAppDeploymentException {

		AppBuilderAppDeployment appBuilderAppDeployment = fetchByPrimaryKey(
			primaryKey);

		if (appBuilderAppDeployment == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAppDeploymentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return appBuilderAppDeployment;
	}

	/**
	 * Returns the app builder app deployment with the primary key or throws a <code>NoSuchAppDeploymentException</code> if it could not be found.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment
	 * @throws NoSuchAppDeploymentException if a app builder app deployment with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDeployment findByPrimaryKey(
			long appBuilderAppDeploymentId)
		throws NoSuchAppDeploymentException {

		return findByPrimaryKey((Serializable)appBuilderAppDeploymentId);
	}

	/**
	 * Returns the app builder app deployment with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppDeploymentId the primary key of the app builder app deployment
	 * @return the app builder app deployment, or <code>null</code> if a app builder app deployment with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDeployment fetchByPrimaryKey(
		long appBuilderAppDeploymentId) {

		return fetchByPrimaryKey((Serializable)appBuilderAppDeploymentId);
	}

	/**
	 * Returns all the app builder app deployments.
	 *
	 * @return the app builder app deployments
	 */
	@Override
	public List<AppBuilderAppDeployment> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @return the range of app builder app deployments
	 */
	@Override
	public List<AppBuilderAppDeployment> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder app deployments
	 */
	@Override
	public List<AppBuilderAppDeployment> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app deployments.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDeploymentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app deployments
	 * @param end the upper bound of the range of app builder app deployments (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder app deployments
	 */
	@Override
	public List<AppBuilderAppDeployment> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppDeployment> orderByComparator,
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

		List<AppBuilderAppDeployment> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppDeployment>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_APPBUILDERAPPDEPLOYMENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_APPBUILDERAPPDEPLOYMENT;

				sql = sql.concat(
					AppBuilderAppDeploymentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AppBuilderAppDeployment>)QueryUtil.list(
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
	 * Removes all the app builder app deployments from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AppBuilderAppDeployment appBuilderAppDeployment : findAll()) {
			remove(appBuilderAppDeployment);
		}
	}

	/**
	 * Returns the number of app builder app deployments.
	 *
	 * @return the number of app builder app deployments
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
					_SQL_COUNT_APPBUILDERAPPDEPLOYMENT);

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
		return "appBuilderAppDeploymentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_APPBUILDERAPPDEPLOYMENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AppBuilderAppDeploymentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the app builder app deployment persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new AppBuilderAppDeploymentModelArgumentsResolver(),
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

		_finderPathWithPaginationFindByAppBuilderAppId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAppBuilderAppId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"appBuilderAppId"}, true);

		_finderPathWithoutPaginationFindByAppBuilderAppId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAppBuilderAppId",
			new String[] {Long.class.getName()},
			new String[] {"appBuilderAppId"}, true);

		_finderPathCountByAppBuilderAppId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAppBuilderAppId",
			new String[] {Long.class.getName()},
			new String[] {"appBuilderAppId"}, false);

		_finderPathFetchByA_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByA_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"appBuilderAppId", "type_"}, true);

		_finderPathCountByA_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"appBuilderAppId", "type_"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AppBuilderAppDeploymentImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = AppBuilderPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AppBuilderPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AppBuilderPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_APPBUILDERAPPDEPLOYMENT =
		"SELECT appBuilderAppDeployment FROM AppBuilderAppDeployment appBuilderAppDeployment";

	private static final String _SQL_SELECT_APPBUILDERAPPDEPLOYMENT_WHERE =
		"SELECT appBuilderAppDeployment FROM AppBuilderAppDeployment appBuilderAppDeployment WHERE ";

	private static final String _SQL_COUNT_APPBUILDERAPPDEPLOYMENT =
		"SELECT COUNT(appBuilderAppDeployment) FROM AppBuilderAppDeployment appBuilderAppDeployment";

	private static final String _SQL_COUNT_APPBUILDERAPPDEPLOYMENT_WHERE =
		"SELECT COUNT(appBuilderAppDeployment) FROM AppBuilderAppDeployment appBuilderAppDeployment WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"appBuilderAppDeployment.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AppBuilderAppDeployment exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AppBuilderAppDeployment exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AppBuilderAppDeploymentPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"settings", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class AppBuilderAppDeploymentModelArgumentsResolver
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

			AppBuilderAppDeploymentModelImpl appBuilderAppDeploymentModelImpl =
				(AppBuilderAppDeploymentModelImpl)baseModel;

			long columnBitmask =
				appBuilderAppDeploymentModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					appBuilderAppDeploymentModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						appBuilderAppDeploymentModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					appBuilderAppDeploymentModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return AppBuilderAppDeploymentImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return AppBuilderAppDeploymentTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			AppBuilderAppDeploymentModelImpl appBuilderAppDeploymentModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						appBuilderAppDeploymentModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] =
						appBuilderAppDeploymentModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}