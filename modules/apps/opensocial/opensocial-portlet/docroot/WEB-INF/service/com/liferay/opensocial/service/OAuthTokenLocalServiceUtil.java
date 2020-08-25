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
 * Provides the local service utility for OAuthToken. This utility wraps
 * <code>com.liferay.opensocial.service.impl.OAuthTokenLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthTokenLocalService
 * @generated
 */
public class OAuthTokenLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.opensocial.service.impl.OAuthTokenLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.opensocial.model.OAuthToken addOAuthToken(
			long userId, String gadgetKey, String serviceName, long moduleId,
			String accessToken, String tokenName, String tokenSecret,
			String sessionHandle, long expiration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addOAuthToken(
			userId, gadgetKey, serviceName, moduleId, accessToken, tokenName,
			tokenSecret, sessionHandle, expiration);
	}

	/**
	 * Adds the o auth token to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthTokenLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthToken the o auth token
	 * @return the o auth token that was added
	 */
	public static com.liferay.opensocial.model.OAuthToken addOAuthToken(
		com.liferay.opensocial.model.OAuthToken oAuthToken) {

		return getService().addOAuthToken(oAuthToken);
	}

	/**
	 * Creates a new o auth token with the primary key. Does not add the o auth token to the database.
	 *
	 * @param oAuthTokenId the primary key for the new o auth token
	 * @return the new o auth token
	 */
	public static com.liferay.opensocial.model.OAuthToken createOAuthToken(
		long oAuthTokenId) {

		return getService().createOAuthToken(oAuthTokenId);
	}

	/**
	 * Deletes the o auth token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthTokenLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthTokenId the primary key of the o auth token
	 * @return the o auth token that was removed
	 * @throws PortalException if a o auth token with the primary key could not be found
	 */
	public static com.liferay.opensocial.model.OAuthToken deleteOAuthToken(
			long oAuthTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteOAuthToken(oAuthTokenId);
	}

	public static void deleteOAuthToken(
			long userId, String gadgetKey, String serviceName, long moduleId,
			String tokenName)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteOAuthToken(
			userId, gadgetKey, serviceName, moduleId, tokenName);
	}

	/**
	 * Deletes the o auth token from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthTokenLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthToken the o auth token
	 * @return the o auth token that was removed
	 */
	public static com.liferay.opensocial.model.OAuthToken deleteOAuthToken(
		com.liferay.opensocial.model.OAuthToken oAuthToken) {

		return getService().deleteOAuthToken(oAuthToken);
	}

	public static void deleteOAuthTokens(String gadgetKey, String serviceName) {
		getService().deleteOAuthTokens(gadgetKey, serviceName);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthTokenModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthTokenModelImpl</code>.
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

	public static com.liferay.opensocial.model.OAuthToken fetchOAuthToken(
		long oAuthTokenId) {

		return getService().fetchOAuthToken(oAuthTokenId);
	}

	public static com.liferay.opensocial.model.OAuthToken fetchOAuthToken(
		long userId, String gadgetKey, String serviceName, long moduleId,
		String tokenName) {

		return getService().fetchOAuthToken(
			userId, gadgetKey, serviceName, moduleId, tokenName);
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
	 * Returns the o auth token with the primary key.
	 *
	 * @param oAuthTokenId the primary key of the o auth token
	 * @return the o auth token
	 * @throws PortalException if a o auth token with the primary key could not be found
	 */
	public static com.liferay.opensocial.model.OAuthToken getOAuthToken(
			long oAuthTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getOAuthToken(oAuthTokenId);
	}

	public static com.liferay.opensocial.model.OAuthToken getOAuthToken(
			long userId, String gadgetKey, String serviceName, long moduleId,
			String tokenName)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getOAuthToken(
			userId, gadgetKey, serviceName, moduleId, tokenName);
	}

	/**
	 * Returns a range of all the o auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth tokens
	 * @param end the upper bound of the range of o auth tokens (not inclusive)
	 * @return the range of o auth tokens
	 */
	public static java.util.List<com.liferay.opensocial.model.OAuthToken>
		getOAuthTokens(int start, int end) {

		return getService().getOAuthTokens(start, end);
	}

	public static java.util.List<com.liferay.opensocial.model.OAuthToken>
		getOAuthTokens(String gadgetKey, String serviceName) {

		return getService().getOAuthTokens(gadgetKey, serviceName);
	}

	/**
	 * Returns the number of o auth tokens.
	 *
	 * @return the number of o auth tokens
	 */
	public static int getOAuthTokensCount() {
		return getService().getOAuthTokensCount();
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

	/**
	 * Updates the o auth token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect OAuthTokenLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param oAuthToken the o auth token
	 * @return the o auth token that was updated
	 */
	public static com.liferay.opensocial.model.OAuthToken updateOAuthToken(
		com.liferay.opensocial.model.OAuthToken oAuthToken) {

		return getService().updateOAuthToken(oAuthToken);
	}

	public static void clearService() {
		_service = null;
	}

	public static OAuthTokenLocalService getService() {
		if (_service == null) {
			_service = (OAuthTokenLocalService)PortletBeanLocatorUtil.locate(
				ServletContextUtil.getServletContextName(),
				OAuthTokenLocalService.class.getName());
		}

		return _service;
	}

	private static OAuthTokenLocalService _service;

}