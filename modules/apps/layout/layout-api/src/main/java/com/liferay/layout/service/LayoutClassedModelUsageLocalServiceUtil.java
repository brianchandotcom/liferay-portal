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

package com.liferay.layout.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for LayoutClassedModelUsage. This utility wraps
 * <code>com.liferay.layout.service.impl.LayoutClassedModelUsageLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutClassedModelUsageLocalService
 * @generated
 */
public class LayoutClassedModelUsageLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.layout.service.impl.LayoutClassedModelUsageLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.layout.model.LayoutClassedModelUsage
		addDefaultLayoutClassedModelUsage(
			long groupId, long classNameId, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return getService().addDefaultLayoutClassedModelUsage(
			groupId, classNameId, classPK, serviceContext);
	}

	/**
	 * Adds the layout classed model usage to the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsage the layout classed model usage
	 * @return the layout classed model usage that was added
	 */
	public static com.liferay.layout.model.LayoutClassedModelUsage
		addLayoutClassedModelUsage(
			com.liferay.layout.model.LayoutClassedModelUsage
				layoutClassedModelUsage) {

		return getService().addLayoutClassedModelUsage(layoutClassedModelUsage);
	}

	public static com.liferay.layout.model.LayoutClassedModelUsage
		addLayoutClassedModelUsage(
			long groupId, long classNameId, long classPK, String containerKey,
			long containerType, long plid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext) {

		return getService().addLayoutClassedModelUsage(
			groupId, classNameId, classPK, containerKey, containerType, plid,
			serviceContext);
	}

	/**
	 * Creates a new layout classed model usage with the primary key. Does not add the layout classed model usage to the database.
	 *
	 * @param layoutClassedModelUsageId the primary key for the new layout classed model usage
	 * @return the new layout classed model usage
	 */
	public static com.liferay.layout.model.LayoutClassedModelUsage
		createLayoutClassedModelUsage(long layoutClassedModelUsageId) {

		return getService().createLayoutClassedModelUsage(
			layoutClassedModelUsageId);
	}

	/**
	 * Deletes the layout classed model usage from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsage the layout classed model usage
	 * @return the layout classed model usage that was removed
	 */
	public static com.liferay.layout.model.LayoutClassedModelUsage
		deleteLayoutClassedModelUsage(
			com.liferay.layout.model.LayoutClassedModelUsage
				layoutClassedModelUsage) {

		return getService().deleteLayoutClassedModelUsage(
			layoutClassedModelUsage);
	}

	/**
	 * Deletes the layout classed model usage with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage that was removed
	 * @throws PortalException if a layout classed model usage with the primary key could not be found
	 */
	public static com.liferay.layout.model.LayoutClassedModelUsage
			deleteLayoutClassedModelUsage(long layoutClassedModelUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLayoutClassedModelUsage(
			layoutClassedModelUsageId);
	}

	public static void deleteLayoutClassedModelUsages(
		long classNameId, long classPK) {

		getService().deleteLayoutClassedModelUsages(classNameId, classPK);
	}

	public static void deleteLayoutClassedModelUsages(
		String containerKey, long containerType, long plid) {

		getService().deleteLayoutClassedModelUsages(
			containerKey, containerType, plid);
	}

	public static void deleteLayoutClassedModelUsagesByPlid(long plid) {
		getService().deleteLayoutClassedModelUsagesByPlid(plid);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.model.impl.LayoutClassedModelUsageModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.model.impl.LayoutClassedModelUsageModelImpl</code>.
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

	public static com.liferay.layout.model.LayoutClassedModelUsage
		fetchLayoutClassedModelUsage(long layoutClassedModelUsageId) {

		return getService().fetchLayoutClassedModelUsage(
			layoutClassedModelUsageId);
	}

	public static com.liferay.layout.model.LayoutClassedModelUsage
		fetchLayoutClassedModelUsage(
			long classNameId, long classPK, String containerKey,
			long containerType, long plid) {

		return getService().fetchLayoutClassedModelUsage(
			classNameId, classPK, containerKey, containerType, plid);
	}

	/**
	 * Returns the layout classed model usage matching the UUID and group.
	 *
	 * @param uuid the layout classed model usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout classed model usage, or <code>null</code> if a matching layout classed model usage could not be found
	 */
	public static com.liferay.layout.model.LayoutClassedModelUsage
		fetchLayoutClassedModelUsageByUuidAndGroupId(
			String uuid, long groupId) {

		return getService().fetchLayoutClassedModelUsageByUuidAndGroupId(
			uuid, groupId);
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

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the layout classed model usage with the primary key.
	 *
	 * @param layoutClassedModelUsageId the primary key of the layout classed model usage
	 * @return the layout classed model usage
	 * @throws PortalException if a layout classed model usage with the primary key could not be found
	 */
	public static com.liferay.layout.model.LayoutClassedModelUsage
			getLayoutClassedModelUsage(long layoutClassedModelUsageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutClassedModelUsage(
			layoutClassedModelUsageId);
	}

	/**
	 * Returns the layout classed model usage matching the UUID and group.
	 *
	 * @param uuid the layout classed model usage's UUID
	 * @param groupId the primary key of the group
	 * @return the matching layout classed model usage
	 * @throws PortalException if a matching layout classed model usage could not be found
	 */
	public static com.liferay.layout.model.LayoutClassedModelUsage
			getLayoutClassedModelUsageByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLayoutClassedModelUsageByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the layout classed model usages.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.layout.model.impl.LayoutClassedModelUsageModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @return the range of layout classed model usages
	 */
	public static java.util.List
		<com.liferay.layout.model.LayoutClassedModelUsage>
			getLayoutClassedModelUsages(int start, int end) {

		return getService().getLayoutClassedModelUsages(start, end);
	}

	public static java.util.List
		<com.liferay.layout.model.LayoutClassedModelUsage>
			getLayoutClassedModelUsages(long classNameId, long classPK) {

		return getService().getLayoutClassedModelUsages(classNameId, classPK);
	}

	public static java.util.List
		<com.liferay.layout.model.LayoutClassedModelUsage>
			getLayoutClassedModelUsages(
				long classNameId, long classPK, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.model.LayoutClassedModelUsage>
						orderByComparator) {

		return getService().getLayoutClassedModelUsages(
			classNameId, classPK, type, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.layout.model.LayoutClassedModelUsage>
			getLayoutClassedModelUsages(
				long classNameId, long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.model.LayoutClassedModelUsage>
						orderByComparator) {

		return getService().getLayoutClassedModelUsages(
			classNameId, classPK, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.layout.model.LayoutClassedModelUsage>
			getLayoutClassedModelUsagesByPlid(long plid) {

		return getService().getLayoutClassedModelUsagesByPlid(plid);
	}

	/**
	 * Returns all the layout classed model usages matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout classed model usages
	 * @param companyId the primary key of the company
	 * @return the matching layout classed model usages, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.model.LayoutClassedModelUsage>
			getLayoutClassedModelUsagesByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getLayoutClassedModelUsagesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of layout classed model usages matching the UUID and company.
	 *
	 * @param uuid the UUID of the layout classed model usages
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of layout classed model usages
	 * @param end the upper bound of the range of layout classed model usages (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching layout classed model usages, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.layout.model.LayoutClassedModelUsage>
			getLayoutClassedModelUsagesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.layout.model.LayoutClassedModelUsage>
						orderByComparator) {

		return getService().getLayoutClassedModelUsagesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of layout classed model usages.
	 *
	 * @return the number of layout classed model usages
	 */
	public static int getLayoutClassedModelUsagesCount() {
		return getService().getLayoutClassedModelUsagesCount();
	}

	public static int getLayoutClassedModelUsagesCount(
		long classNameId, long classPK) {

		return getService().getLayoutClassedModelUsagesCount(
			classNameId, classPK);
	}

	public static int getLayoutClassedModelUsagesCount(
		long classNameId, long classPK, int type) {

		return getService().getLayoutClassedModelUsagesCount(
			classNameId, classPK, type);
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

	public static int getUniqueLayoutClassedModelUsagesCount(
		long classNameId, long classPK) {

		return getService().getUniqueLayoutClassedModelUsagesCount(
			classNameId, classPK);
	}

	public static boolean hasDefaultLayoutClassedModelUsage(
		long classNameId, long classPK) {

		return getService().hasDefaultLayoutClassedModelUsage(
			classNameId, classPK);
	}

	/**
	 * Updates the layout classed model usage in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param layoutClassedModelUsage the layout classed model usage
	 * @return the layout classed model usage that was updated
	 */
	public static com.liferay.layout.model.LayoutClassedModelUsage
		updateLayoutClassedModelUsage(
			com.liferay.layout.model.LayoutClassedModelUsage
				layoutClassedModelUsage) {

		return getService().updateLayoutClassedModelUsage(
			layoutClassedModelUsage);
	}

	public static LayoutClassedModelUsageLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LayoutClassedModelUsageLocalService,
		 LayoutClassedModelUsageLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LayoutClassedModelUsageLocalService.class);

		ServiceTracker
			<LayoutClassedModelUsageLocalService,
			 LayoutClassedModelUsageLocalService> serviceTracker =
				new ServiceTracker
					<LayoutClassedModelUsageLocalService,
					 LayoutClassedModelUsageLocalService>(
						 bundle.getBundleContext(),
						 LayoutClassedModelUsageLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}