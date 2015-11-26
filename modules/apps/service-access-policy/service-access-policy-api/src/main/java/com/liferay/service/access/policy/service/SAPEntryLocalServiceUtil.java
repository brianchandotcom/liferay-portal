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

package com.liferay.service.access.policy.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SAPEntry. This utility wraps
 * {@link com.liferay.service.access.policy.service.impl.SAPEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryLocalService
 * @see com.liferay.service.access.policy.service.base.SAPEntryLocalServiceBaseImpl
 * @see com.liferay.service.access.policy.service.impl.SAPEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class SAPEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.service.access.policy.service.impl.SAPEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the s a p entry to the database. Also notifies the appropriate model listeners.
	*
	* @param sapEntry the s a p entry
	* @return the s a p entry that was added
	*/
	public static com.liferay.service.access.policy.model.SAPEntry addSAPEntry(
		com.liferay.service.access.policy.model.SAPEntry sapEntry) {
		return getService().addSAPEntry(sapEntry);
	}

	public static com.liferay.service.access.policy.model.SAPEntry addSAPEntry(
		long userId, java.lang.String allowedServiceSignatures,
		boolean defaultSAPEntry, boolean enabled, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSAPEntry(userId, allowedServiceSignatures,
			defaultSAPEntry, enabled, name, titleMap, serviceContext);
	}

	public static void checkSystemSAPEntries(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkSystemSAPEntries(companyId);
	}

	/**
	* Creates a new s a p entry with the primary key. Does not add the s a p entry to the database.
	*
	* @param sapEntryId the primary key for the new s a p entry
	* @return the new s a p entry
	*/
	public static com.liferay.service.access.policy.model.SAPEntry createSAPEntry(
		long sapEntryId) {
		return getService().createSAPEntry(sapEntryId);
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
	* Deletes the s a p entry from the database. Also notifies the appropriate model listeners.
	*
	* @param sapEntry the s a p entry
	* @return the s a p entry that was removed
	* @throws PortalException
	*/
	public static com.liferay.service.access.policy.model.SAPEntry deleteSAPEntry(
		com.liferay.service.access.policy.model.SAPEntry sapEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSAPEntry(sapEntry);
	}

	/**
	* Deletes the s a p entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sapEntryId the primary key of the s a p entry
	* @return the s a p entry that was removed
	* @throws PortalException if a s a p entry with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.SAPEntry deleteSAPEntry(
		long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSAPEntry(sapEntryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.service.access.policy.model.SAPEntry fetchSAPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchSAPEntry(companyId, name);
	}

	public static com.liferay.service.access.policy.model.SAPEntry fetchSAPEntry(
		long sapEntryId) {
		return getService().fetchSAPEntry(sapEntryId);
	}

	/**
	* Returns the s a p entry with the matching UUID and company.
	*
	* @param uuid the s a p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	*/
	public static com.liferay.service.access.policy.model.SAPEntry fetchSAPEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().fetchSAPEntryByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.service.access.policy.model.SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end) {
		return getService().getCompanySAPEntries(companyId, start, end);
	}

	public static java.util.List<com.liferay.service.access.policy.model.SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.service.access.policy.model.SAPEntry> obc) {
		return getService().getCompanySAPEntries(companyId, start, end, obc);
	}

	public static int getCompanySAPEntriesCount(long companyId) {
		return getService().getCompanySAPEntriesCount(companyId);
	}

	public static java.util.List<com.liferay.service.access.policy.model.SAPEntry> getDefaultSAPEntries(
		long companyId, boolean defaultSAPEntry) {
		return getService().getDefaultSAPEntries(companyId, defaultSAPEntry);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the s a p entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s a p entries
	* @param end the upper bound of the range of s a p entries (not inclusive)
	* @return the range of s a p entries
	*/
	public static java.util.List<com.liferay.service.access.policy.model.SAPEntry> getSAPEntries(
		int start, int end) {
		return getService().getSAPEntries(start, end);
	}

	/**
	* Returns the number of s a p entries.
	*
	* @return the number of s a p entries
	*/
	public static int getSAPEntriesCount() {
		return getService().getSAPEntriesCount();
	}

	public static com.liferay.service.access.policy.model.SAPEntry getSAPEntry(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSAPEntry(companyId, name);
	}

	/**
	* Returns the s a p entry with the primary key.
	*
	* @param sapEntryId the primary key of the s a p entry
	* @return the s a p entry
	* @throws PortalException if a s a p entry with the primary key could not be found
	*/
	public static com.liferay.service.access.policy.model.SAPEntry getSAPEntry(
		long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSAPEntry(sapEntryId);
	}

	/**
	* Returns the s a p entry with the matching UUID and company.
	*
	* @param uuid the s a p entry's UUID
	* @param companyId the primary key of the company
	* @return the matching s a p entry
	* @throws PortalException if a matching s a p entry could not be found
	*/
	public static com.liferay.service.access.policy.model.SAPEntry getSAPEntryByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSAPEntryByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Updates the s a p entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sapEntry the s a p entry
	* @return the s a p entry that was updated
	*/
	public static com.liferay.service.access.policy.model.SAPEntry updateSAPEntry(
		com.liferay.service.access.policy.model.SAPEntry sapEntry) {
		return getService().updateSAPEntry(sapEntry);
	}

	public static com.liferay.service.access.policy.model.SAPEntry updateSAPEntry(
		long sapEntryId, java.lang.String allowedServiceSignatures,
		boolean defaultSAPEntry, boolean enabled, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSAPEntry(sapEntryId, allowedServiceSignatures,
			defaultSAPEntry, enabled, name, titleMap, serviceContext);
	}

	public static SAPEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SAPEntryLocalService service) {
	}

	private static ServiceTracker<SAPEntryLocalService, SAPEntryLocalService> _serviceTracker =
		ServiceTrackerFactory.open(SAPEntryLocalService.class);
}