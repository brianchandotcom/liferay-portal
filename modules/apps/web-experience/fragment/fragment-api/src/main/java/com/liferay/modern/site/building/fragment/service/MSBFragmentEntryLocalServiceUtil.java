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

package com.liferay.modern.site.building.fragment.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for MSBFragmentEntry. This utility wraps
 * {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentEntryLocalService
 * @see com.liferay.modern.site.building.fragment.service.base.MSBFragmentEntryLocalServiceBaseImpl
 * @see com.liferay.modern.site.building.fragment.service.impl.MSBFragmentEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class MSBFragmentEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.modern.site.building.fragment.service.impl.MSBFragmentEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry addMSBFragmentEntry(
		long groupId, long userId, long msbFragmentCollectionId,
		java.lang.String name, java.lang.String css, java.lang.String html,
		java.lang.String js,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addMSBFragmentEntry(groupId, userId,
			msbFragmentCollectionId, name, css, html, js, serviceContext);
	}

	/**
	* Adds the msb fragment entry to the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntry the msb fragment entry
	* @return the msb fragment entry that was added
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry addMSBFragmentEntry(
		com.liferay.modern.site.building.fragment.model.MSBFragmentEntry msbFragmentEntry) {
		return getService().addMSBFragmentEntry(msbFragmentEntry);
	}

	/**
	* Creates a new msb fragment entry with the primary key. Does not add the msb fragment entry to the database.
	*
	* @param msbFragmentEntryId the primary key for the new msb fragment entry
	* @return the new msb fragment entry
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry createMSBFragmentEntry(
		long msbFragmentEntryId) {
		return getService().createMSBFragmentEntry(msbFragmentEntryId);
	}

	/**
	* Deletes the msb fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry that was removed
	* @throws PortalException if a msb fragment entry with the primary key could not be found
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry deleteMSBFragmentEntry(
		long msbFragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMSBFragmentEntry(msbFragmentEntryId);
	}

	/**
	* Deletes the msb fragment entry from the database. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntry the msb fragment entry
	* @return the msb fragment entry that was removed
	* @throws PortalException
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry deleteMSBFragmentEntry(
		com.liferay.modern.site.building.fragment.model.MSBFragmentEntry msbFragmentEntry)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteMSBFragmentEntry(msbFragmentEntry);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> fetchMSBFragmentEntries(
		long msbFragmentCollectionId) {
		return getService().fetchMSBFragmentEntries(msbFragmentCollectionId);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry fetchMSBFragmentEntry(
		long msbFragmentEntryId) {
		return getService().fetchMSBFragmentEntry(msbFragmentEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns a range of all the msb fragment entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of msb fragment entries
	* @param end the upper bound of the range of msb fragment entries (not inclusive)
	* @return the range of msb fragment entries
	*/
	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		int start, int end) {
		return getService().getMSBFragmentEntries(start, end);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long msbFragmentCollectionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMSBFragmentEntries(msbFragmentCollectionId, start, end);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getMSBFragmentEntries(groupId, msbFragmentCollectionId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> getMSBFragmentEntries(
		long groupId, long msbFragmentCollectionId, java.lang.String name,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.modern.site.building.fragment.model.MSBFragmentEntry> orderByComparator) {
		return getService()
				   .getMSBFragmentEntries(groupId, msbFragmentCollectionId,
			name, start, end, orderByComparator);
	}

	/**
	* Returns the number of msb fragment entries.
	*
	* @return the number of msb fragment entries
	*/
	public static int getMSBFragmentEntriesCount() {
		return getService().getMSBFragmentEntriesCount();
	}

	/**
	* Returns the msb fragment entry with the primary key.
	*
	* @param msbFragmentEntryId the primary key of the msb fragment entry
	* @return the msb fragment entry
	* @throws PortalException if a msb fragment entry with the primary key could not be found
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry getMSBFragmentEntry(
		long msbFragmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getMSBFragmentEntry(msbFragmentEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry updateMSBFragmentEntry(
		long msbFragmentEntryId, java.lang.String name, java.lang.String css,
		java.lang.String html, java.lang.String js)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateMSBFragmentEntry(msbFragmentEntryId, name, css, html,
			js);
	}

	/**
	* Updates the msb fragment entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param msbFragmentEntry the msb fragment entry
	* @return the msb fragment entry that was updated
	*/
	public static com.liferay.modern.site.building.fragment.model.MSBFragmentEntry updateMSBFragmentEntry(
		com.liferay.modern.site.building.fragment.model.MSBFragmentEntry msbFragmentEntry) {
		return getService().updateMSBFragmentEntry(msbFragmentEntry);
	}

	public static MSBFragmentEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<MSBFragmentEntryLocalService, MSBFragmentEntryLocalService> _serviceTracker =
		ServiceTrackerFactory.open(MSBFragmentEntryLocalService.class);
}