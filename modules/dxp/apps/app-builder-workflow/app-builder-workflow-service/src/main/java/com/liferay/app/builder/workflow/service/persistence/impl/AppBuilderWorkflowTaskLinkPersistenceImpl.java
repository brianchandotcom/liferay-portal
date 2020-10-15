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

package com.liferay.app.builder.workflow.service.persistence.impl;

import com.liferay.app.builder.workflow.exception.NoSuchTaskLinkException;
import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLinkTable;
import com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkImpl;
import com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkModelImpl;
import com.liferay.app.builder.workflow.service.persistence.AppBuilderWorkflowTaskLinkPersistence;
import com.liferay.app.builder.workflow.service.persistence.impl.constants.AppBuilderWorkflowPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashSet;
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
 * The persistence implementation for the app builder workflow task link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = AppBuilderWorkflowTaskLinkPersistence.class)
public class AppBuilderWorkflowTaskLinkPersistenceImpl
	extends BasePersistenceImpl<AppBuilderWorkflowTaskLink>
	implements AppBuilderWorkflowTaskLinkPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AppBuilderWorkflowTaskLinkUtil</code> to access the app builder workflow task link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AppBuilderWorkflowTaskLinkImpl.class.getName();

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
	 * Returns all the app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId) {

		return findByAppBuilderAppId(
			appBuilderAppId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end) {

		return findByAppBuilderAppId(appBuilderAppId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
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

		List<AppBuilderWorkflowTaskLink> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderWorkflowTaskLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink :
						list) {

					if (appBuilderAppId !=
							appBuilderWorkflowTaskLink.getAppBuilderAppId()) {

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

			sb.append(_SQL_SELECT_APPBUILDERWORKFLOWTASKLINK_WHERE);

			sb.append(_FINDER_COLUMN_APPBUILDERAPPID_APPBUILDERAPPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderWorkflowTaskLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				list = (List<AppBuilderWorkflowTaskLink>)QueryUtil.list(
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
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink findByAppBuilderAppId_First(
			long appBuilderAppId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			fetchByAppBuilderAppId_First(appBuilderAppId, orderByComparator);

		if (appBuilderWorkflowTaskLink != null) {
			return appBuilderWorkflowTaskLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append("}");

		throw new NoSuchTaskLinkException(sb.toString());
	}

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		List<AppBuilderWorkflowTaskLink> list = findByAppBuilderAppId(
			appBuilderAppId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink findByAppBuilderAppId_Last(
			long appBuilderAppId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			fetchByAppBuilderAppId_Last(appBuilderAppId, orderByComparator);

		if (appBuilderWorkflowTaskLink != null) {
			return appBuilderWorkflowTaskLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append("}");

		throw new NoSuchTaskLinkException(sb.toString());
	}

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		int count = countByAppBuilderAppId(appBuilderAppId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderWorkflowTaskLink> list = findByAppBuilderAppId(
			appBuilderAppId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder workflow task links before and after the current app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the current app builder workflow task link
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink[] findByAppBuilderAppId_PrevAndNext(
			long appBuilderWorkflowTaskLinkId, long appBuilderAppId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			findByPrimaryKey(appBuilderWorkflowTaskLinkId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderWorkflowTaskLink[] array =
				new AppBuilderWorkflowTaskLinkImpl[3];

			array[0] = getByAppBuilderAppId_PrevAndNext(
				session, appBuilderWorkflowTaskLink, appBuilderAppId,
				orderByComparator, true);

			array[1] = appBuilderWorkflowTaskLink;

			array[2] = getByAppBuilderAppId_PrevAndNext(
				session, appBuilderWorkflowTaskLink, appBuilderAppId,
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

	protected AppBuilderWorkflowTaskLink getByAppBuilderAppId_PrevAndNext(
		Session session, AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink,
		long appBuilderAppId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERWORKFLOWTASKLINK_WHERE);

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
			sb.append(AppBuilderWorkflowTaskLinkModelImpl.ORDER_BY_JPQL);
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
						appBuilderWorkflowTaskLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderWorkflowTaskLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder workflow task links where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	@Override
	public void removeByAppBuilderAppId(long appBuilderAppId) {
		for (AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink :
				findByAppBuilderAppId(
					appBuilderAppId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(appBuilderWorkflowTaskLink);
		}
	}

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder workflow task links
	 */
	@Override
	public int countByAppBuilderAppId(long appBuilderAppId) {
		FinderPath finderPath = _finderPathCountByAppBuilderAppId;

		Object[] finderArgs = new Object[] {appBuilderAppId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_APPBUILDERWORKFLOWTASKLINK_WHERE);

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
			"appBuilderWorkflowTaskLink.appBuilderAppId = ?";

	private FinderPath _finderPathWithPaginationFindByA_A;
	private FinderPath _finderPathWithoutPaginationFindByA_A;
	private FinderPath _finderPathCountByA_A;

	/**
	 * Returns all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @return the matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId) {

		return findByA_A(
			appBuilderAppId, appBuilderAppVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId, int start, int end) {

		return findByA_A(
			appBuilderAppId, appBuilderAppVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return findByA_A(
			appBuilderAppId, appBuilderAppVersionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByA_A;
				finderArgs = new Object[] {
					appBuilderAppId, appBuilderAppVersionId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByA_A;
			finderArgs = new Object[] {
				appBuilderAppId, appBuilderAppVersionId, start, end,
				orderByComparator
			};
		}

		List<AppBuilderWorkflowTaskLink> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderWorkflowTaskLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink :
						list) {

					if ((appBuilderAppId !=
							appBuilderWorkflowTaskLink.getAppBuilderAppId()) ||
						(appBuilderAppVersionId !=
							appBuilderWorkflowTaskLink.
								getAppBuilderAppVersionId())) {

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

			sb.append(_SQL_SELECT_APPBUILDERWORKFLOWTASKLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_A_APPBUILDERAPPID_2);

			sb.append(_FINDER_COLUMN_A_A_APPBUILDERAPPVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderWorkflowTaskLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				queryPos.add(appBuilderAppVersionId);

				list = (List<AppBuilderWorkflowTaskLink>)QueryUtil.list(
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
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink findByA_A_First(
			long appBuilderAppId, long appBuilderAppVersionId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			fetchByA_A_First(
				appBuilderAppId, appBuilderAppVersionId, orderByComparator);

		if (appBuilderWorkflowTaskLink != null) {
			return appBuilderWorkflowTaskLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append(", appBuilderAppVersionId=");
		sb.append(appBuilderAppVersionId);

		sb.append("}");

		throw new NoSuchTaskLinkException(sb.toString());
	}

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink fetchByA_A_First(
		long appBuilderAppId, long appBuilderAppVersionId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		List<AppBuilderWorkflowTaskLink> list = findByA_A(
			appBuilderAppId, appBuilderAppVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink findByA_A_Last(
			long appBuilderAppId, long appBuilderAppVersionId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink = fetchByA_A_Last(
			appBuilderAppId, appBuilderAppVersionId, orderByComparator);

		if (appBuilderWorkflowTaskLink != null) {
			return appBuilderWorkflowTaskLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append(", appBuilderAppVersionId=");
		sb.append(appBuilderAppVersionId);

		sb.append("}");

		throw new NoSuchTaskLinkException(sb.toString());
	}

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink fetchByA_A_Last(
		long appBuilderAppId, long appBuilderAppVersionId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		int count = countByA_A(appBuilderAppId, appBuilderAppVersionId);

		if (count == 0) {
			return null;
		}

		List<AppBuilderWorkflowTaskLink> list = findByA_A(
			appBuilderAppId, appBuilderAppVersionId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder workflow task links before and after the current app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the current app builder workflow task link
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink[] findByA_A_PrevAndNext(
			long appBuilderWorkflowTaskLinkId, long appBuilderAppId,
			long appBuilderAppVersionId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			findByPrimaryKey(appBuilderWorkflowTaskLinkId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderWorkflowTaskLink[] array =
				new AppBuilderWorkflowTaskLinkImpl[3];

			array[0] = getByA_A_PrevAndNext(
				session, appBuilderWorkflowTaskLink, appBuilderAppId,
				appBuilderAppVersionId, orderByComparator, true);

			array[1] = appBuilderWorkflowTaskLink;

			array[2] = getByA_A_PrevAndNext(
				session, appBuilderWorkflowTaskLink, appBuilderAppId,
				appBuilderAppVersionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected AppBuilderWorkflowTaskLink getByA_A_PrevAndNext(
		Session session, AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink,
		long appBuilderAppId, long appBuilderAppVersionId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
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

		sb.append(_SQL_SELECT_APPBUILDERWORKFLOWTASKLINK_WHERE);

		sb.append(_FINDER_COLUMN_A_A_APPBUILDERAPPID_2);

		sb.append(_FINDER_COLUMN_A_A_APPBUILDERAPPVERSIONID_2);

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
			sb.append(AppBuilderWorkflowTaskLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(appBuilderAppId);

		queryPos.add(appBuilderAppVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderWorkflowTaskLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderWorkflowTaskLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 */
	@Override
	public void removeByA_A(long appBuilderAppId, long appBuilderAppVersionId) {
		for (AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink :
				findByA_A(
					appBuilderAppId, appBuilderAppVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(appBuilderWorkflowTaskLink);
		}
	}

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @return the number of matching app builder workflow task links
	 */
	@Override
	public int countByA_A(long appBuilderAppId, long appBuilderAppVersionId) {
		FinderPath finderPath = _finderPathCountByA_A;

		Object[] finderArgs = new Object[] {
			appBuilderAppId, appBuilderAppVersionId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_APPBUILDERWORKFLOWTASKLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_A_APPBUILDERAPPID_2);

			sb.append(_FINDER_COLUMN_A_A_APPBUILDERAPPVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				queryPos.add(appBuilderAppVersionId);

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

	private static final String _FINDER_COLUMN_A_A_APPBUILDERAPPID_2 =
		"appBuilderWorkflowTaskLink.appBuilderAppId = ? AND ";

	private static final String _FINDER_COLUMN_A_A_APPBUILDERAPPVERSIONID_2 =
		"appBuilderWorkflowTaskLink.appBuilderAppVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByA_A_W;
	private FinderPath _finderPathWithoutPaginationFindByA_A_W;
	private FinderPath _finderPathCountByA_A_W;

	/**
	 * Returns all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName) {

		return findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName, int start, int end) {

		return findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache) {

		workflowTaskName = Objects.toString(workflowTaskName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByA_A_W;
				finderArgs = new Object[] {
					appBuilderAppId, appBuilderAppVersionId, workflowTaskName
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByA_A_W;
			finderArgs = new Object[] {
				appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
				start, end, orderByComparator
			};
		}

		List<AppBuilderWorkflowTaskLink> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderWorkflowTaskLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink :
						list) {

					if ((appBuilderAppId !=
							appBuilderWorkflowTaskLink.getAppBuilderAppId()) ||
						(appBuilderAppVersionId !=
							appBuilderWorkflowTaskLink.
								getAppBuilderAppVersionId()) ||
						!workflowTaskName.equals(
							appBuilderWorkflowTaskLink.getWorkflowTaskName())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_APPBUILDERWORKFLOWTASKLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_A_W_APPBUILDERAPPID_2);

			sb.append(_FINDER_COLUMN_A_A_W_APPBUILDERAPPVERSIONID_2);

			boolean bindWorkflowTaskName = false;

			if (workflowTaskName.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_A_W_WORKFLOWTASKNAME_3);
			}
			else {
				bindWorkflowTaskName = true;

				sb.append(_FINDER_COLUMN_A_A_W_WORKFLOWTASKNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(AppBuilderWorkflowTaskLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				queryPos.add(appBuilderAppVersionId);

				if (bindWorkflowTaskName) {
					queryPos.add(workflowTaskName);
				}

				list = (List<AppBuilderWorkflowTaskLink>)QueryUtil.list(
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
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink findByA_A_W_First(
			long appBuilderAppId, long appBuilderAppVersionId,
			String workflowTaskName,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			fetchByA_A_W_First(
				appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
				orderByComparator);

		if (appBuilderWorkflowTaskLink != null) {
			return appBuilderWorkflowTaskLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append(", appBuilderAppVersionId=");
		sb.append(appBuilderAppVersionId);

		sb.append(", workflowTaskName=");
		sb.append(workflowTaskName);

		sb.append("}");

		throw new NoSuchTaskLinkException(sb.toString());
	}

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink fetchByA_A_W_First(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		List<AppBuilderWorkflowTaskLink> list = findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink findByA_A_W_Last(
			long appBuilderAppId, long appBuilderAppVersionId,
			String workflowTaskName,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			fetchByA_A_W_Last(
				appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
				orderByComparator);

		if (appBuilderWorkflowTaskLink != null) {
			return appBuilderWorkflowTaskLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("appBuilderAppId=");
		sb.append(appBuilderAppId);

		sb.append(", appBuilderAppVersionId=");
		sb.append(appBuilderAppVersionId);

		sb.append(", workflowTaskName=");
		sb.append(workflowTaskName);

		sb.append("}");

		throw new NoSuchTaskLinkException(sb.toString());
	}

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink fetchByA_A_W_Last(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		int count = countByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName);

		if (count == 0) {
			return null;
		}

		List<AppBuilderWorkflowTaskLink> list = findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
			count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the app builder workflow task links before and after the current app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the current app builder workflow task link
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink[] findByA_A_W_PrevAndNext(
			long appBuilderWorkflowTaskLinkId, long appBuilderAppId,
			long appBuilderAppVersionId, String workflowTaskName,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws NoSuchTaskLinkException {

		workflowTaskName = Objects.toString(workflowTaskName, "");

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			findByPrimaryKey(appBuilderWorkflowTaskLinkId);

		Session session = null;

		try {
			session = openSession();

			AppBuilderWorkflowTaskLink[] array =
				new AppBuilderWorkflowTaskLinkImpl[3];

			array[0] = getByA_A_W_PrevAndNext(
				session, appBuilderWorkflowTaskLink, appBuilderAppId,
				appBuilderAppVersionId, workflowTaskName, orderByComparator,
				true);

			array[1] = appBuilderWorkflowTaskLink;

			array[2] = getByA_A_W_PrevAndNext(
				session, appBuilderWorkflowTaskLink, appBuilderAppId,
				appBuilderAppVersionId, workflowTaskName, orderByComparator,
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

	protected AppBuilderWorkflowTaskLink getByA_A_W_PrevAndNext(
		Session session, AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink,
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_APPBUILDERWORKFLOWTASKLINK_WHERE);

		sb.append(_FINDER_COLUMN_A_A_W_APPBUILDERAPPID_2);

		sb.append(_FINDER_COLUMN_A_A_W_APPBUILDERAPPVERSIONID_2);

		boolean bindWorkflowTaskName = false;

		if (workflowTaskName.isEmpty()) {
			sb.append(_FINDER_COLUMN_A_A_W_WORKFLOWTASKNAME_3);
		}
		else {
			bindWorkflowTaskName = true;

			sb.append(_FINDER_COLUMN_A_A_W_WORKFLOWTASKNAME_2);
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
			sb.append(AppBuilderWorkflowTaskLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(appBuilderAppId);

		queryPos.add(appBuilderAppVersionId);

		if (bindWorkflowTaskName) {
			queryPos.add(workflowTaskName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						appBuilderWorkflowTaskLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<AppBuilderWorkflowTaskLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 */
	@Override
	public void removeByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName) {

		for (AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink :
				findByA_A_W(
					appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(appBuilderWorkflowTaskLink);
		}
	}

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @return the number of matching app builder workflow task links
	 */
	@Override
	public int countByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName) {

		workflowTaskName = Objects.toString(workflowTaskName, "");

		FinderPath finderPath = _finderPathCountByA_A_W;

		Object[] finderArgs = new Object[] {
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_APPBUILDERWORKFLOWTASKLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_A_W_APPBUILDERAPPID_2);

			sb.append(_FINDER_COLUMN_A_A_W_APPBUILDERAPPVERSIONID_2);

			boolean bindWorkflowTaskName = false;

			if (workflowTaskName.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_A_W_WORKFLOWTASKNAME_3);
			}
			else {
				bindWorkflowTaskName = true;

				sb.append(_FINDER_COLUMN_A_A_W_WORKFLOWTASKNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				queryPos.add(appBuilderAppVersionId);

				if (bindWorkflowTaskName) {
					queryPos.add(workflowTaskName);
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

	private static final String _FINDER_COLUMN_A_A_W_APPBUILDERAPPID_2 =
		"appBuilderWorkflowTaskLink.appBuilderAppId = ? AND ";

	private static final String _FINDER_COLUMN_A_A_W_APPBUILDERAPPVERSIONID_2 =
		"appBuilderWorkflowTaskLink.appBuilderAppVersionId = ? AND ";

	private static final String _FINDER_COLUMN_A_A_W_WORKFLOWTASKNAME_2 =
		"appBuilderWorkflowTaskLink.workflowTaskName = ?";

	private static final String _FINDER_COLUMN_A_A_W_WORKFLOWTASKNAME_3 =
		"(appBuilderWorkflowTaskLink.workflowTaskName IS NULL OR appBuilderWorkflowTaskLink.workflowTaskName = '')";

	private FinderPath _finderPathFetchByA_A_D_W;
	private FinderPath _finderPathCountByA_A_D_W;

	/**
	 * Returns the app builder workflow task link where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63; or throws a <code>NoSuchTaskLinkException</code> if it could not be found.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink findByA_A_D_W(
			long appBuilderAppId, long appBuilderAppVersionId,
			long ddmStructureLayoutId, String workflowTaskName)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink = fetchByA_A_D_W(
			appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
			workflowTaskName);

		if (appBuilderWorkflowTaskLink == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("appBuilderAppId=");
			sb.append(appBuilderAppId);

			sb.append(", appBuilderAppVersionId=");
			sb.append(appBuilderAppVersionId);

			sb.append(", ddmStructureLayoutId=");
			sb.append(ddmStructureLayoutId);

			sb.append(", workflowTaskName=");
			sb.append(workflowTaskName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTaskLinkException(sb.toString());
		}

		return appBuilderWorkflowTaskLink;
	}

	/**
	 * Returns the app builder workflow task link where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink fetchByA_A_D_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		long ddmStructureLayoutId, String workflowTaskName) {

		return fetchByA_A_D_W(
			appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
			workflowTaskName, true);
	}

	/**
	 * Returns the app builder workflow task link where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink fetchByA_A_D_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		long ddmStructureLayoutId, String workflowTaskName,
		boolean useFinderCache) {

		workflowTaskName = Objects.toString(workflowTaskName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
				workflowTaskName
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByA_A_D_W, finderArgs, this);
		}

		if (result instanceof AppBuilderWorkflowTaskLink) {
			AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
				(AppBuilderWorkflowTaskLink)result;

			if ((appBuilderAppId !=
					appBuilderWorkflowTaskLink.getAppBuilderAppId()) ||
				(appBuilderAppVersionId !=
					appBuilderWorkflowTaskLink.getAppBuilderAppVersionId()) ||
				(ddmStructureLayoutId !=
					appBuilderWorkflowTaskLink.getDdmStructureLayoutId()) ||
				!Objects.equals(
					workflowTaskName,
					appBuilderWorkflowTaskLink.getWorkflowTaskName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_SELECT_APPBUILDERWORKFLOWTASKLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_A_D_W_APPBUILDERAPPID_2);

			sb.append(_FINDER_COLUMN_A_A_D_W_APPBUILDERAPPVERSIONID_2);

			sb.append(_FINDER_COLUMN_A_A_D_W_DDMSTRUCTURELAYOUTID_2);

			boolean bindWorkflowTaskName = false;

			if (workflowTaskName.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_A_D_W_WORKFLOWTASKNAME_3);
			}
			else {
				bindWorkflowTaskName = true;

				sb.append(_FINDER_COLUMN_A_A_D_W_WORKFLOWTASKNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				queryPos.add(appBuilderAppVersionId);

				queryPos.add(ddmStructureLayoutId);

				if (bindWorkflowTaskName) {
					queryPos.add(workflowTaskName);
				}

				List<AppBuilderWorkflowTaskLink> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByA_A_D_W, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									appBuilderAppId, appBuilderAppVersionId,
									ddmStructureLayoutId, workflowTaskName
								};
							}

							_log.warn(
								"AppBuilderWorkflowTaskLinkPersistenceImpl.fetchByA_A_D_W(long, long, long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
						list.get(0);

					result = appBuilderWorkflowTaskLink;

					cacheResult(appBuilderWorkflowTaskLink);
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
			return (AppBuilderWorkflowTaskLink)result;
		}
	}

	/**
	 * Removes the app builder workflow task link where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @return the app builder workflow task link that was removed
	 */
	@Override
	public AppBuilderWorkflowTaskLink removeByA_A_D_W(
			long appBuilderAppId, long appBuilderAppVersionId,
			long ddmStructureLayoutId, String workflowTaskName)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink = findByA_A_D_W(
			appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
			workflowTaskName);

		return remove(appBuilderWorkflowTaskLink);
	}

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and ddmStructureLayoutId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param ddmStructureLayoutId the ddm structure layout ID
	 * @param workflowTaskName the workflow task name
	 * @return the number of matching app builder workflow task links
	 */
	@Override
	public int countByA_A_D_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		long ddmStructureLayoutId, String workflowTaskName) {

		workflowTaskName = Objects.toString(workflowTaskName, "");

		FinderPath finderPath = _finderPathCountByA_A_D_W;

		Object[] finderArgs = new Object[] {
			appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
			workflowTaskName
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_APPBUILDERWORKFLOWTASKLINK_WHERE);

			sb.append(_FINDER_COLUMN_A_A_D_W_APPBUILDERAPPID_2);

			sb.append(_FINDER_COLUMN_A_A_D_W_APPBUILDERAPPVERSIONID_2);

			sb.append(_FINDER_COLUMN_A_A_D_W_DDMSTRUCTURELAYOUTID_2);

			boolean bindWorkflowTaskName = false;

			if (workflowTaskName.isEmpty()) {
				sb.append(_FINDER_COLUMN_A_A_D_W_WORKFLOWTASKNAME_3);
			}
			else {
				bindWorkflowTaskName = true;

				sb.append(_FINDER_COLUMN_A_A_D_W_WORKFLOWTASKNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(appBuilderAppId);

				queryPos.add(appBuilderAppVersionId);

				queryPos.add(ddmStructureLayoutId);

				if (bindWorkflowTaskName) {
					queryPos.add(workflowTaskName);
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

	private static final String _FINDER_COLUMN_A_A_D_W_APPBUILDERAPPID_2 =
		"appBuilderWorkflowTaskLink.appBuilderAppId = ? AND ";

	private static final String
		_FINDER_COLUMN_A_A_D_W_APPBUILDERAPPVERSIONID_2 =
			"appBuilderWorkflowTaskLink.appBuilderAppVersionId = ? AND ";

	private static final String _FINDER_COLUMN_A_A_D_W_DDMSTRUCTURELAYOUTID_2 =
		"appBuilderWorkflowTaskLink.ddmStructureLayoutId = ? AND ";

	private static final String _FINDER_COLUMN_A_A_D_W_WORKFLOWTASKNAME_2 =
		"appBuilderWorkflowTaskLink.workflowTaskName = ?";

	private static final String _FINDER_COLUMN_A_A_D_W_WORKFLOWTASKNAME_3 =
		"(appBuilderWorkflowTaskLink.workflowTaskName IS NULL OR appBuilderWorkflowTaskLink.workflowTaskName = '')";

	public AppBuilderWorkflowTaskLinkPersistenceImpl() {
		setModelClass(AppBuilderWorkflowTaskLink.class);

		setModelImplClass(AppBuilderWorkflowTaskLinkImpl.class);
		setModelPKClass(long.class);

		setTable(AppBuilderWorkflowTaskLinkTable.INSTANCE);
	}

	/**
	 * Caches the app builder workflow task link in the entity cache if it is enabled.
	 *
	 * @param appBuilderWorkflowTaskLink the app builder workflow task link
	 */
	@Override
	public void cacheResult(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		entityCache.putResult(
			AppBuilderWorkflowTaskLinkImpl.class,
			appBuilderWorkflowTaskLink.getPrimaryKey(),
			appBuilderWorkflowTaskLink);

		finderCache.putResult(
			_finderPathFetchByA_A_D_W,
			new Object[] {
				appBuilderWorkflowTaskLink.getAppBuilderAppId(),
				appBuilderWorkflowTaskLink.getAppBuilderAppVersionId(),
				appBuilderWorkflowTaskLink.getDdmStructureLayoutId(),
				appBuilderWorkflowTaskLink.getWorkflowTaskName()
			},
			appBuilderWorkflowTaskLink);
	}

	/**
	 * Caches the app builder workflow task links in the entity cache if it is enabled.
	 *
	 * @param appBuilderWorkflowTaskLinks the app builder workflow task links
	 */
	@Override
	public void cacheResult(
		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks) {

		for (AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink :
				appBuilderWorkflowTaskLinks) {

			if (entityCache.getResult(
					AppBuilderWorkflowTaskLinkImpl.class,
					appBuilderWorkflowTaskLink.getPrimaryKey()) == null) {

				cacheResult(appBuilderWorkflowTaskLink);
			}
		}
	}

	/**
	 * Clears the cache for all app builder workflow task links.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AppBuilderWorkflowTaskLinkImpl.class);

		finderCache.clearCache(AppBuilderWorkflowTaskLinkImpl.class);
	}

	/**
	 * Clears the cache for the app builder workflow task link.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		entityCache.removeResult(
			AppBuilderWorkflowTaskLinkImpl.class, appBuilderWorkflowTaskLink);
	}

	@Override
	public void clearCache(
		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks) {

		for (AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink :
				appBuilderWorkflowTaskLinks) {

			entityCache.removeResult(
				AppBuilderWorkflowTaskLinkImpl.class,
				appBuilderWorkflowTaskLink);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(AppBuilderWorkflowTaskLinkImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				AppBuilderWorkflowTaskLinkImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AppBuilderWorkflowTaskLinkModelImpl
			appBuilderWorkflowTaskLinkModelImpl) {

		Object[] args = new Object[] {
			appBuilderWorkflowTaskLinkModelImpl.getAppBuilderAppId(),
			appBuilderWorkflowTaskLinkModelImpl.getAppBuilderAppVersionId(),
			appBuilderWorkflowTaskLinkModelImpl.getDdmStructureLayoutId(),
			appBuilderWorkflowTaskLinkModelImpl.getWorkflowTaskName()
		};

		finderCache.putResult(
			_finderPathCountByA_A_D_W, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByA_A_D_W, args,
			appBuilderWorkflowTaskLinkModelImpl, false);
	}

	/**
	 * Creates a new app builder workflow task link with the primary key. Does not add the app builder workflow task link to the database.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key for the new app builder workflow task link
	 * @return the new app builder workflow task link
	 */
	@Override
	public AppBuilderWorkflowTaskLink create(
		long appBuilderWorkflowTaskLinkId) {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			new AppBuilderWorkflowTaskLinkImpl();

		appBuilderWorkflowTaskLink.setNew(true);
		appBuilderWorkflowTaskLink.setPrimaryKey(appBuilderWorkflowTaskLinkId);

		appBuilderWorkflowTaskLink.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return appBuilderWorkflowTaskLink;
	}

	/**
	 * Removes the app builder workflow task link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link that was removed
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink remove(long appBuilderWorkflowTaskLinkId)
		throws NoSuchTaskLinkException {

		return remove((Serializable)appBuilderWorkflowTaskLinkId);
	}

	/**
	 * Removes the app builder workflow task link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the app builder workflow task link
	 * @return the app builder workflow task link that was removed
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink remove(Serializable primaryKey)
		throws NoSuchTaskLinkException {

		Session session = null;

		try {
			session = openSession();

			AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
				(AppBuilderWorkflowTaskLink)session.get(
					AppBuilderWorkflowTaskLinkImpl.class, primaryKey);

			if (appBuilderWorkflowTaskLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaskLinkException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(appBuilderWorkflowTaskLink);
		}
		catch (NoSuchTaskLinkException noSuchEntityException) {
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
	protected AppBuilderWorkflowTaskLink removeImpl(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(appBuilderWorkflowTaskLink)) {
				appBuilderWorkflowTaskLink =
					(AppBuilderWorkflowTaskLink)session.get(
						AppBuilderWorkflowTaskLinkImpl.class,
						appBuilderWorkflowTaskLink.getPrimaryKeyObj());
			}

			if (appBuilderWorkflowTaskLink != null) {
				session.delete(appBuilderWorkflowTaskLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (appBuilderWorkflowTaskLink != null) {
			clearCache(appBuilderWorkflowTaskLink);
		}

		return appBuilderWorkflowTaskLink;
	}

	@Override
	public AppBuilderWorkflowTaskLink updateImpl(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		boolean isNew = appBuilderWorkflowTaskLink.isNew();

		if (!(appBuilderWorkflowTaskLink instanceof
				AppBuilderWorkflowTaskLinkModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(appBuilderWorkflowTaskLink.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					appBuilderWorkflowTaskLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in appBuilderWorkflowTaskLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AppBuilderWorkflowTaskLink implementation " +
					appBuilderWorkflowTaskLink.getClass());
		}

		AppBuilderWorkflowTaskLinkModelImpl
			appBuilderWorkflowTaskLinkModelImpl =
				(AppBuilderWorkflowTaskLinkModelImpl)appBuilderWorkflowTaskLink;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(appBuilderWorkflowTaskLink);
			}
			else {
				appBuilderWorkflowTaskLink =
					(AppBuilderWorkflowTaskLink)session.merge(
						appBuilderWorkflowTaskLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			AppBuilderWorkflowTaskLinkImpl.class,
			appBuilderWorkflowTaskLinkModelImpl, false, true);

		cacheUniqueFindersCache(appBuilderWorkflowTaskLinkModelImpl);

		if (isNew) {
			appBuilderWorkflowTaskLink.setNew(false);
		}

		appBuilderWorkflowTaskLink.resetOriginalValues();

		return appBuilderWorkflowTaskLink;
	}

	/**
	 * Returns the app builder workflow task link with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the app builder workflow task link
	 * @return the app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaskLinkException {

		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink =
			fetchByPrimaryKey(primaryKey);

		if (appBuilderWorkflowTaskLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaskLinkException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return appBuilderWorkflowTaskLink;
	}

	/**
	 * Returns the app builder workflow task link with the primary key or throws a <code>NoSuchTaskLinkException</code> if it could not be found.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink findByPrimaryKey(
			long appBuilderWorkflowTaskLinkId)
		throws NoSuchTaskLinkException {

		return findByPrimaryKey((Serializable)appBuilderWorkflowTaskLinkId);
	}

	/**
	 * Returns the app builder workflow task link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link, or <code>null</code> if a app builder workflow task link with the primary key could not be found
	 */
	@Override
	public AppBuilderWorkflowTaskLink fetchByPrimaryKey(
		long appBuilderWorkflowTaskLinkId) {

		return fetchByPrimaryKey((Serializable)appBuilderWorkflowTaskLinkId);
	}

	/**
	 * Returns all the app builder workflow task links.
	 *
	 * @return the app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the app builder workflow task links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the app builder workflow task links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findAll(
		int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the app builder workflow task links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of app builder workflow task links
	 */
	@Override
	public List<AppBuilderWorkflowTaskLink> findAll(
		int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
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

		List<AppBuilderWorkflowTaskLink> list = null;

		if (useFinderCache) {
			list = (List<AppBuilderWorkflowTaskLink>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_APPBUILDERWORKFLOWTASKLINK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_APPBUILDERWORKFLOWTASKLINK;

				sql = sql.concat(
					AppBuilderWorkflowTaskLinkModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<AppBuilderWorkflowTaskLink>)QueryUtil.list(
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
	 * Removes all the app builder workflow task links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink :
				findAll()) {

			remove(appBuilderWorkflowTaskLink);
		}
	}

	/**
	 * Returns the number of app builder workflow task links.
	 *
	 * @return the number of app builder workflow task links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_APPBUILDERWORKFLOWTASKLINK);

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
		return "appBuilderWorkflowTaskLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_APPBUILDERWORKFLOWTASKLINK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AppBuilderWorkflowTaskLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the app builder workflow task link persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new AppBuilderWorkflowTaskLinkModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name",
				AppBuilderWorkflowTaskLink.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByAppBuilderAppId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAppBuilderAppId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"appBuilderAppId"}, true);

		_finderPathWithoutPaginationFindByAppBuilderAppId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAppBuilderAppId",
			new String[] {Long.class.getName()},
			new String[] {"appBuilderAppId"}, true);

		_finderPathCountByAppBuilderAppId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAppBuilderAppId",
			new String[] {Long.class.getName()},
			new String[] {"appBuilderAppId"}, false);

		_finderPathWithPaginationFindByA_A = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"appBuilderAppId", "appBuilderAppVersionId"}, true);

		_finderPathWithoutPaginationFindByA_A = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_A",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"appBuilderAppId", "appBuilderAppVersionId"}, true);

		_finderPathCountByA_A = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_A",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"appBuilderAppId", "appBuilderAppVersionId"}, false);

		_finderPathWithPaginationFindByA_A_W = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByA_A_W",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"appBuilderAppId", "appBuilderAppVersionId", "workflowTaskName"
			},
			true);

		_finderPathWithoutPaginationFindByA_A_W = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByA_A_W",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {
				"appBuilderAppId", "appBuilderAppVersionId", "workflowTaskName"
			},
			true);

		_finderPathCountByA_A_W = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_A_W",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			new String[] {
				"appBuilderAppId", "appBuilderAppVersionId", "workflowTaskName"
			},
			false);

		_finderPathFetchByA_A_D_W = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByA_A_D_W",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {
				"appBuilderAppId", "appBuilderAppVersionId",
				"ddmStructureLayoutId", "workflowTaskName"
			},
			true);

		_finderPathCountByA_A_D_W = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_A_D_W",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {
				"appBuilderAppId", "appBuilderAppVersionId",
				"ddmStructureLayoutId", "workflowTaskName"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(AppBuilderWorkflowTaskLinkImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	@Override
	@Reference(
		target = AppBuilderWorkflowPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = AppBuilderWorkflowPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = AppBuilderWorkflowPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_APPBUILDERWORKFLOWTASKLINK =
		"SELECT appBuilderWorkflowTaskLink FROM AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink";

	private static final String _SQL_SELECT_APPBUILDERWORKFLOWTASKLINK_WHERE =
		"SELECT appBuilderWorkflowTaskLink FROM AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink WHERE ";

	private static final String _SQL_COUNT_APPBUILDERWORKFLOWTASKLINK =
		"SELECT COUNT(appBuilderWorkflowTaskLink) FROM AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink";

	private static final String _SQL_COUNT_APPBUILDERWORKFLOWTASKLINK_WHERE =
		"SELECT COUNT(appBuilderWorkflowTaskLink) FROM AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"appBuilderWorkflowTaskLink.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AppBuilderWorkflowTaskLink exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AppBuilderWorkflowTaskLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AppBuilderWorkflowTaskLinkPersistenceImpl.class);

	static {
		try {
			Class.forName(
				AppBuilderWorkflowPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;
	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();

	private static class AppBuilderWorkflowTaskLinkModelArgumentsResolver
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

			AppBuilderWorkflowTaskLinkModelImpl
				appBuilderWorkflowTaskLinkModelImpl =
					(AppBuilderWorkflowTaskLinkModelImpl)baseModel;

			long columnBitmask =
				appBuilderWorkflowTaskLinkModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					appBuilderWorkflowTaskLinkModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						appBuilderWorkflowTaskLinkModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					appBuilderWorkflowTaskLinkModelImpl, columnNames, original);
			}

			return null;
		}

		private Object[] _getValue(
			AppBuilderWorkflowTaskLinkModelImpl
				appBuilderWorkflowTaskLinkModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						appBuilderWorkflowTaskLinkModelImpl.
							getColumnOriginalValue(columnName);
				}
				else {
					arguments[i] =
						appBuilderWorkflowTaskLinkModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}