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

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CTProcess. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTProcessLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTProcessLocalService
 * @generated
 */
@ProviderType
public class CTProcessLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTProcessLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ct process to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcess the ct process
	 * @return the ct process that was added
	 */
	public static com.liferay.change.tracking.model.CTProcess addCTProcess(
		com.liferay.change.tracking.model.CTProcess ctProcess) {

		return getService().addCTProcess(ctProcess);
	}

	public static com.liferay.change.tracking.model.CTProcess addCTProcess(
			long userId, long ctCollectionId, boolean ignoreCollision,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCTProcess(
			userId, ctCollectionId, ignoreCollision, serviceContext);
	}

	/**
	 * Creates a new ct process with the primary key. Does not add the ct process to the database.
	 *
	 * @param ctProcessId the primary key for the new ct process
	 * @return the new ct process
	 */
	public static com.liferay.change.tracking.model.CTProcess createCTProcess(
		long ctProcessId) {

		return getService().createCTProcess(ctProcessId);
	}

	/**
	 * Deletes the ct process from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcess the ct process
	 * @return the ct process that was removed
	 * @throws PortalException
	 */
	public static com.liferay.change.tracking.model.CTProcess deleteCTProcess(
			com.liferay.change.tracking.model.CTProcess ctProcess)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCTProcess(ctProcess);
	}

	/**
	 * Deletes the ct process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process that was removed
	 * @throws PortalException if a ct process with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTProcess deleteCTProcess(
			long ctProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCTProcess(ctProcessId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.change.tracking.model.CTProcess fetchCTProcess(
		long ctProcessId) {

		return getService().fetchCTProcess(ctProcessId);
	}

	public static com.liferay.change.tracking.model.CTProcess
		fetchLatestCTProcess(long companyId) {

		return getService().fetchLatestCTProcess(companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the ct process with the primary key.
	 *
	 * @param ctProcessId the primary key of the ct process
	 * @return the ct process
	 * @throws PortalException if a ct process with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTProcess getCTProcess(
			long ctProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCTProcess(ctProcessId);
	}

	/**
	 * Returns a range of all the ct processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTProcessModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct processes
	 * @param end the upper bound of the range of ct processes (not inclusive)
	 * @return the range of ct processes
	 */
	public static java.util.List<com.liferay.change.tracking.model.CTProcess>
		getCTProcesses(int start, int end) {

		return getService().getCTProcesses(start, end);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTProcess>
		getCTProcesses(long ctCollectionId) {

		return getService().getCTProcesses(ctCollectionId);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTProcess>
		getCTProcesses(
			long companyId, int status,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition) {

		return getService().getCTProcesses(companyId, status, queryDefinition);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTProcess>
		getCTProcesses(
			long companyId,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition) {

		return getService().getCTProcesses(companyId, queryDefinition);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTProcess>
		getCTProcesses(
			long companyId, String keywords, int status,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition) {

		return getService().getCTProcesses(
			companyId, keywords, status, queryDefinition);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTProcess>
		getCTProcesses(
			long companyId, String keywords,
			com.liferay.portal.kernel.dao.orm.QueryDefinition<?>
				queryDefinition) {

		return getService().getCTProcesses(
			companyId, keywords, queryDefinition);
	}

	/**
	 * Returns the number of ct processes.
	 *
	 * @return the number of ct processes
	 */
	public static int getCTProcessesCount() {
		return getService().getCTProcessesCount();
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the ct process in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ctProcess the ct process
	 * @return the ct process that was updated
	 */
	public static com.liferay.change.tracking.model.CTProcess updateCTProcess(
		com.liferay.change.tracking.model.CTProcess ctProcess) {

		return getService().updateCTProcess(ctProcess);
	}

	public static CTProcessLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTProcessLocalService, CTProcessLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTProcessLocalService.class);

		ServiceTracker<CTProcessLocalService, CTProcessLocalService>
			serviceTracker =
				new ServiceTracker
					<CTProcessLocalService, CTProcessLocalService>(
						bundle.getBundleContext(), CTProcessLocalService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}