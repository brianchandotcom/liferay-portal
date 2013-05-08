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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for SCLicense. This utility wraps
 * {@link com.liferay.portlet.softwarecatalog.service.impl.SCLicenseLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SCLicenseLocalService
 * @see com.liferay.portlet.softwarecatalog.service.base.SCLicenseLocalServiceBaseImpl
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCLicenseLocalServiceImpl
 * @generated
 */
public class SCLicenseLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.softwarecatalog.service.impl.SCLicenseLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the s c license to the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license
	* @return the s c license that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCLicense addSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSCLicense(scLicense);
	}

	/**
	* Creates a new s c license with the primary key. Does not add the s c license to the database.
	*
	* @param licenseId the primary key for the new s c license
	* @return the new s c license
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCLicense createSCLicense(
		long licenseId) {
		return getService().createSCLicense(licenseId);
	}

	/**
	* Deletes the s c license with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license that was removed
	* @throws PortalException if a s c license with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCLicense deleteSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSCLicense(licenseId);
	}

	/**
	* Deletes the s c license from the database. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license
	* @return the s c license that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCLicense deleteSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSCLicense(scLicense);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense fetchSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchSCLicense(licenseId);
	}

	/**
	* Returns the s c license with the primary key.
	*
	* @param licenseId the primary key of the s c license
	* @return the s c license
	* @throws PortalException if a s c license with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCLicense getSCLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCLicense(licenseId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCLicenses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCLicenses(start, end);
	}

	/**
	* Returns the number of s c licenses.
	*
	* @return the number of s c licenses
	* @throws SystemException if a system exception occurred
	*/
	public static int getSCLicensesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCLicensesCount();
	}

	/**
	* Updates the s c license in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scLicense the s c license
	* @return the s c license that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCLicense updateSCLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSCLicense(scLicense);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductEntrySCLicense(long productEntryId,
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSCProductEntrySCLicense(productEntryId, licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductEntrySCLicense(long productEntryId,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSCProductEntrySCLicense(productEntryId, scLicense);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSCProductEntrySCLicenses(productEntryId, licenseIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductEntrySCLicenses(long productEntryId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> SCLicenses)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSCProductEntrySCLicenses(productEntryId, SCLicenses);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearSCProductEntrySCLicenses(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearSCProductEntrySCLicenses(productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCProductEntrySCLicense(long productEntryId,
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCProductEntrySCLicense(productEntryId, licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCProductEntrySCLicense(long productEntryId,
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCProductEntrySCLicense(productEntryId, scLicense);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCProductEntrySCLicenses(productEntryId, licenseIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCProductEntrySCLicenses(long productEntryId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> SCLicenses)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCProductEntrySCLicenses(productEntryId, SCLicenses);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCProductEntrySCLicenses(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCProductEntrySCLicenses(productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCProductEntrySCLicenses(
		long productEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSCProductEntrySCLicenses(productEntryId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getSCProductEntrySCLicenses(
		long productEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSCProductEntrySCLicenses(productEntryId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getSCProductEntrySCLicensesCount(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCProductEntrySCLicensesCount(productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasSCProductEntrySCLicense(long productEntryId,
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasSCProductEntrySCLicense(productEntryId, licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasSCProductEntrySCLicenses(long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasSCProductEntrySCLicenses(productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setSCProductEntrySCLicenses(long productEntryId,
		long[] licenseIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setSCProductEntrySCLicenses(productEntryId, licenseIds);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense addLicense(
		java.lang.String name, java.lang.String url, boolean openSource,
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addLicense(name, url, openSource, active, recommended);
	}

	public static void deleteLicense(long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLicense(licenseId);
	}

	public static void deleteLicense(
		com.liferay.portlet.softwarecatalog.model.SCLicense license)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLicense(license);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense getLicense(
		long licenseId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLicense(licenseId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLicenses();
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLicenses(active, recommended);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		boolean active, boolean recommended, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLicenses(active, recommended, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getLicenses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLicenses(start, end);
	}

	public static int getLicensesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLicensesCount();
	}

	public static int getLicensesCount(boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLicensesCount(active, recommended);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> getProductEntryLicenses(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductEntryLicenses(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense updateLicense(
		long licenseId, java.lang.String name, java.lang.String url,
		boolean openSource, boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLicense(licenseId, name, url, openSource, active,
			recommended);
	}

	public static SCLicenseLocalService getService() {
		if (_service == null) {
			_service = (SCLicenseLocalService)PortalBeanLocatorUtil.locate(SCLicenseLocalService.class.getName());

			ReferenceRegistry.registerReference(SCLicenseLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(SCLicenseLocalService service) {
	}

	private static SCLicenseLocalService _service;
}