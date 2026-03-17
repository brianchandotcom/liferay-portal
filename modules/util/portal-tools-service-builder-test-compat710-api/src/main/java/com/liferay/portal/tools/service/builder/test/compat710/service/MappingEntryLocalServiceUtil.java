/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.service;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat710.model.MappingEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for MappingEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.compat710.service.impl.MappingEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see MappingEntryLocalService
 * @generated
 */
public class MappingEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.compat710.service.impl.MappingEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addBasicEntryMappingEntries(
		long basicEntryId, List<MappingEntry> mappingEntries) {

		getService().addBasicEntryMappingEntries(basicEntryId, mappingEntries);
	}

	public static void addBasicEntryMappingEntries(
		long basicEntryId, long[] mappingEntryIds) {

		getService().addBasicEntryMappingEntries(basicEntryId, mappingEntryIds);
	}

	public static void addBasicEntryMappingEntry(
		long basicEntryId, long mappingEntryId) {

		getService().addBasicEntryMappingEntry(basicEntryId, mappingEntryId);
	}

	public static void addBasicEntryMappingEntry(
		long basicEntryId, MappingEntry mappingEntry) {

		getService().addBasicEntryMappingEntry(basicEntryId, mappingEntry);
	}

	/**
	 * Adds the mapping entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntry the mapping entry
	 * @return the mapping entry that was added
	 */
	public static MappingEntry addMappingEntry(MappingEntry mappingEntry) {
		return getService().addMappingEntry(mappingEntry);
	}

	public static void clearBasicEntryMappingEntries(long basicEntryId) {
		getService().clearBasicEntryMappingEntries(basicEntryId);
	}

	/**
	 * Creates a new mapping entry with the primary key. Does not add the mapping entry to the database.
	 *
	 * @param mappingEntryId the primary key for the new mapping entry
	 * @return the new mapping entry
	 */
	public static MappingEntry createMappingEntry(long mappingEntryId) {
		return getService().createMappingEntry(mappingEntryId);
	}

	public static void deleteBasicEntryMappingEntries(
		long basicEntryId, List<MappingEntry> mappingEntries) {

		getService().deleteBasicEntryMappingEntries(
			basicEntryId, mappingEntries);
	}

	public static void deleteBasicEntryMappingEntries(
		long basicEntryId, long[] mappingEntryIds) {

		getService().deleteBasicEntryMappingEntries(
			basicEntryId, mappingEntryIds);
	}

	public static void deleteBasicEntryMappingEntry(
		long basicEntryId, long mappingEntryId) {

		getService().deleteBasicEntryMappingEntry(basicEntryId, mappingEntryId);
	}

	public static void deleteBasicEntryMappingEntry(
		long basicEntryId, MappingEntry mappingEntry) {

		getService().deleteBasicEntryMappingEntry(basicEntryId, mappingEntry);
	}

	/**
	 * Deletes the mapping entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntryId the primary key of the mapping entry
	 * @return the mapping entry that was removed
	 * @throws PortalException if a mapping entry with the primary key could not be found
	 */
	public static MappingEntry deleteMappingEntry(long mappingEntryId)
		throws PortalException {

		return getService().deleteMappingEntry(mappingEntryId);
	}

	/**
	 * Deletes the mapping entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntry the mapping entry
	 * @return the mapping entry that was removed
	 */
	public static MappingEntry deleteMappingEntry(MappingEntry mappingEntry) {
		return getService().deleteMappingEntry(mappingEntry);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.MappingEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.MappingEntryModelImpl</code>.
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

	public static MappingEntry fetchMappingEntry(long mappingEntryId) {
		return getService().fetchMappingEntry(mappingEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static List<MappingEntry> getBasicEntryMappingEntries(
		long basicEntryId) {

		return getService().getBasicEntryMappingEntries(basicEntryId);
	}

	public static List<MappingEntry> getBasicEntryMappingEntries(
		long basicEntryId, int start, int end) {

		return getService().getBasicEntryMappingEntries(
			basicEntryId, start, end);
	}

	public static List<MappingEntry> getBasicEntryMappingEntries(
		long basicEntryId, int start, int end,
		OrderByComparator<MappingEntry> orderByComparator) {

		return getService().getBasicEntryMappingEntries(
			basicEntryId, start, end, orderByComparator);
	}

	public static int getBasicEntryMappingEntriesCount(long basicEntryId) {
		return getService().getBasicEntryMappingEntriesCount(basicEntryId);
	}

	/**
	 * Returns the basicEntryIds of the basic entries associated with the mapping entry.
	 *
	 * @param mappingEntryId the mappingEntryId of the mapping entry
	 * @return long[] the basicEntryIds of basic entries associated with the mapping entry
	 */
	public static long[] getBasicEntryPrimaryKeys(long mappingEntryId) {
		return getService().getBasicEntryPrimaryKeys(mappingEntryId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the mapping entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.MappingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mapping entries
	 * @param end the upper bound of the range of mapping entries (not inclusive)
	 * @return the range of mapping entries
	 */
	public static List<MappingEntry> getMappingEntries(int start, int end) {
		return getService().getMappingEntries(start, end);
	}

	/**
	 * Returns the number of mapping entries.
	 *
	 * @return the number of mapping entries
	 */
	public static int getMappingEntriesCount() {
		return getService().getMappingEntriesCount();
	}

	/**
	 * Returns the mapping entry with the primary key.
	 *
	 * @param mappingEntryId the primary key of the mapping entry
	 * @return the mapping entry
	 * @throws PortalException if a mapping entry with the primary key could not be found
	 */
	public static MappingEntry getMappingEntry(long mappingEntryId)
		throws PortalException {

		return getService().getMappingEntry(mappingEntryId);
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

	public static boolean hasBasicEntryMappingEntries(long basicEntryId) {
		return getService().hasBasicEntryMappingEntries(basicEntryId);
	}

	public static boolean hasBasicEntryMappingEntry(
		long basicEntryId, long mappingEntryId) {

		return getService().hasBasicEntryMappingEntry(
			basicEntryId, mappingEntryId);
	}

	public static void setBasicEntryMappingEntries(
		long basicEntryId, long[] mappingEntryIds) {

		getService().setBasicEntryMappingEntries(basicEntryId, mappingEntryIds);
	}

	/**
	 * Updates the mapping entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MappingEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mappingEntry the mapping entry
	 * @return the mapping entry that was updated
	 */
	public static MappingEntry updateMappingEntry(MappingEntry mappingEntry) {
		return getService().updateMappingEntry(mappingEntry);
	}

	public static MappingEntryLocalService getService() {
		return _service;
	}

	public static void setService(MappingEntryLocalService service) {
		_service = service;
	}

	private static volatile MappingEntryLocalService _service;

}
// SB-Hash:-1237505411