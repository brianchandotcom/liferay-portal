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

package com.liferay.commerce.user.segment.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceUserSegmentCriterion. This utility wraps
 * {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentCriterionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterionLocalService
 * @see com.liferay.commerce.user.segment.service.base.CommerceUserSegmentCriterionLocalServiceBaseImpl
 * @see com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentCriterionLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentCriterionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce user segment criterion to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentCriterion the commerce user segment criterion
	* @return the commerce user segment criterion that was added
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
		com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		return getService()
				   .addCommerceUserSegmentCriterion(commerceUserSegmentCriterion);
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
		long commerceUserSegmentEntryId, String type, String typeSettings,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceUserSegmentCriterion(commerceUserSegmentEntryId,
			type, typeSettings, priority, serviceContext);
	}

	/**
	* Creates a new commerce user segment criterion with the primary key. Does not add the commerce user segment criterion to the database.
	*
	* @param commerceUserSegmentCriterionId the primary key for the new commerce user segment criterion
	* @return the new commerce user segment criterion
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion createCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId) {
		return getService()
				   .createCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);
	}

	public static void deleteCommerceUserSegmentCriteria(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceUserSegmentCriteria(commerceUserSegmentEntryId);
	}

	/**
	* Deletes the commerce user segment criterion from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentCriterion the commerce user segment criterion
	* @return the commerce user segment criterion that was removed
	* @throws PortalException
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion deleteCommerceUserSegmentCriterion(
		com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion commerceUserSegmentCriterion)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceUserSegmentCriterion(commerceUserSegmentCriterion);
	}

	/**
	* Deletes the commerce user segment criterion with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	* @return the commerce user segment criterion that was removed
	* @throws PortalException if a commerce user segment criterion with the primary key could not be found
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion deleteCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion fetchCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId) {
		return getService()
				   .fetchCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> getCommerceUserSegmentCriteria(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> orderByComparator) {
		return getService()
				   .getCommerceUserSegmentCriteria(commerceUserSegmentEntryId,
			start, end, orderByComparator);
	}

	public static int getCommerceUserSegmentCriteriaCount(
		long commerceUserSegmentEntryId) {
		return getService()
				   .getCommerceUserSegmentCriteriaCount(commerceUserSegmentEntryId);
	}

	/**
	* Returns the commerce user segment criterion with the primary key.
	*
	* @param commerceUserSegmentCriterionId the primary key of the commerce user segment criterion
	* @return the commerce user segment criterion
	* @throws PortalException if a commerce user segment criterion with the primary key could not be found
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion getCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);
	}

	/**
	* Returns a range of all the commerce user segment criterions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.user.segment.model.impl.CommerceUserSegmentCriterionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce user segment criterions
	* @param end the upper bound of the range of commerce user segment criterions (not inclusive)
	* @return the range of commerce user segment criterions
	*/
	public static java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> getCommerceUserSegmentCriterions(
		int start, int end) {
		return getService().getCommerceUserSegmentCriterions(start, end);
	}

	/**
	* Returns the number of commerce user segment criterions.
	*
	* @return the number of commerce user segment criterions
	*/
	public static int getCommerceUserSegmentCriterionsCount() {
		return getService().getCommerceUserSegmentCriterionsCount();
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
	* Updates the commerce user segment criterion in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceUserSegmentCriterion the commerce user segment criterion
	* @return the commerce user segment criterion that was updated
	*/
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion updateCommerceUserSegmentCriterion(
		com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion commerceUserSegmentCriterion) {
		return getService()
				   .updateCommerceUserSegmentCriterion(commerceUserSegmentCriterion);
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion updateCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId, String type, String typeSettings,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceUserSegmentCriterion(commerceUserSegmentCriterionId,
			type, typeSettings, priority, serviceContext);
	}

	public static CommerceUserSegmentCriterionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceUserSegmentCriterionLocalService, CommerceUserSegmentCriterionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceUserSegmentCriterionLocalService.class);

		ServiceTracker<CommerceUserSegmentCriterionLocalService, CommerceUserSegmentCriterionLocalService> serviceTracker =
			new ServiceTracker<CommerceUserSegmentCriterionLocalService, CommerceUserSegmentCriterionLocalService>(bundle.getBundleContext(),
				CommerceUserSegmentCriterionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}