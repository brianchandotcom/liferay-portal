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

package com.liferay.service.access.control.profile.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SACPEntry. This utility wraps
 * {@link com.liferay.service.access.control.profile.service.impl.SACPEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SACPEntryLocalService
 * @see com.liferay.service.access.control.profile.service.base.SACPEntryLocalServiceBaseImpl
 * @see com.liferay.service.access.control.profile.service.impl.SACPEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class SACPEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.service.access.control.profile.service.impl.SACPEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the s a c p entry to the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntry the s a c p entry
	* @return the s a c p entry that was added
	*/
	public static com.liferay.service.access.control.profile.model.SACPEntry addSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry) {
		return getService().addSACPEntry(sacpEntry);
	}

	public static com.liferay.service.access.control.profile.model.SACPEntry addSACPEntry(
		long userId, java.lang.String allowedServices, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSACPEntry(userId, allowedServices, name, titleMap,
			serviceContext);
	}

	/**
	* Creates a new s a c p entry with the primary key. Does not add the s a c p entry to the database.
	*
	* @param sacpEntryId the primary key for the new s a c p entry
	* @return the new s a c p entry
	*/
	public static com.liferay.service.access.control.profile.model.SACPEntry createSACPEntry(
		long sacpEntryId) {
		return getService().createSACPEntry(sacpEntryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the s a c p entry from the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntry the s a c p entry
	* @return the s a c p entry that was removed
	* @throws PortalException
	*/
	public static com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSACPEntry(sacpEntry);
	}

	/**
	* Deletes the s a c p entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry that was removed
	* @throws PortalException if a s a c p entry with the primary key could not be found
	*/
	public static com.liferay.service.access.control.profile.model.SACPEntry deleteSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSACPEntry(sacpEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.service.access.control.profile.model.SACPEntry fetchSACPEntry(
		long sacpEntryId) {
		return getService().fetchSACPEntry(sacpEntryId);
	}

	/**
	* Returns the s a c p entry with the matching UUID and company.
	*
	* @param uuid the s a c p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a c p entry, or <code>null</code> if a matching s a c p entry could not be found
	*/
	public static com.liferay.service.access.control.profile.model.SACPEntry fetchSACPEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().fetchSACPEntryByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	public static java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end) {
		return getService().getCompanySACPEntries(companyId, start, end);
	}

	public static java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getCompanySACPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.control.profile.model.SACPEntry> obc) {
		return getService().getCompanySACPEntries(companyId, start, end, obc);
	}

	public static int getCompanySACPEntriesCount(long companyId) {
		return getService().getCompanySACPEntriesCount(companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portal.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the s a c p entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.control.profile.model.impl.SACPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s a c p entries
	* @param end the upper bound of the range of s a c p entries (not inclusive)
	* @return the range of s a c p entries
	*/
	public static java.util.List<com.liferay.service.access.control.profile.model.SACPEntry> getSACPEntries(
		int start, int end) {
		return getService().getSACPEntries(start, end);
	}

	/**
	* Returns the number of s a c p entries.
	*
	* @return the number of s a c p entries
	*/
	public static int getSACPEntriesCount() {
		return getService().getSACPEntriesCount();
	}

	public static com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSACPEntry(companyId, name);
	}

	/**
	* Returns the s a c p entry with the primary key.
	*
	* @param sacpEntryId the primary key of the s a c p entry
	* @return the s a c p entry
	* @throws PortalException if a s a c p entry with the primary key could not be found
	*/
	public static com.liferay.service.access.control.profile.model.SACPEntry getSACPEntry(
		long sacpEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSACPEntry(sacpEntryId);
	}

	/**
	* Returns the s a c p entry with the matching UUID and company.
	*
	* @param uuid the s a c p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a c p entry
	* @throws PortalException if a matching s a c p entry could not be found
	*/
	public static com.liferay.service.access.control.profile.model.SACPEntry getSACPEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSACPEntryByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the s a c p entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sacpEntry the s a c p entry
	* @return the s a c p entry that was updated
	*/
	public static com.liferay.service.access.control.profile.model.SACPEntry updateSACPEntry(
		com.liferay.service.access.control.profile.model.SACPEntry sacpEntry) {
		return getService().updateSACPEntry(sacpEntry);
	}

	public static com.liferay.service.access.control.profile.model.SACPEntry updateSACPEntry(
		long sacpEntryId, java.lang.String allowedServices,
		java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSACPEntry(sacpEntryId, allowedServices, name,
			titleMap, serviceContext);
	}

	public static SACPEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SACPEntryLocalService service) {
	}

	private static ServiceTracker<SACPEntryLocalService, SACPEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SACPEntryLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<SACPEntryLocalService, SACPEntryLocalService>(bundle.getBundleContext(),
				SACPEntryLocalService.class, null);

		_serviceTracker.open();
	}
}