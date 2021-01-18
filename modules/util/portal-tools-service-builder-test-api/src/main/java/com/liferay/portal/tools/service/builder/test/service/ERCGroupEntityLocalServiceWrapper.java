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
 * Provides a wrapper for {@link ERCGroupEntityLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntityLocalService
 * @generated
 */
public class ERCGroupEntityLocalServiceWrapper
	implements ERCGroupEntityLocalService,
			   ServiceWrapper<ERCGroupEntityLocalService> {

	public ERCGroupEntityLocalServiceWrapper(
		ERCGroupEntityLocalService ercGroupEntityLocalService) {

		_ercGroupEntityLocalService = ercGroupEntityLocalService;
	}

	/**
	 * Adds the erc group entity to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntity the erc group entity
	 * @return the erc group entity that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
		addERCGroupEntity(
			com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
				ercGroupEntity) {

		return _ercGroupEntityLocalService.addERCGroupEntity(ercGroupEntity);
	}

	/**
	 * Creates a new erc group entity with the primary key. Does not add the erc group entity to the database.
	 *
	 * @param ercGroupEntityId the primary key for the new erc group entity
	 * @return the new erc group entity
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
		createERCGroupEntity(long ercGroupEntityId) {

		return _ercGroupEntityLocalService.createERCGroupEntity(
			ercGroupEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntityLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the erc group entity from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntity the erc group entity
	 * @return the erc group entity that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
		deleteERCGroupEntity(
			com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
				ercGroupEntity) {

		return _ercGroupEntityLocalService.deleteERCGroupEntity(ercGroupEntity);
	}

	/**
	 * Deletes the erc group entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity that was removed
	 * @throws PortalException if a erc group entity with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
			deleteERCGroupEntity(long ercGroupEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntityLocalService.deleteERCGroupEntity(
			ercGroupEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntityLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ercGroupEntityLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ercGroupEntityLocalService.dynamicQuery();
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

		return _ercGroupEntityLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntityModelImpl</code>.
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

		return _ercGroupEntityLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntityModelImpl</code>.
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

		return _ercGroupEntityLocalService.dynamicQuery(
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

		return _ercGroupEntityLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ercGroupEntityLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
		fetchERCGroupEntity(long ercGroupEntityId) {

		return _ercGroupEntityLocalService.fetchERCGroupEntity(
			ercGroupEntityId);
	}

	/**
	 * Returns the erc group entity with the matching external reference code and group.
	 *
	 * @param groupId the primary key of the group
	 * @param externalReferenceCode the erc group entity's external reference code
	 * @return the matching erc group entity, or <code>null</code> if a matching erc group entity could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
		fetchERCGroupEntityByReferenceCode(
			long groupId, String externalReferenceCode) {

		return _ercGroupEntityLocalService.fetchERCGroupEntityByReferenceCode(
			groupId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ercGroupEntityLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the erc group entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCGroupEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc group entities
	 * @param end the upper bound of the range of erc group entities (not inclusive)
	 * @return the range of erc group entities
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity>
			getERCGroupEntities(int start, int end) {

		return _ercGroupEntityLocalService.getERCGroupEntities(start, end);
	}

	/**
	 * Returns the number of erc group entities.
	 *
	 * @return the number of erc group entities
	 */
	@Override
	public int getERCGroupEntitiesCount() {
		return _ercGroupEntityLocalService.getERCGroupEntitiesCount();
	}

	/**
	 * Returns the erc group entity with the primary key.
	 *
	 * @param ercGroupEntityId the primary key of the erc group entity
	 * @return the erc group entity
	 * @throws PortalException if a erc group entity with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
			getERCGroupEntity(long ercGroupEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntityLocalService.getERCGroupEntity(ercGroupEntityId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ercGroupEntityLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ercGroupEntityLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercGroupEntityLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the erc group entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCGroupEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercGroupEntity the erc group entity
	 * @return the erc group entity that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
		updateERCGroupEntity(
			com.liferay.portal.tools.service.builder.test.model.ERCGroupEntity
				ercGroupEntity) {

		return _ercGroupEntityLocalService.updateERCGroupEntity(ercGroupEntity);
	}

	@Override
	public ERCGroupEntityLocalService getWrappedService() {
		return _ercGroupEntityLocalService;
	}

	@Override
	public void setWrappedService(
		ERCGroupEntityLocalService ercGroupEntityLocalService) {

		_ercGroupEntityLocalService = ercGroupEntityLocalService;
	}

	private ERCGroupEntityLocalService _ercGroupEntityLocalService;

}