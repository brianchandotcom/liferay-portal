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

import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for ObjectFieldSetting. This utility wraps
 * <code>com.liferay.object.service.impl.ObjectFieldSettingLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see ObjectFieldSettingLocalService
 * @generated
 */
public class ObjectFieldSettingLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.object.service.impl.ObjectFieldSettingLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static ObjectFieldSetting addObjectFieldSetting(
			long userId, long objectFieldId, String name, String value)
		throws PortalException {

		return getService().addObjectFieldSetting(
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
	public static ObjectFieldSetting addObjectFieldSetting(
		ObjectFieldSetting objectFieldSetting) {

		return getService().addObjectFieldSetting(objectFieldSetting);
	}

	/**
	 * Creates a new object field setting with the primary key. Does not add the object field setting to the database.
	 *
	 * @param objectFieldSettingId the primary key for the new object field setting
	 * @return the new object field setting
	 */
	public static ObjectFieldSetting createObjectFieldSetting(
		long objectFieldSettingId) {

		return getService().createObjectFieldSetting(objectFieldSettingId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
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
	public static ObjectFieldSetting deleteObjectFieldSetting(
			long objectFieldSettingId)
		throws PortalException {

		return getService().deleteObjectFieldSetting(objectFieldSettingId);
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
	public static ObjectFieldSetting deleteObjectFieldSetting(
		ObjectFieldSetting objectFieldSetting) {

		return getService().deleteObjectFieldSetting(objectFieldSetting);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static ObjectFieldSetting fetchObjectFieldSetting(
		long objectFieldSettingId) {

		return getService().fetchObjectFieldSetting(objectFieldSettingId);
	}

	public static ObjectFieldSetting fetchObjectFieldSetting(
		long objectFieldId, String name) {

		return getService().fetchObjectFieldSetting(objectFieldId, name);
	}

	/**
	 * Returns the object field setting with the matching UUID and company.
	 *
	 * @param uuid the object field setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting fetchObjectFieldSettingByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchObjectFieldSettingByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the object field setting with the primary key.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting
	 * @throws PortalException if a object field setting with the primary key could not be found
	 */
	public static ObjectFieldSetting getObjectFieldSetting(
			long objectFieldSettingId)
		throws PortalException {

		return getService().getObjectFieldSetting(objectFieldSettingId);
	}

	/**
	 * Returns the object field setting with the matching UUID and company.
	 *
	 * @param uuid the object field setting's UUID
	 * @param companyId the primary key of the company
	 * @return the matching object field setting
	 * @throws PortalException if a matching object field setting could not be found
	 */
	public static ObjectFieldSetting getObjectFieldSettingByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getObjectFieldSettingByUuidAndCompanyId(
			uuid, companyId);
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
	public static List<ObjectFieldSetting> getObjectFieldSettings(
		int start, int end) {

		return getService().getObjectFieldSettings(start, end);
	}

	public static List<ObjectFieldSetting> getObjectFieldSettings(
		long objectFieldId) {

		return getService().getObjectFieldSettings(objectFieldId);
	}

	/**
	 * Returns the number of object field settings.
	 *
	 * @return the number of object field settings
	 */
	public static int getObjectFieldSettingsCount() {
		return getService().getObjectFieldSettingsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static ObjectFieldSetting updateObjectFieldSetting(
			long objectFieldSettingId, String value)
		throws PortalException {

		return getService().updateObjectFieldSetting(
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
	public static ObjectFieldSetting updateObjectFieldSetting(
		ObjectFieldSetting objectFieldSetting) {

		return getService().updateObjectFieldSetting(objectFieldSetting);
	}

	public static ObjectFieldSettingLocalService getService() {
		return _service;
	}

	private static volatile ObjectFieldSettingLocalService _service;

}