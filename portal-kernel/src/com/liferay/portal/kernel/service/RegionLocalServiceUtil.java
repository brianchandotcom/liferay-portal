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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for Region. This utility wraps
 * <code>com.liferay.portal.service.impl.RegionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see RegionLocalService
 * @generated
 */
public class RegionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.RegionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.Region addRegion(
			long countryId, boolean active, String name, double position,
			String regionCode, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRegion(
			countryId, active, name, position, regionCode, serviceContext);
	}

	/**
	 * Adds the region to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param region the region
	 * @return the region that was added
	 */
	public static com.liferay.portal.kernel.model.Region addRegion(
		com.liferay.portal.kernel.model.Region region) {

		return getService().addRegion(region);
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
	 * Creates a new region with the primary key. Does not add the region to the database.
	 *
	 * @param regionId the primary key for the new region
	 * @return the new region
	 */
	public static com.liferay.portal.kernel.model.Region createRegion(
		long regionId) {

		return getService().createRegion(regionId);
	}

	public static void deleteCountryRegions(long countryId) {
		getService().deleteCountryRegions(countryId);
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

	/**
	 * Deletes the region with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param regionId the primary key of the region
	 * @return the region that was removed
	 * @throws PortalException if a region with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Region deleteRegion(
			long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRegion(regionId);
	}

	/**
	 * Deletes the region from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param region the region
	 * @return the region that was removed
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.Region deleteRegion(
			com.liferay.portal.kernel.model.Region region)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteRegion(region);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RegionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RegionModelImpl</code>.
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

	public static com.liferay.portal.kernel.model.Region fetchRegion(
		long regionId) {

		return getService().fetchRegion(regionId);
	}

	public static com.liferay.portal.kernel.model.Region fetchRegion(
		long countryId, String regionCode) {

		return getService().fetchRegion(countryId, regionCode);
	}

	/**
	 * Returns the region with the matching UUID and company.
	 *
	 * @param uuid the region's UUID
	 * @param companyId the primary key of the company
	 * @return the matching region, or <code>null</code> if a matching region could not be found
	 */
	public static com.liferay.portal.kernel.model.Region
		fetchRegionByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchRegionByUuidAndCompanyId(uuid, companyId);
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

	/**
	 * Returns the region with the primary key.
	 *
	 * @param regionId the primary key of the region
	 * @return the region
	 * @throws PortalException if a region with the primary key could not be found
	 */
	public static com.liferay.portal.kernel.model.Region getRegion(
			long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegion(regionId);
	}

	public static com.liferay.portal.kernel.model.Region getRegion(
			long countryId, String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegion(countryId, regionCode);
	}

	/**
	 * Returns the region with the matching UUID and company.
	 *
	 * @param uuid the region's UUID
	 * @param companyId the primary key of the company
	 * @return the matching region
	 * @throws PortalException if a matching region could not be found
	 */
	public static com.liferay.portal.kernel.model.Region
			getRegionByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegionByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the regions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.RegionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of regions
	 * @param end the upper bound of the range of regions (not inclusive)
	 * @return the range of regions
	 */
	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(int start, int end) {

		return getService().getRegions(start, end);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
			getRegions(long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegions(countryId, active);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(
			long countryId, boolean active, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Region> orderByComparator) {

		return getService().getRegions(
			countryId, active, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(
			long countryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Region> orderByComparator) {

		return getService().getRegions(
			countryId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
			getRegions(long companyId, String a2, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegions(companyId, a2, active);
	}

	/**
	 * Returns the number of regions.
	 *
	 * @return the number of regions
	 */
	public static int getRegionsCount() {
		return getService().getRegionsCount();
	}

	public static int getRegionsCount(long countryId) {
		return getService().getRegionsCount(countryId);
	}

	public static int getRegionsCount(long countryId, boolean active) {
		return getService().getRegionsCount(countryId, active);
	}

	public static com.liferay.portal.kernel.model.Region updateActive(
			long regionId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateActive(regionId, active);
	}

	public static com.liferay.portal.kernel.model.Region updateRegion(
			long regionId, boolean active, String name, double position,
			String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateRegion(
			regionId, active, name, position, regionCode);
	}

	/**
	 * Updates the region in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect RegionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param region the region
	 * @return the region that was updated
	 */
	public static com.liferay.portal.kernel.model.Region updateRegion(
		com.liferay.portal.kernel.model.Region region) {

		return getService().updateRegion(region);
	}

	public static RegionLocalService getService() {
		if (_service == null) {
			_service = (RegionLocalService)PortalBeanLocatorUtil.locate(
				RegionLocalService.class.getName());
		}

		return _service;
	}

	private static RegionLocalService _service;

}