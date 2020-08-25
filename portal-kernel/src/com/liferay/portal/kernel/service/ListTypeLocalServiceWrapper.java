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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link ListTypeLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ListTypeLocalService
 * @generated
 */
public class ListTypeLocalServiceWrapper
	implements ListTypeLocalService, ServiceWrapper<ListTypeLocalService> {

	public ListTypeLocalServiceWrapper(
		ListTypeLocalService listTypeLocalService) {

		_listTypeLocalService = listTypeLocalService;
	}

	/**
	 * Adds the list type to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ListTypeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param listType the list type
	 * @return the list type that was added
	 */
	@Override
	public com.liferay.portal.kernel.model.ListType addListType(
		com.liferay.portal.kernel.model.ListType listType) {

		return _listTypeLocalService.addListType(listType);
	}

	@Override
	public com.liferay.portal.kernel.model.ListType addListType(
		java.lang.String name, java.lang.String type) {

		return _listTypeLocalService.addListType(name, type);
	}

	/**
	 * Creates a new list type with the primary key. Does not add the list type to the database.
	 *
	 * @param listTypeId the primary key for the new list type
	 * @return the new list type
	 */
	@Override
	public com.liferay.portal.kernel.model.ListType createListType(
		long listTypeId) {

		return _listTypeLocalService.createListType(listTypeId);
	}

	/**
	 * Deletes the list type from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ListTypeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param listType the list type
	 * @return the list type that was removed
	 */
	@Override
	public com.liferay.portal.kernel.model.ListType deleteListType(
		com.liferay.portal.kernel.model.ListType listType) {

		return _listTypeLocalService.deleteListType(listType);
	}

	/**
	 * Deletes the list type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ListTypeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type that was removed
	 * @throws PortalException if a list type with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.ListType deleteListType(
			long listTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeLocalService.deleteListType(listTypeId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _listTypeLocalService.dynamicQuery();
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

		return _listTypeLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ListTypeModelImpl</code>.
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

		return _listTypeLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ListTypeModelImpl</code>.
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

		return _listTypeLocalService.dynamicQuery(
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

		return _listTypeLocalService.dynamicQueryCount(dynamicQuery);
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

		return _listTypeLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.kernel.model.ListType fetchListType(
		long listTypeId) {

		return _listTypeLocalService.fetchListType(listTypeId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _listTypeLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _listTypeLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the list type with the primary key.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type
	 * @throws PortalException if a list type with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.kernel.model.ListType getListType(long listTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeLocalService.getListType(listTypeId);
	}

	@Override
	public com.liferay.portal.kernel.model.ListType getListType(
		java.lang.String name, java.lang.String type) {

		return _listTypeLocalService.getListType(name, type);
	}

	/**
	 * Returns a range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of list types
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.model.ListType>
		getListTypes(int start, int end) {

		return _listTypeLocalService.getListTypes(start, end);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.ListType>
		getListTypes(java.lang.String type) {

		return _listTypeLocalService.getListTypes(type);
	}

	/**
	 * Returns the number of list types.
	 *
	 * @return the number of list types
	 */
	@Override
	public int getListTypesCount() {
		return _listTypeLocalService.getListTypesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _listTypeLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _listTypeLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the list type in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ListTypeLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param listType the list type
	 * @return the list type that was updated
	 */
	@Override
	public com.liferay.portal.kernel.model.ListType updateListType(
		com.liferay.portal.kernel.model.ListType listType) {

		return _listTypeLocalService.updateListType(listType);
	}

	@Override
	public void validate(
			long listTypeId, long classNameId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		_listTypeLocalService.validate(listTypeId, classNameId, type);
	}

	@Override
	public void validate(long listTypeId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		_listTypeLocalService.validate(listTypeId, type);
	}

	@Override
	public ListTypeLocalService getWrappedService() {
		return _listTypeLocalService;
	}

	@Override
	public void setWrappedService(ListTypeLocalService listTypeLocalService) {
		_listTypeLocalService = listTypeLocalService;
	}

	private ListTypeLocalService _listTypeLocalService;

}