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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for OAuth2Token. This utility wraps
 * {@link com.liferay.oauth2.provider.service.impl.OAuth2TokenLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2TokenLocalService
 * @see com.liferay.oauth2.provider.service.base.OAuth2TokenLocalServiceBaseImpl
 * @see com.liferay.oauth2.provider.service.impl.OAuth2TokenLocalServiceImpl
 * @generated
 */
@ProviderType
public class OAuth2TokenLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.oauth2.provider.service.impl.OAuth2TokenLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the o auth2 token to the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2Token the o auth2 token
	* @return the o auth2 token that was added
	*/
	public static com.liferay.oauth2.provider.model.OAuth2Token addOAuth2Token(
		com.liferay.oauth2.provider.model.OAuth2Token oAuth2Token) {
		return getService().addOAuth2Token(oAuth2Token);
	}

	/**
	* Creates a new o auth2 token with the primary key. Does not add the o auth2 token to the database.
	*
	* @param oAuth2TokenId the primary key for the new o auth2 token
	* @return the new o auth2 token
	*/
	public static com.liferay.oauth2.provider.model.OAuth2Token createOAuth2Token(
		long oAuth2TokenId) {
		return getService().createOAuth2Token(oAuth2TokenId);
	}

	public static com.liferay.oauth2.provider.model.OAuth2Token createOAuth2Token(
		java.lang.String tokenContent) {
		return getService().createOAuth2Token(tokenContent);
	}

	/**
	* Deletes the o auth2 token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token that was removed
	* @throws PortalException if a o auth2 token with the primary key could not be found
	*/
	public static com.liferay.oauth2.provider.model.OAuth2Token deleteOAuth2Token(
		long oAuth2TokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOAuth2Token(oAuth2TokenId);
	}

	/**
	* Deletes the o auth2 token from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2Token the o auth2 token
	* @return the o auth2 token that was removed
	*/
	public static com.liferay.oauth2.provider.model.OAuth2Token deleteOAuth2Token(
		com.liferay.oauth2.provider.model.OAuth2Token oAuth2Token) {
		return getService().deleteOAuth2Token(oAuth2Token);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.model.impl.OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.model.impl.OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.oauth2.provider.model.OAuth2Token fetchByContent(
		java.lang.String oAuth2TokenContent) {
		return getService().fetchByContent(oAuth2TokenContent);
	}

	public static com.liferay.oauth2.provider.model.OAuth2Token fetchOAuth2Token(
		long oAuth2TokenId) {
		return getService().fetchOAuth2Token(oAuth2TokenId);
	}

	public static java.util.Collection<com.liferay.oauth2.provider.model.OAuth2Token> findByApplicationId(
		long applicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Token> orderByComparator) {
		return getService()
				   .findByApplicationId(applicationId, start, end,
			orderByComparator);
	}

	public static com.liferay.oauth2.provider.model.OAuth2Token findByContent(
		java.lang.String oAuth2TokenContent)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return getService().findByContent(oAuth2TokenContent);
	}

	public static java.util.Collection<com.liferay.oauth2.provider.model.OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId) {
		return getService().findByRefreshToken(oAuth2RefreshTokenId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the o auth2 token with the primary key.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token
	* @throws PortalException if a o auth2 token with the primary key could not be found
	*/
	public static com.liferay.oauth2.provider.model.OAuth2Token getOAuth2Token(
		long oAuth2TokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOAuth2Token(oAuth2TokenId);
	}

	/**
	* Returns a range of all the o auth2 tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.model.impl.OAuth2TokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 tokens
	* @param end the upper bound of the range of o auth2 tokens (not inclusive)
	* @return the range of o auth2 tokens
	*/
	public static java.util.List<com.liferay.oauth2.provider.model.OAuth2Token> getOAuth2Tokens(
		int start, int end) {
		return getService().getOAuth2Tokens(start, end);
	}

	/**
	* Returns the number of o auth2 tokens.
	*
	* @return the number of o auth2 tokens
	*/
	public static int getOAuth2TokensCount() {
		return getService().getOAuth2TokensCount();
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
	* Updates the o auth2 token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param oAuth2Token the o auth2 token
	* @return the o auth2 token that was updated
	*/
	public static com.liferay.oauth2.provider.model.OAuth2Token updateOAuth2Token(
		com.liferay.oauth2.provider.model.OAuth2Token oAuth2Token) {
		return getService().updateOAuth2Token(oAuth2Token);
	}

	public static OAuth2TokenLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuth2TokenLocalService, OAuth2TokenLocalService> _serviceTracker =
		ServiceTrackerFactory.open(OAuth2TokenLocalService.class);
}