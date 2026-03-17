/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ERCVersionedEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERCVersionedEntryLocalService
 * @generated
 */
public class ERCVersionedEntryLocalServiceWrapper
	implements ERCVersionedEntryLocalService,
			   ServiceWrapper<ERCVersionedEntryLocalService> {

	public ERCVersionedEntryLocalServiceWrapper(
		ERCVersionedEntryLocalService ercVersionedEntryLocalService) {

		_ercVersionedEntryLocalService = ercVersionedEntryLocalService;
	}

	/**
	 * Adds the erc versioned entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCVersionedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercVersionedEntry the erc versioned entry
	 * @return the erc versioned entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry addERCVersionedEntry(
			com.liferay.portal.tools.service.builder.test.compat710.model.
				ERCVersionedEntry ercVersionedEntry) {

		return _ercVersionedEntryLocalService.addERCVersionedEntry(
			ercVersionedEntry);
	}

	/**
	 * Creates a new erc versioned entry with the primary key. Does not add the erc versioned entry to the database.
	 *
	 * @param ercVersionedEntryId the primary key for the new erc versioned entry
	 * @return the new erc versioned entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry createERCVersionedEntry(long ercVersionedEntryId) {

		return _ercVersionedEntryLocalService.createERCVersionedEntry(
			ercVersionedEntryId);
	}

	/**
	 * Deletes the erc versioned entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCVersionedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercVersionedEntry the erc versioned entry
	 * @return the erc versioned entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry deleteERCVersionedEntry(
			com.liferay.portal.tools.service.builder.test.compat710.model.
				ERCVersionedEntry ercVersionedEntry) {

		return _ercVersionedEntryLocalService.deleteERCVersionedEntry(
			ercVersionedEntry);
	}

	/**
	 * Deletes the erc versioned entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCVersionedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry that was removed
	 * @throws PortalException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry deleteERCVersionedEntry(long ercVersionedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _ercVersionedEntryLocalService.deleteERCVersionedEntry(
			ercVersionedEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercVersionedEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ercVersionedEntryLocalService.dynamicQuery();
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

		return _ercVersionedEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.ERCVersionedEntryModelImpl</code>.
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

		return _ercVersionedEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.ERCVersionedEntryModelImpl</code>.
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

		return _ercVersionedEntryLocalService.dynamicQuery(
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

		return _ercVersionedEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ercVersionedEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry fetchERCVersionedEntry(long ercVersionedEntryId) {

		return _ercVersionedEntryLocalService.fetchERCVersionedEntry(
			ercVersionedEntryId);
	}

	@Deprecated
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry fetchERCVersionedEntryByExternalReferenceCode(
			long groupId, String externalReferenceCode) {

		return _ercVersionedEntryLocalService.
			fetchERCVersionedEntryByExternalReferenceCode(
				groupId, externalReferenceCode);
	}

	@Deprecated
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry fetchERCVersionedEntryByReferenceCode(
			long groupId, String externalReferenceCode) {

		return _ercVersionedEntryLocalService.
			fetchERCVersionedEntryByReferenceCode(
				groupId, externalReferenceCode);
	}

	/**
	 * Returns the erc versioned entry matching the UUID and group.
	 *
	 * @param uuid the erc versioned entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erc versioned entry, or <code>null</code> if a matching erc versioned entry could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry fetchERCVersionedEntryByUuidAndGroupId(
			String uuid, long groupId) {

		return _ercVersionedEntryLocalService.
			fetchERCVersionedEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ercVersionedEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the erc versioned entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.compat710.model.impl.ERCVersionedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @return the range of erc versioned entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat710.model.
			ERCVersionedEntry> getERCVersionedEntries(int start, int end) {

		return _ercVersionedEntryLocalService.getERCVersionedEntries(
			start, end);
	}

	/**
	 * Returns all the erc versioned entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the erc versioned entries
	 * @param companyId the primary key of the company
	 * @return the matching erc versioned entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat710.model.
			ERCVersionedEntry> getERCVersionedEntriesByUuidAndCompanyId(
				String uuid, long companyId) {

		return _ercVersionedEntryLocalService.
			getERCVersionedEntriesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of erc versioned entries matching the UUID and company.
	 *
	 * @param uuid the UUID of the erc versioned entries
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of erc versioned entries
	 * @param end the upper bound of the range of erc versioned entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching erc versioned entries, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.compat710.model.
			ERCVersionedEntry> getERCVersionedEntriesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.tools.service.builder.test.compat710.
						model.ERCVersionedEntry> orderByComparator) {

		return _ercVersionedEntryLocalService.
			getERCVersionedEntriesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of erc versioned entries.
	 *
	 * @return the number of erc versioned entries
	 */
	@Override
	public int getERCVersionedEntriesCount() {
		return _ercVersionedEntryLocalService.getERCVersionedEntriesCount();
	}

	/**
	 * Returns the erc versioned entry with the primary key.
	 *
	 * @param ercVersionedEntryId the primary key of the erc versioned entry
	 * @return the erc versioned entry
	 * @throws PortalException if a erc versioned entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry getERCVersionedEntry(long ercVersionedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _ercVersionedEntryLocalService.getERCVersionedEntry(
			ercVersionedEntryId);
	}

	@Deprecated
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry getERCVersionedEntryByExternalReferenceCode(
				long groupId, String externalReferenceCode)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _ercVersionedEntryLocalService.
			getERCVersionedEntryByExternalReferenceCode(
				groupId, externalReferenceCode);
	}

	/**
	 * Returns the erc versioned entry matching the UUID and group.
	 *
	 * @param uuid the erc versioned entry's UUID
	 * @param groupId the primary key of the group
	 * @return the matching erc versioned entry
	 * @throws PortalException if a matching erc versioned entry could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry getERCVersionedEntryByUuidAndGroupId(
				String uuid, long groupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _ercVersionedEntryLocalService.
			getERCVersionedEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ercVersionedEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ercVersionedEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercVersionedEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the erc versioned entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCVersionedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercVersionedEntry the erc versioned entry
	 * @return the erc versioned entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.compat710.model.
		ERCVersionedEntry updateERCVersionedEntry(
			com.liferay.portal.tools.service.builder.test.compat710.model.
				ERCVersionedEntry ercVersionedEntry) {

		return _ercVersionedEntryLocalService.updateERCVersionedEntry(
			ercVersionedEntry);
	}

	@Override
	public ERCVersionedEntryLocalService getWrappedService() {
		return _ercVersionedEntryLocalService;
	}

	@Override
	public void setWrappedService(
		ERCVersionedEntryLocalService ercVersionedEntryLocalService) {

		_ercVersionedEntryLocalService = ercVersionedEntryLocalService;
	}

	private ERCVersionedEntryLocalService _ercVersionedEntryLocalService;

}
// SB-Hash:-1809583278