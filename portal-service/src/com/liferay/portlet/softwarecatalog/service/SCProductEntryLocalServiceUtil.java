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
 * Provides the local service utility for SCProductEntry. This utility wraps
 * {@link com.liferay.portlet.softwarecatalog.service.impl.SCProductEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SCProductEntryLocalService
 * @see com.liferay.portlet.softwarecatalog.service.base.SCProductEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.softwarecatalog.service.impl.SCProductEntryLocalServiceImpl
 * @generated
 */
public class SCProductEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.softwarecatalog.service.impl.SCProductEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the s c product entry to the database. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @return the s c product entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry addSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSCProductEntry(scProductEntry);
	}

	/**
	* Creates a new s c product entry with the primary key. Does not add the s c product entry to the database.
	*
	* @param productEntryId the primary key for the new s c product entry
	* @return the new s c product entry
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry createSCProductEntry(
		long productEntryId) {
		return getService().createSCProductEntry(productEntryId);
	}

	/**
	* Deletes the s c product entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry that was removed
	* @throws PortalException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry deleteSCProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSCProductEntry(productEntryId);
	}

	/**
	* Deletes the s c product entry from the database. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @return the s c product entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry deleteSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSCProductEntry(scProductEntry);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry fetchSCProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchSCProductEntry(productEntryId);
	}

	/**
	* Returns the s c product entry with the primary key.
	*
	* @param productEntryId the primary key of the s c product entry
	* @return the s c product entry
	* @throws PortalException if a s c product entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry getSCProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCProductEntry(productEntryId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the s c product entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCProductEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c product entries
	* @param end the upper bound of the range of s c product entries (not inclusive)
	* @return the range of s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCProductEntries(start, end);
	}

	/**
	* Returns the number of s c product entries.
	*
	* @return the number of s c product entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getSCProductEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCProductEntriesCount();
	}

	/**
	* Updates the s c product entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param scProductEntry the s c product entry
	* @return the s c product entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry updateSCProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSCProductEntry(scProductEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCLicenseSCProductEntry(long licenseId,
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSCLicenseSCProductEntry(licenseId, productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCLicenseSCProductEntry(long licenseId,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSCLicenseSCProductEntry(licenseId, scProductEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCLicenseSCProductEntries(long licenseId,
		long[] productEntryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSCLicenseSCProductEntries(licenseId, productEntryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCLicenseSCProductEntries(long licenseId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> SCProductEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addSCLicenseSCProductEntries(licenseId, SCProductEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void clearSCLicenseSCProductEntries(long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().clearSCLicenseSCProductEntries(licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCLicenseSCProductEntry(long licenseId,
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCLicenseSCProductEntry(licenseId, productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCLicenseSCProductEntry(long licenseId,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCLicenseSCProductEntry(licenseId, scProductEntry);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCLicenseSCProductEntries(long licenseId,
		long[] productEntryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCLicenseSCProductEntries(licenseId, productEntryIds);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteSCLicenseSCProductEntries(long licenseId,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> SCProductEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSCLicenseSCProductEntries(licenseId, SCProductEntries);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCLicenseSCProductEntries(
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCLicenseSCProductEntries(licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCLicenseSCProductEntries(
		long licenseId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCLicenseSCProductEntries(licenseId, start, end);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCLicenseSCProductEntries(
		long licenseId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getSCLicenseSCProductEntries(licenseId, start, end,
			orderByComparator);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static int getSCLicenseSCProductEntriesCount(long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSCLicenseSCProductEntriesCount(licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasSCLicenseSCProductEntry(long licenseId,
		long productEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasSCLicenseSCProductEntry(licenseId, productEntryId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static boolean hasSCLicenseSCProductEntries(long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasSCLicenseSCProductEntries(licenseId);
	}

	/**
	* @throws SystemException if a system exception occurred
	*/
	public static void setSCLicenseSCProductEntries(long licenseId,
		long[] productEntryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setSCLicenseSCProductEntries(licenseId, productEntryIds);
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

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry addProductEntry(
		long userId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addProductEntry(userId, name, type, tags, shortDescription,
			longDescription, pageURL, author, repoGroupId, repoArtifactId,
			licenseIds, thumbnails, fullImages, serviceContext);
	}

	public static void addProductEntryResources(long productEntryId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addProductEntryResources(productEntryId, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addProductEntryResources(long productEntryId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addProductEntryResources(productEntryId, groupPermissions,
			guestPermissions);
	}

	public static void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addProductEntryResources(productEntry, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addProductEntryResources(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addProductEntryResources(productEntry, groupPermissions,
			guestPermissions);
	}

	public static void deleteProductEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteProductEntries(groupId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry deleteProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteProductEntry(productEntryId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry deleteProductEntry(
		com.liferay.portlet.softwarecatalog.model.SCProductEntry productEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteProductEntry(productEntry);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getCompanyProductEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyProductEntries(companyId, start, end);
	}

	public static int getCompanyProductEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyProductEntriesCount(companyId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductEntries(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductEntries(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductEntries(groupId, userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getProductEntries(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductEntries(groupId, userId, start, end, obc);
	}

	public static int getProductEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductEntriesCount(groupId);
	}

	public static int getProductEntriesCount(long groupId, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductEntriesCount(groupId, userId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry getProductEntry(
		long productEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getProductEntry(productEntryId);
	}

	public static java.lang.String getRepositoryXML(long groupId,
		java.lang.String baseImageURL, java.util.Date oldestDate,
		int maxNumOfVersions, java.util.Properties repoSettings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRepositoryXML(groupId, baseImageURL, oldestDate,
			maxNumOfVersions, repoSettings);
	}

	public static java.lang.String getRepositoryXML(long groupId,
		java.lang.String version, java.lang.String baseImageURL,
		java.util.Date oldestDate, int maxNumOfVersions,
		java.util.Properties repoSettings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRepositoryXML(groupId, version, baseImageURL,
			oldestDate, maxNumOfVersions, repoSettings);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCProductEntry updateProductEntry(
		long productEntryId, java.lang.String name, java.lang.String type,
		java.lang.String tags, java.lang.String shortDescription,
		java.lang.String longDescription, java.lang.String pageURL,
		java.lang.String author, java.lang.String repoGroupId,
		java.lang.String repoArtifactId, long[] licenseIds,
		java.util.List<byte[]> thumbnails, java.util.List<byte[]> fullImages)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateProductEntry(productEntryId, name, type, tags,
			shortDescription, longDescription, pageURL, author, repoGroupId,
			repoArtifactId, licenseIds, thumbnails, fullImages);
	}

	public static SCProductEntryLocalService getService() {
		if (_service == null) {
			_service = (SCProductEntryLocalService)PortalBeanLocatorUtil.locate(SCProductEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(SCProductEntryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(SCProductEntryLocalService service) {
	}

	private static SCProductEntryLocalService _service;
}