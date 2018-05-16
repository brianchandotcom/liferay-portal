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
 * Provides the local service utility for CommerceForecastEntry. This utility wraps
 * {@link com.liferay.commerce.forecast.service.impl.CommerceForecastEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastEntryLocalService
 * @see com.liferay.commerce.forecast.service.base.CommerceForecastEntryLocalServiceBaseImpl
 * @see com.liferay.commerce.forecast.service.impl.CommerceForecastEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceForecastEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.forecast.service.impl.CommerceForecastEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce forecast entry to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntry the commerce forecast entry
	* @return the commerce forecast entry that was added
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastEntry addCommerceForecastEntry(
		com.liferay.commerce.forecast.model.CommerceForecastEntry commerceForecastEntry) {
		return getService().addCommerceForecastEntry(commerceForecastEntry);
	}

	public static com.liferay.commerce.forecast.model.CommerceForecastEntry addCommerceForecastEntry(
		long companyId, long userId, java.util.Date date, int period,
		int target, long customerId, String sku,
		java.math.BigDecimal assertivity)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceForecastEntry(companyId, userId, date, period,
			target, customerId, sku, assertivity);
	}

	/**
	* Creates a new commerce forecast entry with the primary key. Does not add the commerce forecast entry to the database.
	*
	* @param commerceForecastEntryId the primary key for the new commerce forecast entry
	* @return the new commerce forecast entry
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastEntry createCommerceForecastEntry(
		long commerceForecastEntryId) {
		return getService().createCommerceForecastEntry(commerceForecastEntryId);
	}

	public static void deleteCommerceForecastEntries(long companyId) {
		getService().deleteCommerceForecastEntries(companyId);
	}

	/**
	* Deletes the commerce forecast entry from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntry the commerce forecast entry
	* @return the commerce forecast entry that was removed
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastEntry deleteCommerceForecastEntry(
		com.liferay.commerce.forecast.model.CommerceForecastEntry commerceForecastEntry) {
		return getService().deleteCommerceForecastEntry(commerceForecastEntry);
	}

	/**
	* Deletes the commerce forecast entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry that was removed
	* @throws PortalException if a commerce forecast entry with the primary key could not be found
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastEntry deleteCommerceForecastEntry(
		long commerceForecastEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceForecastEntry(commerceForecastEntryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.forecast.model.CommerceForecastEntry fetchCommerceForecastEntry(
		long commerceForecastEntryId) {
		return getService().fetchCommerceForecastEntry(commerceForecastEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns a range of all the commerce forecast entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.forecast.model.impl.CommerceForecastEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce forecast entries
	* @param end the upper bound of the range of commerce forecast entries (not inclusive)
	* @return the range of commerce forecast entries
	*/
	public static java.util.List<com.liferay.commerce.forecast.model.CommerceForecastEntry> getCommerceForecastEntries(
		int start, int end) {
		return getService().getCommerceForecastEntries(start, end);
	}

	/**
	* Returns the number of commerce forecast entries.
	*
	* @return the number of commerce forecast entries
	*/
	public static int getCommerceForecastEntriesCount() {
		return getService().getCommerceForecastEntriesCount();
	}

	/**
	* Returns the commerce forecast entry with the primary key.
	*
	* @param commerceForecastEntryId the primary key of the commerce forecast entry
	* @return the commerce forecast entry
	* @throws PortalException if a commerce forecast entry with the primary key could not be found
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastEntry getCommerceForecastEntry(
		long commerceForecastEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceForecastEntry(commerceForecastEntryId);
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
	* Updates the commerce forecast entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceForecastEntry the commerce forecast entry
	* @return the commerce forecast entry that was updated
	*/
	public static com.liferay.commerce.forecast.model.CommerceForecastEntry updateCommerceForecastEntry(
		com.liferay.commerce.forecast.model.CommerceForecastEntry commerceForecastEntry) {
		return getService().updateCommerceForecastEntry(commerceForecastEntry);
	}

	public static CommerceForecastEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceForecastEntryLocalService, CommerceForecastEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceForecastEntryLocalService.class);

		ServiceTracker<CommerceForecastEntryLocalService, CommerceForecastEntryLocalService> serviceTracker =
			new ServiceTracker<CommerceForecastEntryLocalService, CommerceForecastEntryLocalService>(bundle.getBundleContext(),
				CommerceForecastEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}