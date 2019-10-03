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

package com.liferay.content.repository.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ContentRepositoryEntry. This utility wraps
 * <code>com.liferay.content.repository.service.impl.ContentRepositoryEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ContentRepositoryEntryLocalService
 * @generated
 */
public class ContentRepositoryEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.content.repository.service.impl.ContentRepositoryEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the content repository entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntry the content repository entry
	 * @return the content repository entry that was added
	 */
	public static com.liferay.content.repository.model.ContentRepositoryEntry
		addContentRepositoryEntry(
			com.liferay.content.repository.model.ContentRepositoryEntry
				contentRepositoryEntry) {

		return getService().addContentRepositoryEntry(contentRepositoryEntry);
	}

	public static com.liferay.content.repository.model.ContentRepositoryEntry
			addContentRepositoryEntry(
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addContentRepositoryEntry(
			nameMap, descriptionMap, serviceContext);
	}

	/**
	 * Creates a new content repository entry with the primary key. Does not add the content repository entry to the database.
	 *
	 * @param contentRepositoryEntryId the primary key for the new content repository entry
	 * @return the new content repository entry
	 */
	public static com.liferay.content.repository.model.ContentRepositoryEntry
		createContentRepositoryEntry(long contentRepositoryEntryId) {

		return getService().createContentRepositoryEntry(
			contentRepositoryEntryId);
	}

	/**
	 * Deletes the content repository entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntry the content repository entry
	 * @return the content repository entry that was removed
	 */
	public static com.liferay.content.repository.model.ContentRepositoryEntry
		deleteContentRepositoryEntry(
			com.liferay.content.repository.model.ContentRepositoryEntry
				contentRepositoryEntry) {

		return getService().deleteContentRepositoryEntry(
			contentRepositoryEntry);
	}

	/**
	 * Deletes the content repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry that was removed
	 * @throws PortalException if a content repository entry with the primary key could not be found
	 */
	public static com.liferay.content.repository.model.ContentRepositoryEntry
			deleteContentRepositoryEntry(long contentRepositoryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteContentRepositoryEntry(
			contentRepositoryEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.content.repository.model.impl.ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.content.repository.model.impl.ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.content.repository.model.ContentRepositoryEntry
		fetchContentRepositoryEntry(long contentRepositoryEntryId) {

		return getService().fetchContentRepositoryEntry(
			contentRepositoryEntryId);
	}

	/**
	 * Returns the content repository entry matching the UUID and group.
	 *
	 * @param uuid the content repository entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	public static com.liferay.content.repository.model.ContentRepositoryEntry
		fetchContentRepositoryEntryByUuidAndGroupId(String uuid, long groupId) {

		return getService().fetchContentRepositoryEntryByUuidAndGroupId(
			uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>com.liferay.content.repository.model.impl.ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of content repository entries
	 */
	public static java.util.List
		<com.liferay.content.repository.model.ContentRepositoryEntry>
			getContentRepositoryEntries(int start, int end) {

		return getService().getContentRepositoryEntries(start, end);
	}

	/**
	 * Returns all the content repository entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the content repository entries
	 * @param companyId the primary key of the company
	 * @return the matching content repository entries, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.content.repository.model.ContentRepositoryEntry>
			getContentRepositoryEntriesByUuidAndCompanyId(
				String uuid, long companyId) {

		return getService().getContentRepositoryEntriesByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Returns a range of content repository entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the content repository entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching content repository entries, or an empty list if no matches were found
	 */
	public static java.util.List
		<com.liferay.content.repository.model.ContentRepositoryEntry>
			getContentRepositoryEntriesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.content.repository.model.
						ContentRepositoryEntry> orderByComparator) {

		return getService().getContentRepositoryEntriesByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of content repository entries.
	 *
	 * @return the number of content repository entries
	 */
	public static int getContentRepositoryEntriesCount() {
		return getService().getContentRepositoryEntriesCount();
	}

	/**
	 * Returns the content repository entry with the primary key.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry
	 * @throws PortalException if a content repository entry with the primary key could not be found
	 */
	public static com.liferay.content.repository.model.ContentRepositoryEntry
			getContentRepositoryEntry(long contentRepositoryEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContentRepositoryEntry(contentRepositoryEntryId);
	}

	/**
	 * Returns the content repository entry matching the UUID and group.
	 *
	 * @param uuid the content repository entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching content repository entry
	 * @throws PortalException if a matching content repository entry could not be found
	 */
	public static com.liferay.content.repository.model.ContentRepositoryEntry
			getContentRepositoryEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getContentRepositoryEntryByUuidAndGroupId(
			uuid, groupId);
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
	 * Updates the content repository entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntry the content repository entry
	 * @return the content repository entry that was updated
	 */
	public static com.liferay.content.repository.model.ContentRepositoryEntry
		updateContentRepositoryEntry(
			com.liferay.content.repository.model.ContentRepositoryEntry
				contentRepositoryEntry) {

		return getService().updateContentRepositoryEntry(
			contentRepositoryEntry);
	}

	public static com.liferay.content.repository.model.ContentRepositoryEntry
			updateContentRepositoryEntry(
				long contentRepositoryEntryId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateContentRepositoryEntry(
			contentRepositoryEntryId, nameMap, descriptionMap, serviceContext);
	}

	public static ContentRepositoryEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ContentRepositoryEntryLocalService, ContentRepositoryEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ContentRepositoryEntryLocalService.class);

		ServiceTracker
			<ContentRepositoryEntryLocalService,
			 ContentRepositoryEntryLocalService> serviceTracker =
				new ServiceTracker
					<ContentRepositoryEntryLocalService,
					 ContentRepositoryEntryLocalService>(
						 bundle.getBundleContext(),
						 ContentRepositoryEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}