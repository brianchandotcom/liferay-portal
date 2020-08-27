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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceChannel. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CommerceChannelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceChannelLocalService
 * @generated
 */
public class CommerceChannelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CommerceChannelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the commerce channel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceChannelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceChannel the commerce channel
	 * @return the commerce channel that was added
	 */
	public static com.liferay.commerce.product.model.CommerceChannel
		addCommerceChannel(
			com.liferay.commerce.product.model.CommerceChannel
				commerceChannel) {

		return getService().addCommerceChannel(commerceChannel);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			addCommerceChannel(
				long siteGroupId, String name, String type,
				com.liferay.portal.kernel.util.UnicodeProperties
					typeSettingsUnicodeProperties,
				String commerceCurrencyCode, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceChannel(
			siteGroupId, name, type, typeSettingsUnicodeProperties,
			commerceCurrencyCode, externalReferenceCode, serviceContext);
	}

	/**
	 * Creates a new commerce channel with the primary key. Does not add the commerce channel to the database.
	 *
	 * @param commerceChannelId the primary key for the new commerce channel
	 * @return the new commerce channel
	 */
	public static com.liferay.commerce.product.model.CommerceChannel
		createCommerceChannel(long commerceChannelId) {

		return getService().createCommerceChannel(commerceChannelId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the commerce channel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceChannelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceChannel the commerce channel
	 * @return the commerce channel that was removed
	 * @throws PortalException
	 */
	public static com.liferay.commerce.product.model.CommerceChannel
			deleteCommerceChannel(
				com.liferay.commerce.product.model.CommerceChannel
					commerceChannel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceChannel(commerceChannel);
	}

	/**
	 * Deletes the commerce channel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceChannelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceChannelId the primary key of the commerce channel
	 * @return the commerce channel that was removed
	 * @throws PortalException if a commerce channel with the primary key could not be found
	 */
	public static com.liferay.commerce.product.model.CommerceChannel
			deleteCommerceChannel(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceChannel(commerceChannelId);
	}

	public static void deleteCommerceChannels(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceChannels(companyId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CommerceChannelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CommerceChannelModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.commerce.product.model.CommerceChannel
		fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
		fetchCommerceChannel(long commerceChannelId) {

		return getService().fetchCommerceChannel(commerceChannelId);
	}

	/**
	 * Returns the commerce channel with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce channel's external reference code
	 * @return the matching commerce channel, or <code>null</code> if a matching commerce channel could not be found
	 */
	public static com.liferay.commerce.product.model.CommerceChannel
		fetchCommerceChannelByReferenceCode(
			long companyId, String externalReferenceCode) {

		return getService().fetchCommerceChannelByReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
		fetchCommerceChannelBySiteGroupId(long siteGroupId) {

		return getService().fetchCommerceChannelBySiteGroupId(siteGroupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the commerce channel with the primary key.
	 *
	 * @param commerceChannelId the primary key of the commerce channel
	 * @return the commerce channel
	 * @throws PortalException if a commerce channel with the primary key could not be found
	 */
	public static com.liferay.commerce.product.model.CommerceChannel
			getCommerceChannel(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceChannel(commerceChannelId);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			getCommerceChannelByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceChannelByGroupId(groupId);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			getCommerceChannelByOrderGroupId(long orderGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceChannelByOrderGroupId(orderGroupId);
	}

	public static com.liferay.portal.kernel.model.Group getCommerceChannelGroup(
			long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceChannelGroup(commerceChannelId);
	}

	public static long getCommerceChannelGroupIdBySiteGroupId(long siteGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceChannelGroupIdBySiteGroupId(siteGroupId);
	}

	/**
	 * Returns a range of all the commerce channels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.product.model.impl.CommerceChannelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce channels
	 * @param end the upper bound of the range of commerce channels (not inclusive)
	 * @return the range of commerce channels
	 */
	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannel>
			getCommerceChannels(int start, int end) {

		return getService().getCommerceChannels(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannel>
			getCommerceChannels(long companyId) {

		return getService().getCommerceChannels(companyId);
	}

	/**
	 * Returns the number of commerce channels.
	 *
	 * @return the number of commerce channels
	 */
	public static int getCommerceChannelsCount() {
		return getService().getCommerceChannelsCount();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

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

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannel>
				searchCommerceChannels(long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceChannels(companyId);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannel>
				searchCommerceChannels(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceChannels(
			companyId, keywords, start, end, sort);
	}

	public static int searchCommerceChannelsCount(
			long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceChannelsCount(companyId, keywords);
	}

	/**
	 * Updates the commerce channel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceChannelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceChannel the commerce channel
	 * @return the commerce channel that was updated
	 */
	public static com.liferay.commerce.product.model.CommerceChannel
		updateCommerceChannel(
			com.liferay.commerce.product.model.CommerceChannel
				commerceChannel) {

		return getService().updateCommerceChannel(commerceChannel);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			updateCommerceChannel(
				long commerceChannelId, long siteGroupId, String name,
				String type,
				com.liferay.portal.kernel.util.UnicodeProperties
					typeSettingsUnicodeProperties,
				String commerceCurrencyCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			updateCommerceChannel(
				long commerceChannelId, long siteGroupId, String name,
				String type,
				com.liferay.portal.kernel.util.UnicodeProperties
					typeSettingsUnicodeProperties,
				String commerceCurrencyCode, String priceDisplayType,
				boolean discountsTargetNetPrice)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			priceDisplayType, discountsTargetNetPrice);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			updateCommerceChannelExternalReferenceCode(
				long commerceChannelId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceChannelExternalReferenceCode(
			commerceChannelId, externalReferenceCode);
	}

	public static CommerceChannelLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceChannelLocalService, CommerceChannelLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceChannelLocalService.class);

		ServiceTracker<CommerceChannelLocalService, CommerceChannelLocalService>
			serviceTracker =
				new ServiceTracker
					<CommerceChannelLocalService, CommerceChannelLocalService>(
						bundle.getBundleContext(),
						CommerceChannelLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}