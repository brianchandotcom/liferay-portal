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

package com.liferay.app.builder.workflow.service;

import com.liferay.app.builder.workflow.model.AppBuilderWorkflowTaskLink;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for AppBuilderWorkflowTaskLink. This utility wraps
 * <code>com.liferay.app.builder.workflow.service.impl.AppBuilderWorkflowTaskLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderWorkflowTaskLinkLocalService
 * @generated
 */
public class AppBuilderWorkflowTaskLinkLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.app.builder.workflow.service.impl.AppBuilderWorkflowTaskLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the app builder workflow task link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderWorkflowTaskLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderWorkflowTaskLink the app builder workflow task link
	 * @return the app builder workflow task link that was added
	 */
	public static AppBuilderWorkflowTaskLink addAppBuilderWorkflowTaskLink(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		return getService().addAppBuilderWorkflowTaskLink(
			appBuilderWorkflowTaskLink);
	}

	public static AppBuilderWorkflowTaskLink addAppBuilderWorkflowTaskLink(
			long companyId, long appBuilderAppId, long appBuilderAppVersionId,
			long ddmStructureLayoutId, boolean readOnly,
			String workflowTaskName)
		throws PortalException {

		return getService().addAppBuilderWorkflowTaskLink(
			companyId, appBuilderAppId, appBuilderAppVersionId,
			ddmStructureLayoutId, readOnly, workflowTaskName);
	}

	/**
	 * Creates a new app builder workflow task link with the primary key. Does not add the app builder workflow task link to the database.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key for the new app builder workflow task link
	 * @return the new app builder workflow task link
	 */
	public static AppBuilderWorkflowTaskLink createAppBuilderWorkflowTaskLink(
		long appBuilderWorkflowTaskLinkId) {

		return getService().createAppBuilderWorkflowTaskLink(
			appBuilderWorkflowTaskLinkId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the app builder workflow task link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderWorkflowTaskLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderWorkflowTaskLink the app builder workflow task link
	 * @return the app builder workflow task link that was removed
	 */
	public static AppBuilderWorkflowTaskLink deleteAppBuilderWorkflowTaskLink(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		return getService().deleteAppBuilderWorkflowTaskLink(
			appBuilderWorkflowTaskLink);
	}

	/**
	 * Deletes the app builder workflow task link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderWorkflowTaskLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link that was removed
	 * @throws PortalException if a app builder workflow task link with the primary key could not be found
	 */
	public static AppBuilderWorkflowTaskLink deleteAppBuilderWorkflowTaskLink(
			long appBuilderWorkflowTaskLinkId)
		throws PortalException {

		return getService().deleteAppBuilderWorkflowTaskLink(
			appBuilderWorkflowTaskLinkId);
	}

	public static void deleteAppBuilderWorkflowTaskLinks(long appBuilderAppId) {
		getService().deleteAppBuilderWorkflowTaskLinks(appBuilderAppId);
	}

	public static void deleteAppBuilderWorkflowTaskLinks(
		long appBuilderAppId, long appBuilderAppVersionId) {

		getService().deleteAppBuilderWorkflowTaskLinks(
			appBuilderAppId, appBuilderAppVersionId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static AppBuilderWorkflowTaskLink fetchAppBuilderWorkflowTaskLink(
		long appBuilderWorkflowTaskLinkId) {

		return getService().fetchAppBuilderWorkflowTaskLink(
			appBuilderWorkflowTaskLinkId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder workflow task link with the primary key.
	 *
	 * @param appBuilderWorkflowTaskLinkId the primary key of the app builder workflow task link
	 * @return the app builder workflow task link
	 * @throws PortalException if a app builder workflow task link with the primary key could not be found
	 */
	public static AppBuilderWorkflowTaskLink getAppBuilderWorkflowTaskLink(
			long appBuilderWorkflowTaskLinkId)
		throws PortalException {

		return getService().getAppBuilderWorkflowTaskLink(
			appBuilderWorkflowTaskLinkId);
	}

	/**
	 * Returns a range of all the app builder workflow task links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.workflow.model.impl.AppBuilderWorkflowTaskLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder workflow task links
	 * @param end the upper bound of the range of app builder workflow task links (not inclusive)
	 * @return the range of app builder workflow task links
	 */
	public static List<AppBuilderWorkflowTaskLink>
		getAppBuilderWorkflowTaskLinks(int start, int end) {

		return getService().getAppBuilderWorkflowTaskLinks(start, end);
	}

	public static List<AppBuilderWorkflowTaskLink>
		getAppBuilderWorkflowTaskLinks(long appBuilderAppId) {

		return getService().getAppBuilderWorkflowTaskLinks(appBuilderAppId);
	}

	public static List<AppBuilderWorkflowTaskLink>
		getAppBuilderWorkflowTaskLinks(
			long appBuilderAppId, long appBuilderAppVersionId) {

		return getService().getAppBuilderWorkflowTaskLinks(
			appBuilderAppId, appBuilderAppVersionId);
	}

	public static List<AppBuilderWorkflowTaskLink>
		getAppBuilderWorkflowTaskLinks(
			long appBuilderAppId, long appBuilderAppVersionId,
			String workflowTaskName) {

		return getService().getAppBuilderWorkflowTaskLinks(
			appBuilderAppId, appBuilderAppVersionId, workflowTaskName);
	}

	/**
	 * Returns the number of app builder workflow task links.
	 *
	 * @return the number of app builder workflow task links
	 */
	public static int getAppBuilderWorkflowTaskLinksCount() {
		return getService().getAppBuilderWorkflowTaskLinksCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the app builder workflow task link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderWorkflowTaskLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderWorkflowTaskLink the app builder workflow task link
	 * @return the app builder workflow task link that was updated
	 */
	public static AppBuilderWorkflowTaskLink updateAppBuilderWorkflowTaskLink(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		return getService().updateAppBuilderWorkflowTaskLink(
			appBuilderWorkflowTaskLink);
	}

	public static AppBuilderWorkflowTaskLinkLocalService getService() {
		return _service;
	}

	private static volatile AppBuilderWorkflowTaskLinkLocalService _service;

}