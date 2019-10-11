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

package com.liferay.portal.workflow.kaleo.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for KaleoDefinitionVersion. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoDefinitionVersionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KaleoDefinitionVersionLocalService
 * @generated
 */
public class KaleoDefinitionVersionLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.service.impl.KaleoDefinitionVersionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the kaleo definition version to the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @return the kaleo definition version that was added
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
		addKaleoDefinitionVersion(
			com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
				kaleoDefinitionVersion) {

		return getService().addKaleoDefinitionVersion(kaleoDefinitionVersion);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
			addKaleoDefinitionVersion(
				String name, String title, String description, String content,
				String version,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addKaleoDefinitionVersion(
			name, title, description, content, version, serviceContext);
	}

	/**
	 * Creates a new kaleo definition version with the primary key. Does not add the kaleo definition version to the database.
	 *
	 * @param kaleoDefinitionVersionId the primary key for the new kaleo definition version
	 * @return the new kaleo definition version
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
		createKaleoDefinitionVersion(long kaleoDefinitionVersionId) {

		return getService().createKaleoDefinitionVersion(
			kaleoDefinitionVersionId);
	}

	/**
	 * Deletes the kaleo definition version from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @return the kaleo definition version that was removed
	 * @throws PortalException
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
			deleteKaleoDefinitionVersion(
				com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
					kaleoDefinitionVersion)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKaleoDefinitionVersion(
			kaleoDefinitionVersion);
	}

	/**
	 * Deletes the kaleo definition version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version that was removed
	 * @throws PortalException if a kaleo definition version with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
			deleteKaleoDefinitionVersion(long kaleoDefinitionVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteKaleoDefinitionVersion(
			kaleoDefinitionVersionId);
	}

	public static void deleteKaleoDefinitionVersion(
			long companyId, String name, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteKaleoDefinitionVersion(companyId, name, version);
	}

	public static void deleteKaleoDefinitionVersions(
			java.util.List
				<com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion>
					kaleoDefinitionVersions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteKaleoDefinitionVersions(kaleoDefinitionVersions);
	}

	public static void deleteKaleoDefinitionVersions(
			long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteKaleoDefinitionVersions(companyId, name);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionVersionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionVersionModelImpl</code>.
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

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
		fetchKaleoDefinitionVersion(long kaleoDefinitionVersionId) {

		return getService().fetchKaleoDefinitionVersion(
			kaleoDefinitionVersionId);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
		fetchKaleoDefinitionVersion(
			long companyId, String name, String version) {

		return getService().fetchKaleoDefinitionVersion(
			companyId, name, version);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
			fetchLatestKaleoDefinitionVersion(long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchLatestKaleoDefinitionVersion(companyId, name);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
			fetchLatestKaleoDefinitionVersion(
				long companyId, String name,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoDefinitionVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchLatestKaleoDefinitionVersion(
			companyId, name, orderByComparator);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
			getFirstKaleoDefinitionVersion(long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getFirstKaleoDefinitionVersion(companyId, name);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the kaleo definition version with the primary key.
	 *
	 * @param kaleoDefinitionVersionId the primary key of the kaleo definition version
	 * @return the kaleo definition version
	 * @throws PortalException if a kaleo definition version with the primary key could not be found
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
			getKaleoDefinitionVersion(long kaleoDefinitionVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoDefinitionVersion(kaleoDefinitionVersionId);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
			getKaleoDefinitionVersion(
				long companyId, String name, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoDefinitionVersion(companyId, name, version);
	}

	/**
	 * Returns a range of all the kaleo definition versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionVersionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definition versions
	 * @param end the upper bound of the range of kaleo definition versions (not inclusive)
	 * @return the range of kaleo definition versions
	 */
	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion>
			getKaleoDefinitionVersions(int start, int end) {

		return getService().getKaleoDefinitionVersions(start, end);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion>
			getKaleoDefinitionVersions(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoDefinitionVersion> orderByComparator) {

		return getService().getKaleoDefinitionVersions(
			companyId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion>
				getKaleoDefinitionVersions(long companyId, String name)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoDefinitionVersions(companyId, name);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion>
			getKaleoDefinitionVersions(
				long companyId, String name, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoDefinitionVersion> orderByComparator) {

		return getService().getKaleoDefinitionVersions(
			companyId, name, start, end, orderByComparator);
	}

	/**
	 * Returns the number of kaleo definition versions.
	 *
	 * @return the number of kaleo definition versions
	 */
	public static int getKaleoDefinitionVersionsCount() {
		return getService().getKaleoDefinitionVersionsCount();
	}

	public static int getKaleoDefinitionVersionsCount(long companyId) {
		return getService().getKaleoDefinitionVersionsCount(companyId);
	}

	public static int getKaleoDefinitionVersionsCount(
		long companyId, String name) {

		return getService().getKaleoDefinitionVersionsCount(companyId, name);
	}

	public static
		com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion[]
				getKaleoDefinitionVersionsPrevAndNext(
					long companyId, String name, String version)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getKaleoDefinitionVersionsPrevAndNext(
			companyId, name, version);
	}

	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
			getLatestKaleoDefinitionVersion(long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLatestKaleoDefinitionVersion(companyId, name);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion>
			getLatestKaleoDefinitionVersions(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoDefinitionVersion> orderByComparator) {

		return getService().getLatestKaleoDefinitionVersions(
			companyId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion>
			getLatestKaleoDefinitionVersions(
				long companyId, String keywords, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.workflow.kaleo.model.
						KaleoDefinitionVersion> orderByComparator) {

		return getService().getLatestKaleoDefinitionVersions(
			companyId, keywords, status, start, end, orderByComparator);
	}

	public static int getLatestKaleoDefinitionVersionsCount(
		long companyId, String keywords, int status) {

		return getService().getLatestKaleoDefinitionVersionsCount(
			companyId, keywords, status);
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
	 * Updates the kaleo definition version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @return the kaleo definition version that was updated
	 */
	public static com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
		updateKaleoDefinitionVersion(
			com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion
				kaleoDefinitionVersion) {

		return getService().updateKaleoDefinitionVersion(
			kaleoDefinitionVersion);
	}

	public static KaleoDefinitionVersionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<KaleoDefinitionVersionLocalService, KaleoDefinitionVersionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			KaleoDefinitionVersionLocalService.class);

		ServiceTracker
			<KaleoDefinitionVersionLocalService,
			 KaleoDefinitionVersionLocalService> serviceTracker =
				new ServiceTracker
					<KaleoDefinitionVersionLocalService,
					 KaleoDefinitionVersionLocalService>(
						 bundle.getBundleContext(),
						 KaleoDefinitionVersionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}