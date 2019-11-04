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

package com.liferay.view.count.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ViewCountEntry. This utility wraps
 * <code>com.liferay.view.count.service.impl.ViewCountEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Preston Crary
 * @see ViewCountEntryLocalService
 * @generated
 */
public class ViewCountEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.view.count.service.impl.ViewCountEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the view count entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntry the view count entry
	 * @return the view count entry that was added
	 */
	public static com.liferay.view.count.model.ViewCountEntry addViewCountEntry(
		com.liferay.view.count.model.ViewCountEntry viewCountEntry) {

		return getService().addViewCountEntry(viewCountEntry);
	}

	/**
	 * Creates a new view count entry with the primary key. Does not add the view count entry to the database.
	 *
	 * @param viewCountEntryPK the primary key for the new view count entry
	 * @return the new view count entry
	 */
	public static com.liferay.view.count.model.ViewCountEntry
		createViewCountEntry(
			com.liferay.view.count.service.persistence.ViewCountEntryPK
				viewCountEntryPK) {

		return getService().createViewCountEntry(viewCountEntryPK);
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
	 * Deletes the view count entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntry the view count entry
	 * @return the view count entry that was removed
	 */
	public static com.liferay.view.count.model.ViewCountEntry
		deleteViewCountEntry(
			com.liferay.view.count.model.ViewCountEntry viewCountEntry) {

		return getService().deleteViewCountEntry(viewCountEntry);
	}

	/**
	 * Deletes the view count entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry that was removed
	 * @throws PortalException if a view count entry with the primary key could not be found
	 */
	public static com.liferay.view.count.model.ViewCountEntry
			deleteViewCountEntry(
				com.liferay.view.count.service.persistence.ViewCountEntryPK
					viewCountEntryPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteViewCountEntry(viewCountEntryPK);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.view.count.model.impl.ViewCountEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.view.count.model.impl.ViewCountEntryModelImpl</code>.
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

	public static com.liferay.view.count.model.ViewCountEntry
		fetchViewCountEntry(
			com.liferay.view.count.service.persistence.ViewCountEntryPK
				viewCountEntryPK) {

		return getService().fetchViewCountEntry(viewCountEntryPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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

	public static long getViewCount(
		long companyId, long classNameId, long classPK) {

		return getService().getViewCount(companyId, classNameId, classPK);
	}

	/**
	 * Returns a range of all the view count entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.view.count.model.impl.ViewCountEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of view count entries
	 * @param end the upper bound of the range of view count entries (not inclusive)
	 * @return the range of view count entries
	 */
	public static java.util.List<com.liferay.view.count.model.ViewCountEntry>
		getViewCountEntries(int start, int end) {

		return getService().getViewCountEntries(start, end);
	}

	/**
	 * Returns the number of view count entries.
	 *
	 * @return the number of view count entries
	 */
	public static int getViewCountEntriesCount() {
		return getService().getViewCountEntriesCount();
	}

	/**
	 * Returns the view count entry with the primary key.
	 *
	 * @param viewCountEntryPK the primary key of the view count entry
	 * @return the view count entry
	 * @throws PortalException if a view count entry with the primary key could not be found
	 */
	public static com.liferay.view.count.model.ViewCountEntry getViewCountEntry(
			com.liferay.view.count.service.persistence.ViewCountEntryPK
				viewCountEntryPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getViewCountEntry(viewCountEntryPK);
	}

	public static void incrementViewCount(
		long companyId, long classNameId, long classPK, int increment) {

		getService().incrementViewCount(
			companyId, classNameId, classPK, increment);
	}

	public static void removeViewCount(
		long companyId, long classNameId, long classPK) {

		getService().removeViewCount(companyId, classNameId, classPK);
	}

	/**
	 * Updates the view count entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param viewCountEntry the view count entry
	 * @return the view count entry that was updated
	 */
	public static com.liferay.view.count.model.ViewCountEntry
		updateViewCountEntry(
			com.liferay.view.count.model.ViewCountEntry viewCountEntry) {

		return getService().updateViewCountEntry(viewCountEntry);
	}

	public static ViewCountEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ViewCountEntryLocalService, ViewCountEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ViewCountEntryLocalService.class);

		ServiceTracker<ViewCountEntryLocalService, ViewCountEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<ViewCountEntryLocalService, ViewCountEntryLocalService>(
						bundle.getBundleContext(),
						ViewCountEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}