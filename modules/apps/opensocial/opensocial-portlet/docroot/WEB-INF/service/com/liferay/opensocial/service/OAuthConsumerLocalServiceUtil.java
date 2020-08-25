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

package com.liferay.opensocial.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;

/**
 * Provides the local service utility for OAuthConsumer. This utility wraps
 * <code>com.liferay.opensocial.service.impl.OAuthConsumerLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthConsumerLocalService
 * @generated
 */
public class OAuthConsumerLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.opensocial.service.impl.OAuthConsumerLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.opensocial.model.OAuthConsumer addOAuthConsumer(
		long companyId, String gadgetKey, String serviceName,
		String consumerKey, String consumerSecret, String keyType) {

		return getService().addOAuthConsumer(
			companyId, gadgetKey, serviceName, consumerKey, consumerSecret,
			keyType);
	}

	/**
	 * Adds the o auth consumer to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthConsumerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthConsumer the o auth consumer
	 * @return the o auth consumer that was added
	 */
	public static com.liferay.opensocial.model.OAuthConsumer addOAuthConsumer(
		com.liferay.opensocial.model.OAuthConsumer oAuthConsumer) {

		return getService().addOAuthConsumer(oAuthConsumer);
	}

	/**
	 * Creates a new o auth consumer with the primary key. Does not add the o auth consumer to the database.
	 *
	 * @param oAuthConsumerId the primary key for the new o auth consumer
	 * @return the new o auth consumer
	 */
	public static com.liferay.opensocial.model.OAuthConsumer
		createOAuthConsumer(long oAuthConsumerId) {

		return getService().createOAuthConsumer(oAuthConsumerId);
	}

	/**
	 * Deletes the o auth consumer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthConsumerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthConsumerId the primary key of the o auth consumer
	 * @return the o auth consumer that was removed
	 * @throws PortalException if a o auth consumer with the primary key could not be found
	 */
	public static com.liferay.opensocial.model.OAuthConsumer
			deleteOAuthConsumer(long oAuthConsumerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteOAuthConsumer(oAuthConsumerId);
	}

	/**
	 * Deletes the o auth consumer from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthConsumerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthConsumer the o auth consumer
	 * @return the o auth consumer that was removed
	 */
	public static com.liferay.opensocial.model.OAuthConsumer
		deleteOAuthConsumer(
			com.liferay.opensocial.model.OAuthConsumer oAuthConsumer) {

		return getService().deleteOAuthConsumer(oAuthConsumer);
	}

	public static void deleteOAuthConsumers(String gadgetKey) {
		getService().deleteOAuthConsumers(gadgetKey);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthConsumerModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthConsumerModelImpl</code>.
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

	public static com.liferay.opensocial.model.OAuthConsumer fetchOAuthConsumer(
		long oAuthConsumerId) {

		return getService().fetchOAuthConsumer(oAuthConsumerId);
	}

	public static com.liferay.opensocial.model.OAuthConsumer fetchOAuthConsumer(
		String gadgetKey, String serviceName) {

		return getService().fetchOAuthConsumer(gadgetKey, serviceName);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the o auth consumer with the primary key.
	 *
	 * @param oAuthConsumerId the primary key of the o auth consumer
	 * @return the o auth consumer
	 * @throws PortalException if a o auth consumer with the primary key could not be found
	 */
	public static com.liferay.opensocial.model.OAuthConsumer getOAuthConsumer(
			long oAuthConsumerId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getOAuthConsumer(oAuthConsumerId);
	}

	public static com.liferay.opensocial.model.OAuthConsumer getOAuthConsumer(
			String gadgetKey, String serviceName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getOAuthConsumer(gadgetKey, serviceName);
	}

	/**
	 * Returns a range of all the o auth consumers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthConsumerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth consumers
	 * @param end the upper bound of the range of o auth consumers (not inclusive)
	 * @return the range of o auth consumers
	 */
	public static java.util.List<com.liferay.opensocial.model.OAuthConsumer>
		getOAuthConsumers(int start, int end) {

		return getService().getOAuthConsumers(start, end);
	}

	public static java.util.List<com.liferay.opensocial.model.OAuthConsumer>
		getOAuthConsumers(String gadgetKey) {

		return getService().getOAuthConsumers(gadgetKey);
	}

	public static java.util.List<com.liferay.opensocial.model.OAuthConsumer>
		getOAuthConsumers(String gadgetKey, int start, int end) {

		return getService().getOAuthConsumers(gadgetKey, start, end);
	}

	/**
	 * Returns the number of o auth consumers.
	 *
	 * @return the number of o auth consumers
	 */
	public static int getOAuthConsumersCount() {
		return getService().getOAuthConsumersCount();
	}

	public static int getOAuthConsumersCount(String gadgetKey) {
		return getService().getOAuthConsumersCount(gadgetKey);
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

	public static com.liferay.opensocial.model.OAuthConsumer
			updateOAuthConsumer(
				long oAuthConsumerId, String consumerKey, String consumerSecret,
				String keyType, String keyName, String callbackURL)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateOAuthConsumer(
			oAuthConsumerId, consumerKey, consumerSecret, keyType, keyName,
			callbackURL);
	}

	/**
	 * Updates the o auth consumer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthConsumerLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthConsumer the o auth consumer
	 * @return the o auth consumer that was updated
	 */
	public static com.liferay.opensocial.model.OAuthConsumer
		updateOAuthConsumer(
			com.liferay.opensocial.model.OAuthConsumer oAuthConsumer) {

		return getService().updateOAuthConsumer(oAuthConsumer);
	}

	public static void clearService() {
		_service = null;
	}

	public static OAuthConsumerLocalService getService() {
		if (_service == null) {
			_service = (OAuthConsumerLocalService)PortletBeanLocatorUtil.locate(
				ServletContextUtil.getServletContextName(),
				OAuthConsumerLocalService.class.getName());
		}

		return _service;
	}

	private static OAuthConsumerLocalService _service;

}