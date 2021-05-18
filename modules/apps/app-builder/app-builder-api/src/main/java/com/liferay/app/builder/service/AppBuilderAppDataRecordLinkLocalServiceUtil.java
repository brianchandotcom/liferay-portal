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

package com.liferay.app.builder.service;

import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for AppBuilderAppDataRecordLink. This utility wraps
 * <code>com.liferay.app.builder.service.impl.AppBuilderAppDataRecordLinkLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDataRecordLinkLocalService
 * @generated
 */
public class AppBuilderAppDataRecordLinkLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.app.builder.service.impl.AppBuilderAppDataRecordLinkLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the app builder app data record link to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 * @return the app builder app data record link that was added
	 */
	public static AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		return getService().addAppBuilderAppDataRecordLink(
			appBuilderAppDataRecordLink);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addAppBuilderAppDataRecordLink(long, long, long, long,
	 long)}
	 */
	@Deprecated
	public static AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink(
		long companyId, long appBuilderAppId, long ddlRecordId) {

		return getService().addAppBuilderAppDataRecordLink(
			companyId, appBuilderAppId, ddlRecordId);
	}

	public static AppBuilderAppDataRecordLink addAppBuilderAppDataRecordLink(
		long groupId, long companyId, long appBuilderAppId,
		long appBuilderAppVersionId, long ddlRecordId) {

		return getService().addAppBuilderAppDataRecordLink(
			groupId, companyId, appBuilderAppId, appBuilderAppVersionId,
			ddlRecordId);
	}

	/**
	 * Creates a new app builder app data record link with the primary key. Does not add the app builder app data record link to the database.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key for the new app builder app data record link
	 * @return the new app builder app data record link
	 */
	public static AppBuilderAppDataRecordLink createAppBuilderAppDataRecordLink(
		long appBuilderAppDataRecordLinkId) {

		return getService().createAppBuilderAppDataRecordLink(
			appBuilderAppDataRecordLinkId);
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
	 * Deletes the app builder app data record link from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 * @return the app builder app data record link that was removed
	 */
	public static AppBuilderAppDataRecordLink deleteAppBuilderAppDataRecordLink(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		return getService().deleteAppBuilderAppDataRecordLink(
			appBuilderAppDataRecordLink);
	}

	/**
	 * Deletes the app builder app data record link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link that was removed
	 * @throws PortalException if a app builder app data record link with the primary key could not be found
	 */
	public static AppBuilderAppDataRecordLink deleteAppBuilderAppDataRecordLink(
			long appBuilderAppDataRecordLinkId)
		throws PortalException {

		return getService().deleteAppBuilderAppDataRecordLink(
			appBuilderAppDataRecordLinkId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl</code>.
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

	public static AppBuilderAppDataRecordLink fetchAppBuilderAppDataRecordLink(
		long appBuilderAppDataRecordLinkId) {

		return getService().fetchAppBuilderAppDataRecordLink(
			appBuilderAppDataRecordLinkId);
	}

	public static AppBuilderAppDataRecordLink
		fetchDDLRecordAppBuilderAppDataRecordLink(long ddlRecordId) {

		return getService().fetchDDLRecordAppBuilderAppDataRecordLink(
			ddlRecordId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the app builder app data record link with the primary key.
	 *
	 * @param appBuilderAppDataRecordLinkId the primary key of the app builder app data record link
	 * @return the app builder app data record link
	 * @throws PortalException if a app builder app data record link with the primary key could not be found
	 */
	public static AppBuilderAppDataRecordLink getAppBuilderAppDataRecordLink(
			long appBuilderAppDataRecordLinkId)
		throws PortalException {

		return getService().getAppBuilderAppDataRecordLink(
			appBuilderAppDataRecordLinkId);
	}

	/**
	 * Returns a range of all the app builder app data record links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.app.builder.model.impl.AppBuilderAppDataRecordLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of app builder app data record links
	 * @param end the upper bound of the range of app builder app data record links (not inclusive)
	 * @return the range of app builder app data record links
	 */
	public static List<AppBuilderAppDataRecordLink>
		getAppBuilderAppDataRecordLinks(int start, int end) {

		return getService().getAppBuilderAppDataRecordLinks(start, end);
	}

	public static List<AppBuilderAppDataRecordLink>
		getAppBuilderAppDataRecordLinks(long appBuilderAppId) {

		return getService().getAppBuilderAppDataRecordLinks(appBuilderAppId);
	}

	public static List<AppBuilderAppDataRecordLink>
		getAppBuilderAppDataRecordLinks(
			long appBuilderAppId, long[] ddlRecordIds) {

		return getService().getAppBuilderAppDataRecordLinks(
			appBuilderAppId, ddlRecordIds);
	}

	/**
	 * Returns the number of app builder app data record links.
	 *
	 * @return the number of app builder app data record links
	 */
	public static int getAppBuilderAppDataRecordLinksCount() {
		return getService().getAppBuilderAppDataRecordLinksCount();
	}

	public static AppBuilderAppDataRecordLink
			getDDLRecordAppBuilderAppDataRecordLink(long ddlRecordId)
		throws PortalException {

		return getService().getDDLRecordAppBuilderAppDataRecordLink(
			ddlRecordId);
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
	 * Updates the app builder app data record link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect AppBuilderAppDataRecordLinkLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param appBuilderAppDataRecordLink the app builder app data record link
	 * @return the app builder app data record link that was updated
	 */
	public static AppBuilderAppDataRecordLink updateAppBuilderAppDataRecordLink(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		return getService().updateAppBuilderAppDataRecordLink(
			appBuilderAppDataRecordLink);
	}

	public static AppBuilderAppDataRecordLinkLocalService getService() {
		return _service;
	}

	private static volatile AppBuilderAppDataRecordLinkLocalService _service;

}