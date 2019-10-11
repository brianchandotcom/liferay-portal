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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMFormInstanceLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceLocalService
 * @generated
 */
public class DDMFormInstanceLocalServiceWrapper
	implements DDMFormInstanceLocalService,
			   ServiceWrapper<DDMFormInstanceLocalService> {

	public DDMFormInstanceLocalServiceWrapper(
		DDMFormInstanceLocalService ddmFormInstanceLocalService) {

		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
	}

	/**
	 * Adds the ddm form instance to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstance the ddm form instance
	 * @return the ddm form instance that was added
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
		addDDMFormInstance(
			com.liferay.dynamic.data.mapping.model.DDMFormInstance
				ddmFormInstance) {

		return _ddmFormInstanceLocalService.addDDMFormInstance(ddmFormInstance);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			addFormInstance(
				long userId, long groupId, long ddmStructureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.addFormInstance(
			userId, groupId, ddmStructureId, nameMap, descriptionMap,
			settingsDDMFormValues, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			addFormInstance(
				long userId, long groupId, long ddmStructureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String serializedSettingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.addFormInstance(
			userId, groupId, ddmStructureId, nameMap, descriptionMap,
			serializedSettingsDDMFormValues, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			addFormInstance(
				long userId, long groupId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.addFormInstance(
			userId, groupId, nameMap, descriptionMap, ddmForm, ddmFormLayout,
			settingsDDMFormValues, serviceContext);
	}

	@Override
	public void addFormInstanceResources(
			com.liferay.dynamic.data.mapping.model.DDMFormInstance
				ddmFormInstance,
			boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddmFormInstanceLocalService.addFormInstanceResources(
			ddmFormInstance, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFormInstanceResources(
			com.liferay.dynamic.data.mapping.model.DDMFormInstance
				ddmFormInstance,
			com.liferay.portal.kernel.service.permission.ModelPermissions
				modelPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddmFormInstanceLocalService.addFormInstanceResources(
			ddmFormInstance, modelPermissions);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #addFormInstanceResources(DDMFormInstance, ModelPermissions)}
	 */
	@Deprecated
	@Override
	public void addFormInstanceResources(
			com.liferay.dynamic.data.mapping.model.DDMFormInstance
				ddmFormInstance,
			String[] groupPermissions, String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddmFormInstanceLocalService.addFormInstanceResources(
			ddmFormInstance, groupPermissions, guestPermissions);
	}

	/**
	 * Creates a new ddm form instance with the primary key. Does not add the ddm form instance to the database.
	 *
	 * @param formInstanceId the primary key for the new ddm form instance
	 * @return the new ddm form instance
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
		createDDMFormInstance(long formInstanceId) {

		return _ddmFormInstanceLocalService.createDDMFormInstance(
			formInstanceId);
	}

	/**
	 * Deletes the ddm form instance from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstance the ddm form instance
	 * @return the ddm form instance that was removed
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
		deleteDDMFormInstance(
			com.liferay.dynamic.data.mapping.model.DDMFormInstance
				ddmFormInstance) {

		return _ddmFormInstanceLocalService.deleteDDMFormInstance(
			ddmFormInstance);
	}

	/**
	 * Deletes the ddm form instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceId the primary key of the ddm form instance
	 * @return the ddm form instance that was removed
	 * @throws PortalException if a ddm form instance with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			deleteDDMFormInstance(long formInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.deleteDDMFormInstance(
			formInstanceId);
	}

	@Override
	public void deleteFormInstance(
			com.liferay.dynamic.data.mapping.model.DDMFormInstance
				ddmFormInstance)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddmFormInstanceLocalService.deleteFormInstance(ddmFormInstance);
	}

	@Override
	public void deleteFormInstance(long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddmFormInstanceLocalService.deleteFormInstance(ddmFormInstanceId);
	}

	@Override
	public void deleteFormInstances(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_ddmFormInstanceLocalService.deleteFormInstances(groupId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _ddmFormInstanceLocalService.dynamicQuery();
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

		return _ddmFormInstanceLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceModelImpl</code>.
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

		return _ddmFormInstanceLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceModelImpl</code>.
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

		return _ddmFormInstanceLocalService.dynamicQuery(
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

		return _ddmFormInstanceLocalService.dynamicQueryCount(dynamicQuery);
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

		return _ddmFormInstanceLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
		fetchDDMFormInstance(long formInstanceId) {

		return _ddmFormInstanceLocalService.fetchDDMFormInstance(
			formInstanceId);
	}

	/**
	 * Returns the ddm form instance matching the UUID and group.
	 *
	 * @param uuid the ddm form instance's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm form instance, or <code>null</code> if a matching ddm form instance could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
		fetchDDMFormInstanceByUuidAndGroupId(String uuid, long groupId) {

		return _ddmFormInstanceLocalService.
			fetchDDMFormInstanceByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
		fetchFormInstance(long ddmFormInstanceId) {

		return _ddmFormInstanceLocalService.fetchFormInstance(
			ddmFormInstanceId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _ddmFormInstanceLocalService.getActionableDynamicQuery();
	}

	/**
	 * Returns the ddm form instance with the primary key.
	 *
	 * @param formInstanceId the primary key of the ddm form instance
	 * @return the ddm form instance
	 * @throws PortalException if a ddm form instance with the primary key could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			getDDMFormInstance(long formInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.getDDMFormInstance(formInstanceId);
	}

	/**
	 * Returns the ddm form instance matching the UUID and group.
	 *
	 * @param uuid the ddm form instance's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddm form instance
	 * @throws PortalException if a matching ddm form instance could not be found
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			getDDMFormInstanceByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.getDDMFormInstanceByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the ddm form instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @return the range of ddm form instances
	 */
	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
			getDDMFormInstances(int start, int end) {

		return _ddmFormInstanceLocalService.getDDMFormInstances(start, end);
	}

	/**
	 * Returns all the ddm form instances matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm form instances
	 * @param companyId the primary key of the company
	 * @return the matching ddm form instances, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
			getDDMFormInstancesByUuidAndCompanyId(String uuid, long companyId) {

		return _ddmFormInstanceLocalService.
			getDDMFormInstancesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of ddm form instances matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddm form instances
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddm form instances
	 * @param end the upper bound of the range of ddm form instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddm form instances, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
			getDDMFormInstancesByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
						orderByComparator) {

		return _ddmFormInstanceLocalService.
			getDDMFormInstancesByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ddm form instances.
	 *
	 * @return the number of ddm form instances
	 */
	@Override
	public int getDDMFormInstancesCount() {
		return _ddmFormInstanceLocalService.getDDMFormInstancesCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _ddmFormInstanceLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			getFormInstance(long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.getFormInstance(ddmFormInstanceId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			getFormInstance(String uuid, long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.getFormInstance(
			uuid, ddmFormInstanceId);
	}

	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
			getFormInstances(long groupId) {

		return _ddmFormInstanceLocalService.getFormInstances(groupId);
	}

	@Override
	public int getFormInstancesCount(long groupId) {
		return _ddmFormInstanceLocalService.getFormInstancesCount(groupId);
	}

	@Override
	public com.liferay.dynamic.data.mapping.storage.DDMFormValues
			getFormInstanceSettingsFormValues(
				com.liferay.dynamic.data.mapping.model.DDMFormInstance
					formInstance)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.getFormInstanceSettingsFormValues(
			formInstance);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings
			getFormInstanceSettingsModel(
				com.liferay.dynamic.data.mapping.model.DDMFormInstance
					formInstance)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.getFormInstanceSettingsModel(
			formInstance);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _ddmFormInstanceLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _ddmFormInstanceLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
			long companyId, long groupId, String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
					orderByComparator) {

		return _ddmFormInstanceLocalService.search(
			companyId, groupId, keywords, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstance> search(
			long companyId, long groupId, String[] names, String[] descriptions,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.mapping.model.DDMFormInstance>
					orderByComparator) {

		return _ddmFormInstanceLocalService.search(
			companyId, groupId, names, descriptions, andOperator, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(long companyId, long groupId, String keywords) {
		return _ddmFormInstanceLocalService.searchCount(
			companyId, groupId, keywords);
	}

	@Override
	public int searchCount(
		long companyId, long groupId, String[] names, String[] descriptions,
		boolean andOperator) {

		return _ddmFormInstanceLocalService.searchCount(
			companyId, groupId, names, descriptions, andOperator);
	}

	/**
	 * Updates the ddm form instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddmFormInstance the ddm form instance
	 * @return the ddm form instance that was updated
	 */
	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
		updateDDMFormInstance(
			com.liferay.dynamic.data.mapping.model.DDMFormInstance
				ddmFormInstance) {

		return _ddmFormInstanceLocalService.updateDDMFormInstance(
			ddmFormInstance);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			updateFormInstance(
				long formInstanceId,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.updateFormInstance(
			formInstanceId, settingsDDMFormValues);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			updateFormInstance(
				long userId, long ddmFormInstanceId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.model.DDMForm ddmForm,
				com.liferay.dynamic.data.mapping.model.DDMFormLayout
					ddmFormLayout,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.updateFormInstance(
			userId, ddmFormInstanceId, nameMap, descriptionMap, ddmForm,
			ddmFormLayout, settingsDDMFormValues, serviceContext);
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormInstance
			updateFormInstance(
				long ddmFormInstanceId, long ddmStructureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _ddmFormInstanceLocalService.updateFormInstance(
			ddmFormInstanceId, ddmStructureId, nameMap, descriptionMap,
			settingsDDMFormValues, serviceContext);
	}

	@Override
	public DDMFormInstanceLocalService getWrappedService() {
		return _ddmFormInstanceLocalService;
	}

	@Override
	public void setWrappedService(
		DDMFormInstanceLocalService ddmFormInstanceLocalService) {

		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
	}

	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

}