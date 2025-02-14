/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.DefinedDefaultOrderEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for DefinedDefaultOrderEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.DefinedDefaultOrderEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DefinedDefaultOrderEntryLocalService
 * @generated
 */
public class DefinedDefaultOrderEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.DefinedDefaultOrderEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the defined default order entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param definedDefaultOrderEntry the defined default order entry
	 * @return the defined default order entry that was added
	 */
	public static DefinedDefaultOrderEntry addDefinedDefaultOrderEntry(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		return getService().addDefinedDefaultOrderEntry(
			definedDefaultOrderEntry);
	}

	/**
	 * Creates a new defined default order entry with the primary key. Does not add the defined default order entry to the database.
	 *
	 * @param definedDefaultOrderEntryId the primary key for the new defined default order entry
	 * @return the new defined default order entry
	 */
	public static DefinedDefaultOrderEntry createDefinedDefaultOrderEntry(
		long definedDefaultOrderEntryId) {

		return getService().createDefinedDefaultOrderEntry(
			definedDefaultOrderEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the defined default order entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param definedDefaultOrderEntry the defined default order entry
	 * @return the defined default order entry that was removed
	 */
	public static DefinedDefaultOrderEntry deleteDefinedDefaultOrderEntry(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		return getService().deleteDefinedDefaultOrderEntry(
			definedDefaultOrderEntry);
	}

	/**
	 * Deletes the defined default order entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry that was removed
	 * @throws PortalException if a defined default order entry with the primary key could not be found
	 */
	public static DefinedDefaultOrderEntry deleteDefinedDefaultOrderEntry(
			long definedDefaultOrderEntryId)
		throws PortalException {

		return getService().deleteDefinedDefaultOrderEntry(
			definedDefaultOrderEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static DefinedDefaultOrderEntry fetchDefinedDefaultOrderEntry(
		long definedDefaultOrderEntryId) {

		return getService().fetchDefinedDefaultOrderEntry(
			definedDefaultOrderEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the defined default order entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.DefinedDefaultOrderEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of defined default order entries
	 * @param end the upper bound of the range of defined default order entries (not inclusive)
	 * @return the range of defined default order entries
	 */
	public static List<DefinedDefaultOrderEntry> getDefinedDefaultOrderEntries(
		int start, int end) {

		return getService().getDefinedDefaultOrderEntries(start, end);
	}

	/**
	 * Returns the number of defined default order entries.
	 *
	 * @return the number of defined default order entries
	 */
	public static int getDefinedDefaultOrderEntriesCount() {
		return getService().getDefinedDefaultOrderEntriesCount();
	}

	/**
	 * Returns the defined default order entry with the primary key.
	 *
	 * @param definedDefaultOrderEntryId the primary key of the defined default order entry
	 * @return the defined default order entry
	 * @throws PortalException if a defined default order entry with the primary key could not be found
	 */
	public static DefinedDefaultOrderEntry getDefinedDefaultOrderEntry(
			long definedDefaultOrderEntryId)
		throws PortalException {

		return getService().getDefinedDefaultOrderEntry(
			definedDefaultOrderEntryId);
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
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the defined default order entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect DefinedDefaultOrderEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param definedDefaultOrderEntry the defined default order entry
	 * @return the defined default order entry that was updated
	 */
	public static DefinedDefaultOrderEntry updateDefinedDefaultOrderEntry(
		DefinedDefaultOrderEntry definedDefaultOrderEntry) {

		return getService().updateDefinedDefaultOrderEntry(
			definedDefaultOrderEntry);
	}

	public static DefinedDefaultOrderEntryLocalService getService() {
		return _service;
	}

	public static void setService(
		DefinedDefaultOrderEntryLocalService service) {

		_service = service;
	}

	private static volatile DefinedDefaultOrderEntryLocalService _service;

}