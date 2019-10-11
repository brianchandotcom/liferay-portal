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

package com.liferay.data.engine.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DEDataDefinitionFieldLinkLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DEDataDefinitionFieldLinkLocalService
 * @generated
 */
public class DEDataDefinitionFieldLinkLocalServiceWrapper
	implements DEDataDefinitionFieldLinkLocalService,
			   ServiceWrapper<DEDataDefinitionFieldLinkLocalService> {

	public DEDataDefinitionFieldLinkLocalServiceWrapper(
		DEDataDefinitionFieldLinkLocalService
			deDataDefinitionFieldLinkLocalService) {

		_deDataDefinitionFieldLinkLocalService =
			deDataDefinitionFieldLinkLocalService;
	}

	/**
	 * Adds the de data definition field link to the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataDefinitionFieldLink the de data definition field link
	 * @return the de data definition field link that was added
	 */
	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
		addDEDataDefinitionFieldLink(
			com.liferay.data.engine.model.DEDataDefinitionFieldLink
				deDataDefinitionFieldLink) {

		return _deDataDefinitionFieldLinkLocalService.
			addDEDataDefinitionFieldLink(deDataDefinitionFieldLink);
	}

	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
		addDEDataDefinitionFieldLink(
			long groupId, long classNameId, long classPK, long ddmStructureId,
			String fieldName) {

		return _deDataDefinitionFieldLinkLocalService.
			addDEDataDefinitionFieldLink(
				groupId, classNameId, classPK, ddmStructureId, fieldName);
	}

	/**
	 * Creates a new de data definition field link with the primary key. Does not add the de data definition field link to the database.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key for the new de data definition field link
	 * @return the new de data definition field link
	 */
	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
		createDEDataDefinitionFieldLink(long deDataDefinitionFieldLinkId) {

		return _deDataDefinitionFieldLinkLocalService.
			createDEDataDefinitionFieldLink(deDataDefinitionFieldLinkId);
	}

	/**
	 * Deletes the de data definition field link from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataDefinitionFieldLink the de data definition field link
	 * @return the de data definition field link that was removed
	 */
	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
		deleteDEDataDefinitionFieldLink(
			com.liferay.data.engine.model.DEDataDefinitionFieldLink
				deDataDefinitionFieldLink) {

		return _deDataDefinitionFieldLinkLocalService.
			deleteDEDataDefinitionFieldLink(deDataDefinitionFieldLink);
	}

	/**
	 * Deletes the de data definition field link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the de data definition field link
	 * @return the de data definition field link that was removed
	 * @throws PortalException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
			deleteDEDataDefinitionFieldLink(long deDataDefinitionFieldLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataDefinitionFieldLinkLocalService.
			deleteDEDataDefinitionFieldLink(deDataDefinitionFieldLinkId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataDefinitionFieldLinkLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _deDataDefinitionFieldLinkLocalService.dynamicQuery();
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

		return _deDataDefinitionFieldLinkLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.data.engine.model.impl.DEDataDefinitionFieldLinkModelImpl</code>.
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

		return _deDataDefinitionFieldLinkLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.data.engine.model.impl.DEDataDefinitionFieldLinkModelImpl</code>.
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

		return _deDataDefinitionFieldLinkLocalService.dynamicQuery(
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

		return _deDataDefinitionFieldLinkLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _deDataDefinitionFieldLinkLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
		fetchDEDataDefinitionFieldLink(long deDataDefinitionFieldLinkId) {

		return _deDataDefinitionFieldLinkLocalService.
			fetchDEDataDefinitionFieldLink(deDataDefinitionFieldLinkId);
	}

	/**
	 * Returns the de data definition field link matching the UUID and group.
	 *
	 * @param uuid the de data definition field link's UUID
	 * @param groupId the primary key of the group
	 * @return the matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
		fetchDEDataDefinitionFieldLinkByUuidAndGroupId(
			String uuid, long groupId) {

		return _deDataDefinitionFieldLinkLocalService.
			fetchDEDataDefinitionFieldLinkByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _deDataDefinitionFieldLinkLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the de data definition field link with the primary key.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the de data definition field link
	 * @return the de data definition field link
	 * @throws PortalException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
			getDEDataDefinitionFieldLink(long deDataDefinitionFieldLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataDefinitionFieldLinkLocalService.
			getDEDataDefinitionFieldLink(deDataDefinitionFieldLinkId);
	}

	/**
	 * Returns the de data definition field link matching the UUID and group.
	 *
	 * @param uuid the de data definition field link's UUID
	 * @param groupId the primary key of the group
	 * @return the matching de data definition field link
	 * @throws PortalException if a matching de data definition field link could not be found
	 */
	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
			getDEDataDefinitionFieldLinkByUuidAndGroupId(
				String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataDefinitionFieldLinkLocalService.
			getDEDataDefinitionFieldLinkByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the de data definition field links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.data.engine.model.impl.DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of de data definition field links
	 */
	@Override
	public java.util.List
		<com.liferay.data.engine.model.DEDataDefinitionFieldLink>
			getDEDataDefinitionFieldLinks(int start, int end) {

		return _deDataDefinitionFieldLinkLocalService.
			getDEDataDefinitionFieldLinks(start, end);
	}

	/**
	 * Returns the number of de data definition field links.
	 *
	 * @return the number of de data definition field links
	 */
	@Override
	public int getDEDataDefinitionFieldLinksCount() {
		return _deDataDefinitionFieldLinkLocalService.
			getDEDataDefinitionFieldLinksCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _deDataDefinitionFieldLinkLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _deDataDefinitionFieldLinkLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _deDataDefinitionFieldLinkLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the de data definition field link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param deDataDefinitionFieldLink the de data definition field link
	 * @return the de data definition field link that was updated
	 */
	@Override
	public com.liferay.data.engine.model.DEDataDefinitionFieldLink
		updateDEDataDefinitionFieldLink(
			com.liferay.data.engine.model.DEDataDefinitionFieldLink
				deDataDefinitionFieldLink) {

		return _deDataDefinitionFieldLinkLocalService.
			updateDEDataDefinitionFieldLink(deDataDefinitionFieldLink);
	}

	@Override
	public DEDataDefinitionFieldLinkLocalService getWrappedService() {
		return _deDataDefinitionFieldLinkLocalService;
	}

	@Override
	public void setWrappedService(
		DEDataDefinitionFieldLinkLocalService
			deDataDefinitionFieldLinkLocalService) {

		_deDataDefinitionFieldLinkLocalService =
			deDataDefinitionFieldLinkLocalService;
	}

	private DEDataDefinitionFieldLinkLocalService
		_deDataDefinitionFieldLinkLocalService;

}