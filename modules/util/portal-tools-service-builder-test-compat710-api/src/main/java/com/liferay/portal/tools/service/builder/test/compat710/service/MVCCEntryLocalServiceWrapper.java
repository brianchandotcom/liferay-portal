/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MVCCEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see MVCCEntryLocalService
 * @generated
 */
public class MVCCEntryLocalServiceWrapper
	implements MVCCEntryLocalService, ServiceWrapper<MVCCEntryLocalService> {

	public MVCCEntryLocalServiceWrapper(
		MVCCEntryLocalService mvccEntryLocalService) {

		_mvccEntryLocalService = mvccEntryLocalService;
	}

	/**
	 * Adds the mvcc entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MVCCEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mvccEntry the mvcc entry
	 * @return the mvcc entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat710.model.MVCCEntry
			addMVCCEntry(
				com.liferay.portal.tools.service.builder.test.compat710.model.
					MVCCEntry mvccEntry) {

		return _mvccEntryLocalService.addMVCCEntry(mvccEntry);
	}

	/**
	 * Creates a new mvcc entry with the primary key. Does not add the mvcc entry to the database.
	 *
	 * @param mvccEntryId the primary key for the new mvcc entry
	 * @return the new mvcc entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat710.model.MVCCEntry
			createMVCCEntry(long mvccEntryId) {

		return _mvccEntryLocalService.createMVCCEntry(mvccEntryId);
	}

	/**
	 * Deletes the mvcc entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MVCCEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mvccEntryId the primary key of the mvcc entry
	 * @return the mvcc entry that was removed
	 * @throws PortalException if a mvcc entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat710.model.MVCCEntry
				deleteMVCCEntry(long mvccEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mvccEntryLocalService.deleteMVCCEntry(mvccEntryId);
	}

	/**
	 * Deletes the mvcc entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MVCCEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mvccEntry the mvcc entry
	 * @return the mvcc entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat710.model.MVCCEntry
			deleteMVCCEntry(
				com.liferay.portal.tools.service.builder.test.compat710.model.
					MVCCEntry mvccEntry) {

		return _mvccEntryLocalService.deleteMVCCEntry(mvccEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mvccEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _mvccEntryLocalService.dynamicQuery();
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

		return _mvccEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.MVCCEntryModelImpl</code>.
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

		return _mvccEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.MVCCEntryModelImpl</code>.
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

		return _mvccEntryLocalService.dynamicQuery(
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

		return _mvccEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _mvccEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat710.model.MVCCEntry
			fetchMVCCEntry(long mvccEntryId) {

		return _mvccEntryLocalService.fetchMVCCEntry(mvccEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _mvccEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _mvccEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the mvcc entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.MVCCEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of mvcc entries
	 * @param end the upper bound of the range of mvcc entries (not inclusive)
	 * @return the range of mvcc entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat710.model.
			MVCCEntry> getMVCCEntries(int start, int end) {

		return _mvccEntryLocalService.getMVCCEntries(start, end);
	}

	/**
	 * Returns the number of mvcc entries.
	 *
	 * @return the number of mvcc entries
	 */
	@Override
	public int getMVCCEntriesCount() {
		return _mvccEntryLocalService.getMVCCEntriesCount();
	}

	/**
	 * Returns the mvcc entry with the primary key.
	 *
	 * @param mvccEntryId the primary key of the mvcc entry
	 * @return the mvcc entry
	 * @throws PortalException if a mvcc entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat710.model.MVCCEntry
				getMVCCEntry(long mvccEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _mvccEntryLocalService.getMVCCEntry(mvccEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _mvccEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _mvccEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the mvcc entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect MVCCEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param mvccEntry the mvcc entry
	 * @return the mvcc entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.compat710.model.MVCCEntry
			updateMVCCEntry(
				com.liferay.portal.tools.service.builder.test.compat710.model.
					MVCCEntry mvccEntry) {

		return _mvccEntryLocalService.updateMVCCEntry(mvccEntry);
	}

	@Override
	public MVCCEntryLocalService getWrappedService() {
		return _mvccEntryLocalService;
	}

	@Override
	public void setWrappedService(MVCCEntryLocalService mvccEntryLocalService) {
		_mvccEntryLocalService = mvccEntryLocalService;
	}

	private MVCCEntryLocalService _mvccEntryLocalService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-825587614