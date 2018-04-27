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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceTaxMethod. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceTaxMethodLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxMethodLocalService
 * @see com.liferay.commerce.service.base.CommerceTaxMethodLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTaxMethodLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxMethodLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTaxMethodLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce tax method to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxMethod the commerce tax method
	* @return the commerce tax method that was added
	*/
	public static com.liferay.commerce.model.CommerceTaxMethod addCommerceTaxMethod(
		com.liferay.commerce.model.CommerceTaxMethod commerceTaxMethod) {
		return getService().addCommerceTaxMethod(commerceTaxMethod);
	}

	public static com.liferay.commerce.model.CommerceTaxMethod addCommerceTaxMethod(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String engineKey, boolean percentage, boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceTaxMethod(nameMap, descriptionMap, engineKey,
			percentage, active, serviceContext);
	}

	/**
	* Creates a new commerce tax method with the primary key. Does not add the commerce tax method to the database.
	*
	* @param commerceTaxMethodId the primary key for the new commerce tax method
	* @return the new commerce tax method
	*/
	public static com.liferay.commerce.model.CommerceTaxMethod createCommerceTaxMethod(
		long commerceTaxMethodId) {
		return getService().createCommerceTaxMethod(commerceTaxMethodId);
	}

	/**
	* Deletes the commerce tax method from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxMethod the commerce tax method
	* @return the commerce tax method that was removed
	*/
	public static com.liferay.commerce.model.CommerceTaxMethod deleteCommerceTaxMethod(
		com.liferay.commerce.model.CommerceTaxMethod commerceTaxMethod) {
		return getService().deleteCommerceTaxMethod(commerceTaxMethod);
	}

	/**
	* Deletes the commerce tax method with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxMethodId the primary key of the commerce tax method
	* @return the commerce tax method that was removed
	* @throws PortalException if a commerce tax method with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceTaxMethod deleteCommerceTaxMethod(
		long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceTaxMethod(commerceTaxMethodId);
	}

	public static void deleteCommerceTaxMethods(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceTaxMethods(groupId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceTaxMethod fetchCommerceTaxMethod(
		long commerceTaxMethodId) {
		return getService().fetchCommerceTaxMethod(commerceTaxMethodId);
	}

	public static com.liferay.commerce.model.CommerceTaxMethod fetchCommerceTaxMethod(
		long groupId, String engineKey) {
		return getService().fetchCommerceTaxMethod(groupId, engineKey);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce tax method with the primary key.
	*
	* @param commerceTaxMethodId the primary key of the commerce tax method
	* @return the commerce tax method
	* @throws PortalException if a commerce tax method with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceTaxMethod getCommerceTaxMethod(
		long commerceTaxMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceTaxMethod(commerceTaxMethodId);
	}

	/**
	* Returns a range of all the commerce tax methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax methods
	* @param end the upper bound of the range of commerce tax methods (not inclusive)
	* @return the range of commerce tax methods
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceTaxMethod> getCommerceTaxMethods(
		int start, int end) {
		return getService().getCommerceTaxMethods(start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTaxMethod> getCommerceTaxMethods(
		long groupId) {
		return getService().getCommerceTaxMethods(groupId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTaxMethod> getCommerceTaxMethods(
		long groupId, boolean active) {
		return getService().getCommerceTaxMethods(groupId, active);
	}

	/**
	* Returns the number of commerce tax methods.
	*
	* @return the number of commerce tax methods
	*/
	public static int getCommerceTaxMethodsCount() {
		return getService().getCommerceTaxMethodsCount();
	}

	public static int getCommerceTaxMethodsCount(long groupId, boolean active) {
		return getService().getCommerceTaxMethodsCount(groupId, active);
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

	public static com.liferay.commerce.model.CommerceTaxMethod setActive(
		long commerceTaxMethodId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setActive(commerceTaxMethodId, active);
	}

	/**
	* Updates the commerce tax method in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxMethod the commerce tax method
	* @return the commerce tax method that was updated
	*/
	public static com.liferay.commerce.model.CommerceTaxMethod updateCommerceTaxMethod(
		com.liferay.commerce.model.CommerceTaxMethod commerceTaxMethod) {
		return getService().updateCommerceTaxMethod(commerceTaxMethod);
	}

	public static com.liferay.commerce.model.CommerceTaxMethod updateCommerceTaxMethod(
		long commerceTaxMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean percentage, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxMethod(commerceTaxMethodId, nameMap,
			descriptionMap, percentage, active);
	}

	public static CommerceTaxMethodLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxMethodLocalService, CommerceTaxMethodLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxMethodLocalService.class);

		ServiceTracker<CommerceTaxMethodLocalService, CommerceTaxMethodLocalService> serviceTracker =
			new ServiceTracker<CommerceTaxMethodLocalService, CommerceTaxMethodLocalService>(bundle.getBundleContext(),
				CommerceTaxMethodLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}