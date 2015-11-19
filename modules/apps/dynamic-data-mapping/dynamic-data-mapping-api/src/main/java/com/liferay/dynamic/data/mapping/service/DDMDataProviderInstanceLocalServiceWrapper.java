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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMDataProviderInstanceLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLocalService
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceLocalServiceWrapper
	implements DDMDataProviderInstanceLocalService,
		ServiceWrapper<DDMDataProviderInstanceLocalService> {
	public DDMDataProviderInstanceLocalServiceWrapper(
		DDMDataProviderInstanceLocalService ddmDataProviderInstanceLocalService) {
		_ddmDataProviderInstanceLocalService = ddmDataProviderInstanceLocalService;
	}

	/**
	* Adds the d d m data provider instance to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProviderInstance the d d m data provider instance
	* @return the d d m data provider instance that was added
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance addDDMDataProviderInstance(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance ddmDataProviderInstance) {
		return _ddmDataProviderInstanceLocalService.addDDMDataProviderInstance(ddmDataProviderInstance);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance addDataProviderInstance(
		long userId, long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLocalService.addDataProviderInstance(userId,
			groupId, nameMap, descriptionMap, ddmFormValues, type,
			serviceContext);
	}

	/**
	* Creates a new d d m data provider instance with the primary key. Does not add the d d m data provider instance to the database.
	*
	* @param dataProviderInstanceId the primary key for the new d d m data provider instance
	* @return the new d d m data provider instance
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance createDDMDataProviderInstance(
		long dataProviderInstanceId) {
		return _ddmDataProviderInstanceLocalService.createDDMDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Deletes the d d m data provider instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderInstanceId the primary key of the d d m data provider instance
	* @return the d d m data provider instance that was removed
	* @throws PortalException if a d d m data provider instance with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance deleteDDMDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLocalService.deleteDDMDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Deletes the d d m data provider instance from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProviderInstance the d d m data provider instance
	* @return the d d m data provider instance that was removed
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance deleteDDMDataProviderInstance(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance ddmDataProviderInstance) {
		return _ddmDataProviderInstanceLocalService.deleteDDMDataProviderInstance(ddmDataProviderInstance);
	}

	@Override
	public void deleteDataProviderInstance(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance dataProviderInstance) {
		_ddmDataProviderInstanceLocalService.deleteDataProviderInstance(dataProviderInstance);
	}

	@Override
	public void deleteDataProviderInstance(long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_ddmDataProviderInstanceLocalService.deleteDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmDataProviderInstanceLocalService.dynamicQuery();
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
		return _ddmDataProviderInstanceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmDataProviderInstanceLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _ddmDataProviderInstanceLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _ddmDataProviderInstanceLocalService.dynamicQueryCount(dynamicQuery);
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
		return _ddmDataProviderInstanceLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance fetchDDMDataProviderInstance(
		long dataProviderInstanceId) {
		return _ddmDataProviderInstanceLocalService.fetchDDMDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Returns the d d m data provider instance matching the UUID and group.
	*
	* @param uuid the d d m data provider instance's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance fetchDDMDataProviderInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _ddmDataProviderInstanceLocalService.fetchDDMDataProviderInstanceByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _ddmDataProviderInstanceLocalService.getActionableDynamicQuery();
	}

	/**
	* Returns the d d m data provider instance with the primary key.
	*
	* @param dataProviderInstanceId the primary key of the d d m data provider instance
	* @return the d d m data provider instance
	* @throws PortalException if a d d m data provider instance with the primary key could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDDMDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLocalService.getDDMDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Returns the d d m data provider instance matching the UUID and group.
	*
	* @param uuid the d d m data provider instance's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider instance
	* @throws PortalException if a matching d d m data provider instance could not be found
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDDMDataProviderInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLocalService.getDDMDataProviderInstanceByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns a range of all the d d m data provider instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @return the range of d d m data provider instances
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> getDDMDataProviderInstances(
		int start, int end) {
		return _ddmDataProviderInstanceLocalService.getDDMDataProviderInstances(start,
			end);
	}

	/**
	* Returns all the d d m data provider instances matching the UUID and company.
	*
	* @param uuid the UUID of the d d m data provider instances
	* @param companyId the primary key of the company
	* @return the matching d d m data provider instances, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> getDDMDataProviderInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _ddmDataProviderInstanceLocalService.getDDMDataProviderInstancesByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of d d m data provider instances matching the UUID and company.
	*
	* @param uuid the UUID of the d d m data provider instances
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d m data provider instances
	* @param end the upper bound of the range of d d m data provider instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d m data provider instances, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> getDDMDataProviderInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return _ddmDataProviderInstanceLocalService.getDDMDataProviderInstancesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of d d m data provider instances.
	*
	* @return the number of d d m data provider instances
	*/
	@Override
	public int getDDMDataProviderInstancesCount() {
		return _ddmDataProviderInstanceLocalService.getDDMDataProviderInstancesCount();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLocalService.getDataProviderInstance(dataProviderInstanceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return _ddmDataProviderInstanceLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _ddmDataProviderInstanceLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddmDataProviderInstanceLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return _ddmDataProviderInstanceLocalService.search(companyId, groupIds,
			keywords, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return _ddmDataProviderInstanceLocalService.search(companyId, groupIds,
			name, description, andOperator, start, end, orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords) {
		return _ddmDataProviderInstanceLocalService.searchCount(companyId,
			groupIds, keywords);
	}

	@Override
	public int searchCount(long companyId, long[] groupIds,
		java.lang.String name, java.lang.String description, boolean andOperator) {
		return _ddmDataProviderInstanceLocalService.searchCount(companyId,
			groupIds, name, description, andOperator);
	}

	/**
	* Updates the d d m data provider instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProviderInstance the d d m data provider instance
	* @return the d d m data provider instance that was updated
	*/
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance updateDDMDataProviderInstance(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance ddmDataProviderInstance) {
		return _ddmDataProviderInstanceLocalService.updateDDMDataProviderInstance(ddmDataProviderInstance);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance updateDataProviderInstance(
		long userId, long dataProviderInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmDataProviderInstanceLocalService.updateDataProviderInstance(userId,
			dataProviderInstanceId, nameMap, descriptionMap, ddmFormValues,
			serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDMDataProviderInstanceLocalService getWrappedDDMDataProviderInstanceLocalService() {
		return _ddmDataProviderInstanceLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDMDataProviderInstanceLocalService(
		DDMDataProviderInstanceLocalService ddmDataProviderInstanceLocalService) {
		_ddmDataProviderInstanceLocalService = ddmDataProviderInstanceLocalService;
	}

	@Override
	public DDMDataProviderInstanceLocalService getWrappedService() {
		return _ddmDataProviderInstanceLocalService;
	}

	@Override
	public void setWrappedService(
		DDMDataProviderInstanceLocalService ddmDataProviderInstanceLocalService) {
		_ddmDataProviderInstanceLocalService = ddmDataProviderInstanceLocalService;
	}

	private DDMDataProviderInstanceLocalService _ddmDataProviderInstanceLocalService;
}