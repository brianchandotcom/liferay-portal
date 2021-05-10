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

package com.liferay.frontend.view.state.service;

import com.liferay.frontend.view.state.model.FrontendViewStateEntry;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for FrontendViewStateEntry. This utility wraps
 * <code>com.liferay.frontend.view.state.service.impl.FrontendViewStateEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see FrontendViewStateEntryLocalService
 * @generated
 */
public class FrontendViewStateEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.frontend.view.state.service.impl.FrontendViewStateEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the frontend view state entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateEntry the frontend view state entry
	 * @return the frontend view state entry that was added
	 */
	public static FrontendViewStateEntry addFrontendViewStateEntry(
		FrontendViewStateEntry frontendViewStateEntry) {

		return getService().addFrontendViewStateEntry(frontendViewStateEntry);
	}

	/**
	 * Creates a new frontend view state entry with the primary key. Does not add the frontend view state entry to the database.
	 *
	 * @param frontendViewStateEntryId the primary key for the new frontend view state entry
	 * @return the new frontend view state entry
	 */
	public static FrontendViewStateEntry createFrontendViewStateEntry(
		long frontendViewStateEntryId) {

		return getService().createFrontendViewStateEntry(
			frontendViewStateEntryId);
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
	 * Deletes the frontend view state entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateEntry the frontend view state entry
	 * @return the frontend view state entry that was removed
	 */
	public static FrontendViewStateEntry deleteFrontendViewStateEntry(
		FrontendViewStateEntry frontendViewStateEntry) {

		return getService().deleteFrontendViewStateEntry(
			frontendViewStateEntry);
	}

	/**
	 * Deletes the frontend view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry that was removed
	 * @throws PortalException if a frontend view state entry with the primary key could not be found
	 */
	public static FrontendViewStateEntry deleteFrontendViewStateEntry(
			long frontendViewStateEntryId)
		throws PortalException {

		return getService().deleteFrontendViewStateEntry(
			frontendViewStateEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateEntryModelImpl</code>.
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

	public static FrontendViewStateEntry fetchFrontendViewStateEntry(
		long frontendViewStateEntryId) {

		return getService().fetchFrontendViewStateEntry(
			frontendViewStateEntryId);
	}

	/**
	 * Returns the frontend view state entry with the matching UUID and company.
	 *
	 * @param uuid the frontend view state entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry
		fetchFrontendViewStateEntryByUuidAndCompanyId(
			String uuid, long companyId) {

		return getService().fetchFrontendViewStateEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	 * Returns a range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.frontend.view.state.model.impl.FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of frontend view state entries
	 */
	public static List<FrontendViewStateEntry> getFrontendViewStateEntries(
		int start, int end) {

		return getService().getFrontendViewStateEntries(start, end);
	}

	/**
	 * Returns the number of frontend view state entries.
	 *
	 * @return the number of frontend view state entries
	 */
	public static int getFrontendViewStateEntriesCount() {
		return getService().getFrontendViewStateEntriesCount();
	}

	/**
	 * Returns the frontend view state entry with the primary key.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry
	 * @throws PortalException if a frontend view state entry with the primary key could not be found
	 */
	public static FrontendViewStateEntry getFrontendViewStateEntry(
			long frontendViewStateEntryId)
		throws PortalException {

		return getService().getFrontendViewStateEntry(frontendViewStateEntryId);
	}

	/**
	 * Returns the frontend view state entry with the matching UUID and company.
	 *
	 * @param uuid the frontend view state entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching frontend view state entry
	 * @throws PortalException if a matching frontend view state entry could not be found
	 */
	public static FrontendViewStateEntry
			getFrontendViewStateEntryByUuidAndCompanyId(
				String uuid, long companyId)
		throws PortalException {

		return getService().getFrontendViewStateEntryByUuidAndCompanyId(
			uuid, companyId);
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
	 * Updates the frontend view state entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FrontendViewStateEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param frontendViewStateEntry the frontend view state entry
	 * @return the frontend view state entry that was updated
	 */
	public static FrontendViewStateEntry updateFrontendViewStateEntry(
		FrontendViewStateEntry frontendViewStateEntry) {

		return getService().updateFrontendViewStateEntry(
			frontendViewStateEntry);
	}

	public static FrontendViewStateEntryLocalService getService() {
		return _service;
	}

	private static volatile FrontendViewStateEntryLocalService _service;

}