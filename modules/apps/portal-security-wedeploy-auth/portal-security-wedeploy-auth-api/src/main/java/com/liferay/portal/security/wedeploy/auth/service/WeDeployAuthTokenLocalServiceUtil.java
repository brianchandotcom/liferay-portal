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

package com.liferay.portal.security.wedeploy.auth.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for WeDeployAuthToken. This utility wraps
 * {@link com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthTokenLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthTokenLocalService
 * @see com.liferay.portal.security.wedeploy.auth.service.base.WeDeployAuthTokenLocalServiceBaseImpl
 * @see com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthTokenLocalServiceImpl
 * @generated
 */
@ProviderType
public class WeDeployAuthTokenLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthTokenLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken addAccessWeDeployAuthToken(
		java.lang.String redirectURI, java.lang.String clientId,
		java.lang.String clientSecret, java.lang.String authorizationToken,
		int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addAccessWeDeployAuthToken(redirectURI, clientId,
			clientSecret, authorizationToken, type, serviceContext);
	}

	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken addAuthorizationWeDeployAuthToken(
		long userId, java.lang.String redirectURI, java.lang.String clientId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addAuthorizationWeDeployAuthToken(userId, redirectURI,
			clientId, serviceContext);
	}

	/**
	* Adds the we deploy auth token to the database. Also notifies the appropriate model listeners.
	*
	* @param weDeployAuthToken the we deploy auth token
	* @return the we deploy auth token that was added
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken addWeDeployAuthToken(
		com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken weDeployAuthToken) {
		return getService().addWeDeployAuthToken(weDeployAuthToken);
	}

	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken addWeDeployAuthToken(
		long userId, java.lang.String clientId, java.lang.String token,
		int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addWeDeployAuthToken(userId, clientId, token, type,
			serviceContext);
	}

	/**
	* Creates a new we deploy auth token with the primary key. Does not add the we deploy auth token to the database.
	*
	* @param weDeployAuthTokenId the primary key for the new we deploy auth token
	* @return the new we deploy auth token
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken createWeDeployAuthToken(
		long weDeployAuthTokenId) {
		return getService().createWeDeployAuthToken(weDeployAuthTokenId);
	}

	/**
	* Deletes the we deploy auth token from the database. Also notifies the appropriate model listeners.
	*
	* @param weDeployAuthToken the we deploy auth token
	* @return the we deploy auth token that was removed
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken deleteWeDeployAuthToken(
		com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken weDeployAuthToken) {
		return getService().deleteWeDeployAuthToken(weDeployAuthToken);
	}

	/**
	* Deletes the we deploy auth token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param weDeployAuthTokenId the primary key of the we deploy auth token
	* @return the we deploy auth token that was removed
	* @throws PortalException if a we deploy auth token with the primary key could not be found
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken deleteWeDeployAuthToken(
		long weDeployAuthTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteWeDeployAuthToken(weDeployAuthTokenId);
	}

	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken fetchWeDeployAuthToken(
		long weDeployAuthTokenId) {
		return getService().fetchWeDeployAuthToken(weDeployAuthTokenId);
	}

	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken getWeDeployAuthToken(
		java.lang.String token, int type)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWeDeployAuthToken(token, type);
	}

	/**
	* Returns the we deploy auth token with the primary key.
	*
	* @param weDeployAuthTokenId the primary key of the we deploy auth token
	* @return the we deploy auth token
	* @throws PortalException if a we deploy auth token with the primary key could not be found
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken getWeDeployAuthToken(
		long weDeployAuthTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWeDeployAuthToken(weDeployAuthTokenId);
	}

	/**
	* Updates the we deploy auth token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param weDeployAuthToken the we deploy auth token
	* @return the we deploy auth token that was updated
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken updateWeDeployAuthToken(
		com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken weDeployAuthToken) {
		return getService().updateWeDeployAuthToken(weDeployAuthToken);
	}

	/**
	* Returns the number of we deploy auth tokens.
	*
	* @return the number of we deploy auth tokens
	*/
	public static int getWeDeployAuthTokensCount() {
		return getService().getWeDeployAuthTokensCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the we deploy auth tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of we deploy auth tokens
	* @param end the upper bound of the range of we deploy auth tokens (not inclusive)
	* @return the range of we deploy auth tokens
	*/
	public static java.util.List<com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken> getWeDeployAuthTokens(
		int start, int end) {
		return getService().getWeDeployAuthTokens(start, end);
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

	public static WeDeployAuthTokenLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<WeDeployAuthTokenLocalService, WeDeployAuthTokenLocalService> _serviceTracker =
		ServiceTrackerFactory.open(WeDeployAuthTokenLocalService.class);
}