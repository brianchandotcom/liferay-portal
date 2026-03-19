/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.service;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.compat700.model.BasicEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for BasicEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.compat700.service.impl.BasicEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see BasicEntryLocalService
 * @generated
 */
public class BasicEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.compat700.service.impl.BasicEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the basic entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntry the basic entry
	 * @return the basic entry that was added
	 */
	public static BasicEntry addBasicEntry(BasicEntry basicEntry) {
		return getService().addBasicEntry(basicEntry);
	}

	public static void addMappingEntryBasicEntries(
		long mappingEntryId, List<BasicEntry> basicEntries) {

		getService().addMappingEntryBasicEntries(mappingEntryId, basicEntries);
	}

	public static void addMappingEntryBasicEntries(
		long mappingEntryId, long[] basicEntryIds) {

		getService().addMappingEntryBasicEntries(mappingEntryId, basicEntryIds);
	}

	public static void addMappingEntryBasicEntry(
		long mappingEntryId, BasicEntry basicEntry) {

		getService().addMappingEntryBasicEntry(mappingEntryId, basicEntry);
	}

	public static void addMappingEntryBasicEntry(
		long mappingEntryId, long basicEntryId) {

		getService().addMappingEntryBasicEntry(mappingEntryId, basicEntryId);
	}

	public static void clearMappingEntryBasicEntries(long mappingEntryId) {
		getService().clearMappingEntryBasicEntries(mappingEntryId);
	}

	/**
	 * Creates a new basic entry with the primary key. Does not add the basic entry to the database.
	 *
	 * @param basicEntryId the primary key for the new basic entry
	 * @return the new basic entry
	 */
	public static BasicEntry createBasicEntry(long basicEntryId) {
		return getService().createBasicEntry(basicEntryId);
	}

	/**
	 * Deletes the basic entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntry the basic entry
	 * @return the basic entry that was removed
	 */
	public static BasicEntry deleteBasicEntry(BasicEntry basicEntry) {
		return getService().deleteBasicEntry(basicEntry);
	}

	/**
	 * Deletes the basic entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry that was removed
	 * @throws PortalException if a basic entry with the primary key could not be found
	 */
	public static BasicEntry deleteBasicEntry(long basicEntryId)
		throws PortalException {

		return getService().deleteBasicEntry(basicEntryId);
	}

	public static void deleteMappingEntryBasicEntries(
		long mappingEntryId, List<BasicEntry> basicEntries) {

		getService().deleteMappingEntryBasicEntries(
			mappingEntryId, basicEntries);
	}

	public static void deleteMappingEntryBasicEntries(
		long mappingEntryId, long[] basicEntryIds) {

		getService().deleteMappingEntryBasicEntries(
			mappingEntryId, basicEntryIds);
	}

	public static void deleteMappingEntryBasicEntry(
		long mappingEntryId, BasicEntry basicEntry) {

		getService().deleteMappingEntryBasicEntry(mappingEntryId, basicEntry);
	}

	public static void deleteMappingEntryBasicEntry(
		long mappingEntryId, long basicEntryId) {

		getService().deleteMappingEntryBasicEntry(mappingEntryId, basicEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.BasicEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.BasicEntryModelImpl</code>.
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

	public static BasicEntry fetchBasicEntry(long basicEntryId) {
		return getService().fetchBasicEntry(basicEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the basic entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.BasicEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of basic entries
	 * @param end the upper bound of the range of basic entries (not inclusive)
	 * @return the range of basic entries
	 */
	public static List<BasicEntry> getBasicEntries(int start, int end) {
		return getService().getBasicEntries(start, end);
	}

	/**
	 * Returns the number of basic entries.
	 *
	 * @return the number of basic entries
	 */
	public static int getBasicEntriesCount() {
		return getService().getBasicEntriesCount();
	}

	/**
	 * Returns the basic entry with the primary key.
	 *
	 * @param basicEntryId the primary key of the basic entry
	 * @return the basic entry
	 * @throws PortalException if a basic entry with the primary key could not be found
	 */
	public static BasicEntry getBasicEntry(long basicEntryId)
		throws PortalException {

		return getService().getBasicEntry(basicEntryId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static List<BasicEntry> getMappingEntryBasicEntries(
		long mappingEntryId) {

		return getService().getMappingEntryBasicEntries(mappingEntryId);
	}

	public static List<BasicEntry> getMappingEntryBasicEntries(
		long mappingEntryId, int start, int end) {

		return getService().getMappingEntryBasicEntries(
			mappingEntryId, start, end);
	}

	public static List<BasicEntry> getMappingEntryBasicEntries(
		long mappingEntryId, int start, int end,
		OrderByComparator<BasicEntry> orderByComparator) {

		return getService().getMappingEntryBasicEntries(
			mappingEntryId, start, end, orderByComparator);
	}

	public static int getMappingEntryBasicEntriesCount(long mappingEntryId) {
		return getService().getMappingEntryBasicEntriesCount(mappingEntryId);
	}

	/**
	 * Returns the mappingEntryIds of the mapping entries associated with the basic entry.
	 *
	 * @param basicEntryId the basicEntryId of the basic entry
	 * @return long[] the mappingEntryIds of mapping entries associated with the basic entry
	 */
	public static long[] getMappingEntryPrimaryKeys(long basicEntryId) {
		return getService().getMappingEntryPrimaryKeys(basicEntryId);
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

	public static boolean hasMappingEntryBasicEntries(long mappingEntryId) {
		return getService().hasMappingEntryBasicEntries(mappingEntryId);
	}

	public static boolean hasMappingEntryBasicEntry(
		long mappingEntryId, long basicEntryId) {

		return getService().hasMappingEntryBasicEntry(
			mappingEntryId, basicEntryId);
	}

	public static void setMappingEntryBasicEntries(
		long mappingEntryId, long[] basicEntryIds) {

		getService().setMappingEntryBasicEntries(mappingEntryId, basicEntryIds);
	}

	/**
	 * Updates the basic entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect BasicEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param basicEntry the basic entry
	 * @return the basic entry that was updated
	 */
	public static BasicEntry updateBasicEntry(BasicEntry basicEntry) {
		return getService().updateBasicEntry(basicEntry);
	}

	public static BasicEntryLocalService getService() {
		return _service;
	}

	public static void setService(BasicEntryLocalService service) {
		_service = service;
	}

	private static volatile BasicEntryLocalService _service;

}
// LIFERAY-SERVICE-BUILDER-HASH:2017467101