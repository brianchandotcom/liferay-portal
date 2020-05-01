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

package com.liferay.portal.tools.service.builder.test.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for LocalizedEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.LocalizedEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntryLocalService
 * @generated
 */
public class LocalizedEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.LocalizedEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the localized entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param localizedEntry the localized entry
	 * @return the localized entry that was added
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LocalizedEntry
			addLocalizedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					LocalizedEntry localizedEntry) {

		return getService().addLocalizedEntry(localizedEntry);
	}

	/**
	 * Creates a new localized entry with the primary key. Does not add the localized entry to the database.
	 *
	 * @param localizedEntryId the primary key for the new localized entry
	 * @return the new localized entry
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LocalizedEntry
			createLocalizedEntry(long localizedEntryId) {

		return getService().createLocalizedEntry(localizedEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the localized entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param localizedEntry the localized entry
	 * @return the localized entry that was removed
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LocalizedEntry
			deleteLocalizedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					LocalizedEntry localizedEntry) {

		return getService().deleteLocalizedEntry(localizedEntry);
	}

	/**
	 * Deletes the localized entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param localizedEntryId the primary key of the localized entry
	 * @return the localized entry that was removed
	 * @throws PortalException if a localized entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LocalizedEntry
				deleteLocalizedEntry(long localizedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteLocalizedEntry(localizedEntryId);
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

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LocalizedEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LocalizedEntryModelImpl</code>.
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
		com.liferay.portal.tools.service.builder.test.model.LocalizedEntry
			fetchLocalizedEntry(long localizedEntryId) {

		return getService().fetchLocalizedEntry(localizedEntryId);
	}

	public static com.liferay.portal.tools.service.builder.test.model.
		LocalizedEntryLocalization fetchLocalizedEntryLocalization(
			long localizedEntryId, String languageId) {

		return getService().fetchLocalizedEntryLocalization(
			localizedEntryId, languageId);
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
	 * Returns a range of all the localized entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.LocalizedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of localized entries
	 * @param end the upper bound of the range of localized entries (not inclusive)
	 * @return the range of localized entries
	 */
	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.LocalizedEntry>
			getLocalizedEntries(int start, int end) {

		return getService().getLocalizedEntries(start, end);
	}

	/**
	 * Returns the number of localized entries.
	 *
	 * @return the number of localized entries
	 */
	public static int getLocalizedEntriesCount() {
		return getService().getLocalizedEntriesCount();
	}

	/**
	 * Returns the localized entry with the primary key.
	 *
	 * @param localizedEntryId the primary key of the localized entry
	 * @return the localized entry
	 * @throws PortalException if a localized entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LocalizedEntry
				getLocalizedEntry(long localizedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLocalizedEntry(localizedEntryId);
	}

	public static com.liferay.portal.tools.service.builder.test.model.
		LocalizedEntryLocalization getLocalizedEntryLocalization(
				long localizedEntryId, String languageId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getLocalizedEntryLocalization(
			localizedEntryId, languageId);
	}

	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			LocalizedEntryLocalization> getLocalizedEntryLocalizations(
				long localizedEntryId) {

		return getService().getLocalizedEntryLocalizations(localizedEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the localized entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param localizedEntry the localized entry
	 * @return the localized entry that was updated
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.LocalizedEntry
			updateLocalizedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					LocalizedEntry localizedEntry) {

		return getService().updateLocalizedEntry(localizedEntry);
	}

	public static com.liferay.portal.tools.service.builder.test.model.
		LocalizedEntryLocalization updateLocalizedEntryLocalization(
				com.liferay.portal.tools.service.builder.test.model.
					LocalizedEntry localizedEntry,
				String languageId, String title, String content)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLocalizedEntryLocalization(
			localizedEntry, languageId, title, content);
	}

	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.
			LocalizedEntryLocalization> updateLocalizedEntryLocalizations(
					com.liferay.portal.tools.service.builder.test.model.
						LocalizedEntry localizedEntry,
					java.util.Map<String, String> titleMap,
					java.util.Map<String, String> contentMap)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateLocalizedEntryLocalizations(
			localizedEntry, titleMap, contentMap);
	}

	public static LocalizedEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<LocalizedEntryLocalService, LocalizedEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			LocalizedEntryLocalService.class);

		ServiceTracker<LocalizedEntryLocalService, LocalizedEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<LocalizedEntryLocalService, LocalizedEntryLocalService>(
						bundle.getBundleContext(),
						LocalizedEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}