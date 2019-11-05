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

package com.liferay.depot.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DepotEntryGroupRelLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryGroupRelLocalService
 * @generated
 */
public class DepotEntryGroupRelLocalServiceWrapper
	implements DepotEntryGroupRelLocalService,
			   ServiceWrapper<DepotEntryGroupRelLocalService> {

	public DepotEntryGroupRelLocalServiceWrapper(
		DepotEntryGroupRelLocalService depotEntryGroupRelLocalService) {

		_depotEntryGroupRelLocalService = depotEntryGroupRelLocalService;
	}

	/**
	 * Adds the depot entry group rel to the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRel the depot entry group rel
	 * @return the depot entry group rel that was added
	 */
	@Override
	public com.liferay.depot.model.DepotEntryGroupRel addDepotEntryGroupRel(
		com.liferay.depot.model.DepotEntryGroupRel depotEntryGroupRel) {

		return _depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntryGroupRel);
	}

	@Override
	public com.liferay.depot.model.DepotEntryGroupRel addDepotEntryGroupRel(
		long depotEntryId, long toGroupId) {

		return _depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			depotEntryId, toGroupId);
	}

	/**
	 * Creates a new depot entry group rel with the primary key. Does not add the depot entry group rel to the database.
	 *
	 * @param depotEntryGroupRelId the primary key for the new depot entry group rel
	 * @return the new depot entry group rel
	 */
	@Override
	public com.liferay.depot.model.DepotEntryGroupRel createDepotEntryGroupRel(
		long depotEntryGroupRelId) {

		return _depotEntryGroupRelLocalService.createDepotEntryGroupRel(
			depotEntryGroupRelId);
	}

	/**
	 * Deletes the depot entry group rel from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRel the depot entry group rel
	 * @return the depot entry group rel that was removed
	 */
	@Override
	public com.liferay.depot.model.DepotEntryGroupRel deleteDepotEntryGroupRel(
		com.liferay.depot.model.DepotEntryGroupRel depotEntryGroupRel) {

		return _depotEntryGroupRelLocalService.deleteDepotEntryGroupRel(
			depotEntryGroupRel);
	}

	/**
	 * Deletes the depot entry group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel that was removed
	 * @throws PortalException if a depot entry group rel with the primary key could not be found
	 */
	@Override
	public com.liferay.depot.model.DepotEntryGroupRel deleteDepotEntryGroupRel(
			long depotEntryGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelLocalService.deleteDepotEntryGroupRel(
			depotEntryGroupRelId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public void deleteToGroupDepotEntryGroupRels(long toGroupId) {
		_depotEntryGroupRelLocalService.deleteToGroupDepotEntryGroupRels(
			toGroupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _depotEntryGroupRelLocalService.dynamicQuery();
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

		return _depotEntryGroupRelLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryGroupRelModelImpl</code>.
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

		return _depotEntryGroupRelLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryGroupRelModelImpl</code>.
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

		return _depotEntryGroupRelLocalService.dynamicQuery(
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

		return _depotEntryGroupRelLocalService.dynamicQueryCount(dynamicQuery);
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

		return _depotEntryGroupRelLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.depot.model.DepotEntryGroupRel fetchDepotEntryGroupRel(
		long depotEntryGroupRelId) {

		return _depotEntryGroupRelLocalService.fetchDepotEntryGroupRel(
			depotEntryGroupRelId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _depotEntryGroupRelLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the depot entry group rel with the primary key.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel
	 * @throws PortalException if a depot entry group rel with the primary key could not be found
	 */
	@Override
	public com.liferay.depot.model.DepotEntryGroupRel getDepotEntryGroupRel(
			long depotEntryGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelLocalService.getDepotEntryGroupRel(
			depotEntryGroupRelId);
	}

	@Override
	public java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
		getDepotEntryGroupRels(com.liferay.depot.model.DepotEntry depotEntry) {

		return _depotEntryGroupRelLocalService.getDepotEntryGroupRels(
			depotEntry);
	}

	/**
	 * Returns a range of all the depot entry group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.depot.model.impl.DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @return the range of depot entry group rels
	 */
	@Override
	public java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
		getDepotEntryGroupRels(int start, int end) {

		return _depotEntryGroupRelLocalService.getDepotEntryGroupRels(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.depot.model.DepotEntryGroupRel>
		getDepotEntryGroupRels(long groupId) {

		return _depotEntryGroupRelLocalService.getDepotEntryGroupRels(groupId);
	}

	/**
	 * Returns the number of depot entry group rels.
	 *
	 * @return the number of depot entry group rels
	 */
	@Override
	public int getDepotEntryGroupRelsCount() {
		return _depotEntryGroupRelLocalService.getDepotEntryGroupRelsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _depotEntryGroupRelLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _depotEntryGroupRelLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _depotEntryGroupRelLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the depot entry group rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRel the depot entry group rel
	 * @return the depot entry group rel that was updated
	 */
	@Override
	public com.liferay.depot.model.DepotEntryGroupRel updateDepotEntryGroupRel(
		com.liferay.depot.model.DepotEntryGroupRel depotEntryGroupRel) {

		return _depotEntryGroupRelLocalService.updateDepotEntryGroupRel(
			depotEntryGroupRel);
	}

	@Override
	public DepotEntryGroupRelLocalService getWrappedService() {
		return _depotEntryGroupRelLocalService;
	}

	@Override
	public void setWrappedService(
		DepotEntryGroupRelLocalService depotEntryGroupRelLocalService) {

		_depotEntryGroupRelLocalService = depotEntryGroupRelLocalService;
	}

	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

}