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
 * Provides a wrapper for {@link ERCCompanyEntityLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntityLocalService
 * @generated
 */
public class ERCCompanyEntityLocalServiceWrapper
	implements ERCCompanyEntityLocalService,
			   ServiceWrapper<ERCCompanyEntityLocalService> {

	public ERCCompanyEntityLocalServiceWrapper(
		ERCCompanyEntityLocalService ercCompanyEntityLocalService) {

		_ercCompanyEntityLocalService = ercCompanyEntityLocalService;
	}

	/**
	 * Adds the erc company entity to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntity the erc company entity
	 * @return the erc company entity that was added
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
		addERCCompanyEntity(
			com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
				ercCompanyEntity) {

		return _ercCompanyEntityLocalService.addERCCompanyEntity(
			ercCompanyEntity);
	}

	/**
	 * Creates a new erc company entity with the primary key. Does not add the erc company entity to the database.
	 *
	 * @param ercCompanyEntityId the primary key for the new erc company entity
	 * @return the new erc company entity
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
		createERCCompanyEntity(long ercCompanyEntityId) {

		return _ercCompanyEntityLocalService.createERCCompanyEntity(
			ercCompanyEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntityLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the erc company entity from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntity the erc company entity
	 * @return the erc company entity that was removed
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
		deleteERCCompanyEntity(
			com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
				ercCompanyEntity) {

		return _ercCompanyEntityLocalService.deleteERCCompanyEntity(
			ercCompanyEntity);
	}

	/**
	 * Deletes the erc company entity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity that was removed
	 * @throws PortalException if a erc company entity with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
			deleteERCCompanyEntity(long ercCompanyEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntityLocalService.deleteERCCompanyEntity(
			ercCompanyEntityId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntityLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _ercCompanyEntityLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ercCompanyEntityLocalService.dynamicQuery();
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

		return _ercCompanyEntityLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntityModelImpl</code>.
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

		return _ercCompanyEntityLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntityModelImpl</code>.
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

		return _ercCompanyEntityLocalService.dynamicQuery(
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

		return _ercCompanyEntityLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ercCompanyEntityLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
		fetchERCCompanyEntity(long ercCompanyEntityId) {

		return _ercCompanyEntityLocalService.fetchERCCompanyEntity(
			ercCompanyEntityId);
	}

	/**
	 * Returns the erc company entity with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the erc company entity's external reference code
	 * @return the matching erc company entity, or <code>null</code> if a matching erc company entity could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
		fetchERCCompanyEntityByReferenceCode(
			long companyId, String externalReferenceCode) {

		return _ercCompanyEntityLocalService.
			fetchERCCompanyEntityByReferenceCode(
				companyId, externalReferenceCode);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ercCompanyEntityLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the erc company entities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntityModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of erc company entities
	 * @param end the upper bound of the range of erc company entities (not inclusive)
	 * @return the range of erc company entities
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity>
			getERCCompanyEntities(int start, int end) {

		return _ercCompanyEntityLocalService.getERCCompanyEntities(start, end);
	}

	/**
	 * Returns the number of erc company entities.
	 *
	 * @return the number of erc company entities
	 */
	@Override
	public int getERCCompanyEntitiesCount() {
		return _ercCompanyEntityLocalService.getERCCompanyEntitiesCount();
	}

	/**
	 * Returns the erc company entity with the primary key.
	 *
	 * @param ercCompanyEntityId the primary key of the erc company entity
	 * @return the erc company entity
	 * @throws PortalException if a erc company entity with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
			getERCCompanyEntity(long ercCompanyEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntityLocalService.getERCCompanyEntity(
			ercCompanyEntityId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ercCompanyEntityLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ercCompanyEntityLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ercCompanyEntityLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the erc company entity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ERCCompanyEntityLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param ercCompanyEntity the erc company entity
	 * @return the erc company entity that was updated
	 */
	@Override
	public com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
		updateERCCompanyEntity(
			com.liferay.portal.tools.service.builder.test.model.ERCCompanyEntity
				ercCompanyEntity) {

		return _ercCompanyEntityLocalService.updateERCCompanyEntity(
			ercCompanyEntity);
	}

	@Override
	public ERCCompanyEntityLocalService getWrappedService() {
		return _ercCompanyEntityLocalService;
	}

	@Override
	public void setWrappedService(
		ERCCompanyEntityLocalService ercCompanyEntityLocalService) {

		_ercCompanyEntityLocalService = ercCompanyEntityLocalService;
	}

	private ERCCompanyEntityLocalService _ercCompanyEntityLocalService;

}