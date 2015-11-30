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

package com.liferay.portlet.softwarecatalog.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SCLicenseLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SCLicenseLocalService
 * @generated
 */
@ProviderType
public class SCLicenseLocalServiceWrapper implements SCLicenseLocalService,
	ServiceWrapper<SCLicenseLocalService> {
	public SCLicenseLocalServiceWrapper(
		SCLicenseLocalService scLicenseLocalService) {
		_scLicenseLocalService = scLicenseLocalService;
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseLocalService.addLicense(name, url, openSource, active,
			recommended);
	}

	/**
	* Adds the s c license to the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license
	* @return the s c license that was added
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense addSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		return _scLicenseLocalService.addSCLicense(scLicense);
	}

	@Override
	public void addSCProductEntrySCLicense(long productEntryId, long licenseId) {
		_scLicenseLocalService.addSCProductEntrySCLicense(productEntryId,
			licenseId);
	}

	@Override
	public void addSCProductEntrySCLicense(long productEntryId,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		_scLicenseLocalService.addSCProductEntrySCLicense(productEntryId,
			scLicense);
	}

	@Override
	public void addSCProductEntrySCLicenses(long productEntryId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> SCLicenses) {
		_scLicenseLocalService.addSCProductEntrySCLicenses(productEntryId,
			SCLicenses);
	}

	@Override
	public void addSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds) {
		_scLicenseLocalService.addSCProductEntrySCLicenses(productEntryId,
			licenseIds);
	}

	@Override
	public void clearSCProductEntrySCLicenses(long productEntryId) {
		_scLicenseLocalService.clearSCProductEntrySCLicenses(productEntryId);
	}

	/**
	* Creates a new s c license with the primary key. Does not add the s c license to the database.
	*
	* @param licenseId the primary key for the new s c license
	* @return the new s c license
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense createSCLicense(
		long licenseId) {
		return _scLicenseLocalService.createSCLicense(licenseId);
	}

	@Override
	public void deleteLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense license) {
		_scLicenseLocalService.deleteLicense(license);
	}

	@Override
	public void deleteLicense(long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_scLicenseLocalService.deleteLicense(licenseId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the s c license with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license that was removed
	* @throws PortalException if a s c license with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense deleteSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseLocalService.deleteSCLicense(licenseId);
	}

	/**
	* Deletes the s c license from the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license
	* @return the s c license that was removed
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense deleteSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		return _scLicenseLocalService.deleteSCLicense(scLicense);
	}

	@Override
	public void deleteSCProductEntrySCLicense(long productEntryId,
		long licenseId) {
		_scLicenseLocalService.deleteSCProductEntrySCLicense(productEntryId,
			licenseId);
	}

	@Override
	public void deleteSCProductEntrySCLicense(long productEntryId,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		_scLicenseLocalService.deleteSCProductEntrySCLicense(productEntryId,
			scLicense);
	}

	@Override
	public void deleteSCProductEntrySCLicenses(long productEntryId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> SCLicenses) {
		_scLicenseLocalService.deleteSCProductEntrySCLicenses(productEntryId,
			SCLicenses);
	}

	@Override
	public void deleteSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds) {
		_scLicenseLocalService.deleteSCProductEntrySCLicenses(productEntryId,
			licenseIds);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _scLicenseLocalService.dynamicQuery();
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
		return _scLicenseLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _scLicenseLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _scLicenseLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _scLicenseLocalService.dynamicQueryCount(dynamicQuery);
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
		return _scLicenseLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense fetchSCLicense(
		long licenseId) {
		return _scLicenseLocalService.fetchSCLicense(licenseId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _scLicenseLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _scLicenseLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense getLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseLocalService.getLicense(licenseId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses() {
		return _scLicenseLocalService.getLicenses();
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		boolean active, boolean recommended) {
		return _scLicenseLocalService.getLicenses(active, recommended);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		boolean active, boolean recommended, int start, int end) {
		return _scLicenseLocalService.getLicenses(active, recommended, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		int start, int end) {
		return _scLicenseLocalService.getLicenses(start, end);
	}

	@Override
	public int getLicensesCount() {
		return _scLicenseLocalService.getLicensesCount();
	}

	@Override
	public int getLicensesCount(boolean active, boolean recommended) {
		return _scLicenseLocalService.getLicensesCount(active, recommended);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _scLicenseLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getProductEntryLicenses(
		long productEntryId) {
		return _scLicenseLocalService.getProductEntryLicenses(productEntryId);
	}

	/**
	* Returns the s c license with the primary key.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license
	* @throws PortalException if a s c license with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense getSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseLocalService.getSCLicense(licenseId);
	}

	/**
	* Returns a range of all the s c licenses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCLicenseModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c licenses
	* @param end the upper bound of the range of s c licenses (not inclusive)
	* @return the range of s c licenses
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		int start, int end) {
		return _scLicenseLocalService.getSCLicenses(start, end);
	}

	/**
	* Returns the number of s c licenses.
	*
	* @return the number of s c licenses
	*/
	@Override
	public int getSCLicensesCount() {
		return _scLicenseLocalService.getSCLicensesCount();
	}

	/**
	* Returns the productEntryIds of the s c product entries associated with the s c license.
	*
	* @param licenseId the licenseId of the s c license
	* @return long[] the productEntryIds of s c product entries associated with the s c license
	*/
	@Override
	public long[] getSCProductEntryPrimaryKeys(long licenseId) {
		return _scLicenseLocalService.getSCProductEntryPrimaryKeys(licenseId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCProductEntrySCLicenses(
		long productEntryId) {
		return _scLicenseLocalService.getSCProductEntrySCLicenses(productEntryId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCProductEntrySCLicenses(
		long productEntryId, int start, int end) {
		return _scLicenseLocalService.getSCProductEntrySCLicenses(productEntryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCProductEntrySCLicenses(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.softwarecatalog.model.SCLicense> orderByComparator) {
		return _scLicenseLocalService.getSCProductEntrySCLicenses(productEntryId,
			start, end, orderByComparator);
	}

	@Override
	public int getSCProductEntrySCLicensesCount(long productEntryId) {
		return _scLicenseLocalService.getSCProductEntrySCLicensesCount(productEntryId);
	}

	@Override
	public boolean hasSCProductEntrySCLicense(long productEntryId,
		long licenseId) {
		return _scLicenseLocalService.hasSCProductEntrySCLicense(productEntryId,
			licenseId);
	}

	@Override
	public boolean hasSCProductEntrySCLicenses(long productEntryId) {
		return _scLicenseLocalService.hasSCProductEntrySCLicenses(productEntryId);
	}

	@Override
	public void setSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds) {
		_scLicenseLocalService.setSCProductEntrySCLicenses(productEntryId,
			licenseIds);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _scLicenseLocalService.updateLicense(licenseId, name, url,
			openSource, active, recommended);
	}

	/**
	* Updates the s c license in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license
	* @return the s c license that was updated
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense updateSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		return _scLicenseLocalService.updateSCLicense(scLicense);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SCLicenseLocalService getWrappedSCLicenseLocalService() {
		return _scLicenseLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSCLicenseLocalService(
		SCLicenseLocalService scLicenseLocalService) {
		_scLicenseLocalService = scLicenseLocalService;
	}

	@Override
	public SCLicenseLocalService getWrappedService() {
		return _scLicenseLocalService;
	}

	@Override
	public void setWrappedService(SCLicenseLocalService scLicenseLocalService) {
		_scLicenseLocalService = scLicenseLocalService;
	}

	private SCLicenseLocalService _scLicenseLocalService;
}