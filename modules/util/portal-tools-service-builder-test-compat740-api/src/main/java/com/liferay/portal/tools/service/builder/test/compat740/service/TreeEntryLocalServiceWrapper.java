/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link TreeEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see TreeEntryLocalService
 * @generated
 */
public class TreeEntryLocalServiceWrapper
	implements ServiceWrapper<TreeEntryLocalService>, TreeEntryLocalService {

	public TreeEntryLocalServiceWrapper() {
		this(null);
	}

	public TreeEntryLocalServiceWrapper(
		TreeEntryLocalService treeEntryLocalService) {

		_treeEntryLocalService = treeEntryLocalService;
	}

	/**
	 * Adds the tree entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TreeEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param treeEntry the tree entry
	 * @return the tree entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TreeEntry
			addTreeEntry(
				com.liferay.portal.tools.service.builder.test.compat740.model.
					TreeEntry treeEntry) {

		return _treeEntryLocalService.addTreeEntry(treeEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _treeEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new tree entry with the primary key. Does not add the tree entry to the database.
	 *
	 * @param treeEntryId the primary key for the new tree entry
	 * @return the new tree entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TreeEntry
			createTreeEntry(long treeEntryId) {

		return _treeEntryLocalService.createTreeEntry(treeEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _treeEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the tree entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TreeEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param treeEntryId the primary key of the tree entry
	 * @return the tree entry that was removed
	 * @throws PortalException if a tree entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TreeEntry
				deleteTreeEntry(long treeEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _treeEntryLocalService.deleteTreeEntry(treeEntryId);
	}

	/**
	 * Deletes the tree entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TreeEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param treeEntry the tree entry
	 * @return the tree entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TreeEntry
			deleteTreeEntry(
				com.liferay.portal.tools.service.builder.test.compat740.model.
					TreeEntry treeEntry) {

		return _treeEntryLocalService.deleteTreeEntry(treeEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _treeEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _treeEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _treeEntryLocalService.dynamicQuery();
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

		return _treeEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.TreeEntryModelImpl</code>.
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

		return _treeEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.TreeEntryModelImpl</code>.
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

		return _treeEntryLocalService.dynamicQuery(
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

		return _treeEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _treeEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TreeEntry
			fetchTreeEntry(long treeEntryId) {

		return _treeEntryLocalService.fetchTreeEntry(treeEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _treeEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _treeEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _treeEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _treeEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the tree entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat740.model.impl.TreeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of tree entries
	 * @param end the upper bound of the range of tree entries (not inclusive)
	 * @return the range of tree entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat740.model.
			TreeEntry> getTreeEntries(int start, int end) {

		return _treeEntryLocalService.getTreeEntries(start, end);
	}

	/**
	 * Returns the number of tree entries.
	 *
	 * @return the number of tree entries
	 */
	@Override
	public int getTreeEntriesCount() {
		return _treeEntryLocalService.getTreeEntriesCount();
	}

	/**
	 * Returns the tree entry with the primary key.
	 *
	 * @param treeEntryId the primary key of the tree entry
	 * @return the tree entry
	 * @throws PortalException if a tree entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TreeEntry
				getTreeEntry(long treeEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _treeEntryLocalService.getTreeEntry(treeEntryId);
	}

	/**
	 * Updates the tree entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect TreeEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param treeEntry the tree entry
	 * @return the tree entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat740.model.TreeEntry
			updateTreeEntry(
				com.liferay.portal.tools.service.builder.test.compat740.model.
					TreeEntry treeEntry) {

		return _treeEntryLocalService.updateTreeEntry(treeEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _treeEntryLocalService.getBasePersistence();
	}

	@Override
	public TreeEntryLocalService getWrappedService() {
		return _treeEntryLocalService;
	}

	@Override
	public void setWrappedService(TreeEntryLocalService treeEntryLocalService) {
		_treeEntryLocalService = treeEntryLocalService;
	}

	private TreeEntryLocalService _treeEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1715770454