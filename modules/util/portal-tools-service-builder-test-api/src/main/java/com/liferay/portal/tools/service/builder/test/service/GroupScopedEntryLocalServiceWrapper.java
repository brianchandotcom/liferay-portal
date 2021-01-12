/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.service.builder.test.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link GroupScopedEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see GroupScopedEntryLocalService
 * @generated
 */
public class GroupScopedEntryLocalServiceWrapper
	implements GroupScopedEntryLocalService,
			   ServiceWrapper<GroupScopedEntryLocalService> {

	public GroupScopedEntryLocalServiceWrapper(
		GroupScopedEntryLocalService groupScopedEntryLocalService) {

		_groupScopedEntryLocalService = groupScopedEntryLocalService;
	}

	/**
	 * Adds the group scoped entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GroupScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param groupScopedEntry the group scoped entry
	 * @return the group scoped entry that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
		addGroupScopedEntry(
			com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
				groupScopedEntry) {

		return _groupScopedEntryLocalService.addGroupScopedEntry(
			groupScopedEntry);
	}

	/**
	 * Creates a new group scoped entry with the primary key. Does not add the group scoped entry to the database.
	 *
	 * @param GroupScopedEntryId the primary key for the new group scoped entry
	 * @return the new group scoped entry
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
		createGroupScopedEntry(long GroupScopedEntryId) {

		return _groupScopedEntryLocalService.createGroupScopedEntry(
			GroupScopedEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _groupScopedEntryLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the group scoped entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GroupScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param groupScopedEntry the group scoped entry
	 * @return the group scoped entry that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
		deleteGroupScopedEntry(
			com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
				groupScopedEntry) {

		return _groupScopedEntryLocalService.deleteGroupScopedEntry(
			groupScopedEntry);
	}

	/**
	 * Deletes the group scoped entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GroupScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry that was removed
	 * @throws PortalException if a group scoped entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
			deleteGroupScopedEntry(long GroupScopedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _groupScopedEntryLocalService.deleteGroupScopedEntry(
			GroupScopedEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _groupScopedEntryLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _groupScopedEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _groupScopedEntryLocalService.dynamicQuery();
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

		return _groupScopedEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.GroupScopedEntryModelImpl</code>.
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

		return _groupScopedEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.GroupScopedEntryModelImpl</code>.
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

		return _groupScopedEntryLocalService.dynamicQuery(
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

		return _groupScopedEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _groupScopedEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
		fetchGroupScopedEntry(long GroupScopedEntryId) {

		return _groupScopedEntryLocalService.fetchGroupScopedEntry(
			GroupScopedEntryId);
	}

	/**
	 * Returns the group scoped entry with the matching external reference code and group.
	 *
	 * @param groupId the primary key of the group
	 * @param externalReferenceCode the group scoped entry's external reference code
	 * @return the matching group scoped entry, or <code>null</code> if a matching group scoped entry could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
		fetchGroupScopedEntryByReferenceCode(
			long groupId, String externalReferenceCode) {

		return _groupScopedEntryLocalService.
			fetchGroupScopedEntryByReferenceCode(
				groupId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _groupScopedEntryLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the group scoped entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.GroupScopedEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of group scoped entries
	 * @param end the upper bound of the range of group scoped entries (not inclusive)
	 * @return the range of group scoped entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry>
			getGroupScopedEntries(int start, int end) {

		return _groupScopedEntryLocalService.getGroupScopedEntries(start, end);
	}

	/**
	 * Returns the number of group scoped entries.
	 *
	 * @return the number of group scoped entries
	 */
	@Override
	public int getGroupScopedEntriesCount() {
		return _groupScopedEntryLocalService.getGroupScopedEntriesCount();
	}

	/**
	 * Returns the group scoped entry with the primary key.
	 *
	 * @param GroupScopedEntryId the primary key of the group scoped entry
	 * @return the group scoped entry
	 * @throws PortalException if a group scoped entry with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
			getGroupScopedEntry(long GroupScopedEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _groupScopedEntryLocalService.getGroupScopedEntry(
			GroupScopedEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _groupScopedEntryLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _groupScopedEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _groupScopedEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the group scoped entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect GroupScopedEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param groupScopedEntry the group scoped entry
	 * @return the group scoped entry that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
		updateGroupScopedEntry(
			com.liferay.portal.tools.service.builder.test.model.GroupScopedEntry
				groupScopedEntry) {

		return _groupScopedEntryLocalService.updateGroupScopedEntry(
			groupScopedEntry);
	}

	@Override
	public GroupScopedEntryLocalService getWrappedService() {
		return _groupScopedEntryLocalService;
	}

	@Override
	public void setWrappedService(
		GroupScopedEntryLocalService groupScopedEntryLocalService) {

		_groupScopedEntryLocalService = groupScopedEntryLocalService;
	}

	private GroupScopedEntryLocalService _groupScopedEntryLocalService;

}