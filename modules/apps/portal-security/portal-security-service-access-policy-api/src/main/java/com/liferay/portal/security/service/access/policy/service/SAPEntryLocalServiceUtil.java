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

package com.liferay.portal.security.service.access.policy.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SAPEntry. This utility wraps
 * <code>com.liferay.portal.security.service.access.policy.service.impl.SAPEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryLocalService
 * @generated
 */
public class SAPEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.security.service.access.policy.service.impl.SAPEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SAPEntryLocalServiceUtil} to access the sap entry local service. Add custom service methods to <code>com.liferay.portal.security.service.access.policy.service.impl.SAPEntryLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
				addSAPEntry(
					long userId, String allowedServiceSignatures,
					boolean defaultSAPEntry, boolean enabled, String name,
					java.util.Map<java.util.Locale, String> titleMap,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSAPEntry(
			userId, allowedServiceSignatures, defaultSAPEntry, enabled, name,
			titleMap, serviceContext);
	}

	/**
	 * Adds the sap entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntry the sap entry
	 * @return the sap entry that was added
	 */
	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
			addSAPEntry(
				com.liferay.portal.security.service.access.policy.model.SAPEntry
					sapEntry) {

		return getService().addSAPEntry(sapEntry);
	}

	public static void checkSystemSAPEntries(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().checkSystemSAPEntries(companyId);
	}

	/**
	 * Creates a new sap entry with the primary key. Does not add the sap entry to the database.
	 *
	 * @param sapEntryId the primary key for the new sap entry
	 * @return the new sap entry
	 */
	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
			createSAPEntry(long sapEntryId) {

		return getService().createSAPEntry(sapEntryId);
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
	 * Deletes the sap entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry that was removed
	 * @throws PortalException if a sap entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
				deleteSAPEntry(long sapEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSAPEntry(sapEntryId);
	}

	/**
	 * Deletes the sap entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntry the sap entry
	 * @return the sap entry that was removed
	 * @throws PortalException
	 */
	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
				deleteSAPEntry(
					com.liferay.portal.security.service.access.policy.model.
						SAPEntry sapEntry)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSAPEntry(sapEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.service.access.policy.model.impl.SAPEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.service.access.policy.model.impl.SAPEntryModelImpl</code>.
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

	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
			fetchSAPEntry(long sapEntryId) {

		return getService().fetchSAPEntry(sapEntryId);
	}

	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
				fetchSAPEntry(long companyId, String name)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchSAPEntry(companyId, name);
	}

	/**
	 * Returns the sap entry with the matching UUID and company.
	 *
	 * @param uuid the sap entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sap entry, or <code>null</code> if a matching sap entry could not be found
	 */
	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
			fetchSAPEntryByUuidAndCompanyId(String uuid, long companyId) {

		return getService().fetchSAPEntryByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.portal.security.service.access.policy.model.SAPEntry>
			getCompanySAPEntries(long companyId, int start, int end) {

		return getService().getCompanySAPEntries(companyId, start, end);
	}

	public static java.util.List
		<com.liferay.portal.security.service.access.policy.model.SAPEntry>
			getCompanySAPEntries(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.security.service.access.policy.model.
						SAPEntry> obc) {

		return getService().getCompanySAPEntries(companyId, start, end, obc);
	}

	public static int getCompanySAPEntriesCount(long companyId) {
		return getService().getCompanySAPEntriesCount(companyId);
	}

	public static java.util.List
		<com.liferay.portal.security.service.access.policy.model.SAPEntry>
			getDefaultSAPEntries(long companyId, boolean defaultSAPEntry) {

		return getService().getDefaultSAPEntries(companyId, defaultSAPEntry);
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

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the sap entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.service.access.policy.model.impl.SAPEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sap entries
	 * @param end the upper bound of the range of sap entries (not inclusive)
	 * @return the range of sap entries
	 */
	public static java.util.List
		<com.liferay.portal.security.service.access.policy.model.SAPEntry>
			getSAPEntries(int start, int end) {

		return getService().getSAPEntries(start, end);
	}

	/**
	 * Returns the number of sap entries.
	 *
	 * @return the number of sap entries
	 */
	public static int getSAPEntriesCount() {
		return getService().getSAPEntriesCount();
	}

	/**
	 * Returns the sap entry with the primary key.
	 *
	 * @param sapEntryId the primary key of the sap entry
	 * @return the sap entry
	 * @throws PortalException if a sap entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
				getSAPEntry(long sapEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSAPEntry(sapEntryId);
	}

	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
				getSAPEntry(long companyId, String name)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSAPEntry(companyId, name);
	}

	/**
	 * Returns the sap entry with the matching UUID and company.
	 *
	 * @param uuid the sap entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sap entry
	 * @throws PortalException if a matching sap entry could not be found
	 */
	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
				getSAPEntryByUuidAndCompanyId(String uuid, long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSAPEntryByUuidAndCompanyId(uuid, companyId);
	}

	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
				updateSAPEntry(
					long sapEntryId, String allowedServiceSignatures,
					boolean defaultSAPEntry, boolean enabled, String name,
					java.util.Map<java.util.Locale, String> titleMap,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSAPEntry(
			sapEntryId, allowedServiceSignatures, defaultSAPEntry, enabled,
			name, titleMap, serviceContext);
	}

	/**
	 * Updates the sap entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntry the sap entry
	 * @return the sap entry that was updated
	 */
	public static
		com.liferay.portal.security.service.access.policy.model.SAPEntry
			updateSAPEntry(
				com.liferay.portal.security.service.access.policy.model.SAPEntry
					sapEntry) {

		return getService().updateSAPEntry(sapEntry);
	}

	public static SAPEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SAPEntryLocalService, SAPEntryLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SAPEntryLocalService.class);

		ServiceTracker<SAPEntryLocalService, SAPEntryLocalService>
			serviceTracker =
				new ServiceTracker<SAPEntryLocalService, SAPEntryLocalService>(
					bundle.getBundleContext(), SAPEntryLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}