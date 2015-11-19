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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDMDataProviderInstance. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLocalService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMDataProviderInstanceLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDMDataProviderInstanceLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderInstanceLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m data provider instance to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProviderInstance the d d m data provider instance
	* @return the d d m data provider instance that was added
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance addDDMDataProviderInstance(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance ddmDataProviderInstance) {
		return getService().addDDMDataProviderInstance(ddmDataProviderInstance);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance addDataProviderInstance(
		long userId, long groupId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		java.lang.String type,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addDataProviderInstance(userId, groupId, nameMap,
			descriptionMap, ddmFormValues, type, serviceContext);
	}

	/**
	* Creates a new d d m data provider instance with the primary key. Does not add the d d m data provider instance to the database.
	*
	* @param dataProviderInstanceId the primary key for the new d d m data provider instance
	* @return the new d d m data provider instance
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance createDDMDataProviderInstance(
		long dataProviderInstanceId) {
		return getService().createDDMDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Deletes the d d m data provider instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderInstanceId the primary key of the d d m data provider instance
	* @return the d d m data provider instance that was removed
	* @throws PortalException if a d d m data provider instance with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance deleteDDMDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDMDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Deletes the d d m data provider instance from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProviderInstance the d d m data provider instance
	* @return the d d m data provider instance that was removed
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance deleteDDMDataProviderInstance(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance ddmDataProviderInstance) {
		return getService()
				   .deleteDDMDataProviderInstance(ddmDataProviderInstance);
	}

	public static void deleteDataProviderInstance(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance dataProviderInstance) {
		getService().deleteDataProviderInstance(dataProviderInstance);
	}

	public static void deleteDataProviderInstance(long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
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
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance fetchDDMDataProviderInstance(
		long dataProviderInstanceId) {
		return getService().fetchDDMDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Returns the d d m data provider instance matching the UUID and group.
	*
	* @param uuid the d d m data provider instance's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider instance, or <code>null</code> if a matching d d m data provider instance could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance fetchDDMDataProviderInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchDDMDataProviderInstanceByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the d d m data provider instance with the primary key.
	*
	* @param dataProviderInstanceId the primary key of the d d m data provider instance
	* @return the d d m data provider instance
	* @throws PortalException if a d d m data provider instance with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDDMDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMDataProviderInstance(dataProviderInstanceId);
	}

	/**
	* Returns the d d m data provider instance matching the UUID and group.
	*
	* @param uuid the d d m data provider instance's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider instance
	* @throws PortalException if a matching d d m data provider instance could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDDMDataProviderInstanceByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getDDMDataProviderInstanceByUuidAndGroupId(uuid, groupId);
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
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> getDDMDataProviderInstances(
		int start, int end) {
		return getService().getDDMDataProviderInstances(start, end);
	}

	/**
	* Returns all the d d m data provider instances matching the UUID and company.
	*
	* @param uuid the UUID of the d d m data provider instances
	* @param companyId the primary key of the company
	* @return the matching d d m data provider instances, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> getDDMDataProviderInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getDDMDataProviderInstancesByUuidAndCompanyId(uuid,
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
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> getDDMDataProviderInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return getService()
				   .getDDMDataProviderInstancesByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of d d m data provider instances.
	*
	* @return the number of d d m data provider instances
	*/
	public static int getDDMDataProviderInstancesCount() {
		return getService().getDDMDataProviderInstancesCount();
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance getDataProviderInstance(
		long dataProviderInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDataProviderInstance(dataProviderInstanceId);
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return getService()
				   .search(companyId, groupIds, keywords, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> search(
		long companyId, long[] groupIds, java.lang.String name,
		java.lang.String description, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance> orderByComparator) {
		return getService()
				   .search(companyId, groupIds, name, description, andOperator,
			start, end, orderByComparator);
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String keywords) {
		return getService().searchCount(companyId, groupIds, keywords);
	}

	public static int searchCount(long companyId, long[] groupIds,
		java.lang.String name, java.lang.String description, boolean andOperator) {
		return getService()
				   .searchCount(companyId, groupIds, name, description,
			andOperator);
	}

	/**
	* Updates the d d m data provider instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProviderInstance the d d m data provider instance
	* @return the d d m data provider instance that was updated
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance updateDDMDataProviderInstance(
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance ddmDataProviderInstance) {
		return getService()
				   .updateDDMDataProviderInstance(ddmDataProviderInstance);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance updateDataProviderInstance(
		long userId, long dataProviderInstanceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateDataProviderInstance(userId, dataProviderInstanceId,
			nameMap, descriptionMap, ddmFormValues, serviceContext);
	}

	public static DDMDataProviderInstanceLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDMDataProviderInstanceLocalService service) {
	}

	private static ServiceTracker<DDMDataProviderInstanceLocalService, DDMDataProviderInstanceLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMDataProviderInstanceLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<DDMDataProviderInstanceLocalService, DDMDataProviderInstanceLocalService>(bundle.getBundleContext(),
				DDMDataProviderInstanceLocalService.class, null);

		_serviceTracker.open();
	}
}