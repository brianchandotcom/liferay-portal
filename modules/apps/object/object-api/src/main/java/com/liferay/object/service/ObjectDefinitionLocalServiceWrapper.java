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

package com.liferay.object.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ObjectDefinitionLocalService}.
 *
 * @author Marco Leo
 * @see ObjectDefinitionLocalService
 * @generated
 */
public class ObjectDefinitionLocalServiceWrapper
	implements ObjectDefinitionLocalService,
			   ServiceWrapper<ObjectDefinitionLocalService> {

	public ObjectDefinitionLocalServiceWrapper(
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	@Override
	public com.liferay.object.model.ObjectDefinition addObjectDefinition(
			long userId, String name,
			java.util.List<com.liferay.object.model.ObjectField> objectFields)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionLocalService.addObjectDefinition(
			userId, name, objectFields);
	}

	/**
	 * Adds the object definition to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectDefinition the object definition
	 * @return the object definition that was added
	 */
	@Override
	public com.liferay.object.model.ObjectDefinition addObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		return _objectDefinitionLocalService.addObjectDefinition(
			objectDefinition);
	}

	/**
	 * Creates a new object definition with the primary key. Does not add the object definition to the database.
	 *
	 * @param objectDefinitionId the primary key for the new object definition
	 * @return the new object definition
	 */
	@Override
	public com.liferay.object.model.ObjectDefinition createObjectDefinition(
		long objectDefinitionId) {

		return _objectDefinitionLocalService.createObjectDefinition(
			objectDefinitionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the object definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectDefinitionId the primary key of the object definition
	 * @return the object definition that was removed
	 * @throws PortalException if a object definition with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectDefinition deleteObjectDefinition(
			long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinitionId);
	}

	/**
	 * Deletes the object definition from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectDefinition the object definition
	 * @return the object definition that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.object.model.ObjectDefinition deleteObjectDefinition(
			com.liferay.object.model.ObjectDefinition objectDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionLocalService.deleteObjectDefinition(
			objectDefinition);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public void deployObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		_objectDefinitionLocalService.deployObjectDefinition(objectDefinition);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _objectDefinitionLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _objectDefinitionLocalService.dynamicQuery();
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

		return _objectDefinitionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectDefinitionModelImpl</code>.
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

		return _objectDefinitionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectDefinitionModelImpl</code>.
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

		return _objectDefinitionLocalService.dynamicQuery(
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

		return _objectDefinitionLocalService.dynamicQueryCount(dynamicQuery);
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

		return _objectDefinitionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.object.model.ObjectDefinition fetchObjectDefinition(
		long objectDefinitionId) {

		return _objectDefinitionLocalService.fetchObjectDefinition(
			objectDefinitionId);
	}

	/**
	 * Returns the object definition with the matching UUID and company.
	 *
	 * @param uuid the object definition's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectDefinition
		fetchObjectDefinitionByUuidAndCompanyId(String uuid, long companyId) {

		return _objectDefinitionLocalService.
			fetchObjectDefinitionByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _objectDefinitionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _objectDefinitionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectDefinitionLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the object definition with the primary key.
	 *
	 * @param objectDefinitionId the primary key of the object definition
	 * @return the object definition
	 * @throws PortalException if a object definition with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectDefinition getObjectDefinition(
			long objectDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionLocalService.getObjectDefinition(
			objectDefinitionId);
	}

	/**
	 * Returns the object definition with the matching UUID and company.
	 *
	 * @param uuid the object definition's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object definition
	 * @throws PortalException if a matching object definition could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectDefinition
			getObjectDefinitionByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionLocalService.
			getObjectDefinitionByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the object definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of object definitions
	 */
	@Override
	public java.util.List<com.liferay.object.model.ObjectDefinition>
		getObjectDefinitions(int start, int end) {

		return _objectDefinitionLocalService.getObjectDefinitions(start, end);
	}

	/**
	 * Returns the number of object definitions.
	 *
	 * @return the number of object definitions
	 */
	@Override
	public int getObjectDefinitionsCount() {
		return _objectDefinitionLocalService.getObjectDefinitionsCount();
	}

	@Override
	public int getObjectDefinitionsCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionLocalService.getObjectDefinitionsCount(
			companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectDefinitionLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectDefinitionLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public void undeployObjectDefinition(long objectDefinitionId) {
		_objectDefinitionLocalService.undeployObjectDefinition(
			objectDefinitionId);
	}

	/**
	 * Updates the object definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectDefinition the object definition
	 * @return the object definition that was updated
	 */
	@Override
	public com.liferay.object.model.ObjectDefinition updateObjectDefinition(
		com.liferay.object.model.ObjectDefinition objectDefinition) {

		return _objectDefinitionLocalService.updateObjectDefinition(
			objectDefinition);
	}

	@Override
	public ObjectDefinitionLocalService getWrappedService() {
		return _objectDefinitionLocalService;
	}

	@Override
	public void setWrappedService(
		ObjectDefinitionLocalService objectDefinitionLocalService) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
	}

	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}