/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link KeywordsEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see KeywordsEntryLocalService
 * @generated
 */
public class KeywordsEntryLocalServiceWrapper
	implements KeywordsEntryLocalService,
			   ServiceWrapper<KeywordsEntryLocalService> {

	public KeywordsEntryLocalServiceWrapper() {
		this(null);
	}

	public KeywordsEntryLocalServiceWrapper(
		KeywordsEntryLocalService keywordsEntryLocalService) {

		_keywordsEntryLocalService = keywordsEntryLocalService;
	}

	@Override
	public boolean addIndexEntryKeywordsEntries(
		long indexEntryId,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
				keywordsEntries) {

		return _keywordsEntryLocalService.addIndexEntryKeywordsEntries(
			indexEntryId, keywordsEntries);
	}

	@Override
	public boolean addIndexEntryKeywordsEntries(
		long indexEntryId, long[] keywordsEntryIds) {

		return _keywordsEntryLocalService.addIndexEntryKeywordsEntries(
			indexEntryId, keywordsEntryIds);
	}

	@Override
	public boolean addIndexEntryKeywordsEntry(
		long indexEntryId,
		com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
			keywordsEntry) {

		return _keywordsEntryLocalService.addIndexEntryKeywordsEntry(
			indexEntryId, keywordsEntry);
	}

	@Override
	public boolean addIndexEntryKeywordsEntry(
		long indexEntryId, long keywordsEntryId) {

		return _keywordsEntryLocalService.addIndexEntryKeywordsEntry(
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
		addKeywordsEntry(
			com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
				keywordsEntry) {

		return _keywordsEntryLocalService.addKeywordsEntry(keywordsEntry);
	}

	@Override
	public void clearIndexEntryKeywordsEntries(long indexEntryId) {
		_keywordsEntryLocalService.clearIndexEntryKeywordsEntries(indexEntryId);
	}

	/**
	 * Creates a new keywords entry with the primary key. Does not add the keywords entry to the database.
	 *
	 * @param keywordsEntryId the primary key for the new keywords entry
	 * @return the new keywords entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
		createKeywordsEntry(long keywordsEntryId) {

		return _keywordsEntryLocalService.createKeywordsEntry(keywordsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _keywordsEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	@Override
	public void deleteIndexEntryKeywordsEntries(
		long indexEntryId,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
				keywordsEntries) {

		_keywordsEntryLocalService.deleteIndexEntryKeywordsEntries(
			indexEntryId, keywordsEntries);
	}

	@Override
	public void deleteIndexEntryKeywordsEntries(
		long indexEntryId, long[] keywordsEntryIds) {

		_keywordsEntryLocalService.deleteIndexEntryKeywordsEntries(
			indexEntryId, keywordsEntryIds);
	}

	@Override
	public void deleteIndexEntryKeywordsEntry(
		long indexEntryId,
		com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
			keywordsEntry) {

		_keywordsEntryLocalService.deleteIndexEntryKeywordsEntry(
			indexEntryId, keywordsEntry);
	}

	@Override
	public void deleteIndexEntryKeywordsEntry(
		long indexEntryId, long keywordsEntryId) {

		_keywordsEntryLocalService.deleteIndexEntryKeywordsEntry(
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
		deleteKeywordsEntry(
			com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
				keywordsEntry) {

		return _keywordsEntryLocalService.deleteKeywordsEntry(keywordsEntry);
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
			deleteKeywordsEntry(long keywordsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _keywordsEntryLocalService.deleteKeywordsEntry(keywordsEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _keywordsEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _keywordsEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _keywordsEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _keywordsEntryLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _keywordsEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _keywordsEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _keywordsEntryLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _keywordsEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _keywordsEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
		fetchKeywordsEntry(long keywordsEntryId) {

		return _keywordsEntryLocalService.fetchKeywordsEntry(keywordsEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _keywordsEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _keywordsEntryLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getIndexEntryKeywordsEntries(long indexEntryId) {

		return _keywordsEntryLocalService.getIndexEntryKeywordsEntries(
			indexEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getIndexEntryKeywordsEntries(
				long indexEntryId, int start, int end) {

		return _keywordsEntryLocalService.getIndexEntryKeywordsEntries(
			indexEntryId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getIndexEntryKeywordsEntries(
				long indexEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						KeywordsEntry> orderByComparator) {

		return _keywordsEntryLocalService.getIndexEntryKeywordsEntries(
			indexEntryId, start, end, orderByComparator);
	}

	@Override
	public int getIndexEntryKeywordsEntriesCount(long indexEntryId) {
		return _keywordsEntryLocalService.getIndexEntryKeywordsEntriesCount(
			indexEntryId);
	}

	/**
	 * Returns the indexEntryIds of the index entries associated with the keywords entry.
	 *
	 * @param keywordsEntryId the keywordsEntryId of the keywords entry
	 * @return long[] the indexEntryIds of index entries associated with the keywords entry
	 */
	@Override
	public long[] getIndexEntryPrimaryKeys(long keywordsEntryId) {
		return _keywordsEntryLocalService.getIndexEntryPrimaryKeys(
			keywordsEntryId);
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
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.KeywordsEntry>
			getKeywordsEntries(int start, int end) {

		return _keywordsEntryLocalService.getKeywordsEntries(start, end);
	}

	/**
	 * Returns the number of keywords entries.
	 *
	 * @return the number of keywords entries
	 */
	@Override
	public int getKeywordsEntriesCount() {
		return _keywordsEntryLocalService.getKeywordsEntriesCount();
	}

	/**
	 * Returns the keywords entry with the primary key.
	 *
	 * @param keywordsEntryId the primary key of the keywords entry
	 * @return the keywords entry
	 * @throws PortalException if a keywords entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
			getKeywordsEntry(long keywordsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _keywordsEntryLocalService.getKeywordsEntry(keywordsEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _keywordsEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _keywordsEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasIndexEntryKeywordsEntries(long indexEntryId) {
		return _keywordsEntryLocalService.hasIndexEntryKeywordsEntries(
			indexEntryId);
	}

	@Override
	public boolean hasIndexEntryKeywordsEntry(
		long indexEntryId, long keywordsEntryId) {

		return _keywordsEntryLocalService.hasIndexEntryKeywordsEntry(
			indexEntryId, keywordsEntryId);
	}

	@Override
	public void setIndexEntryKeywordsEntries(
		long indexEntryId, long[] keywordsEntryIds) {

		_keywordsEntryLocalService.setIndexEntryKeywordsEntries(
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
		updateKeywordsEntry(
			com.liferay.portal.tools.service.builder.test.model.KeywordsEntry
				keywordsEntry) {

		return _keywordsEntryLocalService.updateKeywordsEntry(keywordsEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _keywordsEntryLocalService.getBasePersistence();
	}

	@Override
	public KeywordsEntryLocalService getWrappedService() {
		return _keywordsEntryLocalService;
	}

	@Override
	public void setWrappedService(
		KeywordsEntryLocalService keywordsEntryLocalService) {

		_keywordsEntryLocalService = keywordsEntryLocalService;
	}

	private KeywordsEntryLocalService _keywordsEntryLocalService;

}