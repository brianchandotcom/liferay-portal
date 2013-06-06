/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SCLicenseLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SCLicenseLocalService
 * @generated
 */
public class SCLicenseLocalServiceWrapper implements SCLicenseLocalService,
	ServiceWrapper<SCLicenseLocalService> {
	public SCLicenseLocalServiceWrapper(
		SCLicenseLocalService scLicenseLocalService) {
		_scLicenseLocalService = scLicenseLocalService;
	}

	/**
	* Adds the s c license to the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license
	* @return the s c license that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense addSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.addSCLicense(scLicense);
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

	/**
	* Deletes the s c license with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license that was removed
	* @throws PortalException if a s c license with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense deleteSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.deleteSCLicense(licenseId);
	}

	/**
	* Deletes the s c license from the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license
	* @return the s c license that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense deleteSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.deleteSCLicense(scLicense);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense fetchSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.fetchSCLicense(licenseId);
	}

	/**
	* Returns the s c license with the primary key.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license
	* @throws PortalException if a s c license with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense getSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCLicense(licenseId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getPersistedModel(primaryKeyObj);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCLicenses(start, end);
	}

	/**
	* Returns the number of s c licenses.
	*
	* @return the number of s c licenses
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getSCLicensesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCLicensesCount();
	}

	/**
	* Updates the s c license in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license
	* @return the s c license that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense updateSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.updateSCLicense(scLicense);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addSCProductEntrySCLicense(long productEntryId, long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.addSCProductEntrySCLicense(productEntryId,
			licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addSCProductEntrySCLicense(long productEntryId,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.addSCProductEntrySCLicense(productEntryId,
			scLicense);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.addSCProductEntrySCLicenses(productEntryId,
			licenseIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addSCProductEntrySCLicenses(long productEntryId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> SCLicenses)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.addSCProductEntrySCLicenses(productEntryId,
			SCLicenses);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void clearSCProductEntrySCLicenses(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.clearSCProductEntrySCLicenses(productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteSCProductEntrySCLicense(long productEntryId,
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteSCProductEntrySCLicense(productEntryId,
			licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteSCProductEntrySCLicense(long productEntryId,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteSCProductEntrySCLicense(productEntryId,
			scLicense);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteSCProductEntrySCLicenses(productEntryId,
			licenseIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteSCProductEntrySCLicenses(long productEntryId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> SCLicenses)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteSCProductEntrySCLicenses(productEntryId,
			SCLicenses);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCProductEntrySCLicenses(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCProductEntrySCLicenses(productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCProductEntrySCLicenses(
		long productEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCProductEntrySCLicenses(productEntryId,
			start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCProductEntrySCLicenses(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCProductEntrySCLicenses(productEntryId,
			start, end, orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getSCProductEntrySCLicensesCount(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getSCProductEntrySCLicensesCount(productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasSCProductEntrySCLicense(long productEntryId,
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.hasSCProductEntrySCLicense(productEntryId,
			licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public boolean hasSCProductEntrySCLicenses(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.hasSCProductEntrySCLicenses(productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void setSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.setSCProductEntrySCLicenses(productEntryId,
			licenseIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _scLicenseLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_scLicenseLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.addLicense(name, url, openSource, active,
			recommended);
	}

	@Override
	public void deleteLicense(long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteLicense(licenseId);
	}

	@Override
	public void deleteLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense license)
		throws com.liferay.portal.kernel.exception.SystemException {
		_scLicenseLocalService.deleteLicense(license);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense getLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicense(licenseId);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicenses();
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicenses(active, recommended);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		boolean active, boolean recommended, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicenses(active, recommended, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicenses(start, end);
	}

	@Override
	public int getLicensesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicensesCount();
	}

	@Override
	public int getLicensesCount(boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getLicensesCount(active, recommended);
	}

	@Override
	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getProductEntryLicenses(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.getProductEntryLicenses(productEntryId);
	}

	@Override
	public com.liferay.portlet.softwarecatalog.model.SCLicense updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _scLicenseLocalService.updateLicense(licenseId, name, url,
			openSource, active, recommended);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public SCLicenseLocalService getWrappedSCLicenseLocalService() {
		return _scLicenseLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
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