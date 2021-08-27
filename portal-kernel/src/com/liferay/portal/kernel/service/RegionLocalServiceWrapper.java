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

/**
 * Provides a wrapper for {@link RegionLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see RegionLocalService
 * @generated
 */
public class RegionLocalServiceWrapper
	implements RegionLocalService, ServiceWrapper<RegionLocalService> {

	public RegionLocalServiceWrapper(RegionLocalService regionLocalService) {
		_regionLocalService = regionLocalService;
	}

	@Override
	public com.liferay.portal.kernel.model.Region addRegion(
			long countryId, boolean active, java.lang.String name,
			double position, java.lang.String regionCode,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.addRegion(
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
	@Override
	public com.liferay.portal.kernel.model.Region addRegion(
		com.liferay.portal.kernel.model.Region region) {

		return _regionLocalService.addRegion(region);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new region with the primary key. Does not add the region to the database.
	 *
	 * @param regionId the primary key for the new region
	 * @return the new region
	 */
	@Override
	public com.liferay.portal.kernel.model.Region createRegion(long regionId) {
		return _regionLocalService.createRegion(regionId);
	}

	@Override
	public void deleteCountryRegions(long countryId) {
		_regionLocalService.deleteCountryRegions(countryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.deletePersistedModel(persistedModel);
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
	@Override
	public com.liferay.portal.kernel.model.Region deleteRegion(long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.deleteRegion(regionId);
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
	 */
	@Override
	public com.liferay.portal.kernel.model.Region deleteRegion(
		com.liferay.portal.kernel.model.Region region) {

		return _regionLocalService.deleteRegion(region);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _regionLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _regionLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _regionLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _regionLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _regionLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _regionLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _regionLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _regionLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.Region fetchRegion(long regionId) {
		return _regionLocalService.fetchRegion(regionId);
	}

	@Override
	public com.liferay.portal.kernel.model.Region fetchRegion(
		long countryId, java.lang.String regionCode) {

		return _regionLocalService.fetchRegion(countryId, regionCode);
	}

	/**
	 * Returns the region with the matching UUID and company.
	 *
	 * @param uuid the region's UUID
	 * @param companyId the primary key of the company
	 * @return the matching region, or <code>null</code> if a matching region could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Region fetchRegionByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {

		return _regionLocalService.fetchRegionByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.model.RegionLocalization
		fetchRegionLocalization(long regionId, java.lang.String languageId) {

		return _regionLocalService.fetchRegionLocalization(
			regionId, languageId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _regionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _regionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _regionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _regionLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the region with the primary key.
	 *
	 * @param regionId the primary key of the region
	 * @return the region
	 * @throws PortalException if a region with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Region getRegion(long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.getRegion(regionId);
	}

	@Override
	public com.liferay.portal.kernel.model.Region getRegion(
			long countryId, java.lang.String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.getRegion(countryId, regionCode);
	}

	/**
	 * Returns the region with the matching UUID and company.
	 *
	 * @param uuid the region's UUID
	 * @param companyId the primary key of the company
	 * @return the matching region
	 * @throws PortalException if a matching region could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.Region getRegionByUuidAndCompanyId(
			java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.getRegionByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.model.RegionLocalization
			getRegionLocalization(long regionId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.getRegionLocalization(regionId, languageId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.RegionLocalization>
		getRegionLocalizations(long regionId) {

		return _regionLocalService.getRegionLocalizations(regionId);
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
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
		int start, int end) {

		return _regionLocalService.getRegions(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
			long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.getRegions(countryId, active);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
		long countryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Region> orderByComparator) {

		return _regionLocalService.getRegions(
			countryId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Region> orderByComparator) {

		return _regionLocalService.getRegions(
			countryId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
			long companyId, java.lang.String a2, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.getRegions(companyId, a2, active);
	}

	/**
	 * Returns the number of regions.
	 *
	 * @return the number of regions
	 */
	@Override
	public int getRegionsCount() {
		return _regionLocalService.getRegionsCount();
	}

	@Override
	public int getRegionsCount(long countryId) {
		return _regionLocalService.getRegionsCount(countryId);
	}

	@Override
	public int getRegionsCount(long countryId, boolean active) {
		return _regionLocalService.getRegionsCount(countryId, active);
	}

	@Override
	public com.liferay.portal.kernel.model.Region updateActive(
			long regionId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.updateActive(regionId, active);
	}

	@Override
	public com.liferay.portal.kernel.model.Region updateRegion(
			long regionId, boolean active, java.lang.String name,
			double position, java.lang.String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.updateRegion(
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
	@Override
	public com.liferay.portal.kernel.model.Region updateRegion(
		com.liferay.portal.kernel.model.Region region) {

		return _regionLocalService.updateRegion(region);
	}

	@Override
	public com.liferay.portal.kernel.model.RegionLocalization
			updateRegionLocalization(
				com.liferay.portal.kernel.model.Region region,
				java.lang.String languageId, java.lang.String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.updateRegionLocalization(
			region, languageId, title);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.RegionLocalization>
			updateRegionLocalizations(
				com.liferay.portal.kernel.model.Region region,
				java.util.Map<java.lang.String, java.lang.String> titleMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionLocalService.updateRegionLocalizations(region, titleMap);
	}

	@Override
	public RegionLocalService getWrappedService() {
		return _regionLocalService;
	}

	@Override
	public void setWrappedService(RegionLocalService regionLocalService) {
		_regionLocalService = regionLocalService;
	}

	private RegionLocalService _regionLocalService;

}