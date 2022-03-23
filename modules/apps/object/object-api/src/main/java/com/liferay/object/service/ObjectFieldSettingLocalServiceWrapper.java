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
 * Provides a wrapper for {@link ObjectFieldSettingLocalService}.
 *
 * @author Marco Leo
 * @see ObjectFieldSettingLocalService
 * @generated
 */
public class ObjectFieldSettingLocalServiceWrapper
	implements ObjectFieldSettingLocalService,
			   ServiceWrapper<ObjectFieldSettingLocalService> {

	public ObjectFieldSettingLocalServiceWrapper() {
		this(null);
	}

	public ObjectFieldSettingLocalServiceWrapper(
		ObjectFieldSettingLocalService objectFieldSettingLocalService) {

		_objectFieldSettingLocalService = objectFieldSettingLocalService;
	}

	@Override
	public com.liferay.object.model.ObjectFieldSetting addObjectFieldSetting(
			long userId, long objectFieldId, String name, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingLocalService.addObjectFieldSetting(
			userId, objectFieldId, name, value);
	}

	/**
	 * Adds the object field setting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectFieldSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectFieldSetting the object field setting
	 * @return the object field setting that was added
	 */
	@Override
	public com.liferay.object.model.ObjectFieldSetting addObjectFieldSetting(
		com.liferay.object.model.ObjectFieldSetting objectFieldSetting) {

		return _objectFieldSettingLocalService.addObjectFieldSetting(
			objectFieldSetting);
	}

	/**
	 * Creates a new object field setting with the primary key. Does not add the object field setting to the database.
	 *
	 * @param objectFieldSettingId the primary key for the new object field setting
	 * @return the new object field setting
	 */
	@Override
	public com.liferay.object.model.ObjectFieldSetting createObjectFieldSetting(
		long objectFieldSettingId) {

		return _objectFieldSettingLocalService.createObjectFieldSetting(
			objectFieldSettingId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Deletes the object field setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectFieldSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting that was removed
	 * @throws PortalException if a object field setting with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectFieldSetting deleteObjectFieldSetting(
			long objectFieldSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingLocalService.deleteObjectFieldSetting(
			objectFieldSettingId);
	}

	/**
	 * Deletes the object field setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectFieldSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectFieldSetting the object field setting
	 * @return the object field setting that was removed
	 */
	@Override
	public com.liferay.object.model.ObjectFieldSetting deleteObjectFieldSetting(
		com.liferay.object.model.ObjectFieldSetting objectFieldSetting) {

		return _objectFieldSettingLocalService.deleteObjectFieldSetting(
			objectFieldSetting);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _objectFieldSettingLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _objectFieldSettingLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _objectFieldSettingLocalService.dynamicQuery();
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

		return _objectFieldSettingLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFieldSettingModelImpl</code>.
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

		return _objectFieldSettingLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFieldSettingModelImpl</code>.
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

		return _objectFieldSettingLocalService.dynamicQuery(
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

		return _objectFieldSettingLocalService.dynamicQueryCount(dynamicQuery);
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

		return _objectFieldSettingLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.object.model.ObjectFieldSetting fetchObjectFieldSetting(
		long objectFieldSettingId) {

		return _objectFieldSettingLocalService.fetchObjectFieldSetting(
			objectFieldSettingId);
	}

	@Override
	public com.liferay.object.model.ObjectFieldSetting fetchObjectFieldSetting(
		long objectFieldId, String name) {

		return _objectFieldSettingLocalService.fetchObjectFieldSetting(
			objectFieldId, name);
	}

	/**
	 * Returns the object field setting with the matching UUID and company.
	 *
	 * @param uuid the object field setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectFieldSetting
		fetchObjectFieldSettingByUuidAndCompanyId(String uuid, long companyId) {

		return _objectFieldSettingLocalService.
			fetchObjectFieldSettingByUuidAndCompanyId(uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _objectFieldSettingLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _objectFieldSettingLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _objectFieldSettingLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the object field setting with the primary key.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting
	 * @throws PortalException if a object field setting with the primary key could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectFieldSetting getObjectFieldSetting(
			long objectFieldSettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingLocalService.getObjectFieldSetting(
			objectFieldSettingId);
	}

	/**
	 * Returns the object field setting with the matching UUID and company.
	 *
	 * @param uuid the object field setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object field setting
	 * @throws PortalException if a matching object field setting could not be found
	 */
	@Override
	public com.liferay.object.model.ObjectFieldSetting
			getObjectFieldSettingByUuidAndCompanyId(String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingLocalService.
			getObjectFieldSettingByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.object.model.impl.ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of object field settings
	 */
	@Override
	public java.util.List<com.liferay.object.model.ObjectFieldSetting>
		getObjectFieldSettings(int start, int end) {

		return _objectFieldSettingLocalService.getObjectFieldSettings(
			start, end);
	}

	@Override
	public java.util.List<com.liferay.object.model.ObjectFieldSetting>
		getObjectFieldSettings(long objectFieldId) {

		return _objectFieldSettingLocalService.getObjectFieldSettings(
			objectFieldId);
	}

	/**
	 * Returns the number of object field settings.
	 *
	 * @return the number of object field settings
	 */
	@Override
	public int getObjectFieldSettingsCount() {
		return _objectFieldSettingLocalService.getObjectFieldSettingsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _objectFieldSettingLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public com.liferay.object.model.ObjectFieldSetting updateObjectFieldSetting(
			long objectFieldSettingId, String value)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _objectFieldSettingLocalService.updateObjectFieldSetting(
			objectFieldSettingId, value);
	}

	/**
	 * Updates the object field setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ObjectFieldSettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param objectFieldSetting the object field setting
	 * @return the object field setting that was updated
	 */
	@Override
	public com.liferay.object.model.ObjectFieldSetting updateObjectFieldSetting(
		com.liferay.object.model.ObjectFieldSetting objectFieldSetting) {

		return _objectFieldSettingLocalService.updateObjectFieldSetting(
			objectFieldSetting);
	}

	@Override
	public ObjectFieldSettingLocalService getWrappedService() {
		return _objectFieldSettingLocalService;
	}

	@Override
	public void setWrappedService(
		ObjectFieldSettingLocalService objectFieldSettingLocalService) {

		_objectFieldSettingLocalService = objectFieldSettingLocalService;
	}

	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

}