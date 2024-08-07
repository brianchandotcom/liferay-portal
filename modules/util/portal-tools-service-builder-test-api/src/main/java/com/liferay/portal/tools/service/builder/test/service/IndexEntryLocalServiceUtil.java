/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.model.IndexEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for IndexEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.IndexEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntryLocalService
 * @generated
 */
public class IndexEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.IndexEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the index entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect IndexEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param indexEntry the index entry
	 * @return the index entry that was added
	 */
	public static IndexEntry addIndexEntry(IndexEntry indexEntry) {
		return getService().addIndexEntry(indexEntry);
	}

	public static boolean addKeywordsEntryIndexEntries(
		long keywordsEntryId, List<IndexEntry> indexEntries) {

		return getService().addKeywordsEntryIndexEntries(
			keywordsEntryId, indexEntries);
	}

	public static boolean addKeywordsEntryIndexEntries(
		long keywordsEntryId, long[] indexEntryIds) {

		return getService().addKeywordsEntryIndexEntries(
			keywordsEntryId, indexEntryIds);
	}

	public static boolean addKeywordsEntryIndexEntry(
		long keywordsEntryId, IndexEntry indexEntry) {

		return getService().addKeywordsEntryIndexEntry(
			keywordsEntryId, indexEntry);
	}

	public static boolean addKeywordsEntryIndexEntry(
		long keywordsEntryId, long indexEntryId) {

		return getService().addKeywordsEntryIndexEntry(
			keywordsEntryId, indexEntryId);
	}

	public static void clearKeywordsEntryIndexEntries(long keywordsEntryId) {
		getService().clearKeywordsEntryIndexEntries(keywordsEntryId);
	}

	/**
	 * Creates a new index entry with the primary key. Does not add the index entry to the database.
	 *
	 * @param indexEntryId the primary key for the new index entry
	 * @return the new index entry
	 */
	public static IndexEntry createIndexEntry(long indexEntryId) {
		return getService().createIndexEntry(indexEntryId);
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
	 * Deletes the index entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect IndexEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param indexEntry the index entry
	 * @return the index entry that was removed
	 */
	public static IndexEntry deleteIndexEntry(IndexEntry indexEntry) {
		return getService().deleteIndexEntry(indexEntry);
	}

	/**
	 * Deletes the index entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect IndexEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry that was removed
	 * @throws PortalException if a index entry with the primary key could not be found
	 */
	public static IndexEntry deleteIndexEntry(long indexEntryId)
		throws PortalException {

		return getService().deleteIndexEntry(indexEntryId);
	}

	public static void deleteKeywordsEntryIndexEntries(
		long keywordsEntryId, List<IndexEntry> indexEntries) {

		getService().deleteKeywordsEntryIndexEntries(
			keywordsEntryId, indexEntries);
	}

	public static void deleteKeywordsEntryIndexEntries(
		long keywordsEntryId, long[] indexEntryIds) {

		getService().deleteKeywordsEntryIndexEntries(
			keywordsEntryId, indexEntryIds);
	}

	public static void deleteKeywordsEntryIndexEntry(
		long keywordsEntryId, IndexEntry indexEntry) {

		getService().deleteKeywordsEntryIndexEntry(keywordsEntryId, indexEntry);
	}

	public static void deleteKeywordsEntryIndexEntry(
		long keywordsEntryId, long indexEntryId) {

		getService().deleteKeywordsEntryIndexEntry(
			keywordsEntryId, indexEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.IndexEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.IndexEntryModelImpl</code>.
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

	public static IndexEntry fetchIndexEntry(long indexEntryId) {
		return getService().fetchIndexEntry(indexEntryId);
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
	 * Returns a range of all the index entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.IndexEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of index entries
	 * @param end the upper bound of the range of index entries (not inclusive)
	 * @return the range of index entries
	 */
	public static List<IndexEntry> getIndexEntries(int start, int end) {
		return getService().getIndexEntries(start, end);
	}

	/**
	 * Returns the number of index entries.
	 *
	 * @return the number of index entries
	 */
	public static int getIndexEntriesCount() {
		return getService().getIndexEntriesCount();
	}

	/**
	 * Returns the index entry with the primary key.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry
	 * @throws PortalException if a index entry with the primary key could not be found
	 */
	public static IndexEntry getIndexEntry(long indexEntryId)
		throws PortalException {

		return getService().getIndexEntry(indexEntryId);
	}

	public static List<IndexEntry> getKeywordsEntryIndexEntries(
		long keywordsEntryId) {

		return getService().getKeywordsEntryIndexEntries(keywordsEntryId);
	}

	public static List<IndexEntry> getKeywordsEntryIndexEntries(
		long keywordsEntryId, int start, int end) {

		return getService().getKeywordsEntryIndexEntries(
			keywordsEntryId, start, end);
	}

	public static List<IndexEntry> getKeywordsEntryIndexEntries(
		long keywordsEntryId, int start, int end,
		OrderByComparator<IndexEntry> orderByComparator) {

		return getService().getKeywordsEntryIndexEntries(
			keywordsEntryId, start, end, orderByComparator);
	}

	public static int getKeywordsEntryIndexEntriesCount(long keywordsEntryId) {
		return getService().getKeywordsEntryIndexEntriesCount(keywordsEntryId);
	}

	/**
	 * Returns the keywordsEntryIds of the keywords entries associated with the index entry.
	 *
	 * @param indexEntryId the indexEntryId of the index entry
	 * @return long[] the keywordsEntryIds of keywords entries associated with the index entry
	 */
	public static long[] getKeywordsEntryPrimaryKeys(long indexEntryId) {
		return getService().getKeywordsEntryPrimaryKeys(indexEntryId);
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

	public static boolean hasKeywordsEntryIndexEntries(long keywordsEntryId) {
		return getService().hasKeywordsEntryIndexEntries(keywordsEntryId);
	}

	public static boolean hasKeywordsEntryIndexEntry(
		long keywordsEntryId, long indexEntryId) {

		return getService().hasKeywordsEntryIndexEntry(
			keywordsEntryId, indexEntryId);
	}

	public static void setKeywordsEntryIndexEntries(
		long keywordsEntryId, long[] indexEntryIds) {

		getService().setKeywordsEntryIndexEntries(
			keywordsEntryId, indexEntryIds);
	}

	/**
	 * Updates the index entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect IndexEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param indexEntry the index entry
	 * @return the index entry that was updated
	 */
	public static IndexEntry updateIndexEntry(IndexEntry indexEntry) {
		return getService().updateIndexEntry(indexEntry);
	}

	public static IndexEntryLocalService getService() {
		return _service;
	}

	public static void setService(IndexEntryLocalService service) {
		_service = service;
	}

	private static volatile IndexEntryLocalService _service;

}