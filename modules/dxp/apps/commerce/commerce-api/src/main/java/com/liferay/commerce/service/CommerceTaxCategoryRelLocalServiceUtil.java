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
 * Provides the local service utility for CommerceTaxCategoryRel. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceTaxCategoryRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryRelLocalService
 * @see com.liferay.commerce.service.base.CommerceTaxCategoryRelLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTaxCategoryRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTaxCategoryRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce tax category rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRel the commerce tax category rel
	* @return the commerce tax category rel that was added
	*/
	public static com.liferay.commerce.model.CommerceTaxCategoryRel addCommerceTaxCategoryRel(
		com.liferay.commerce.model.CommerceTaxCategoryRel commerceTaxCategoryRel) {
		return getService().addCommerceTaxCategoryRel(commerceTaxCategoryRel);
	}

	/**
	* Creates a new commerce tax category rel with the primary key. Does not add the commerce tax category rel to the database.
	*
	* @param commerceTaxCategoryRelId the primary key for the new commerce tax category rel
	* @return the new commerce tax category rel
	*/
	public static com.liferay.commerce.model.CommerceTaxCategoryRel createCommerceTaxCategoryRel(
		long commerceTaxCategoryRelId) {
		return getService()
				   .createCommerceTaxCategoryRel(commerceTaxCategoryRelId);
	}

	/**
	* Deletes the commerce tax category rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRel the commerce tax category rel
	* @return the commerce tax category rel that was removed
	*/
	public static com.liferay.commerce.model.CommerceTaxCategoryRel deleteCommerceTaxCategoryRel(
		com.liferay.commerce.model.CommerceTaxCategoryRel commerceTaxCategoryRel) {
		return getService().deleteCommerceTaxCategoryRel(commerceTaxCategoryRel);
	}

	/**
	* Deletes the commerce tax category rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel that was removed
	* @throws PortalException if a commerce tax category rel with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceTaxCategoryRel deleteCommerceTaxCategoryRel(
		long commerceTaxCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceTaxCategoryRel(commerceTaxCategoryRelId);
	}

	public static void deleteCommerceTaxCategoryRel(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceTaxCategoryRel(className, classPK);
	}

	public static void deleteCommerceTaxCategoryRels(long commerceTaxCategoryId) {
		getService().deleteCommerceTaxCategoryRels(commerceTaxCategoryId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.model.CommerceTaxCategoryRel fetchCommerceTaxCategoryRel(
		long commerceTaxCategoryRelId) {
		return getService().fetchCommerceTaxCategoryRel(commerceTaxCategoryRelId);
	}

	public static com.liferay.commerce.model.CommerceTaxCategoryRel fetchCommerceTaxCategoryRel(
		java.lang.String className, long classPK) {
		return getService().fetchCommerceTaxCategoryRel(className, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce tax category rel with the primary key.
	*
	* @param commerceTaxCategoryRelId the primary key of the commerce tax category rel
	* @return the commerce tax category rel
	* @throws PortalException if a commerce tax category rel with the primary key could not be found
	*/
	public static com.liferay.commerce.model.CommerceTaxCategoryRel getCommerceTaxCategoryRel(
		long commerceTaxCategoryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceTaxCategoryRel(commerceTaxCategoryRelId);
	}

	/**
	* Returns a range of all the commerce tax category rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceTaxCategoryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax category rels
	* @param end the upper bound of the range of commerce tax category rels (not inclusive)
	* @return the range of commerce tax category rels
	*/
	public static java.util.List<com.liferay.commerce.model.CommerceTaxCategoryRel> getCommerceTaxCategoryRels(
		int start, int end) {
		return getService().getCommerceTaxCategoryRels(start, end);
	}

	/**
	* Returns the number of commerce tax category rels.
	*
	* @return the number of commerce tax category rels
	*/
	public static int getCommerceTaxCategoryRelsCount() {
		return getService().getCommerceTaxCategoryRelsCount();
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
	* Updates the commerce tax category rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxCategoryRel the commerce tax category rel
	* @return the commerce tax category rel that was updated
	*/
	public static com.liferay.commerce.model.CommerceTaxCategoryRel updateCommerceTaxCategoryRel(
		com.liferay.commerce.model.CommerceTaxCategoryRel commerceTaxCategoryRel) {
		return getService().updateCommerceTaxCategoryRel(commerceTaxCategoryRel);
	}

	public static com.liferay.commerce.model.CommerceTaxCategoryRel updateCommerceTaxCategoryRel(
		long commerceTaxCategoryId, java.lang.String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTaxCategoryRel(commerceTaxCategoryId,
			className, classPK, serviceContext);
	}

	public static CommerceTaxCategoryRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTaxCategoryRelLocalService, CommerceTaxCategoryRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceTaxCategoryRelLocalService.class);

		ServiceTracker<CommerceTaxCategoryRelLocalService, CommerceTaxCategoryRelLocalService> serviceTracker =
			new ServiceTracker<CommerceTaxCategoryRelLocalService, CommerceTaxCategoryRelLocalService>(bundle.getBundleContext(),
				CommerceTaxCategoryRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}