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

package com.liferay.trash.kernel.service;

import org.osgi.annotation.versioning.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for TrashEntry. This utility wraps
 * {@link com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryLocalService
 * @see com.liferay.portlet.trash.service.base.TrashEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl
 * @deprecated As of Judson (7.1.x), replaced by {@link
com.liferay.trash.service.impl.TrashEntryLocalServiceImpl}
 * @generated
 */
@Deprecated
public class TrashEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.trash.kernel.model.TrashEntry> searchTrashEntries(
		long companyId, long groupId, long userId, java.lang.String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort) {
		return getService()
				   .searchTrashEntries(companyId, groupId, userId, keywords,
			start, end, sort);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		long groupId, long userId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort) {
		return getService()
				   .search(companyId, groupId, userId, keywords, start, end,
			sort);
	}

	/**
	* Adds the trash entry to the database. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @return the trash entry that was added
	*/
	public static com.liferay.trash.kernel.model.TrashEntry addTrashEntry(
		com.liferay.trash.kernel.model.TrashEntry trashEntry) {
		return getService().addTrashEntry(trashEntry);
	}

	/**
	* Moves an entry to trash.
	*
	* @param userId the primary key of the user removing the entity
	* @param groupId the primary key of the entry's group
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @param classUuid the UUID of the entity's class
	* @param referrerClassName the referrer class name used to add a deletion
	{@link SystemEvent}
	* @param status the status of the entity prior to being moved to trash
	* @param statusOVPs the primary keys and statuses of any of the entry's
	versions (e.g., {@link
	com.liferay.portlet.documentlibrary.model.DLFileVersion})
	* @param typeSettingsProperties the type settings properties
	* @return the trashEntry
	*/
	public static com.liferay.trash.kernel.model.TrashEntry addTrashEntry(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, java.lang.String referrerClassName,
		int status,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<java.lang.Long, java.lang.Integer>> statusOVPs,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addTrashEntry(userId, groupId, className, classPK,
			classUuid, referrerClassName, status, statusOVPs,
			typeSettingsProperties);
	}

	/**
	* Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	*
	* @param entryId the primary key for the new trash entry
	* @return the new trash entry
	*/
	public static com.liferay.trash.kernel.model.TrashEntry createTrashEntry(
		long entryId) {
		return getService().createTrashEntry(entryId);
	}

	public static com.liferay.trash.kernel.model.TrashEntry deleteEntry(
		com.liferay.trash.kernel.model.TrashEntry trashEntry) {
		return getService().deleteEntry(trashEntry);
	}

	/**
	* Deletes the trash entry with the entity class name and primary key.
	*
	* @param className the class name of entity
	* @param classPK the primary key of the entry
	* @return the trash entry with the entity class name and primary key
	*/
	public static com.liferay.trash.kernel.model.TrashEntry deleteEntry(
		java.lang.String className, long classPK) {
		return getService().deleteEntry(className, classPK);
	}

	public static void deleteEntries(long groupId, boolean deleteTrashedModels) {
		getService().deleteEntries(groupId, deleteTrashedModels);
	}

	/**
	* Deletes the trash entry with the primary key.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry with the primary key
	*/
	public static com.liferay.trash.kernel.model.TrashEntry deleteEntry(
		long entryId) {
		return getService().deleteEntry(entryId);
	}

	/**
	* Deletes the trash entry from the database. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @return the trash entry that was removed
	*/
	public static com.liferay.trash.kernel.model.TrashEntry deleteTrashEntry(
		com.liferay.trash.kernel.model.TrashEntry trashEntry) {
		return getService().deleteTrashEntry(trashEntry);
	}

	/**
	* Deletes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry that was removed
	* @throws PortalException if a trash entry with the primary key could not be found
	*/
	public static com.liferay.trash.kernel.model.TrashEntry deleteTrashEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteTrashEntry(entryId);
	}

	/**
	* Returns the trash entry with the entity class name and primary key.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the trash entry with the entity class name and primary key
	*/
	public static com.liferay.trash.kernel.model.TrashEntry fetchEntry(
		java.lang.String className, long classPK) {
		return getService().fetchEntry(className, classPK);
	}

	/**
	* Returns the trash entry with the primary key.
	*
	* @param entryId the primary key of the entry
	* @return the trash entry with the primary key
	*/
	public static com.liferay.trash.kernel.model.TrashEntry fetchEntry(
		long entryId) {
		return getService().fetchEntry(entryId);
	}

	public static com.liferay.trash.kernel.model.TrashEntry fetchTrashEntry(
		long entryId) {
		return getService().fetchTrashEntry(entryId);
	}

	/**
	* Returns the entry with the entity class name and primary key.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the trash entry with the entity class name and primary key
	*/
	public static com.liferay.trash.kernel.model.TrashEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntry(className, classPK);
	}

	/**
	* Returns the trash entry with the primary key.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry with the primary key
	*/
	public static com.liferay.trash.kernel.model.TrashEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getEntry(entryId);
	}

	/**
	* Returns the trash entry with the primary key.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry
	* @throws PortalException if a trash entry with the primary key could not be found
	*/
	public static com.liferay.trash.kernel.model.TrashEntry getTrashEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTrashEntry(entryId);
	}

	/**
	* Updates the trash entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @return the trash entry that was updated
	*/
	public static com.liferay.trash.kernel.model.TrashEntry updateTrashEntry(
		com.liferay.trash.kernel.model.TrashEntry trashEntry) {
		return getService().updateTrashEntry(trashEntry);
	}

	/**
	* Returns the number of trash entries with the group ID.
	*
	* @param groupId the primary key of the group
	* @return the number of matching trash entries
	*/
	public static int getEntriesCount(long groupId) {
		return getService().getEntriesCount(groupId);
	}

	/**
	* Returns the number of trash entries.
	*
	* @return the number of trash entries
	*/
	public static int getTrashEntriesCount() {
		return getService().getTrashEntriesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns the trash entries with the matching group ID.
	*
	* @param groupId the primary key of the group
	* @return the trash entries with the group ID
	*/
	public static java.util.List<com.liferay.trash.kernel.model.TrashEntry> getEntries(
		long groupId) {
		return getService().getEntries(groupId);
	}

	/**
	* Returns a range of all the trash entries matching the group ID.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of trash entries to return
	* @param end the upper bound of the range of trash entries to return (not
	inclusive)
	* @return the range of matching trash entries
	*/
	public static java.util.List<com.liferay.trash.kernel.model.TrashEntry> getEntries(
		long groupId, int start, int end) {
		return getService().getEntries(groupId, start, end);
	}

	/**
	* Returns a range of all the trash entries matching the group ID.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of trash entries to return
	* @param end the upper bound of the range of trash entries to return (not
	inclusive)
	* @param obc the comparator to order the trash entries (optionally
	<code>null</code>)
	* @return the range of matching trash entries ordered by comparator
	<code>obc</code>
	*/
	public static java.util.List<com.liferay.trash.kernel.model.TrashEntry> getEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.trash.kernel.model.TrashEntry> obc) {
		return getService().getEntries(groupId, start, end, obc);
	}

	public static java.util.List<com.liferay.trash.kernel.model.TrashEntry> getEntries(
		long groupId, java.lang.String className) {
		return getService().getEntries(groupId, className);
	}

	/**
	* Returns a range of all the trash entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.trash.model.impl.TrashEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @return the range of trash entries
	*/
	public static java.util.List<com.liferay.trash.kernel.model.TrashEntry> getTrashEntries(
		int start, int end) {
		return getService().getTrashEntries(start, end);
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

	public static void checkEntries()
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().checkEntries();
	}

	public static void deleteEntries(long groupId) {
		getService().deleteEntries(groupId);
	}

	public static TrashEntryLocalService getService() {
		if (_service == null) {
			_service = (TrashEntryLocalService)PortalBeanLocatorUtil.locate(TrashEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(TrashEntryLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static TrashEntryLocalService _service;
}