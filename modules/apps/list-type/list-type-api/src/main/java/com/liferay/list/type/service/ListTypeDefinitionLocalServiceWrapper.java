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

package com.liferay.list.type.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ListTypeDefinitionLocalService}.
 *
 * @author Gabriel Albuquerque
 * @see ListTypeDefinitionLocalService
 * @generated
 */
public class ListTypeDefinitionLocalServiceWrapper
	implements ListTypeDefinitionLocalService,
			   ServiceWrapper<ListTypeDefinitionLocalService> {

	public ListTypeDefinitionLocalServiceWrapper(
		ListTypeDefinitionLocalService listTypeDefinitionLocalService) {

		_listTypeDefinitionLocalService = listTypeDefinitionLocalService;
	}

	/**
	 * Adds the list type definition to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ListTypeDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param listTypeDefinition the list type definition
	 * @return the list type definition that was added
	 */
	@Override
	public com.liferay.list.type.model.ListTypeDefinition addListTypeDefinition(
		com.liferay.list.type.model.ListTypeDefinition listTypeDefinition) {

		return _listTypeDefinitionLocalService.addListTypeDefinition(
			listTypeDefinition);
	}

	@Override
	public com.liferay.list.type.model.ListTypeDefinition addListTypeDefinition(
			long userId, java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionLocalService.addListTypeDefinition(
			userId, nameMap);
	}

	/**
	 * Creates a new list type definition with the primary key. Does not add the list type definition to the database.
	 *
	 * @param listTypeDefinitionId the primary key for the new list type definition
	 * @return the new list type definition
	 */
	@Override
	public com.liferay.list.type.model.ListTypeDefinition
		createListTypeDefinition(long listTypeDefinitionId) {

		return _listTypeDefinitionLocalService.createListTypeDefinition(
			listTypeDefinitionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the list type definition from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ListTypeDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param listTypeDefinition the list type definition
	 * @return the list type definition that was removed
	 * @throws PortalException
	 */
	@Override
	public com.liferay.list.type.model.ListTypeDefinition
			deleteListTypeDefinition(
				com.liferay.list.type.model.ListTypeDefinition
					listTypeDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinition);
	}

	/**
	 * Deletes the list type definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ListTypeDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param listTypeDefinitionId the primary key of the list type definition
	 * @return the list type definition that was removed
	 * @throws PortalException if a list type definition with the primary key could not be found
	 */
	@Override
	public com.liferay.list.type.model.ListTypeDefinition
			deleteListTypeDefinition(long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionLocalService.deleteListTypeDefinition(
			listTypeDefinitionId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _listTypeDefinitionLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _listTypeDefinitionLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _listTypeDefinitionLocalService.dynamicQuery();
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

		return _listTypeDefinitionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.list.type.model.impl.ListTypeDefinitionModelImpl</code>.
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

		return _listTypeDefinitionLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.list.type.model.impl.ListTypeDefinitionModelImpl</code>.
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

		return _listTypeDefinitionLocalService.dynamicQuery(
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

		return _listTypeDefinitionLocalService.dynamicQueryCount(dynamicQuery);
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

		return _listTypeDefinitionLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.list.type.model.ListTypeDefinition
		fetchListTypeDefinition(long listTypeDefinitionId) {

		return _listTypeDefinitionLocalService.fetchListTypeDefinition(
			listTypeDefinitionId);
	}

	/**
	 * Returns the list type definition with the matching UUID and company.
	 *
	 * @param uuid the list type definition's UUID
	 * @param companyId the primary key of the company
	 * @return the matching list type definition, or <code>null</code> if a matching list type definition could not be found
	 */
	@Override
	public com.liferay.list.type.model.ListTypeDefinition
		fetchListTypeDefinitionByUuidAndCompanyId(String uuid, long companyId) {

		return _listTypeDefinitionLocalService.
			fetchListTypeDefinitionByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _listTypeDefinitionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _listTypeDefinitionLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _listTypeDefinitionLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the list type definition with the primary key.
	 *
	 * @param listTypeDefinitionId the primary key of the list type definition
	 * @return the list type definition
	 * @throws PortalException if a list type definition with the primary key could not be found
	 */
	@Override
	public com.liferay.list.type.model.ListTypeDefinition getListTypeDefinition(
			long listTypeDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionLocalService.getListTypeDefinition(
			listTypeDefinitionId);
	}

	/**
	 * Returns the list type definition with the matching UUID and company.
	 *
	 * @param uuid the list type definition's UUID
	 * @param companyId the primary key of the company
	 * @return the matching list type definition
	 * @throws PortalException if a matching list type definition could not be found
	 */
	@Override
	public com.liferay.list.type.model.ListTypeDefinition
			getListTypeDefinitionByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionLocalService.
			getListTypeDefinitionByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the list type definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.list.type.model.impl.ListTypeDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type definitions
	 * @param end the upper bound of the range of list type definitions (not inclusive)
	 * @return the range of list type definitions
	 */
	@Override
	public java.util.List<com.liferay.list.type.model.ListTypeDefinition>
		getListTypeDefinitions(int start, int end) {

		return _listTypeDefinitionLocalService.getListTypeDefinitions(
			start, end);
	}

	/**
	 * Returns the number of list type definitions.
	 *
	 * @return the number of list type definitions
	 */
	@Override
	public int getListTypeDefinitionsCount() {
		return _listTypeDefinitionLocalService.getListTypeDefinitionsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _listTypeDefinitionLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the list type definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ListTypeDefinitionLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param listTypeDefinition the list type definition
	 * @return the list type definition that was updated
	 */
	@Override
	public com.liferay.list.type.model.ListTypeDefinition
		updateListTypeDefinition(
			com.liferay.list.type.model.ListTypeDefinition listTypeDefinition) {

		return _listTypeDefinitionLocalService.updateListTypeDefinition(
			listTypeDefinition);
	}

	@Override
	public com.liferay.list.type.model.ListTypeDefinition
			updateListTypeDefinition(
				long listTypeDefinitionId,
				java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeDefinitionLocalService.updateListTypeDefinition(
			listTypeDefinitionId, nameMap);
	}

	@Override
	public ListTypeDefinitionLocalService getWrappedService() {
		return _listTypeDefinitionLocalService;
	}

	@Override
	public void setWrappedService(
		ListTypeDefinitionLocalService listTypeDefinitionLocalService) {

		_listTypeDefinitionLocalService = listTypeDefinitionLocalService;
	}

	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

}