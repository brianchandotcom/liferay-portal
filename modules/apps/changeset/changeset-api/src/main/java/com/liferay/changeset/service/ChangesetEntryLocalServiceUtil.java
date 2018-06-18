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

package com.liferay.changeset.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ChangesetEntry. This utility wraps
 * {@link com.liferay.changeset.service.impl.ChangesetEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetEntryLocalService
 * @see com.liferay.changeset.service.base.ChangesetEntryLocalServiceBaseImpl
 * @see com.liferay.changeset.service.impl.ChangesetEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class ChangesetEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.changeset.service.impl.ChangesetEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the changeset entry to the database. Also notifies the appropriate model listeners.
	*
	* @param changesetEntry the changeset entry
	* @return the changeset entry that was added
	*/
	public static com.liferay.changeset.model.ChangesetEntry addChangesetEntry(
		com.liferay.changeset.model.ChangesetEntry changesetEntry) {
		return getService().addChangesetEntry(changesetEntry);
	}

	public static com.liferay.changeset.model.ChangesetEntry addChangesetEntry(
		long userId, long changesetCollectionId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addChangesetEntry(userId, changesetCollectionId,
			classNameId, classPK);
	}

	/**
	* Creates a new changeset entry with the primary key. Does not add the changeset entry to the database.
	*
	* @param changesetEntryId the primary key for the new changeset entry
	* @return the new changeset entry
	*/
	public static com.liferay.changeset.model.ChangesetEntry createChangesetEntry(
		long changesetEntryId) {
		return getService().createChangesetEntry(changesetEntryId);
	}

	public static void deleteChangesetEntries(long changesetCollectionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteChangesetEntries(changesetCollectionId);
	}

	/**
	* Deletes the changeset entry from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetEntry the changeset entry
	* @return the changeset entry that was removed
	*/
	public static com.liferay.changeset.model.ChangesetEntry deleteChangesetEntry(
		com.liferay.changeset.model.ChangesetEntry changesetEntry) {
		return getService().deleteChangesetEntry(changesetEntry);
	}

	/**
	* Deletes the changeset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetEntryId the primary key of the changeset entry
	* @return the changeset entry that was removed
	* @throws PortalException if a changeset entry with the primary key could not be found
	*/
	public static com.liferay.changeset.model.ChangesetEntry deleteChangesetEntry(
		long changesetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteChangesetEntry(changesetEntryId);
	}

	public static void deleteEntry(long changesetId, long classNameId,
		long classPK) {
		getService().deleteEntry(changesetId, classNameId, classPK);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.changeset.model.ChangesetEntry fetchChangesetEntry(
		long changesetEntryId) {
		return getService().fetchChangesetEntry(changesetEntryId);
	}

	public static com.liferay.changeset.model.ChangesetEntry fetchChangesetEntry(
		long changesetCollectionId, long classNameId, long classPK) {
		return getService()
				   .fetchChangesetEntry(changesetCollectionId, classNameId,
			classPK);
	}

	public static com.liferay.changeset.model.ChangesetEntry fetchOrAddChangesetEntry(
		long changesetCollectionId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchOrAddChangesetEntry(changesetCollectionId,
			classNameId, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the changeset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changeset entries
	* @param end the upper bound of the range of changeset entries (not inclusive)
	* @return the range of changeset entries
	*/
	public static java.util.List<com.liferay.changeset.model.ChangesetEntry> getChangesetEntries(
		int start, int end) {
		return getService().getChangesetEntries(start, end);
	}

	/**
	* Returns the number of changeset entries.
	*
	* @return the number of changeset entries
	*/
	public static int getChangesetEntriesCount() {
		return getService().getChangesetEntriesCount();
	}

	public static long getChangesetEntriesCount(long changesetCollectionId) {
		return getService().getChangesetEntriesCount(changesetCollectionId);
	}

	public static long getChangesetEntriesCount(long changesetCollectionId,
		long classNameId) {
		return getService()
				   .getChangesetEntriesCount(changesetCollectionId, classNameId);
	}

	public static long getChangesetEntriesCount(long changesetCollectionId,
		long classNameId, java.util.Set<Long> classPKs) {
		return getService()
				   .getChangesetEntriesCount(changesetCollectionId,
			classNameId, classPKs);
	}

	/**
	* Returns the changeset entry with the primary key.
	*
	* @param changesetEntryId the primary key of the changeset entry
	* @return the changeset entry
	* @throws PortalException if a changeset entry with the primary key could not be found
	*/
	public static com.liferay.changeset.model.ChangesetEntry getChangesetEntry(
		long changesetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getChangesetEntry(changesetEntryId);
	}

	public static com.liferay.changeset.model.ChangesetEntry getChangesetEntry(
		long changesetCollectionId, long classNameId, long classPK)
		throws com.liferay.changeset.exception.NoSuchEntryException {
		return getService()
				   .getChangesetEntry(changesetCollectionId, classNameId,
			classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the changeset entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changesetEntry the changeset entry
	* @return the changeset entry that was updated
	*/
	public static com.liferay.changeset.model.ChangesetEntry updateChangesetEntry(
		com.liferay.changeset.model.ChangesetEntry changesetEntry) {
		return getService().updateChangesetEntry(changesetEntry);
	}

	public static ChangesetEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ChangesetEntryLocalService, ChangesetEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ChangesetEntryLocalService.class);

		ServiceTracker<ChangesetEntryLocalService, ChangesetEntryLocalService> serviceTracker =
			new ServiceTracker<ChangesetEntryLocalService, ChangesetEntryLocalService>(bundle.getBundleContext(),
				ChangesetEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}