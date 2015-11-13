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
 * Provides the local service utility for DDMDataProvider. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderLocalService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMDataProviderLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDMDataProviderLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMDataProviderLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m data provider to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProvider the d d m data provider
	* @return the d d m data provider that was added
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProvider addDDMDataProvider(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider) {
		return getService().addDDMDataProvider(ddmDataProvider);
	}

	/**
	* Creates a new d d m data provider with the primary key. Does not add the d d m data provider to the database.
	*
	* @param dataProviderId the primary key for the new d d m data provider
	* @return the new d d m data provider
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProvider createDDMDataProvider(
		long dataProviderId) {
		return getService().createDDMDataProvider(dataProviderId);
	}

	/**
	* Deletes the d d m data provider with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider that was removed
	* @throws PortalException if a d d m data provider with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProvider deleteDDMDataProvider(
		long dataProviderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDMDataProvider(dataProviderId);
	}

	/**
	* Deletes the d d m data provider from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProvider the d d m data provider
	* @return the d d m data provider that was removed
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProvider deleteDDMDataProvider(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider) {
		return getService().deleteDDMDataProvider(ddmDataProvider);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.dynamic.data.mapping.model.DDMDataProvider fetchDDMDataProvider(
		long dataProviderId) {
		return getService().fetchDDMDataProvider(dataProviderId);
	}

	/**
	* Returns the d d m data provider matching the UUID and group.
	*
	* @param uuid the d d m data provider's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider, or <code>null</code> if a matching d d m data provider could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProvider fetchDDMDataProviderByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchDDMDataProviderByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the d d m data provider with the primary key.
	*
	* @param dataProviderId the primary key of the d d m data provider
	* @return the d d m data provider
	* @throws PortalException if a d d m data provider with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProvider getDDMDataProvider(
		long dataProviderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMDataProvider(dataProviderId);
	}

	/**
	* Returns the d d m data provider matching the UUID and group.
	*
	* @param uuid the d d m data provider's UUID
	* @param groupId the primary key of the group
	* @return the matching d d m data provider
	* @throws PortalException if a matching d d m data provider could not be found
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProvider getDDMDataProviderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMDataProviderByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the d d m data providers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.mapping.model.impl.DDMDataProviderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @return the range of d d m data providers
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProvider> getDDMDataProviders(
		int start, int end) {
		return getService().getDDMDataProviders(start, end);
	}

	/**
	* Returns all the d d m data providers matching the UUID and company.
	*
	* @param uuid the UUID of the d d m data providers
	* @param companyId the primary key of the company
	* @return the matching d d m data providers, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProvider> getDDMDataProvidersByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getDDMDataProvidersByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of d d m data providers matching the UUID and company.
	*
	* @param uuid the UUID of the d d m data providers
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of d d m data providers
	* @param end the upper bound of the range of d d m data providers (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching d d m data providers, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMDataProvider> getDDMDataProvidersByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMDataProvider> orderByComparator) {
		return getService()
				   .getDDMDataProvidersByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of d d m data providers.
	*
	* @return the number of d d m data providers
	*/
	public static int getDDMDataProvidersCount() {
		return getService().getDDMDataProvidersCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
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

	/**
	* Updates the d d m data provider in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmDataProvider the d d m data provider
	* @return the d d m data provider that was updated
	*/
	public static com.liferay.dynamic.data.mapping.model.DDMDataProvider updateDDMDataProvider(
		com.liferay.dynamic.data.mapping.model.DDMDataProvider ddmDataProvider) {
		return getService().updateDDMDataProvider(ddmDataProvider);
	}

	public static DDMDataProviderLocalService getService() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDMDataProviderLocalService service) {
	}

	private static ServiceTracker<DDMDataProviderLocalService, DDMDataProviderLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMDataProviderLocalServiceUtil.class);

		_serviceTracker = new ServiceTracker<DDMDataProviderLocalService, DDMDataProviderLocalService>(bundle.getBundleContext(),
				DDMDataProviderLocalService.class, null);

		_serviceTracker.open();
	}
}