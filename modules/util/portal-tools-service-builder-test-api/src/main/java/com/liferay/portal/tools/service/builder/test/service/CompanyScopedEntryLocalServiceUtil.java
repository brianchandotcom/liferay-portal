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
 * Provides the local service utility for CompanyScopedEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.CompanyScopedEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see CompanyScopedEntryLocalService
 * @generated
 */
public class CompanyScopedEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.CompanyScopedEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the company scoped entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompanyScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param companyScopedEntry the company scoped entry
	 * @return the company scoped entry that was added
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry
			addCompanyScopedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					CompanyScopedEntry companyScopedEntry) {

		return getService().addCompanyScopedEntry(companyScopedEntry);
	}

	/**
	 * Creates a new company scoped entry with the primary key. Does not add the company scoped entry to the database.
	 *
	 * @param CompanyScopedEntryId the primary key for the new company scoped entry
	 * @return the new company scoped entry
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry
			createCompanyScopedEntry(long CompanyScopedEntryId) {

		return getService().createCompanyScopedEntry(CompanyScopedEntryId);
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
	 * Deletes the company scoped entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompanyScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param companyScopedEntry the company scoped entry
	 * @return the company scoped entry that was removed
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry
			deleteCompanyScopedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					CompanyScopedEntry companyScopedEntry) {

		return getService().deleteCompanyScopedEntry(companyScopedEntry);
	}

	/**
	 * Deletes the company scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompanyScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param CompanyScopedEntryId the primary key of the company scoped entry
	 * @return the company scoped entry that was removed
	 * @throws PortalException if a company scoped entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry
				deleteCompanyScopedEntry(long CompanyScopedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCompanyScopedEntry(CompanyScopedEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CompanyScopedEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CompanyScopedEntryModelImpl</code>.
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
		com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry
			fetchCompanyScopedEntry(long CompanyScopedEntryId) {

		return getService().fetchCompanyScopedEntry(CompanyScopedEntryId);
	}

	/**
	 * Returns the company scoped entry with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the company scoped entry's external reference code
	 * @return the matching company scoped entry, or <code>null</code> if a matching company scoped entry could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry
			fetchCompanyScopedEntryByReferenceCode(
				long companyId, String externalReferenceCode) {

		return getService().fetchCompanyScopedEntryByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the company scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.CompanyScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of company scoped entries
	 * @param end the upper bound of the range of company scoped entries (not inclusive)
	 * @return the range of company scoped entries
	 */
	public static java.util.List
		<com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry>
			getCompanyScopedEntries(int start, int end) {

		return getService().getCompanyScopedEntries(start, end);
	}

	/**
	 * Returns the number of company scoped entries.
	 *
	 * @return the number of company scoped entries
	 */
	public static int getCompanyScopedEntriesCount() {
		return getService().getCompanyScopedEntriesCount();
	}

	/**
	 * Returns the company scoped entry with the primary key.
	 *
	 * @param CompanyScopedEntryId the primary key of the company scoped entry
	 * @return the company scoped entry
	 * @throws PortalException if a company scoped entry with the primary key could not be found
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry
				getCompanyScopedEntry(long CompanyScopedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCompanyScopedEntry(CompanyScopedEntryId);
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
	 * Updates the company scoped entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CompanyScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param companyScopedEntry the company scoped entry
	 * @return the company scoped entry that was updated
	 */
	public static
		com.liferay.portal.tools.service.builder.test.model.CompanyScopedEntry
			updateCompanyScopedEntry(
				com.liferay.portal.tools.service.builder.test.model.
					CompanyScopedEntry companyScopedEntry) {

		return getService().updateCompanyScopedEntry(companyScopedEntry);
	}

	public static CompanyScopedEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CompanyScopedEntryLocalService, CompanyScopedEntryLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CompanyScopedEntryLocalService.class);

		ServiceTracker
			<CompanyScopedEntryLocalService, CompanyScopedEntryLocalService>
				serviceTracker =
					new ServiceTracker
						<CompanyScopedEntryLocalService,
						 CompanyScopedEntryLocalService>(
							 bundle.getBundleContext(),
							 CompanyScopedEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}