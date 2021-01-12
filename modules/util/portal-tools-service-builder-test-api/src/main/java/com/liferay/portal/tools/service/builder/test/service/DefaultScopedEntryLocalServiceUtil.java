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
 * Provides the local service utility for DefaultScopedEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.DefaultScopedEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DefaultScopedEntryLocalService
 * @generated
 */
public class DefaultScopedEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.DefaultScopedEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the default scoped entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefaultScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param defaultScopedEntry the default scoped entry
	 * @return the default scoped entry that was added
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			addDefaultScopedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DefaultScopedEntry defaultScopedEntry) {

		return getService().addDefaultScopedEntry(defaultScopedEntry);
	}

	/**
	 * Creates a new default scoped entry with the primary key. Does not add the default scoped entry to the database.
	 *
	 * @param DefaultScopedEntryId the primary key for the new default scoped entry
	 * @return the new default scoped entry
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			createDefaultScopedEntry(long DefaultScopedEntryId) {

		return getService().createDefaultScopedEntry(DefaultScopedEntryId);
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
	 * Deletes the default scoped entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefaultScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param defaultScopedEntry the default scoped entry
	 * @return the default scoped entry that was removed
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			deleteDefaultScopedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DefaultScopedEntry defaultScopedEntry) {

		return getService().deleteDefaultScopedEntry(defaultScopedEntry);
	}

	/**
	 * Deletes the default scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefaultScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry that was removed
	 * @throws PortalException if a default scoped entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
				deleteDefaultScopedEntry(long DefaultScopedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteDefaultScopedEntry(DefaultScopedEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefaultScopedEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefaultScopedEntryModelImpl</code>.
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
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			fetchDefaultScopedEntry(long DefaultScopedEntryId) {

		return getService().fetchDefaultScopedEntry(DefaultScopedEntryId);
	}

	/**
	 * Returns the default scoped entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the default scoped entry's external reference code
	 * @return the matching default scoped entry, or <code>null</code> if a matching default scoped entry could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			fetchDefaultScopedEntryByReferenceCode(
				long companyId, String externalReferenceCode) {

		return getService().fetchDefaultScopedEntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the default scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefaultScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of default scoped entries
	 * @param end the upper bound of the range of default scoped entries (not inclusive)
	 * @return the range of default scoped entries
	 */
	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry>
			getDefaultScopedEntries(int start, int end) {

		return getService().getDefaultScopedEntries(start, end);
	}

	/**
	 * Returns the number of default scoped entries.
	 *
	 * @return the number of default scoped entries
	 */
	public static int getDefaultScopedEntriesCount() {
		return getService().getDefaultScopedEntriesCount();
	}

	/**
	 * Returns the default scoped entry with the primary key.
	 *
	 * @param DefaultScopedEntryId the primary key of the default scoped entry
	 * @return the default scoped entry
	 * @throws PortalException if a default scoped entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
				getDefaultScopedEntry(long DefaultScopedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getDefaultScopedEntry(DefaultScopedEntryId);
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

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the default scoped entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefaultScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param defaultScopedEntry the default scoped entry
	 * @return the default scoped entry that was updated
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.DefaultScopedEntry
			updateDefaultScopedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					DefaultScopedEntry defaultScopedEntry) {

		return getService().updateDefaultScopedEntry(defaultScopedEntry);
	}

	public static DefaultScopedEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<DefaultScopedEntryLocalService, DefaultScopedEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DefaultScopedEntryLocalService.class);

		ServiceTracker
			<DefaultScopedEntryLocalService, DefaultScopedEntryLocalService>
				serviceTracker =
					new ServiceTracker
						<DefaultScopedEntryLocalService,
						 DefaultScopedEntryLocalService>(
							 bundle.getBundleContext(),
							 DefaultScopedEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}