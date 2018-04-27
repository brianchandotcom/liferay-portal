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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CPRuleUserSegmentRel. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPRuleUserSegmentRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CPRuleUserSegmentRelLocalService
 * @see com.liferay.commerce.product.service.base.CPRuleUserSegmentRelLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPRuleUserSegmentRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CPRuleUserSegmentRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPRuleUserSegmentRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the cp rule user segment rel to the database. Also notifies the appropriate model listeners.
	*
	* @param cpRuleUserSegmentRel the cp rule user segment rel
	* @return the cp rule user segment rel that was added
	*/
	public static com.liferay.commerce.product.model.CPRuleUserSegmentRel addCPRuleUserSegmentRel(
		com.liferay.commerce.product.model.CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		return getService().addCPRuleUserSegmentRel(cpRuleUserSegmentRel);
	}

	public static com.liferay.commerce.product.model.CPRuleUserSegmentRel addCPRuleUserSegmentRel(
		long cpRuleId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPRuleUserSegmentRel(cpRuleId,
			commerceUserSegmentEntryId, serviceContext);
	}

	/**
	* Creates a new cp rule user segment rel with the primary key. Does not add the cp rule user segment rel to the database.
	*
	* @param CPRuleUserSegmentRelId the primary key for the new cp rule user segment rel
	* @return the new cp rule user segment rel
	*/
	public static com.liferay.commerce.product.model.CPRuleUserSegmentRel createCPRuleUserSegmentRel(
		long CPRuleUserSegmentRelId) {
		return getService().createCPRuleUserSegmentRel(CPRuleUserSegmentRelId);
	}

	/**
	* Deletes the cp rule user segment rel from the database. Also notifies the appropriate model listeners.
	*
	* @param cpRuleUserSegmentRel the cp rule user segment rel
	* @return the cp rule user segment rel that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.product.model.CPRuleUserSegmentRel deleteCPRuleUserSegmentRel(
		com.liferay.commerce.product.model.CPRuleUserSegmentRel cpRuleUserSegmentRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPRuleUserSegmentRel(cpRuleUserSegmentRel);
	}

	/**
	* Deletes the cp rule user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel that was removed
	* @throws PortalException if a cp rule user segment rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPRuleUserSegmentRel deleteCPRuleUserSegmentRel(
		long CPRuleUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPRuleUserSegmentRel(CPRuleUserSegmentRelId);
	}

	public static void deleteCPRuleUserSegmentRelsByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCPRuleUserSegmentRelsByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	public static void deleteCPRuleUserSegmentRelsByCPRuleId(long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPRuleUserSegmentRelsByCPRuleId(cpRuleId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.product.model.CPRuleUserSegmentRel fetchCPRuleUserSegmentRel(
		long CPRuleUserSegmentRelId) {
		return getService().fetchCPRuleUserSegmentRel(CPRuleUserSegmentRelId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the cp rule user segment rel with the primary key.
	*
	* @param CPRuleUserSegmentRelId the primary key of the cp rule user segment rel
	* @return the cp rule user segment rel
	* @throws PortalException if a cp rule user segment rel with the primary key could not be found
	*/
	public static com.liferay.commerce.product.model.CPRuleUserSegmentRel getCPRuleUserSegmentRel(
		long CPRuleUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPRuleUserSegmentRel(CPRuleUserSegmentRelId);
	}

	/**
	* Returns a range of all the cp rule user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPRuleUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp rule user segment rels
	* @param end the upper bound of the range of cp rule user segment rels (not inclusive)
	* @return the range of cp rule user segment rels
	*/
	public static java.util.List<com.liferay.commerce.product.model.CPRuleUserSegmentRel> getCPRuleUserSegmentRels(
		int start, int end) {
		return getService().getCPRuleUserSegmentRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPRuleUserSegmentRel> getCPRuleUserSegmentRels(
		long cpRuleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPRuleUserSegmentRel> orderByComparator) {
		return getService()
				   .getCPRuleUserSegmentRels(cpRuleId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of cp rule user segment rels.
	*
	* @return the number of cp rule user segment rels
	*/
	public static int getCPRuleUserSegmentRelsCount() {
		return getService().getCPRuleUserSegmentRelsCount();
	}

	public static int getCPRuleUserSegmentRelsCount(long cpRuleId) {
		return getService().getCPRuleUserSegmentRelsCount(cpRuleId);
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
	* Updates the cp rule user segment rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpRuleUserSegmentRel the cp rule user segment rel
	* @return the cp rule user segment rel that was updated
	*/
	public static com.liferay.commerce.product.model.CPRuleUserSegmentRel updateCPRuleUserSegmentRel(
		com.liferay.commerce.product.model.CPRuleUserSegmentRel cpRuleUserSegmentRel) {
		return getService().updateCPRuleUserSegmentRel(cpRuleUserSegmentRel);
	}

	public static CPRuleUserSegmentRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPRuleUserSegmentRelLocalService, CPRuleUserSegmentRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPRuleUserSegmentRelLocalService.class);

		ServiceTracker<CPRuleUserSegmentRelLocalService, CPRuleUserSegmentRelLocalService> serviceTracker =
			new ServiceTracker<CPRuleUserSegmentRelLocalService, CPRuleUserSegmentRelLocalService>(bundle.getBundleContext(),
				CPRuleUserSegmentRelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}