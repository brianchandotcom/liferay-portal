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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMStructureLayoutLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayoutLocalService
 * @generated
 */
@ProviderType
public class DDMStructureLayoutLocalServiceWrapper
	implements DDMStructureLayoutLocalService,
		ServiceWrapper<DDMStructureLayoutLocalService> {
	public DDMStructureLayoutLocalServiceWrapper(
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormLayout getStructureLayoutDDMFormLayout(
		com.liferay.dynamic.data.mapping.model.DDMStructureLayout structureLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.getStructureLayoutDDMFormLayout(structureLayout);
	}

	/**
	* Adds the ddm structure layout to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureLayout the ddm structure layout
	* @return the ddm structure layout that was added
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout addDDMStructureLayout(
		com.liferay.dynamic.data.mapping.model.DDMStructureLayout ddmStructureLayout) {
		return _ddmStructureLayoutLocalService.addDDMStructureLayout(ddmStructureLayout);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout addStructureLayout(
		long userId, long groupId, long structureVersionId,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.addStructureLayout(userId,
			groupId, structureVersionId, ddmFormLayout, serviceContext);
	}

	/**
	* Creates a new ddm structure layout with the primary key. Does not add the ddm structure layout to the database.
	*
	* @param structureLayoutId the primary key for the new ddm structure layout
	* @return the new ddm structure layout
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout createDDMStructureLayout(
		long structureLayoutId) {
		return _ddmStructureLayoutLocalService.createDDMStructureLayout(structureLayoutId);
	}

	/**
	* Deletes the ddm structure layout from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureLayout the ddm structure layout
	* @return the ddm structure layout that was removed
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout deleteDDMStructureLayout(
		com.liferay.dynamic.data.mapping.model.DDMStructureLayout ddmStructureLayout) {
		return _ddmStructureLayoutLocalService.deleteDDMStructureLayout(ddmStructureLayout);
	}

	/**
	* Deletes the ddm structure layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureLayoutId the primary key of the ddm structure layout
	* @return the ddm structure layout that was removed
	* @throws PortalException if a ddm structure layout with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout deleteDDMStructureLayout(
		long structureLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.deleteDDMStructureLayout(structureLayoutId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout fetchDDMStructureLayout(
		long structureLayoutId) {
		return _ddmStructureLayoutLocalService.fetchDDMStructureLayout(structureLayoutId);
	}

	/**
	* Returns the ddm structure layout matching the UUID and group.
	*
	* @param uuid the ddm structure layout's UUID
	* @param groupId the primary key of the group
	* @return the matching ddm structure layout, or <code>null</code> if a matching ddm structure layout could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout fetchDDMStructureLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _ddmStructureLayoutLocalService.fetchDDMStructureLayoutByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the ddm structure layout with the primary key.
	*
	* @param structureLayoutId the primary key of the ddm structure layout
	* @return the ddm structure layout
	* @throws PortalException if a ddm structure layout with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout getDDMStructureLayout(
		long structureLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.getDDMStructureLayout(structureLayoutId);
	}

	/**
	* Returns the ddm structure layout matching the UUID and group.
	*
	* @param uuid the ddm structure layout's UUID
	* @param groupId the primary key of the group
	* @return the matching ddm structure layout
	* @throws PortalException if a matching ddm structure layout could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout getDDMStructureLayoutByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.getDDMStructureLayoutByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout getStructureLayout(
		long structureLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.getStructureLayout(structureLayoutId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout getStructureLayoutByStructureVersionId(
		long structureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.getStructureLayoutByStructureVersionId(structureVersionId);
	}

	/**
	* Updates the ddm structure layout in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureLayout the ddm structure layout
	* @return the ddm structure layout that was updated
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout updateDDMStructureLayout(
		com.liferay.dynamic.data.mapping.model.DDMStructureLayout ddmStructureLayout) {
		return _ddmStructureLayoutLocalService.updateDDMStructureLayout(ddmStructureLayout);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout updateStructureLayout(
		long structureLayoutId,
		com.liferay.dynamic.data.mapping.model.DDMFormLayout ddmFormLayout,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.updateStructureLayout(structureLayoutId,
			ddmFormLayout, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ddmStructureLayoutLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmStructureLayoutLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _ddmStructureLayoutLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ddmStructureLayoutLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureLayoutLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of ddm structure layouts.
	*
	* @return the number of ddm structure layouts
	*/
	@Override
	public int getDDMStructureLayoutsCount() {
		return _ddmStructureLayoutLocalService.getDDMStructureLayoutsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmStructureLayoutLocalService.getOSGiServiceIdentifier();
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
		return _ddmStructureLayoutLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmStructureLayoutLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmStructureLayoutLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the ddm structure layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMStructureLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddm structure layouts
	* @param end the upper bound of the range of ddm structure layouts (not inclusive)
	* @return the range of ddm structure layouts
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureLayout> getDDMStructureLayouts(
		int start, int end) {
		return _ddmStructureLayoutLocalService.getDDMStructureLayouts(start, end);
	}

	/**
	* Returns all the ddm structure layouts matching the UUID and company.
	*
	* @param uuid the UUID of the ddm structure layouts
	* @param companyId the primary key of the company
	* @return the matching ddm structure layouts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureLayout> getDDMStructureLayoutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _ddmStructureLayoutLocalService.getDDMStructureLayoutsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of ddm structure layouts matching the UUID and company.
	*
	* @param uuid the UUID of the ddm structure layouts
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of ddm structure layouts
	* @param end the upper bound of the range of ddm structure layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching ddm structure layouts, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMStructureLayout> getDDMStructureLayoutsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMStructureLayout> orderByComparator) {
		return _ddmStructureLayoutLocalService.getDDMStructureLayoutsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
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
		return _ddmStructureLayoutLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ddmStructureLayoutLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteStructureLayout(
		com.liferay.dynamic.data.mapping.model.DDMStructureLayout structureLayout) {
		_ddmStructureLayoutLocalService.deleteStructureLayout(structureLayout);
	}

	@Override
	public void deleteStructureLayout(long structureLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddmStructureLayoutLocalService.deleteStructureLayout(structureLayoutId);
	}

	@Override
	public DDMStructureLayoutLocalService getWrappedService() {
		return _ddmStructureLayoutLocalService;
	}

	@Override
	public void setWrappedService(
		DDMStructureLayoutLocalService ddmStructureLayoutLocalService) {
		_ddmStructureLayoutLocalService = ddmStructureLayoutLocalService;
	}

	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;
}