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

package com.liferay.html.preview.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for HtmlPreviewEntry. This utility wraps
 * <code>com.liferay.html.preview.service.impl.HtmlPreviewEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntryLocalService
 * @generated
 */
public class HtmlPreviewEntryLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.html.preview.service.impl.HtmlPreviewEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the html preview entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntry the html preview entry
	 * @return the html preview entry that was added
	 */
	public static com.liferay.html.preview.model.HtmlPreviewEntry
		addHtmlPreviewEntry(
			com.liferay.html.preview.model.HtmlPreviewEntry htmlPreviewEntry) {

		return getService().addHtmlPreviewEntry(htmlPreviewEntry);
	}

	public static com.liferay.html.preview.model.HtmlPreviewEntry
			addHtmlPreviewEntry(
				long userId, long groupId, long classNameId, long classPK,
				String content, String mimeType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addHtmlPreviewEntry(
			userId, groupId, classNameId, classPK, content, mimeType,
			serviceContext);
	}

	/**
	 * Creates a new html preview entry with the primary key. Does not add the html preview entry to the database.
	 *
	 * @param htmlPreviewEntryId the primary key for the new html preview entry
	 * @return the new html preview entry
	 */
	public static com.liferay.html.preview.model.HtmlPreviewEntry
		createHtmlPreviewEntry(long htmlPreviewEntryId) {

		return getService().createHtmlPreviewEntry(htmlPreviewEntryId);
	}

	/**
	 * Deletes the html preview entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntry the html preview entry
	 * @return the html preview entry that was removed
	 * @throws PortalException
	 */
	public static com.liferay.html.preview.model.HtmlPreviewEntry
			deleteHtmlPreviewEntry(
				com.liferay.html.preview.model.HtmlPreviewEntry
					htmlPreviewEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteHtmlPreviewEntry(htmlPreviewEntry);
	}

	/**
	 * Deletes the html preview entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry that was removed
	 * @throws PortalException if a html preview entry with the primary key could not be found
	 */
	public static com.liferay.html.preview.model.HtmlPreviewEntry
			deleteHtmlPreviewEntry(long htmlPreviewEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteHtmlPreviewEntry(htmlPreviewEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.html.preview.model.impl.HtmlPreviewEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.html.preview.model.impl.HtmlPreviewEntryModelImpl</code>.
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

	public static com.liferay.html.preview.model.HtmlPreviewEntry
		fetchHtmlPreviewEntry(long htmlPreviewEntryId) {

		return getService().fetchHtmlPreviewEntry(htmlPreviewEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the html preview entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.html.preview.model.impl.HtmlPreviewEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of html preview entries
	 * @param end the upper bound of the range of html preview entries (not inclusive)
	 * @return the range of html preview entries
	 */
	public static java.util.List
		<com.liferay.html.preview.model.HtmlPreviewEntry> getHtmlPreviewEntries(
			int start, int end) {

		return getService().getHtmlPreviewEntries(start, end);
	}

	/**
	 * Returns the number of html preview entries.
	 *
	 * @return the number of html preview entries
	 */
	public static int getHtmlPreviewEntriesCount() {
		return getService().getHtmlPreviewEntriesCount();
	}

	/**
	 * Returns the html preview entry with the primary key.
	 *
	 * @param htmlPreviewEntryId the primary key of the html preview entry
	 * @return the html preview entry
	 * @throws PortalException if a html preview entry with the primary key could not be found
	 */
	public static com.liferay.html.preview.model.HtmlPreviewEntry
			getHtmlPreviewEntry(long htmlPreviewEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getHtmlPreviewEntry(htmlPreviewEntryId);
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
	 * Updates the html preview entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param htmlPreviewEntry the html preview entry
	 * @return the html preview entry that was updated
	 */
	public static com.liferay.html.preview.model.HtmlPreviewEntry
		updateHtmlPreviewEntry(
			com.liferay.html.preview.model.HtmlPreviewEntry htmlPreviewEntry) {

		return getService().updateHtmlPreviewEntry(htmlPreviewEntry);
	}

	public static com.liferay.html.preview.model.HtmlPreviewEntry
			updateHtmlPreviewEntry(
				long htmlPreviewEntryId, String content, String mimeType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateHtmlPreviewEntry(
			htmlPreviewEntryId, content, mimeType, serviceContext);
	}

	public static HtmlPreviewEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<HtmlPreviewEntryLocalService, HtmlPreviewEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			HtmlPreviewEntryLocalService.class);

		ServiceTracker
			<HtmlPreviewEntryLocalService, HtmlPreviewEntryLocalService>
				serviceTracker =
					new ServiceTracker
						<HtmlPreviewEntryLocalService,
						 HtmlPreviewEntryLocalService>(
							 bundle.getBundleContext(),
							 HtmlPreviewEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}