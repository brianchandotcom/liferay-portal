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

package com.liferay.changeset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ChangesetLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetLocalService
 * @generated
 */
@ProviderType
public class ChangesetLocalServiceWrapper implements ChangesetLocalService,
	ServiceWrapper<ChangesetLocalService> {
	public ChangesetLocalServiceWrapper(
		ChangesetLocalService changesetLocalService) {
		_changesetLocalService = changesetLocalService;
	}

	/**
	* Adds the changeset to the database. Also notifies the appropriate model listeners.
	*
	* @param changeset the changeset
	* @return the changeset that was added
	*/
	@Override
	public com.liferay.changeset.model.Changeset addChangeset(
		com.liferay.changeset.model.Changeset changeset) {
		return _changesetLocalService.addChangeset(changeset);
	}

	/**
	* NOTE FOR DEVELOPERS:
	*
	* Never reference this class directly. Always use {@link ChangesetLocalServiceUtil} to access the changeset local service.
	*/
	@Override
	public com.liferay.changeset.model.Changeset addChangeset(long userId,
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetLocalService.addChangeset(userId, groupId, name,
			description);
	}

	/**
	* Creates a new changeset with the primary key. Does not add the changeset to the database.
	*
	* @param changesetId the primary key for the new changeset
	* @return the new changeset
	*/
	@Override
	public com.liferay.changeset.model.Changeset createChangeset(
		long changesetId) {
		return _changesetLocalService.createChangeset(changesetId);
	}

	/**
	* Deletes the changeset from the database. Also notifies the appropriate model listeners.
	*
	* @param changeset the changeset
	* @return the changeset that was removed
	*/
	@Override
	public com.liferay.changeset.model.Changeset deleteChangeset(
		com.liferay.changeset.model.Changeset changeset) {
		return _changesetLocalService.deleteChangeset(changeset);
	}

	/**
	* Deletes the changeset with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param changesetId the primary key of the changeset
	* @return the changeset that was removed
	* @throws PortalException if a changeset with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.model.Changeset deleteChangeset(
		long changesetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetLocalService.deleteChangeset(changesetId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _changesetLocalService.dynamicQuery();
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
		return _changesetLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changesetLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _changesetLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _changesetLocalService.dynamicQueryCount(dynamicQuery);
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
		return _changesetLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.changeset.model.Changeset fetchChangeset(
		long changesetId) {
		return _changesetLocalService.fetchChangeset(changesetId);
	}

	@Override
	public com.liferay.changeset.model.Changeset fetchChangeset(long groupId,
		java.lang.String name) {
		return _changesetLocalService.fetchChangeset(groupId, name);
	}

	@Override
	public com.liferay.changeset.model.Changeset fetchOrAddChangeset(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetLocalService.fetchOrAddChangeset(groupId, name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _changesetLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the changeset with the primary key.
	*
	* @param changesetId the primary key of the changeset
	* @return the changeset
	* @throws PortalException if a changeset with the primary key could not be found
	*/
	@Override
	public com.liferay.changeset.model.Changeset getChangeset(long changesetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetLocalService.getChangeset(changesetId);
	}

	@Override
	public com.liferay.changeset.model.Changeset getChangeset(long groupId,
		java.lang.String name)
		throws com.liferay.changeset.exception.NoSuchChangesetException {
		return _changesetLocalService.getChangeset(groupId, name);
	}

	/**
	* Returns a range of all the changesets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.changeset.model.impl.ChangesetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of changesets
	* @param end the upper bound of the range of changesets (not inclusive)
	* @return the range of changesets
	*/
	@Override
	public java.util.List<com.liferay.changeset.model.Changeset> getChangesets(
		int start, int end) {
		return _changesetLocalService.getChangesets(start, end);
	}

	/**
	* Returns the number of changesets.
	*
	* @return the number of changesets
	*/
	@Override
	public int getChangesetsCount() {
		return _changesetLocalService.getChangesetsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _changesetLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _changesetLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _changesetLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the changeset in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param changeset the changeset
	* @return the changeset that was updated
	*/
	@Override
	public com.liferay.changeset.model.Changeset updateChangeset(
		com.liferay.changeset.model.Changeset changeset) {
		return _changesetLocalService.updateChangeset(changeset);
	}

	@Override
	public ChangesetLocalService getWrappedService() {
		return _changesetLocalService;
	}

	@Override
	public void setWrappedService(ChangesetLocalService changesetLocalService) {
		_changesetLocalService = changesetLocalService;
	}

	private ChangesetLocalService _changesetLocalService;
}