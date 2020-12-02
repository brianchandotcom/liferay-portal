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

import com.liferay.app.builder.exception.NoSuchAppDataRecordLinkException;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLinkTable;
import com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkImpl;
import com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl;
import com.liferay.app.builder.service.persistence.AppBuilderAppDataRecordLinkPersistence;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
 * The persistence implementation for the app builder app data record link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		AppBuilderAppDataRecordLinkPersistence.class, BasePersistence.class
	}
)
public class AppBuilderAppDataRecordLinkPersistenceImpl
	extends BasePersistenceImpl<AppBuilderAppDataRecordLink>
	implements AppBuilderAppDataRecordLinkPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AppBuilderAppDataRecordLinkUtil</code> to access the app builder app data record link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AppBuilderAppDataRecordLinkImpl.class.getName();

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
	 * Returns all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId) {

		return findByAppBuilderAppId(
			appBuilderAppId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end) {

		return findByAppBuilderAppId(appBuilderAppId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		return findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator,
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

		List<AppBuilderAppDataRecordLink> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppDataRecordLink>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderAppDataRecordLink appBuilderAppDataRecordLink :
						list) {

					if (appBuilderAppId !=
							appBuilderAppDataRecordLink.getAppBuilderAppId()) {

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

			sb.append(_SQL_SELECT_APPBUILDERAPPDATARECORDLINK_WHERE);

			sb.append(_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppDataRecordLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				list = (List<AppBuilderAppDataRecordLink>)QueryUtil.list(
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
	 * Returns the first app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink findByAppBuilderAppId_First(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator)
		throws NoSuchAppDataRecordLinkException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			fetchByAppBuilderAppId_First(appBuilderAppId, orderByComparator);

		if (appBuilderAppDataRecordLink != null) {
			return appBuilderAppDataRecordLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append("}");

		throw new NoSuchAppDataRecordLinkException(sb.toString());
	}

	/**
	 * Returns the first app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		List<AppBuilderAppDataRecordLink> list = findByAppBuilderAppId(
			appBuilderAppId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink findByAppBuilderAppId_Last(
			long appBuilderAppId,
			OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator)
		throws NoSuchAppDataRecordLinkException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			fetchByAppBuilderAppId_Last(appBuilderAppId, orderByComparator);

		if (appBuilderAppDataRecordLink != null) {
			return appBuilderAppDataRecordLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append("}");

		throw new NoSuchAppDataRecordLinkException(sb.toString());
	}

	/**
	 * Returns the last app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		int count = countByAppBuilderAppId(appBuilderAppId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderAppDataRecordLink> list = findByAppBuilderAppId(
			appBuilderAppId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder app data record links before and after the current app builder app data record link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the current app builder app data record link
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderAppDataRecordLinkId, long appBuilderAppId,
			OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator)
		throws NoSuchAppDataRecordLinkException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			findByPrimaryKey(appBuilderAppDataRecordLinkId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppDataRecordLink[] array =
				new AppBuilderAppDataRecordLinkImpl[3];

			array[0] = getByAppBuilderAppId_PrevAndNext(
				session, appBuilderAppDataRecordLink, appBuilderAppId,
				orderByComparator, true);

			array[1] = appBuilderAppDataRecordLink;

			array[2] = getByAppBuilderAppId_PrevAndNext(
				session, appBuilderAppDataRecordLink, appBuilderAppId,
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

	protected AppBuilderAppDataRecordLink getByAppBuilderAppId_PrevAndNext(
		Session session,
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink,
		long appBuilderAppId,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPPDATARECORDLINK_WHERE);

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
			sb.append(AppBuilderAppDataRecordLinkModelImpl.ORDER_BY_JPQL);
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
						appBuilderAppDataRecordLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderAppDataRecordLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder app data record links where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	@Override
	public void removeByAppBuilderAppId(long appBuilderAppId) {
		for (AppBuilderAppDataRecordLink appBuilderAppDataRecordLink :
				findByAppBuilderAppId(
					appBuilderAppId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderAppDataRecordLink);
		}
	}

	/**
	 * Returns the number of app builder app data record links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder app data record links
	 */
	@Override
	public int countByAppBuilderAppId(long appBuilderAppId) {
		FinderPath finderPath = _finderPathCountByAppBuilderAppId;

		Object[] finderArgs = new Object[] {appBuilderAppId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPPDATARECORDLINK_WHERE);

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
			"appBuilderAppDataRecordLink.appBuilderAppId = ?";

	private FinderPath _finderPathFetchByDDLRecordId;
	private FinderPath _finderPathCountByDDLRecordId;

	/**
	 * Returns the app builder app data record link where ddlRecordId = &#63; or throws a <code>NoSuchAppDataRecordLinkException</code> if it could not be found.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink findByDDLRecordId(long ddlRecordId)
		throws NoSuchAppDataRecordLinkException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			fetchByDDLRecordId(ddlRecordId);

		if (appBuilderAppDataRecordLink == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("ddlRecordId=");
			sb.append(ddlRecordId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAppDataRecordLinkException(sb.toString());
		}

		return appBuilderAppDataRecordLink;
	}

	/**
	 * Returns the app builder app data record link where ddlRecordId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink fetchByDDLRecordId(long ddlRecordId) {
		return fetchByDDLRecordId(ddlRecordId, true);
	}

	/**
	 * Returns the app builder app data record link where ddlRecordId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink fetchByDDLRecordId(
		long ddlRecordId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {ddlRecordId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByDDLRecordId, finderArgs);
		}

		if (result instanceof AppBuilderAppDataRecordLink) {
			AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
				(AppBuilderAppDataRecordLink)result;

			if (ddlRecordId != appBuilderAppDataRecordLink.getDdlRecordId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_APPBUILDERAPPDATARECORDLINK_WHERE);

			sb.append(_FINDER_COLUMN_DDLRECORDID_DDLRECORDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ddlRecordId);

				List<AppBuilderAppDataRecordLink> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByDDLRecordId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {ddlRecordId};
							}

							_log.warn(
								"AppBuilderAppDataRecordLinkPersistenceImpl.fetchByDDLRecordId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
						list.get(0);

					result = appBuilderAppDataRecordLink;

					cacheResult(appBuilderAppDataRecordLink);
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
			return (AppBuilderAppDataRecordLink)result;
		}
	}

	/**
	 * Removes the app builder app data record link where ddlRecordId = &#63; from the database.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the app builder app data record link that was removed
	 */
	@Override
	public AppBuilderAppDataRecordLink removeByDDLRecordId(long ddlRecordId)
		throws NoSuchAppDataRecordLinkException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			findByDDLRecordId(ddlRecordId);

		return remove(appBuilderAppDataRecordLink);
	}

	/**
	 * Returns the number of app builder app data record links where ddlRecordId = &#63;.
	 *
	 * @param ddlRecordId the ddl record ID
	 * @return the number of matching app builder app data record links
	 */
	@Override
	public int countByDDLRecordId(long ddlRecordId) {
		FinderPath finderPath = _finderPathCountByDDLRecordId;

		Object[] finderArgs = new Object[] {ddlRecordId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERAPPDATARECORDLINK_WHERE);

			sb.append(_FINDER_COLUMN_DDLRECORDID_DDLRECORDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(ddlRecordId);

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

	private static final String _FINDER_COLUMN_DDLRECORDID_DDLRECORDID_2 =
		"appBuilderAppDataRecordLink.ddlRecordId = ?";

	private FinderPath _finderPathWithPaginationFindByA_D;
	private FinderPath _finderPathWithoutPaginationFindByA_D;
	private FinderPath _finderPathCountByA_D;
	private FinderPath _finderPathWithPaginationCountByA_D;

	/**
	 * Returns all the app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @return the matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByA_D(
		long appBuilderAppId, long ddlRecordId) {

		return findByA_D(
			appBuilderAppId, ddlRecordId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByA_D(
		long appBuilderAppId, long ddlRecordId, int start, int end) {

		return findByA_D(appBuilderAppId, ddlRecordId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByA_D(
		long appBuilderAppId, long ddlRecordId, int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		return findByA_D(
			appBuilderAppId, ddlRecordId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByA_D(
		long appBuilderAppId, long ddlRecordId, int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByA_D;
				finderArgs = new Object[] {appBuilderAppId, ddlRecordId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByA_D;
			finderArgs = new Object[] {
				appBuilderAppId, ddlRecordId, start, end, orderByComparator
			};
		}

		List<AppBuilderAppDataRecordLink> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppDataRecordLink>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderAppDataRecordLink appBuilderAppDataRecordLink :
						list) {

					if ((appBuilderAppId !=
							appBuilderAppDataRecordLink.getAppBuilderAppId()) ||
						(ddlRecordId !=
							appBuilderAppDataRecordLink.getDdlRecordId())) {

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

			sb.append(_SQL_SELECT_APPBUILDERAPPDATARECORDLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_D_APPBUILDERAPPID_2);

			sb.append(_FINDER_COLUMN_A_D_DDLRECORDID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppDataRecordLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				queryPos.add(ddlRecordId);

				list = (List<AppBuilderAppDataRecordLink>)QueryUtil.list(
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
	 * Returns the first app builder app data record link in the ordered set where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink findByA_D_First(
			long appBuilderAppId, long ddlRecordId,
			OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator)
		throws NoSuchAppDataRecordLinkException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			fetchByA_D_First(appBuilderAppId, ddlRecordId, orderByComparator);

		if (appBuilderAppDataRecordLink != null) {
			return appBuilderAppDataRecordLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append(", ddlRecordId=");
		sb.append(ddlRecordId);

		sb.append("}");

		throw new NoSuchAppDataRecordLinkException(sb.toString());
	}

	/**
	 * Returns the first app builder app data record link in the ordered set where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink fetchByA_D_First(
		long appBuilderAppId, long ddlRecordId,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		List<AppBuilderAppDataRecordLink> list = findByA_D(
			appBuilderAppId, ddlRecordId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder app data record link in the ordered set where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink findByA_D_Last(
			long appBuilderAppId, long ddlRecordId,
			OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator)
		throws NoSuchAppDataRecordLinkException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			fetchByA_D_Last(appBuilderAppId, ddlRecordId, orderByComparator);

		if (appBuilderAppDataRecordLink != null) {
			return appBuilderAppDataRecordLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append(", ddlRecordId=");
		sb.append(ddlRecordId);

		sb.append("}");

		throw new NoSuchAppDataRecordLinkException(sb.toString());
	}

	/**
	 * Returns the last app builder app data record link in the ordered set where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder app data record link, or <code>null</code> if a matching app builder app data record link could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink fetchByA_D_Last(
		long appBuilderAppId, long ddlRecordId,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		int count = countByA_D(appBuilderAppId, ddlRecordId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderAppDataRecordLink> list = findByA_D(
			appBuilderAppId, ddlRecordId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder app data record links before and after the current app builder app data record link in the ordered set where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the current app builder app data record link
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink[] findByA_D_PrevAndNext(
			long appBuilderAppDataRecordLinkId, long appBuilderAppId,
			long ddlRecordId,
			OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator)
		throws NoSuchAppDataRecordLinkException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			findByPrimaryKey(appBuilderAppDataRecordLinkId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppDataRecordLink[] array =
				new AppBuilderAppDataRecordLinkImpl[3];

			array[0] = getByA_D_PrevAndNext(
				session, appBuilderAppDataRecordLink, appBuilderAppId,
				ddlRecordId, orderByComparator, true);

			array[1] = appBuilderAppDataRecordLink;

			array[2] = getByA_D_PrevAndNext(
				session, appBuilderAppDataRecordLink, appBuilderAppId,
				ddlRecordId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AppBuilderAppDataRecordLink getByA_D_PrevAndNext(
		Session session,
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink,
		long appBuilderAppId, long ddlRecordId,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERAPPDATARECORDLINK_WHERE);

		sb.append(_FINDER_COLUMN_A_D_APPBUILDERAPPID_2);

		sb.append(_FINDER_COLUMN_A_D_DDLRECORDID_2);

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
			sb.append(AppBuilderAppDataRecordLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(appBuilderAppId);

		queryPos.add(ddlRecordId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderAppDataRecordLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderAppDataRecordLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordIds the ddl record IDs
	 * @return the matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByA_D(
		long appBuilderAppId, long[] ddlRecordIds) {

		return findByA_D(
			appBuilderAppId, ddlRecordIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordIds the ddl record IDs
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByA_D(
		long appBuilderAppId, long[] ddlRecordIds, int start, int end) {

		return findByA_D(appBuilderAppId, ddlRecordIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordIds the ddl record IDs
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByA_D(
		long appBuilderAppId, long[] ddlRecordIds, int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		return findByA_D(
			appBuilderAppId, ddlRecordIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findByA_D(
		long appBuilderAppId, long[] ddlRecordIds, int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator,
		boolean useFinderCache) {

		if (ddlRecordIds == null) {
			ddlRecordIds = new long[0];
		}
		else if (ddlRecordIds.length > 1) {
			ddlRecordIds = ArrayUtil.sortedUnique(ddlRecordIds);
		}

		if (ddlRecordIds.length == 1) {
			return findByA_D(
				appBuilderAppId, ddlRecordIds[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					appBuilderAppId, StringUtil.merge(ddlRecordIds)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				appBuilderAppId, StringUtil.merge(ddlRecordIds), start, end,
				orderByComparator
			};
		}

		List<AppBuilderAppDataRecordLink> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppDataRecordLink>)finderCache.getResult(
				_finderPathWithPaginationFindByA_D, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderAppDataRecordLink appBuilderAppDataRecordLink :
						list) {

					if ((appBuilderAppId !=
							appBuilderAppDataRecordLink.getAppBuilderAppId()) ||
						!ArrayUtil.contains(
							ddlRecordIds,
							appBuilderAppDataRecordLink.getDdlRecordId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_APPBUILDERAPPDATARECORDLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_D_APPBUILDERAPPID_2);

			if (ddlRecordIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_A_D_DDLRECORDID_7);

				sb.append(StringUtil.merge(ddlRecordIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderAppDataRecordLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				list = (List<AppBuilderAppDataRecordLink>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByA_D, finderArgs, list);
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
	 * Removes all the app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 */
	@Override
	public void removeByA_D(long appBuilderAppId, long ddlRecordId) {
		for (AppBuilderAppDataRecordLink appBuilderAppDataRecordLink :
				findByA_D(
					appBuilderAppId, ddlRecordId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(appBuilderAppDataRecordLink);
		}
	}

	/**
	 * Returns the number of app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordId the ddl record ID
	 * @return the number of matching app builder app data record links
	 */
	@Override
	public int countByA_D(long appBuilderAppId, long ddlRecordId) {
		FinderPath finderPath = _finderPathCountByA_D;

		Object[] finderArgs = new Object[] {appBuilderAppId, ddlRecordId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERAPPDATARECORDLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_D_APPBUILDERAPPID_2);

			sb.append(_FINDER_COLUMN_A_D_DDLRECORDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				queryPos.add(ddlRecordId);

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

	/**
	 * Returns the number of app builder app data record links where appBuilderAppId = &#63; and ddlRecordId = any &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param ddlRecordIds the ddl record IDs
	 * @return the number of matching app builder app data record links
	 */
	@Override
	public int countByA_D(long appBuilderAppId, long[] ddlRecordIds) {
		if (ddlRecordIds == null) {
			ddlRecordIds = new long[0];
		}
		else if (ddlRecordIds.length > 1) {
			ddlRecordIds = ArrayUtil.sortedUnique(ddlRecordIds);
		}

		Object[] finderArgs = new Object[] {
			appBuilderAppId, StringUtil.merge(ddlRecordIds)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByA_D, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_APPBUILDERAPPDATARECORDLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_D_APPBUILDERAPPID_2);

			if (ddlRecordIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_A_D_DDLRECORDID_7);

				sb.append(StringUtil.merge(ddlRecordIds));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByA_D, finderArgs, count);
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

	private static final String _FINDER_COLUMN_A_D_APPBUILDERAPPID_2 =
		"appBuilderAppDataRecordLink.appBuilderAppId = ? AND ";

	private static final String _FINDER_COLUMN_A_D_DDLRECORDID_2 =
		"appBuilderAppDataRecordLink.ddlRecordId = ?";

	private static final String _FINDER_COLUMN_A_D_DDLRECORDID_7 =
		"appBuilderAppDataRecordLink.ddlRecordId IN (";

	public AppBuilderAppDataRecordLinkPersistenceImpl() {
		setModelClass(AppBuilderAppDataRecordLink.class);

		setModelImplClass(AppBuilderAppDataRecordLinkImpl.class);
		setModelPKClass(long.class);

		setTable(AppBuilderAppDataRecordLinkTable.INSTANCE);
	}

	/**
	 * Caches the app builder app data record link in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 */
	@Override
	public void cacheResult(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		entityCache.putResult(
			AppBuilderAppDataRecordLinkImpl.class,
			appBuilderAppDataRecordLink.getPrimaryKey(),
			appBuilderAppDataRecordLink);

		finderCache.putResult(
			_finderPathFetchByDDLRecordId,
			new Object[] {appBuilderAppDataRecordLink.getDdlRecordId()},
			appBuilderAppDataRecordLink);
	}

	/**
	 * Caches the app builder app data record links in the entity cache if it is enabled.
	 *
	 * @param appBuilderAppDataRecordLinks the app builder app data record links
	 */
	@Override
	public void cacheResult(
		List<AppBuilderAppDataRecordLink> appBuilderAppDataRecordLinks) {

		for (AppBuilderAppDataRecordLink appBuilderAppDataRecordLink :
				appBuilderAppDataRecordLinks) {

			if (entityCache.getResult(
					AppBuilderAppDataRecordLinkImpl.class,
					appBuilderAppDataRecordLink.getPrimaryKey()) == null) {

				cacheResult(appBuilderAppDataRecordLink);
			}
		}
	}

	/**
	 * Clears the cache for all app builder app data record links.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AppBuilderAppDataRecordLinkImpl.class);

		finderCache.clearCache(AppBuilderAppDataRecordLinkImpl.class);
	}

	/**
	 * Clears the cache for the app builder app data record link.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		entityCache.removeResult(
			AppBuilderAppDataRecordLinkImpl.class, appBuilderAppDataRecordLink);
	}

	@Override
	public void clearCache(
		List<AppBuilderAppDataRecordLink> appBuilderAppDataRecordLinks) {

		for (AppBuilderAppDataRecordLink appBuilderAppDataRecordLink :
				appBuilderAppDataRecordLinks) {

			entityCache.removeResult(
				AppBuilderAppDataRecordLinkImpl.class,
				appBuilderAppDataRecordLink);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AppBuilderAppDataRecordLinkImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AppBuilderAppDataRecordLinkImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AppBuilderAppDataRecordLinkModelImpl
			appBuilderAppDataRecordLinkModelImpl) {

		Object[] args = new Object[] {
			appBuilderAppDataRecordLinkModelImpl.getDdlRecordId()
		};

		finderCache.putResult(
			_finderPathCountByDDLRecordId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByDDLRecordId, args,
			appBuilderAppDataRecordLinkModelImpl);
	}

	/**
	 * Creates a new app builder app data record link with the primary key. Does not add the app builder app data record link to the database.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key for the new app builder app data record link
	 * @return the new app builder app data record link
	 */
	@Override
	public AppBuilderAppDataRecordLink create(
		long appBuilderAppDataRecordLinkId) {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			new AppBuilderAppDataRecordLinkImpl();

		appBuilderAppDataRecordLink.setNew(true);
		appBuilderAppDataRecordLink.setPrimaryKey(
			appBuilderAppDataRecordLinkId);

		appBuilderAppDataRecordLink.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return appBuilderAppDataRecordLink;
	}

	/**
	 * Removes the app builder app data record link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link that was removed
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink remove(
			long appBuilderAppDataRecordLinkId)
		throws NoSuchAppDataRecordLinkException {

		return remove((Serializable)appBuilderAppDataRecordLinkId);
	}

	/**
	 * Removes the app builder app data record link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the app builder app data record link
	 * @return the app builder app data record link that was removed
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink remove(Serializable primaryKey)
		throws NoSuchAppDataRecordLinkException {

		Session session = null;

		try {
			session = openSession();

			AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
				(AppBuilderAppDataRecordLink)session.get(
					AppBuilderAppDataRecordLinkImpl.class, primaryKey);

			if (appBuilderAppDataRecordLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAppDataRecordLinkException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(appBuilderAppDataRecordLink);
		}
		catch (NoSuchAppDataRecordLinkException noSuchEntityException) {
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
	protected AppBuilderAppDataRecordLink removeImpl(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(appBuilderAppDataRecordLink)) {
				appBuilderAppDataRecordLink =
					(AppBuilderAppDataRecordLink)session.get(
						AppBuilderAppDataRecordLinkImpl.class,
						appBuilderAppDataRecordLink.getPrimaryKeyObj());
			}

			if (appBuilderAppDataRecordLink != null) {
				session.delete(appBuilderAppDataRecordLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (appBuilderAppDataRecordLink != null) {
			clearCache(appBuilderAppDataRecordLink);
		}

		return appBuilderAppDataRecordLink;
	}

	@Override
	public AppBuilderAppDataRecordLink updateImpl(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		boolean isNew = appBuilderAppDataRecordLink.isNew();

		if (!(appBuilderAppDataRecordLink instanceof
				AppBuilderAppDataRecordLinkModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					appBuilderAppDataRecordLink.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					appBuilderAppDataRecordLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in appBuilderAppDataRecordLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AppBuilderAppDataRecordLink implementation " +
					appBuilderAppDataRecordLink.getClass());
		}

		AppBuilderAppDataRecordLinkModelImpl
			appBuilderAppDataRecordLinkModelImpl =
				(AppBuilderAppDataRecordLinkModelImpl)
					appBuilderAppDataRecordLink;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(appBuilderAppDataRecordLink);
			}
			else {
				appBuilderAppDataRecordLink =
					(AppBuilderAppDataRecordLink)session.merge(
						appBuilderAppDataRecordLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AppBuilderAppDataRecordLinkImpl.class,
			appBuilderAppDataRecordLinkModelImpl, false, true);

		cacheUniqueFindersCache(appBuilderAppDataRecordLinkModelImpl);

		if (isNew) {
			appBuilderAppDataRecordLink.setNew(false);
		}

		appBuilderAppDataRecordLink.resetOriginalValues();

		return appBuilderAppDataRecordLink;
	}

	/**
	 * Returns the app builder app data record link with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the app builder app data record link
	 * @return the app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAppDataRecordLinkException {

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			fetchByPrimaryKey(primaryKey);

		if (appBuilderAppDataRecordLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAppDataRecordLinkException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return appBuilderAppDataRecordLink;
	}

	/**
	 * Returns the app builder app data record link with the primary key or throws a <code>NoSuchAppDataRecordLinkException</code> if it could not be found.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link
	 * @throws NoSuchAppDataRecordLinkException if a app builder app data record link with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink findByPrimaryKey(
			long appBuilderAppDataRecordLinkId)
		throws NoSuchAppDataRecordLinkException {

		return findByPrimaryKey((Serializable)appBuilderAppDataRecordLinkId);
	}

	/**
	 * Returns the app builder app data record link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link, or <code>null</code> if a app builder app data record link with the primary key could not be found
	 */
	@Override
	public AppBuilderAppDataRecordLink fetchByPrimaryKey(
		long appBuilderAppDataRecordLinkId) {

		return fetchByPrimaryKey((Serializable)appBuilderAppDataRecordLinkId);
	}

	/**
	 * Returns all the app builder app data record links.
	 *
	 * @return the app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder app data record links
	 */
	@Override
	public List<AppBuilderAppDataRecordLink> findAll(
		int start, int end,
		OrderByComparator<AppBuilderAppDataRecordLink> orderByComparator,
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

		List<AppBuilderAppDataRecordLink> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderAppDataRecordLink>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_APPBUILDERAPPDATARECORDLINK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_APPBUILDERAPPDATARECORDLINK;

				sql = sql.concat(
					AppBuilderAppDataRecordLinkModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AppBuilderAppDataRecordLink>)QueryUtil.list(
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
	 * Removes all the app builder app data record links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AppBuilderAppDataRecordLink appBuilderAppDataRecordLink :
				findAll()) {

			remove(appBuilderAppDataRecordLink);
		}
	}

	/**
	 * Returns the number of app builder app data record links.
	 *
	 * @return the number of app builder app data record links
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
					_SQL_COUNT_APPBUILDERAPPDATARECORDLINK);

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
		return "appBuilderAppDataRecordLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_APPBUILDERAPPDATARECORDLINK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AppBuilderAppDataRecordLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the app builder app data record link persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new AppBuilderAppDataRecordLinkModelArgumentsResolver(),
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

		_finderPathFetchByDDLRecordId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByDDLRecordId",
			new String[] {Long.class.getName()}, new String[] {"ddlRecordId"},
			true);

		_finderPathCountByDDLRecordId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDDLRecordId",
			new String[] {Long.class.getName()}, new String[] {"ddlRecordId"},
			false);

		_finderPathWithPaginationFindByA_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_D",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"appBuilderAppId", "ddlRecordId"}, true);

		_finderPathWithoutPaginationFindByA_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_D",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"appBuilderAppId", "ddlRecordId"}, true);

		_finderPathCountByA_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_D",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"appBuilderAppId", "ddlRecordId"}, false);

		_finderPathWithPaginationCountByA_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByA_D",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"appBuilderAppId", "ddlRecordId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			AppBuilderAppDataRecordLinkImpl.class.getName());

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

	private static final String _SQL_SELECT_APPBUILDERAPPDATARECORDLINK =
		"SELECT appBuilderAppDataRecordLink FROM AppBuilderAppDataRecordLink appBuilderAppDataRecordLink";

	private static final String _SQL_SELECT_APPBUILDERAPPDATARECORDLINK_WHERE =
		"SELECT appBuilderAppDataRecordLink FROM AppBuilderAppDataRecordLink appBuilderAppDataRecordLink WHERE ";

	private static final String _SQL_COUNT_APPBUILDERAPPDATARECORDLINK =
		"SELECT COUNT(appBuilderAppDataRecordLink) FROM AppBuilderAppDataRecordLink appBuilderAppDataRecordLink";

	private static final String _SQL_COUNT_APPBUILDERAPPDATARECORDLINK_WHERE =
		"SELECT COUNT(appBuilderAppDataRecordLink) FROM AppBuilderAppDataRecordLink appBuilderAppDataRecordLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"appBuilderAppDataRecordLink.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AppBuilderAppDataRecordLink exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AppBuilderAppDataRecordLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AppBuilderAppDataRecordLinkPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class AppBuilderAppDataRecordLinkModelArgumentsResolver
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

			AppBuilderAppDataRecordLinkModelImpl
				appBuilderAppDataRecordLinkModelImpl =
					(AppBuilderAppDataRecordLinkModelImpl)baseModel;

			long columnBitmask =
				appBuilderAppDataRecordLinkModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					appBuilderAppDataRecordLinkModelImpl, columnNames,
					original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						appBuilderAppDataRecordLinkModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					appBuilderAppDataRecordLinkModelImpl, columnNames,
					original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return AppBuilderAppDataRecordLinkImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return AppBuilderAppDataRecordLinkTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			AppBuilderAppDataRecordLinkModelImpl
				appBuilderAppDataRecordLinkModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						appBuilderAppDataRecordLinkModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						appBuilderAppDataRecordLinkModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}