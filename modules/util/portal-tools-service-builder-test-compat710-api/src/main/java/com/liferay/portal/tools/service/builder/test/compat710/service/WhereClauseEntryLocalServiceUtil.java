/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.service;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat710.model.WhereClauseEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for WhereClauseEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.compat710.service.impl.WhereClauseEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see WhereClauseEntryLocalService
 * @generated
 */
public class WhereClauseEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.compat710.service.impl.WhereClauseEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the where clause entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param whereClauseEntry the where clause entry
	 * @return the where clause entry that was added
	 */
	public static WhereClauseEntry addWhereClauseEntry(
		WhereClauseEntry whereClauseEntry) {

		return getService().addWhereClauseEntry(whereClauseEntry);
	}

	/**
	 * Creates a new where clause entry with the primary key. Does not add the where clause entry to the database.
	 *
	 * @param whereClauseEntryId the primary key for the new where clause entry
	 * @return the new where clause entry
	 */
	public static WhereClauseEntry createWhereClauseEntry(
		long whereClauseEntryId) {

		return getService().createWhereClauseEntry(whereClauseEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the where clause entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry that was removed
	 * @throws PortalException if a where clause entry with the primary key could not be found
	 */
	public static WhereClauseEntry deleteWhereClauseEntry(
			long whereClauseEntryId)
		throws PortalException {

		return getService().deleteWhereClauseEntry(whereClauseEntryId);
	}

	/**
	 * Deletes the where clause entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param whereClauseEntry the where clause entry
	 * @return the where clause entry that was removed
	 */
	public static WhereClauseEntry deleteWhereClauseEntry(
		WhereClauseEntry whereClauseEntry) {

		return getService().deleteWhereClauseEntry(whereClauseEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.WhereClauseEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.WhereClauseEntryModelImpl</code>.
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

	public static WhereClauseEntry fetchWhereClauseEntry(
		long whereClauseEntryId) {

		return getService().fetchWhereClauseEntry(whereClauseEntryId);
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

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the where clause entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.WhereClauseEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of where clause entries
	 * @param end the upper bound of the range of where clause entries (not inclusive)
	 * @return the range of where clause entries
	 */
	public static List<WhereClauseEntry> getWhereClauseEntries(
		int start, int end) {

		return getService().getWhereClauseEntries(start, end);
	}

	/**
	 * Returns the number of where clause entries.
	 *
	 * @return the number of where clause entries
	 */
	public static int getWhereClauseEntriesCount() {
		return getService().getWhereClauseEntriesCount();
	}

	/**
	 * Returns the where clause entry with the primary key.
	 *
	 * @param whereClauseEntryId the primary key of the where clause entry
	 * @return the where clause entry
	 * @throws PortalException if a where clause entry with the primary key could not be found
	 */
	public static WhereClauseEntry getWhereClauseEntry(long whereClauseEntryId)
		throws PortalException {

		return getService().getWhereClauseEntry(whereClauseEntryId);
	}

	/**
	 * Updates the where clause entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect WhereClauseEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param whereClauseEntry the where clause entry
	 * @return the where clause entry that was updated
	 */
	public static WhereClauseEntry updateWhereClauseEntry(
		WhereClauseEntry whereClauseEntry) {

		return getService().updateWhereClauseEntry(whereClauseEntry);
	}

	public static WhereClauseEntryLocalService getService() {
		return _service;
	}

	public static void setService(WhereClauseEntryLocalService service) {
		_service = service;
	}

	private static volatile WhereClauseEntryLocalService _service;

}
// LIFERAY-SERVICE-BUILDER-HASH:158662577