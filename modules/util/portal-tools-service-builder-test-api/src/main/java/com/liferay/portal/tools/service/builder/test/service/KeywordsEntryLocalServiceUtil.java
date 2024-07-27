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
import com.liferay.portal.tools.service.builder.test.model.KeywordsEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for KeywordsEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.service.impl.KeywordsEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see KeywordsEntryLocalService
 * @generated
 */
public class KeywordsEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.service.impl.KeywordsEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static boolean addIndexEntryKeywordsEntries(
		long indexEntryId, List<KeywordsEntry> keywordsEntries) {

		return getService().addIndexEntryKeywordsEntries(
			indexEntryId, keywordsEntries);
	}

	public static boolean addIndexEntryKeywordsEntries(
		long indexEntryId, long[] keywordsEntryIds) {

		return getService().addIndexEntryKeywordsEntries(
			indexEntryId, keywordsEntryIds);
	}

	public static boolean addIndexEntryKeywordsEntry(
		long indexEntryId, KeywordsEntry keywordsEntry) {

		return getService().addIndexEntryKeywordsEntry(
			indexEntryId, keywordsEntry);
	}

	public static boolean addIndexEntryKeywordsEntry(
		long indexEntryId, long keywordsEntryId) {

		return getService().addIndexEntryKeywordsEntry(
			indexEntryId, keywordsEntryId);
	}

	/**
	 * Adds the keywords entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KeywordsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param keywordsEntry the keywords entry
	 * @return the keywords entry that was added
	 */
	public static KeywordsEntry addKeywordsEntry(KeywordsEntry keywordsEntry) {
		return getService().addKeywordsEntry(keywordsEntry);
	}

	public static void clearIndexEntryKeywordsEntries(long indexEntryId) {
		getService().clearIndexEntryKeywordsEntries(indexEntryId);
	}

	/**
	 * Creates a new keywords entry with the primary key. Does not add the keywords entry to the database.
	 *
	 * @param keywordsEntryId the primary key for the new keywords entry
	 * @return the new keywords entry
	 */
	public static KeywordsEntry createKeywordsEntry(long keywordsEntryId) {
		return getService().createKeywordsEntry(keywordsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	public static void deleteIndexEntryKeywordsEntries(
		long indexEntryId, List<KeywordsEntry> keywordsEntries) {

		getService().deleteIndexEntryKeywordsEntries(
			indexEntryId, keywordsEntries);
	}

	public static void deleteIndexEntryKeywordsEntries(
		long indexEntryId, long[] keywordsEntryIds) {

		getService().deleteIndexEntryKeywordsEntries(
			indexEntryId, keywordsEntryIds);
	}

	public static void deleteIndexEntryKeywordsEntry(
		long indexEntryId, KeywordsEntry keywordsEntry) {

		getService().deleteIndexEntryKeywordsEntry(indexEntryId, keywordsEntry);
	}

	public static void deleteIndexEntryKeywordsEntry(
		long indexEntryId, long keywordsEntryId) {

		getService().deleteIndexEntryKeywordsEntry(
			indexEntryId, keywordsEntryId);
	}

	/**
	 * Deletes the keywords entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KeywordsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param keywordsEntry the keywords entry
	 * @return the keywords entry that was removed
	 */
	public static KeywordsEntry deleteKeywordsEntry(
		KeywordsEntry keywordsEntry) {

		return getService().deleteKeywordsEntry(keywordsEntry);
	}

	/**
	 * Deletes the keywords entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KeywordsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry that was removed
	 * @throws PortalException if a keywords entry with the primary key could not be found
	 */
	public static KeywordsEntry deleteKeywordsEntry(long keywordsEntryId)
		throws PortalException {

		return getService().deleteKeywordsEntry(keywordsEntryId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.KeywordsEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.KeywordsEntryModelImpl</code>.
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

	public static KeywordsEntry fetchKeywordsEntry(long keywordsEntryId) {
		return getService().fetchKeywordsEntry(keywordsEntryId);
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

	public static List<KeywordsEntry> getIndexEntryKeywordsEntries(
		long indexEntryId) {

		return getService().getIndexEntryKeywordsEntries(indexEntryId);
	}

	public static List<KeywordsEntry> getIndexEntryKeywordsEntries(
		long indexEntryId, int start, int end) {

		return getService().getIndexEntryKeywordsEntries(
			indexEntryId, start, end);
	}

	public static List<KeywordsEntry> getIndexEntryKeywordsEntries(
		long indexEntryId, int start, int end,
		OrderByComparator<KeywordsEntry> orderByComparator) {

		return getService().getIndexEntryKeywordsEntries(
			indexEntryId, start, end, orderByComparator);
	}

	public static int getIndexEntryKeywordsEntriesCount(long indexEntryId) {
		return getService().getIndexEntryKeywordsEntriesCount(indexEntryId);
	}

	/**
	 * Returns the indexEntryIds of the index entries associated with the keywords entry.
	 *
	 * @param keywordsEntryId the keywordsEntryId of the keywords entry
	 * @return long[] the indexEntryIds of index entries associated with the keywords entry
	 */
	public static long[] getIndexEntryPrimaryKeys(long keywordsEntryId) {
		return getService().getIndexEntryPrimaryKeys(keywordsEntryId);
	}

	/**
	 * Returns a range of all the keywords entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.KeywordsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of keywords entries
	 * @param end the upper bound of the range of keywords entries (not inclusive)
	 * @return the range of keywords entries
	 */
	public static List<KeywordsEntry> getKeywordsEntries(int start, int end) {
		return getService().getKeywordsEntries(start, end);
	}

	/**
	 * Returns the number of keywords entries.
	 *
	 * @return the number of keywords entries
	 */
	public static int getKeywordsEntriesCount() {
		return getService().getKeywordsEntriesCount();
	}

	/**
	 * Returns the keywords entry with the primary key.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry
	 * @throws PortalException if a keywords entry with the primary key could not be found
	 */
	public static KeywordsEntry getKeywordsEntry(long keywordsEntryId)
		throws PortalException {

		return getService().getKeywordsEntry(keywordsEntryId);
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

	public static boolean hasIndexEntryKeywordsEntries(long indexEntryId) {
		return getService().hasIndexEntryKeywordsEntries(indexEntryId);
	}

	public static boolean hasIndexEntryKeywordsEntry(
		long indexEntryId, long keywordsEntryId) {

		return getService().hasIndexEntryKeywordsEntry(
			indexEntryId, keywordsEntryId);
	}

	public static void setIndexEntryKeywordsEntries(
		long indexEntryId, long[] keywordsEntryIds) {

		getService().setIndexEntryKeywordsEntries(
			indexEntryId, keywordsEntryIds);
	}

	/**
	 * Updates the keywords entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect KeywordsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param keywordsEntry the keywords entry
	 * @return the keywords entry that was updated
	 */
	public static KeywordsEntry updateKeywordsEntry(
		KeywordsEntry keywordsEntry) {

		return getService().updateKeywordsEntry(keywordsEntry);
	}

	public static KeywordsEntryLocalService getService() {
		return _service;
	}

	public static void setService(KeywordsEntryLocalService service) {
		_service = service;
	}

	private static volatile KeywordsEntryLocalService _service;

}