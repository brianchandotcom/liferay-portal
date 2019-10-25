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

package com.liferay.change.tracking.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CTEntry. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryLocalService
 * @generated
 */
public class CTEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the ct entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntry the ct entry
	 * @return the ct entry that was added
	 */
	public static com.liferay.change.tracking.model.CTEntry addCTEntry(
		com.liferay.change.tracking.model.CTEntry ctEntry) {

		return getService().addCTEntry(ctEntry);
	}

	public static com.liferay.change.tracking.model.CTEntry addCTEntry(
			long ctCollectionId, long modelClassNameId,
			com.liferay.portal.kernel.model.change.tracking.CTModel<?> ctModel,
			long userId, int changeType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCTEntry(
			ctCollectionId, modelClassNameId, ctModel, userId, changeType);
	}

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	public static com.liferay.change.tracking.model.CTEntry createCTEntry(
		long ctEntryId) {

		return getService().createCTEntry(ctEntryId);
	}

	/**
	 * Deletes the ct entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntry the ct entry
	 * @return the ct entry that was removed
	 */
	public static com.liferay.change.tracking.model.CTEntry deleteCTEntry(
		com.liferay.change.tracking.model.CTEntry ctEntry) {

		return getService().deleteCTEntry(ctEntry);
	}

	/**
	 * Deletes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws PortalException if a ct entry with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTEntry deleteCTEntry(
			long ctEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCTEntry(ctEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryModelImpl</code>.
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

	public static com.liferay.change.tracking.model.CTEntry fetchCTEntry(
		long ctEntryId) {

		return getService().fetchCTEntry(ctEntryId);
	}

	public static com.liferay.change.tracking.model.CTEntry fetchCTEntry(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		return getService().fetchCTEntry(
			ctCollectionId, modelClassNameId, modelClassPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.change.tracking.model.CTEntry>
		getCTCollectionCTEntries(long ctCollectionId) {

		return getService().getCTCollectionCTEntries(ctCollectionId);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTEntry>
		getCTCollectionCTEntries(
			long ctCollectionId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.change.tracking.model.CTEntry> orderByComparator) {

		return getService().getCTCollectionCTEntries(
			ctCollectionId, start, end, orderByComparator);
	}

	public static int getCTCollectionCTEntriesCount(long ctCollectionId) {
		return getService().getCTCollectionCTEntriesCount(ctCollectionId);
	}

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.change.tracking.model.impl.CTEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	public static java.util.List<com.liferay.change.tracking.model.CTEntry>
		getCTEntries(int start, int end) {

		return getService().getCTEntries(start, end);
	}

	public static java.util.List<com.liferay.change.tracking.model.CTEntry>
		getCTEntries(long ctCollectionId, long modelClassNameId) {

		return getService().getCTEntries(ctCollectionId, modelClassNameId);
	}

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	public static int getCTEntriesCount() {
		return getService().getCTEntriesCount();
	}

	/**
	 * Returns the ct entry with the primary key.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws PortalException if a ct entry with the primary key could not be found
	 */
	public static com.liferay.change.tracking.model.CTEntry getCTEntry(
			long ctEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCTEntry(ctEntryId);
	}

	public static java.util.List<Long> getExclusiveModelClassPKs(
		long ctCollectionId, long modelClassNameId) {

		return getService().getExclusiveModelClassPKs(
			ctCollectionId, modelClassNameId);
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

	public static boolean hasCTEntries(
		long ctCollectionId, long modelClassNameId) {

		return getService().hasCTEntries(ctCollectionId, modelClassNameId);
	}

	/**
	 * Updates the ct entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntry the ct entry
	 * @return the ct entry that was updated
	 */
	public static com.liferay.change.tracking.model.CTEntry updateCTEntry(
		com.liferay.change.tracking.model.CTEntry ctEntry) {

		return getService().updateCTEntry(ctEntry);
	}

	public static CTEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTEntryLocalService, CTEntryLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTEntryLocalService.class);

		ServiceTracker<CTEntryLocalService, CTEntryLocalService>
			serviceTracker =
				new ServiceTracker<CTEntryLocalService, CTEntryLocalService>(
					bundle.getBundleContext(), CTEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}