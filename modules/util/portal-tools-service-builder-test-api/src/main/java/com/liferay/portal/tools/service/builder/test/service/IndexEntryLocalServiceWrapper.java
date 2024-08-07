/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link IndexEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntryLocalService
 * @generated
 */
public class IndexEntryLocalServiceWrapper
	implements IndexEntryLocalService, ServiceWrapper<IndexEntryLocalService> {

	public IndexEntryLocalServiceWrapper() {
		this(null);
	}

	public IndexEntryLocalServiceWrapper(
		IndexEntryLocalService indexEntryLocalService) {

		_indexEntryLocalService = indexEntryLocalService;
	}

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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.IndexEntry
		addIndexEntry(
			com.liferay.portal.tools.service.builder.test.model.IndexEntry
				indexEntry) {

		return _indexEntryLocalService.addIndexEntry(indexEntry);
	}

	@Override
	public boolean addKeywordsEntryIndexEntries(
		long keywordsEntryId,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
				indexEntries) {

		return _indexEntryLocalService.addKeywordsEntryIndexEntries(
			keywordsEntryId, indexEntries);
	}

	@Override
	public boolean addKeywordsEntryIndexEntries(
		long keywordsEntryId, long[] indexEntryIds) {

		return _indexEntryLocalService.addKeywordsEntryIndexEntries(
			keywordsEntryId, indexEntryIds);
	}

	@Override
	public boolean addKeywordsEntryIndexEntry(
		long keywordsEntryId,
		com.liferay.portal.tools.service.builder.test.model.IndexEntry
			indexEntry) {

		return _indexEntryLocalService.addKeywordsEntryIndexEntry(
			keywordsEntryId, indexEntry);
	}

	@Override
	public boolean addKeywordsEntryIndexEntry(
		long keywordsEntryId, long indexEntryId) {

		return _indexEntryLocalService.addKeywordsEntryIndexEntry(
			keywordsEntryId, indexEntryId);
	}

	@Override
	public void clearKeywordsEntryIndexEntries(long keywordsEntryId) {
		_indexEntryLocalService.clearKeywordsEntryIndexEntries(keywordsEntryId);
	}

	/**
	 * Creates a new index entry with the primary key. Does not add the index entry to the database.
	 *
	 * @param indexEntryId the primary key for the new index entry
	 * @return the new index entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.IndexEntry
		createIndexEntry(long indexEntryId) {

		return _indexEntryLocalService.createIndexEntry(indexEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _indexEntryLocalService.createPersistedModel(primaryKeyObj);
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.IndexEntry
		deleteIndexEntry(
			com.liferay.portal.tools.service.builder.test.model.IndexEntry
				indexEntry) {

		return _indexEntryLocalService.deleteIndexEntry(indexEntry);
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.IndexEntry
			deleteIndexEntry(long indexEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _indexEntryLocalService.deleteIndexEntry(indexEntryId);
	}

	@Override
	public void deleteKeywordsEntryIndexEntries(
		long keywordsEntryId,
		java.util.List
			<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
				indexEntries) {

		_indexEntryLocalService.deleteKeywordsEntryIndexEntries(
			keywordsEntryId, indexEntries);
	}

	@Override
	public void deleteKeywordsEntryIndexEntries(
		long keywordsEntryId, long[] indexEntryIds) {

		_indexEntryLocalService.deleteKeywordsEntryIndexEntries(
			keywordsEntryId, indexEntryIds);
	}

	@Override
	public void deleteKeywordsEntryIndexEntry(
		long keywordsEntryId,
		com.liferay.portal.tools.service.builder.test.model.IndexEntry
			indexEntry) {

		_indexEntryLocalService.deleteKeywordsEntryIndexEntry(
			keywordsEntryId, indexEntry);
	}

	@Override
	public void deleteKeywordsEntryIndexEntry(
		long keywordsEntryId, long indexEntryId) {

		_indexEntryLocalService.deleteKeywordsEntryIndexEntry(
			keywordsEntryId, indexEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _indexEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _indexEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _indexEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _indexEntryLocalService.dynamicQuery();
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

		return _indexEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _indexEntryLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _indexEntryLocalService.dynamicQuery(
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

		return _indexEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _indexEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.IndexEntry
		fetchIndexEntry(long indexEntryId) {

		return _indexEntryLocalService.fetchIndexEntry(indexEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _indexEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _indexEntryLocalService.getIndexableActionableDynamicQuery();
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
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getIndexEntries(int start, int end) {

		return _indexEntryLocalService.getIndexEntries(start, end);
	}

	/**
	 * Returns the number of index entries.
	 *
	 * @return the number of index entries
	 */
	@Override
	public int getIndexEntriesCount() {
		return _indexEntryLocalService.getIndexEntriesCount();
	}

	/**
	 * Returns the index entry with the primary key.
	 *
	 * @param indexEntryId the primary key of the index entry
	 * @return the index entry
	 * @throws PortalException if a index entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.IndexEntry
			getIndexEntry(long indexEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _indexEntryLocalService.getIndexEntry(indexEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getKeywordsEntryIndexEntries(long keywordsEntryId) {

		return _indexEntryLocalService.getKeywordsEntryIndexEntries(
			keywordsEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getKeywordsEntryIndexEntries(
				long keywordsEntryId, int start, int end) {

		return _indexEntryLocalService.getKeywordsEntryIndexEntries(
			keywordsEntryId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.IndexEntry>
			getKeywordsEntryIndexEntries(
				long keywordsEntryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						IndexEntry> orderByComparator) {

		return _indexEntryLocalService.getKeywordsEntryIndexEntries(
			keywordsEntryId, start, end, orderByComparator);
	}

	@Override
	public int getKeywordsEntryIndexEntriesCount(long keywordsEntryId) {
		return _indexEntryLocalService.getKeywordsEntryIndexEntriesCount(
			keywordsEntryId);
	}

	/**
	 * Returns the keywordsEntryIds of the keywords entries associated with the index entry.
	 *
	 * @param indexEntryId the indexEntryId of the index entry
	 * @return long[] the keywordsEntryIds of keywords entries associated with the index entry
	 */
	@Override
	public long[] getKeywordsEntryPrimaryKeys(long indexEntryId) {
		return _indexEntryLocalService.getKeywordsEntryPrimaryKeys(
			indexEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _indexEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _indexEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasKeywordsEntryIndexEntries(long keywordsEntryId) {
		return _indexEntryLocalService.hasKeywordsEntryIndexEntries(
			keywordsEntryId);
	}

	@Override
	public boolean hasKeywordsEntryIndexEntry(
		long keywordsEntryId, long indexEntryId) {

		return _indexEntryLocalService.hasKeywordsEntryIndexEntry(
			keywordsEntryId, indexEntryId);
	}

	@Override
	public void setKeywordsEntryIndexEntries(
		long keywordsEntryId, long[] indexEntryIds) {

		_indexEntryLocalService.setKeywordsEntryIndexEntries(
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
	@Override
	public com.liferay.portal.tools.service.builder.test.model.IndexEntry
		updateIndexEntry(
			com.liferay.portal.tools.service.builder.test.model.IndexEntry
				indexEntry) {

		return _indexEntryLocalService.updateIndexEntry(indexEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _indexEntryLocalService.getBasePersistence();
	}

	@Override
	public IndexEntryLocalService getWrappedService() {
		return _indexEntryLocalService;
	}

	@Override
	public void setWrappedService(
		IndexEntryLocalService indexEntryLocalService) {

		_indexEntryLocalService = indexEntryLocalService;
	}

	private IndexEntryLocalService _indexEntryLocalService;

}