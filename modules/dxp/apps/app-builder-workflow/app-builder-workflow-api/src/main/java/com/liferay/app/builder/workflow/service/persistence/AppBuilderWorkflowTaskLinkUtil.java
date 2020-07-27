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

package com.liferay.app.builder.workflow.service.persistence;

import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the app builder workflow task link service. This utility wraps <code>com.liferay.app.builder.workflow.service.persistence.impl.AppBuilderWorkflowTaskLinkPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderWorkflowTaskLinkPersistence
 * @generated
 */
public class AppBuilderWorkflowTaskLinkUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		getPersistence().clearCache(appBuilderWorkflowTaskLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, AppBuilderWorkflowTaskLink>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AppBuilderWorkflowTaskLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AppBuilderWorkflowTaskLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AppBuilderWorkflowTaskLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AppBuilderWorkflowTaskLink update(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		return getPersistence().update(appBuilderWorkflowTaskLink);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AppBuilderWorkflowTaskLink update(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink,
		ServiceContext serviceContext) {

		return getPersistence().update(
			appBuilderWorkflowTaskLink, serviceContext);
	}

	/**
	 * Returns all the app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the matching app builder workflow task links
	 */
	public static List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId) {

		return getPersistence().findByAppBuilderAppId(appBuilderAppId);
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
	public static List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end);
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
	public static List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator);
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
	public static List<AppBuilderWorkflowTaskLink> findByAppBuilderAppId(
		long appBuilderAppId, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByAppBuilderAppId(
			appBuilderAppId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	public static AppBuilderWorkflowTaskLink findByAppBuilderAppId_First(
			long appBuilderAppId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByAppBuilderAppId_First(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public static AppBuilderWorkflowTaskLink fetchByAppBuilderAppId_First(
		long appBuilderAppId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().fetchByAppBuilderAppId_First(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link
	 * @throws NoSuchTaskLinkException if a matching app builder workflow task link could not be found
	 */
	public static AppBuilderWorkflowTaskLink findByAppBuilderAppId_Last(
			long appBuilderAppId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByAppBuilderAppId_Last(
			appBuilderAppId, orderByComparator);
	}

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public static AppBuilderWorkflowTaskLink fetchByAppBuilderAppId_Last(
		long appBuilderAppId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().fetchByAppBuilderAppId_Last(
			appBuilderAppId, orderByComparator);
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
	public static AppBuilderWorkflowTaskLink[]
			findByAppBuilderAppId_PrevAndNext(
				long appBuilderWorkflowTaskLinkId, long appBuilderAppId,
				OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByAppBuilderAppId_PrevAndNext(
			appBuilderWorkflowTaskLinkId, appBuilderAppId, orderByComparator);
	}

	/**
	 * Removes all the app builder workflow task links where appBuilderAppId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 */
	public static void removeByAppBuilderAppId(long appBuilderAppId) {
		getPersistence().removeByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @return the number of matching app builder workflow task links
	 */
	public static int countByAppBuilderAppId(long appBuilderAppId) {
		return getPersistence().countByAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Returns all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @return the matching app builder workflow task links
	 */
	public static List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId) {

		return getPersistence().findByA_A(
			appBuilderAppId, appBuilderAppVersionId);
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
	public static List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId, int start, int end) {

		return getPersistence().findByA_A(
			appBuilderAppId, appBuilderAppVersionId, start, end);
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
	public static List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().findByA_A(
			appBuilderAppId, appBuilderAppVersionId, start, end,
			orderByComparator);
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
	public static List<AppBuilderWorkflowTaskLink> findByA_A(
		long appBuilderAppId, long appBuilderAppVersionId, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByA_A(
			appBuilderAppId, appBuilderAppVersionId, start, end,
			orderByComparator, useFinderCache);
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
	public static AppBuilderWorkflowTaskLink findByA_A_First(
			long appBuilderAppId, long appBuilderAppVersionId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByA_A_First(
			appBuilderAppId, appBuilderAppVersionId, orderByComparator);
	}

	/**
	 * Returns the first app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public static AppBuilderWorkflowTaskLink fetchByA_A_First(
		long appBuilderAppId, long appBuilderAppVersionId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().fetchByA_A_First(
			appBuilderAppId, appBuilderAppVersionId, orderByComparator);
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
	public static AppBuilderWorkflowTaskLink findByA_A_Last(
			long appBuilderAppId, long appBuilderAppVersionId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByA_A_Last(
			appBuilderAppId, appBuilderAppVersionId, orderByComparator);
	}

	/**
	 * Returns the last app builder workflow task link in the ordered set where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching app builder workflow task link, or <code>null</code> if a matching app builder workflow task link could not be found
	 */
	public static AppBuilderWorkflowTaskLink fetchByA_A_Last(
		long appBuilderAppId, long appBuilderAppVersionId,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().fetchByA_A_Last(
			appBuilderAppId, appBuilderAppVersionId, orderByComparator);
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
	public static AppBuilderWorkflowTaskLink[] findByA_A_PrevAndNext(
			long appBuilderWorkflowTaskLinkId, long appBuilderAppId,
			long appBuilderAppVersionId,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByA_A_PrevAndNext(
			appBuilderWorkflowTaskLinkId, appBuilderAppId,
			appBuilderAppVersionId, orderByComparator);
	}

	/**
	 * Removes all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 */
	public static void removeByA_A(
		long appBuilderAppId, long appBuilderAppVersionId) {

		getPersistence().removeByA_A(appBuilderAppId, appBuilderAppVersionId);
	}

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @return the number of matching app builder workflow task links
	 */
	public static int countByA_A(
		long appBuilderAppId, long appBuilderAppVersionId) {

		return getPersistence().countByA_A(
			appBuilderAppId, appBuilderAppVersionId);
	}

	/**
	 * Returns all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @return the matching app builder workflow task links
	 */
	public static List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName) {

		return getPersistence().findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName);
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
	public static List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName, int start, int end) {

		return getPersistence().findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName, start,
			end);
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
	public static List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName, start,
			end, orderByComparator);
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
	public static List<AppBuilderWorkflowTaskLink> findByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName, int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName, start,
			end, orderByComparator, useFinderCache);
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
	public static AppBuilderWorkflowTaskLink findByA_A_W_First(
			long appBuilderAppId, long appBuilderAppVersionId,
			String workflowTaskName,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByA_A_W_First(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
			orderByComparator);
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
	public static AppBuilderWorkflowTaskLink fetchByA_A_W_First(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().fetchByA_A_W_First(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
			orderByComparator);
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
	public static AppBuilderWorkflowTaskLink findByA_A_W_Last(
			long appBuilderAppId, long appBuilderAppVersionId,
			String workflowTaskName,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByA_A_W_Last(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
			orderByComparator);
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
	public static AppBuilderWorkflowTaskLink fetchByA_A_W_Last(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().fetchByA_A_W_Last(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName,
			orderByComparator);
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
	public static AppBuilderWorkflowTaskLink[] findByA_A_W_PrevAndNext(
			long appBuilderWorkflowTaskLinkId, long appBuilderAppId,
			long appBuilderAppVersionId, String workflowTaskName,
			OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByA_A_W_PrevAndNext(
			appBuilderWorkflowTaskLinkId, appBuilderAppId,
			appBuilderAppVersionId, workflowTaskName, orderByComparator);
	}

	/**
	 * Removes all the app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63; from the database.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 */
	public static void removeByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName) {

		getPersistence().removeByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName);
	}

	/**
	 * Returns the number of app builder workflow task links where appBuilderAppId = &#63; and appBuilderAppVersionId = &#63; and workflowTaskName = &#63;.
	 *
	 * @param appBuilderAppId the app builder app ID
	 * @param appBuilderAppVersionId the app builder app version ID
	 * @param workflowTaskName the workflow task name
	 * @return the number of matching app builder workflow task links
	 */
	public static int countByA_A_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		String workflowTaskName) {

		return getPersistence().countByA_A_W(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName);
	}

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
	public static AppBuilderWorkflowTaskLink findByA_A_D_W(
			long appBuilderAppId, long appBuilderAppVersionId,
			long ddmStructureLayoutId, String workflowTaskName)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByA_A_D_W(
			appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
			workflowTaskName);
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
	public static AppBuilderWorkflowTaskLink fetchByA_A_D_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		long ddmStructureLayoutId, String workflowTaskName) {

		return getPersistence().fetchByA_A_D_W(
			appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
			workflowTaskName);
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
	public static AppBuilderWorkflowTaskLink fetchByA_A_D_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		long ddmStructureLayoutId, String workflowTaskName,
		boolean useFinderCache) {

		return getPersistence().fetchByA_A_D_W(
			appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
			workflowTaskName, useFinderCache);
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
	public static AppBuilderWorkflowTaskLink removeByA_A_D_W(
			long appBuilderAppId, long appBuilderAppVersionId,
			long ddmStructureLayoutId, String workflowTaskName)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().removeByA_A_D_W(
			appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
			workflowTaskName);
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
	public static int countByA_A_D_W(
		long appBuilderAppId, long appBuilderAppVersionId,
		long ddmStructureLayoutId, String workflowTaskName) {

		return getPersistence().countByA_A_D_W(
			appBuilderAppId, appBuilderAppVersionId, ddmStructureLayoutId,
			workflowTaskName);
	}

	/**
	 * Caches the app builder workflow task link in the entity cache if it is enabled.
	 *
	 * @param appBuilderWorkflowTaskLink the app builder workflow task link
	 */
	public static void cacheResult(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		getPersistence().cacheResult(appBuilderWorkflowTaskLink);
	}

	/**
	 * Caches the app builder workflow task links in the entity cache if it is enabled.
	 *
	 * @param appBuilderWorkflowTaskLinks the app builder workflow task links
	 */
	public static void cacheResult(
		List<AppBuilderWorkflowTaskLink> appBuilderWorkflowTaskLinks) {

		getPersistence().cacheResult(appBuilderWorkflowTaskLinks);
	}

	/**
	 * Creates a new app builder workflow task link with the primary key. Does not add the app builder workflow task link to the database.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key for the new app builder workflow task link
	 * @return the new app builder workflow task link
	 */
	public static AppBuilderWorkflowTaskLink create(
		long appBuilderWorkflowTaskLinkId) {

		return getPersistence().create(appBuilderWorkflowTaskLinkId);
	}

	/**
	 * Removes the app builder workflow task link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link that was removed
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	public static AppBuilderWorkflowTaskLink remove(
			long appBuilderWorkflowTaskLinkId)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().remove(appBuilderWorkflowTaskLinkId);
	}

	public static AppBuilderWorkflowTaskLink updateImpl(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		return getPersistence().updateImpl(appBuilderWorkflowTaskLink);
	}

	/**
	 * Returns the app builder workflow task link with the primary key or throws a <code>NoSuchTaskLinkException</code> if it could not be found.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link
	 * @throws NoSuchTaskLinkException if a app builder workflow task link with the primary key could not be found
	 */
	public static AppBuilderWorkflowTaskLink findByPrimaryKey(
			long appBuilderWorkflowTaskLinkId)
		throws com.liferay.app.builder.workflow.exception.
			NoSuchTaskLinkException {

		return getPersistence().findByPrimaryKey(appBuilderWorkflowTaskLinkId);
	}

	/**
	 * Returns the app builder workflow task link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link, or <code>null</code> if a app builder workflow task link with the primary key could not be found
	 */
	public static AppBuilderWorkflowTaskLink fetchByPrimaryKey(
		long appBuilderWorkflowTaskLinkId) {

		return getPersistence().fetchByPrimaryKey(appBuilderWorkflowTaskLinkId);
	}

	/**
	 * Returns all the app builder workflow task links.
	 *
	 * @return the app builder workflow task links
	 */
	public static List<AppBuilderWorkflowTaskLink> findAll() {
		return getPersistence().findAll();
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
	public static List<AppBuilderWorkflowTaskLink> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<AppBuilderWorkflowTaskLink> findAll(
		int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<AppBuilderWorkflowTaskLink> findAll(
		int start, int end,
		OrderByComparator<AppBuilderWorkflowTaskLink> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the app builder workflow task links from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of app builder workflow task links.
	 *
	 * @return the number of app builder workflow task links
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AppBuilderWorkflowTaskLinkPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AppBuilderWorkflowTaskLinkPersistence,
		 AppBuilderWorkflowTaskLinkPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AppBuilderWorkflowTaskLinkPersistence.class);

		ServiceTracker
			<AppBuilderWorkflowTaskLinkPersistence,
			 AppBuilderWorkflowTaskLinkPersistence> serviceTracker =
				new ServiceTracker
					<AppBuilderWorkflowTaskLinkPersistence,
					 AppBuilderWorkflowTaskLinkPersistence>(
						 bundle.getBundleContext(),
						 AppBuilderWorkflowTaskLinkPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}