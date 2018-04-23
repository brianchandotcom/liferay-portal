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

package com.liferay.commerce.cloud.client.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceCloudOrderForecastSync. This utility wraps
 * {@link com.liferay.commerce.cloud.client.service.impl.CommerceCloudOrderForecastSyncLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Andrea Di Giorgi
 * @see CommerceCloudOrderForecastSyncLocalService
 * @see com.liferay.commerce.cloud.client.service.base.CommerceCloudOrderForecastSyncLocalServiceBaseImpl
 * @see com.liferay.commerce.cloud.client.service.impl.CommerceCloudOrderForecastSyncLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceCloudOrderForecastSyncLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.cloud.client.service.impl.CommerceCloudOrderForecastSyncLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce cloud order forecast sync to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudOrderForecastSync the commerce cloud order forecast sync
	* @return the commerce cloud order forecast sync that was added
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync addCommerceCloudOrderForecastSync(
		com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		return getService()
				   .addCommerceCloudOrderForecastSync(commerceCloudOrderForecastSync);
	}

	public static com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync addCommerceCloudOrderForecastSync(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCommerceCloudOrderForecastSync(commerceOrderId);
	}

	public static void checkCommerceCloudOrderForecastSyncs() {
		getService().checkCommerceCloudOrderForecastSyncs();
	}

	/**
	* Creates a new commerce cloud order forecast sync with the primary key. Does not add the commerce cloud order forecast sync to the database.
	*
	* @param commerceCloudOrderForecastSyncId the primary key for the new commerce cloud order forecast sync
	* @return the new commerce cloud order forecast sync
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync createCommerceCloudOrderForecastSync(
		long commerceCloudOrderForecastSyncId) {
		return getService()
				   .createCommerceCloudOrderForecastSync(commerceCloudOrderForecastSyncId);
	}

	/**
	* Deletes the commerce cloud order forecast sync from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudOrderForecastSync the commerce cloud order forecast sync
	* @return the commerce cloud order forecast sync that was removed
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync deleteCommerceCloudOrderForecastSync(
		com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		return getService()
				   .deleteCommerceCloudOrderForecastSync(commerceCloudOrderForecastSync);
	}

	/**
	* Deletes the commerce cloud order forecast sync with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudOrderForecastSyncId the primary key of the commerce cloud order forecast sync
	* @return the commerce cloud order forecast sync that was removed
	* @throws PortalException if a commerce cloud order forecast sync with the primary key could not be found
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync deleteCommerceCloudOrderForecastSync(
		long commerceCloudOrderForecastSyncId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceCloudOrderForecastSync(commerceCloudOrderForecastSyncId);
	}

	public static com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync deleteCommerceCloudOrderForecastSyncByCommerceOrderId(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceCloudOrderForecastSyncByCommerceOrderId(commerceOrderId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync fetchCommerceCloudOrderForecastSync(
		long commerceCloudOrderForecastSyncId) {
		return getService()
				   .fetchCommerceCloudOrderForecastSync(commerceCloudOrderForecastSyncId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce cloud order forecast sync with the primary key.
	*
	* @param commerceCloudOrderForecastSyncId the primary key of the commerce cloud order forecast sync
	* @return the commerce cloud order forecast sync
	* @throws PortalException if a commerce cloud order forecast sync with the primary key could not be found
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync getCommerceCloudOrderForecastSync(
		long commerceCloudOrderForecastSyncId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceCloudOrderForecastSync(commerceCloudOrderForecastSyncId);
	}

	/**
	* Returns a range of all the commerce cloud order forecast syncs.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.cloud.client.model.impl.CommerceCloudOrderForecastSyncModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce cloud order forecast syncs
	* @param end the upper bound of the range of commerce cloud order forecast syncs (not inclusive)
	* @return the range of commerce cloud order forecast syncs
	*/
	public static java.util.List<com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync> getCommerceCloudOrderForecastSyncs(
		int start, int end) {
		return getService().getCommerceCloudOrderForecastSyncs(start, end);
	}

	/**
	* Returns the number of commerce cloud order forecast syncs.
	*
	* @return the number of commerce cloud order forecast syncs
	*/
	public static int getCommerceCloudOrderForecastSyncsCount() {
		return getService().getCommerceCloudOrderForecastSyncsCount();
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

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce cloud order forecast sync in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceCloudOrderForecastSync the commerce cloud order forecast sync
	* @return the commerce cloud order forecast sync that was updated
	*/
	public static com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync updateCommerceCloudOrderForecastSync(
		com.liferay.commerce.cloud.client.model.CommerceCloudOrderForecastSync commerceCloudOrderForecastSync) {
		return getService()
				   .updateCommerceCloudOrderForecastSync(commerceCloudOrderForecastSync);
	}

	public static CommerceCloudOrderForecastSyncLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceCloudOrderForecastSyncLocalService, CommerceCloudOrderForecastSyncLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceCloudOrderForecastSyncLocalService.class);

		ServiceTracker<CommerceCloudOrderForecastSyncLocalService, CommerceCloudOrderForecastSyncLocalService> serviceTracker =
			new ServiceTracker<CommerceCloudOrderForecastSyncLocalService, CommerceCloudOrderForecastSyncLocalService>(bundle.getBundleContext(),
				CommerceCloudOrderForecastSyncLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}