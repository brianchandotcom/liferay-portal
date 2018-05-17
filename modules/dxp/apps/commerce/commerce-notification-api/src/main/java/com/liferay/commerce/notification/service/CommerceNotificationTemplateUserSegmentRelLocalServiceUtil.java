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

package com.liferay.commerce.notification.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceNotificationTemplateUserSegmentRel. This utility wraps
 * {@link com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateUserSegmentRelLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUserSegmentRelLocalService
 * @see com.liferay.commerce.notification.service.base.CommerceNotificationTemplateUserSegmentRelLocalServiceBaseImpl
 * @see com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateUserSegmentRelLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateUserSegmentRelLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the commerce notification template user segment rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRel the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was added
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel addCommerceNotificationTemplateUserSegmentRel(
		com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		return getService()
				   .addCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRel);
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel addCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateId,
			commerceUserSegmentEntryId, serviceContext);
	}

	/**
	* Creates a new commerce notification template user segment rel with the primary key. Does not add the commerce notification template user segment rel to the database.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key for the new commerce notification template user segment rel
	* @return the new commerce notification template user segment rel
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel createCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId) {
		return getService()
				   .createCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRelId);
	}

	public static void deleteCNTemplateUserSegmentRelsByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		getService()
			.deleteCNTemplateUserSegmentRelsByCommerceNotificationTemplateId(commerceNotificationTemplateId);
	}

	public static void deleteCNTemplateUserSegmentRelsByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		getService()
			.deleteCNTemplateUserSegmentRelsByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);
	}

	/**
	* Deletes the commerce notification template user segment rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRel the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was removed
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel deleteCommerceNotificationTemplateUserSegmentRel(
		com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		return getService()
				   .deleteCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRel);
	}

	/**
	* Deletes the commerce notification template user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was removed
	* @throws PortalException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel deleteCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRelId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel fetchCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId) {
		return getService()
				   .fetchCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRelId);
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel fetchCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId) {
		return getService()
				   .fetchCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateId,
			commerceUserSegmentEntryId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	/**
	* Returns the commerce notification template user segment rel with the primary key.
	*
	* @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	* @return the commerce notification template user segment rel
	* @throws PortalException if a commerce notification template user segment rel with the primary key could not be found
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel getCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRelId);
	}

	/**
	* Returns a range of all the commerce notification template user segment rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce notification template user segment rels
	* @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	* @return the range of commerce notification template user segment rels
	*/
	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> getCommerceNotificationTemplateUserSegmentRels(
		int start, int end) {
		return getService()
				   .getCommerceNotificationTemplateUserSegmentRels(start, end);
	}

	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> getCommerceNotificationTemplateUserSegmentRels(
		long commerceNotificationTemplateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return getService()
				   .getCommerceNotificationTemplateUserSegmentRels(commerceNotificationTemplateId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of commerce notification template user segment rels.
	*
	* @return the number of commerce notification template user segment rels
	*/
	public static int getCommerceNotificationTemplateUserSegmentRelsCount() {
		return getService().getCommerceNotificationTemplateUserSegmentRelsCount();
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
	* Updates the commerce notification template user segment rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceNotificationTemplateUserSegmentRel the commerce notification template user segment rel
	* @return the commerce notification template user segment rel that was updated
	*/
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel updateCommerceNotificationTemplateUserSegmentRel(
		com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		return getService()
				   .updateCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRel);
	}

	public static CommerceNotificationTemplateUserSegmentRelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceNotificationTemplateUserSegmentRelLocalService, CommerceNotificationTemplateUserSegmentRelLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceNotificationTemplateUserSegmentRelLocalService.class);

		ServiceTracker<CommerceNotificationTemplateUserSegmentRelLocalService, CommerceNotificationTemplateUserSegmentRelLocalService> serviceTracker =
			new ServiceTracker<CommerceNotificationTemplateUserSegmentRelLocalService, CommerceNotificationTemplateUserSegmentRelLocalService>(bundle.getBundleContext(),
				CommerceNotificationTemplateUserSegmentRelLocalService.class,
				null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}