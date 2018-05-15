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

package com.liferay.commerce.forecast.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceForecastValue. This utility wraps
 * {@link com.liferay.commerce.forecast.service.impl.CommerceForecastValueLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastValueLocalService
 * @see com.liferay.commerce.forecast.service.base.CommerceForecastValueLocalServiceBaseImpl
 * @see com.liferay.commerce.forecast.service.impl.CommerceForecastValueLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceForecastValueLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.forecast.service.impl.CommerceForecastValueLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce forecast value to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValue the commerce forecast value
	* @return the commerce forecast value that was added
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastValue addCommerceForecastValue(
		com.liferay.commerce.forecast.model.CommerceForecastValue commerceForecastValue) {
		return getService().addCommerceForecastValue(commerceForecastValue);
	}

	public static com.liferay.commerce.forecast.model.CommerceForecastValue addCommerceForecastValue(
		long userId, long commerceForecastEntryId, java.util.Date date,
		java.math.BigDecimal lowerValue, java.math.BigDecimal value,
		java.math.BigDecimal upperValue)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceForecastValue(userId, commerceForecastEntryId,
			date, lowerValue, value, upperValue);
	}

	/**
	* Creates a new commerce forecast value with the primary key. Does not add the commerce forecast value to the database.
	*
	* @param commerceForecastValueId the primary key for the new commerce forecast value
	* @return the new commerce forecast value
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastValue createCommerceForecastValue(
		long commerceForecastValueId) {
		return getService().createCommerceForecastValue(commerceForecastValueId);
	}

	/**
	* Deletes the commerce forecast value from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValue the commerce forecast value
	* @return the commerce forecast value that was removed
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastValue deleteCommerceForecastValue(
		com.liferay.commerce.forecast.model.CommerceForecastValue commerceForecastValue) {
		return getService().deleteCommerceForecastValue(commerceForecastValue);
	}

	/**
	* Deletes the commerce forecast value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value that was removed
	* @throws PortalException if a commerce forecast value with the primary key could not be found
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastValue deleteCommerceForecastValue(
		long commerceForecastValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceForecastValue(commerceForecastValueId);
	}

	public static void deleteCommerceForecastValues(
		long commerceForecastEntryId) {
		getService().deleteCommerceForecastValues(commerceForecastEntryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.forecast.model.CommerceForecastValue fetchCommerceForecastValue(
		long commerceForecastValueId) {
		return getService().fetchCommerceForecastValue(commerceForecastValueId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce forecast value with the primary key.
	*
	* @param commerceForecastValueId the primary key of the commerce forecast value
	* @return the commerce forecast value
	* @throws PortalException if a commerce forecast value with the primary key could not be found
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastValue getCommerceForecastValue(
		long commerceForecastValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceForecastValue(commerceForecastValueId);
	}

	/**
	* Returns a range of all the commerce forecast values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast values
	* @param end the upper bound of the range of commerce forecast values (not inclusive)
	* @return the range of commerce forecast values
	*/
	public static java.util.List<com.liferay.commerce.forecast.model.CommerceForecastValue> getCommerceForecastValues(
		int start, int end) {
		return getService().getCommerceForecastValues(start, end);
	}

	/**
	* Returns the number of commerce forecast values.
	*
	* @return the number of commerce forecast values
	*/
	public static int getCommerceForecastValuesCount() {
		return getService().getCommerceForecastValuesCount();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the commerce forecast value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastValue the commerce forecast value
	* @return the commerce forecast value that was updated
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastValue updateCommerceForecastValue(
		com.liferay.commerce.forecast.model.CommerceForecastValue commerceForecastValue) {
		return getService().updateCommerceForecastValue(commerceForecastValue);
	}

	public static CommerceForecastValueLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceForecastValueLocalService, CommerceForecastValueLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceForecastValueLocalService.class);

		ServiceTracker<CommerceForecastValueLocalService, CommerceForecastValueLocalService> serviceTracker =
			new ServiceTracker<CommerceForecastValueLocalService, CommerceForecastValueLocalService>(bundle.getBundleContext(),
				CommerceForecastValueLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}