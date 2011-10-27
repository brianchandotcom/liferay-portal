/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link ReleaseLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReleaseLocalService
 * @generated
 */
public class ReleaseLocalServiceWrapper implements ReleaseLocalService,
	ServiceWrapper<ReleaseLocalService> {
	public ReleaseLocalServiceWrapper(ReleaseLocalService releaseLocalService) {
		_releaseLocalService = releaseLocalService;
	}

	/**
	* Adds the release to the database. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @return the release that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release addRelease(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.addRelease(release);
	}

	/**
	* Creates a new release with the primary key. Does not add the release to the database.
	*
	* @param releaseId the primary key for the new release
	* @return the new release
	*/
	public com.liferay.portal.model.Release createRelease(long releaseId) {
		return _releaseLocalService.createRelease(releaseId);
	}

	/**
	* Deletes the release with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param releaseId the primary key of the release
	* @throws PortalException if a release with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_releaseLocalService.deleteRelease(releaseId);
	}

	/**
	* Deletes the release from the database. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRelease(com.liferay.portal.model.Release release)
		throws com.liferay.portal.kernel.exception.SystemException {
		_releaseLocalService.deleteRelease(release);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the release with the primary key.
	*
	* @param releaseId the primary key of the release
	* @return the release
	* @throws PortalException if a release with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release getRelease(long releaseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getRelease(releaseId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the releases.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of releases
	* @param end the upper bound of the range of releases (not inclusive)
	* @return the range of releases
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Release> getReleases(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getReleases(start, end);
	}

	/**
	* Returns the number of releases.
	*
	* @return the number of releases
	* @throws SystemException if a system exception occurred
	*/
	public int getReleasesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getReleasesCount();
	}

	/**
	* Updates the release in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @return the release that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.updateRelease(release);
	}

	/**
	* Updates the release in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param release the release
	* @param merge whether to merge the release with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the release that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Release updateRelease(
		com.liferay.portal.model.Release release, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.updateRelease(release, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _releaseLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_releaseLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Release addRelease(
		java.lang.String servletContextName, int buildNumber)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.addRelease(servletContextName, buildNumber);
	}

	public void createTablesAndPopulate()
		throws com.liferay.portal.kernel.exception.SystemException {
		_releaseLocalService.createTablesAndPopulate();
	}

	public int getBuildNumberOrCreate()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getBuildNumberOrCreate();
	}

	public com.liferay.portal.model.Release getRelease(
		java.lang.String servletContextName, int buildNumber)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.getRelease(servletContextName, buildNumber);
	}

	public com.liferay.portal.model.Release updateRelease(long releaseId,
		int buildNumber, java.util.Date buildDate, boolean verified)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _releaseLocalService.updateRelease(releaseId, buildNumber,
			buildDate, verified);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ReleaseLocalService getWrappedReleaseLocalService() {
		return _releaseLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedReleaseLocalService(
		ReleaseLocalService releaseLocalService) {
		_releaseLocalService = releaseLocalService;
	}

	public ReleaseLocalService getWrappedService() {
		return _releaseLocalService;
	}

	public void setWrappedService(ReleaseLocalService releaseLocalService) {
		_releaseLocalService = releaseLocalService;
	}

	private ReleaseLocalService _releaseLocalService;
}