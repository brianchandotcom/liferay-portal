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

package com.liferay.oauth2.provider.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for OAuth2AccessToken. This utility wraps
 * {@link com.liferay.oauth2.provider.service.impl.OAuth2AccessTokenLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AccessTokenLocalService
 * @see com.liferay.oauth2.provider.service.base.OAuth2AccessTokenLocalServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.impl.OAuth2AccessTokenLocalServiceImpl
 * @generated
 */
@ProviderType
public class OAuth2AccessTokenLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.oauth2.provider.service.impl.OAuth2AccessTokenLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the o auth2 access token to the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2AccessToken the o auth2 access token
	* @return the o auth2 access token that was added
	*/
	public static com.liferay.oauth2.provider.model.OAuth2AccessToken addOAuth2AccessToken(
		com.liferay.oauth2.provider.model.OAuth2AccessToken oAuth2AccessToken) {
		return getService().addOAuth2AccessToken(oAuth2AccessToken);
	}

	/**
	* Creates a new o auth2 access token with the primary key. Does not add the o auth2 access token to the database.
	*
	* @param oAuth2AccessTokenId the primary key for the new o auth2 access token
	* @return the new o auth2 access token
	*/
	public static com.liferay.oauth2.provider.model.OAuth2AccessToken createOAuth2AccessToken(
		long oAuth2AccessTokenId) {
		return getService().createOAuth2AccessToken(oAuth2AccessTokenId);
	}

	/**
	* Deletes the o auth2 access token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2AccessTokenId the primary key of the o auth2 access token
	* @return the o auth2 access token that was removed
	* @throws PortalException if a o auth2 access token with the primary key could not be found
	*/
	public static com.liferay.oauth2.provider.model.OAuth2AccessToken deleteOAuth2AccessToken(
		long oAuth2AccessTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOAuth2AccessToken(oAuth2AccessTokenId);
	}

	/**
	* Deletes the o auth2 access token from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2AccessToken the o auth2 access token
	* @return the o auth2 access token that was removed
	*/
	public static com.liferay.oauth2.provider.model.OAuth2AccessToken deleteOAuth2AccessToken(
		com.liferay.oauth2.provider.model.OAuth2AccessToken oAuth2AccessToken) {
		return getService().deleteOAuth2AccessToken(oAuth2AccessToken);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.model.impl.OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.model.impl.OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.oauth2.provider.model.OAuth2AccessToken fetchOAuth2AccessToken(
		long oAuth2AccessTokenId) {
		return getService().fetchOAuth2AccessToken(oAuth2AccessTokenId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the o auth2 access token with the primary key.
	*
	* @param oAuth2AccessTokenId the primary key of the o auth2 access token
	* @return the o auth2 access token
	* @throws PortalException if a o auth2 access token with the primary key could not be found
	*/
	public static com.liferay.oauth2.provider.model.OAuth2AccessToken getOAuth2AccessToken(
		long oAuth2AccessTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOAuth2AccessToken(oAuth2AccessTokenId);
	}

	/**
	* Returns a range of all the o auth2 access tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.model.impl.OAuth2AccessTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 access tokens
	* @param end the upper bound of the range of o auth2 access tokens (not inclusive)
	* @return the range of o auth2 access tokens
	*/
	public static java.util.List<com.liferay.oauth2.provider.model.OAuth2AccessToken> getOAuth2AccessTokens(
		int start, int end) {
		return getService().getOAuth2AccessTokens(start, end);
	}

	/**
	* Returns the number of o auth2 access tokens.
	*
	* @return the number of o auth2 access tokens
	*/
	public static int getOAuth2AccessTokensCount() {
		return getService().getOAuth2AccessTokensCount();
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
	* Updates the o auth2 access token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param oAuth2AccessToken the o auth2 access token
	* @return the o auth2 access token that was updated
	*/
	public static com.liferay.oauth2.provider.model.OAuth2AccessToken updateOAuth2AccessToken(
		com.liferay.oauth2.provider.model.OAuth2AccessToken oAuth2AccessToken) {
		return getService().updateOAuth2AccessToken(oAuth2AccessToken);
	}

	public static OAuth2AccessTokenLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2AccessTokenLocalService, OAuth2AccessTokenLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(OAuth2AccessTokenLocalService.class);

		ServiceTracker<OAuth2AccessTokenLocalService, OAuth2AccessTokenLocalService> serviceTracker =
			new ServiceTracker<OAuth2AccessTokenLocalService, OAuth2AccessTokenLocalService>(bundle.getBundleContext(),
				OAuth2AccessTokenLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}