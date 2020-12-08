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

package com.liferay.change.tracking.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CTSchemaVersion. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTSchemaVersionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTSchemaVersionLocalService
 * @generated
 */
public class CTSchemaVersionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTSchemaVersionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ct schema version to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTSchemaVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctSchemaVersion the ct schema version
	 * @return the ct schema version that was added
	 */
	public static com.liferay.change.tracking.model.CTSchemaVersion
		addCTSchemaVersion(
			com.liferay.change.tracking.model.CTSchemaVersion ctSchemaVersion) {

		return getService().addCTSchemaVersion(ctSchemaVersion);
	}

	public static com.liferay.change.tracking.model.CTSchemaVersion
		addLatestCTSchemaVersion(long companyId) {

		return getService().addLatestCTSchemaVersion(companyId);
	}

	/**
	 * Creates a new ct schema version with the primary key. Does not add the ct schema version to the database.
	 *
	 * @param schemaVersionId the primary key for the new ct schema version
	 * @return the new ct schema version
	 */
	public static com.liferay.change.tracking.model.CTSchemaVersion
		createCTSchemaVersion(long schemaVersionId) {

		return getService().createCTSchemaVersion(schemaVersionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the ct schema version from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTSchemaVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctSchemaVersion the ct schema version
	 * @return the ct schema version that was removed
	 */
	public static com.liferay.change.tracking.model.CTSchemaVersion
		deleteCTSchemaVersion(
			com.liferay.change.tracking.model.CTSchemaVersion ctSchemaVersion) {

		return getService().deleteCTSchemaVersion(ctSchemaVersion);
	}

	/**
	 * Deletes the ct schema version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTSchemaVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version that was removed
	 * @throws PortalException if a ct schema version with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTSchemaVersion
			deleteCTSchemaVersion(long schemaVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCTSchemaVersion(schemaVersionId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.change.tracking.model.CTSchemaVersion
		fetchCTSchemaVersion(long schemaVersionId) {

		return getService().fetchCTSchemaVersion(schemaVersionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the ct schema version with the primary key.
	 *
	 * @param schemaVersionId the primary key of the ct schema version
	 * @return the ct schema version
	 * @throws PortalException if a ct schema version with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTSchemaVersion
			getCTSchemaVersion(long schemaVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCTSchemaVersion(schemaVersionId);
	}

	/**
	 * Returns a range of all the ct schema versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTSchemaVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct schema versions
	 * @param end the upper bound of the range of ct schema versions (not inclusive)
	 * @return the range of ct schema versions
	 */
	public static java.util.List
		<com.liferay.change.tracking.model.CTSchemaVersion> getCTSchemaVersions(
			int start, int end) {

		return getService().getCTSchemaVersions(start, end);
	}

	/**
	 * Returns the number of ct schema versions.
	 *
	 * @return the number of ct schema versions
	 */
	public static int getCTSchemaVersionsCount() {
		return getService().getCTSchemaVersionsCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static com.liferay.change.tracking.model.CTSchemaVersion
		getLatestCTSchemaVersion(long companyId) {

		return getService().getLatestCTSchemaVersion(companyId);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static boolean isLatestCTSchemaVersion(
		com.liferay.change.tracking.model.CTSchemaVersion ctSchemaVersion,
		boolean strict) {

		return getService().isLatestCTSchemaVersion(ctSchemaVersion, strict);
	}

	public static boolean isLatestCTSchemaVersion(long ctSchemaVersionId) {
		return getService().isLatestCTSchemaVersion(ctSchemaVersionId);
	}

	/**
	 * Updates the ct schema version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CTSchemaVersionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ctSchemaVersion the ct schema version
	 * @return the ct schema version that was updated
	 */
	public static com.liferay.change.tracking.model.CTSchemaVersion
		updateCTSchemaVersion(
			com.liferay.change.tracking.model.CTSchemaVersion ctSchemaVersion) {

		return getService().updateCTSchemaVersion(ctSchemaVersion);
	}

	public static CTSchemaVersionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CTSchemaVersionLocalService, CTSchemaVersionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CTSchemaVersionLocalService.class);

		ServiceTracker<CTSchemaVersionLocalService, CTSchemaVersionLocalService>
			serviceTracker =
				new ServiceTracker
					<CTSchemaVersionLocalService, CTSchemaVersionLocalService>(
						bundle.getBundleContext(),
						CTSchemaVersionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}