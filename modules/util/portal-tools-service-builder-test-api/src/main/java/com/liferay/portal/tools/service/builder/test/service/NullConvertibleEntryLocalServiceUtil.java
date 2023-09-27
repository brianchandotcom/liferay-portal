/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for NullConvertibleEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.NullConvertibleEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see NullConvertibleEntryLocalService
 * @generated
 */
public class NullConvertibleEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.NullConvertibleEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the null convertible entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NullConvertibleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param nullConvertibleEntry the null convertible entry
	 * @return the null convertible entry that was added
	 */
	public static NullConvertibleEntry addNullConvertibleEntry(
		NullConvertibleEntry nullConvertibleEntry) {

		return getService().addNullConvertibleEntry(nullConvertibleEntry);
	}

	public static NullConvertibleEntry addNullConvertibleEntry(String name) {
		return getService().addNullConvertibleEntry(name);
	}

	/**
	 * Creates a new null convertible entry with the primary key. Does not add the null convertible entry to the database.
	 *
	 * @param nullConvertibleEntryId the primary key for the new null convertible entry
	 * @return the new null convertible entry
	 */
	public static NullConvertibleEntry createNullConvertibleEntry(
		long nullConvertibleEntryId) {

		return getService().createNullConvertibleEntry(nullConvertibleEntryId);
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
	 * Deletes the null convertible entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NullConvertibleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry that was removed
	 * @throws PortalException if a null convertible entry with the primary key could not be found
	 */
	public static NullConvertibleEntry deleteNullConvertibleEntry(
			long nullConvertibleEntryId)
		throws PortalException {

		return getService().deleteNullConvertibleEntry(nullConvertibleEntryId);
	}

	/**
	 * Deletes the null convertible entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NullConvertibleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param nullConvertibleEntry the null convertible entry
	 * @return the null convertible entry that was removed
	 */
	public static NullConvertibleEntry deleteNullConvertibleEntry(
		NullConvertibleEntry nullConvertibleEntry) {

		return getService().deleteNullConvertibleEntry(nullConvertibleEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.NullConvertibleEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.NullConvertibleEntryModelImpl</code>.
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

	public static NullConvertibleEntry fetchNullConvertibleEntry(
		long nullConvertibleEntryId) {

		return getService().fetchNullConvertibleEntry(nullConvertibleEntryId);
	}

	public static NullConvertibleEntry fetchNullConvertibleEntry(String name) {
		return getService().fetchNullConvertibleEntry(name);
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
	 * Returns a range of all the null convertible entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.NullConvertibleEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of null convertible entries
	 * @param end the upper bound of the range of null convertible entries (not inclusive)
	 * @return the range of null convertible entries
	 */
	public static List<NullConvertibleEntry> getNullConvertibleEntries(
		int start, int end) {

		return getService().getNullConvertibleEntries(start, end);
	}

	public static int getNullConvertibleEntries(String name) {
		return getService().getNullConvertibleEntries(name);
	}

	/**
	 * Returns the number of null convertible entries.
	 *
	 * @return the number of null convertible entries
	 */
	public static int getNullConvertibleEntriesCount() {
		return getService().getNullConvertibleEntriesCount();
	}

	/**
	 * Returns the null convertible entry with the primary key.
	 *
	 * @param nullConvertibleEntryId the primary key of the null convertible entry
	 * @return the null convertible entry
	 * @throws PortalException if a null convertible entry with the primary key could not be found
	 */
	public static NullConvertibleEntry getNullConvertibleEntry(
			long nullConvertibleEntryId)
		throws PortalException {

		return getService().getNullConvertibleEntry(nullConvertibleEntryId);
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
	 * Updates the null convertible entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect NullConvertibleEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param nullConvertibleEntry the null convertible entry
	 * @return the null convertible entry that was updated
	 */
	public static NullConvertibleEntry updateNullConvertibleEntry(
		NullConvertibleEntry nullConvertibleEntry) {

		return getService().updateNullConvertibleEntry(nullConvertibleEntry);
	}

	public static NullConvertibleEntryLocalService getService() {
		return _service;
	}

	public static void setService(NullConvertibleEntryLocalService service) {
		_service = service;
	}

	private static volatile NullConvertibleEntryLocalService _service;

}