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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link WeDeployAuthTokenLocalService}.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthTokenLocalService
 * @generated
 */
public class WeDeployAuthTokenLocalServiceWrapper
	implements ServiceWrapper<WeDeployAuthTokenLocalService>,
			   WeDeployAuthTokenLocalService {

	public WeDeployAuthTokenLocalServiceWrapper(
		WeDeployAuthTokenLocalService weDeployAuthTokenLocalService) {

		_weDeployAuthTokenLocalService = weDeployAuthTokenLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link WeDeployAuthTokenLocalServiceUtil} to access the we deploy auth token local service. Add custom service methods to <code>com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthTokenLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
			addAccessWeDeployAuthToken(
				String redirectURI, String clientId, String clientSecret,
				String authorizationToken, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthTokenLocalService.addAccessWeDeployAuthToken(
			redirectURI, clientId, clientSecret, authorizationToken, type,
			serviceContext);
	}

	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
			addAuthorizationWeDeployAuthToken(
				long userId, String redirectURI, String clientId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthTokenLocalService.addAuthorizationWeDeployAuthToken(
			userId, redirectURI, clientId, serviceContext);
	}

	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
			addWeDeployAuthToken(
				long userId, String clientId, String token, int type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthTokenLocalService.addWeDeployAuthToken(
			userId, clientId, token, type, serviceContext);
	}

	/**
	 * Adds the we deploy auth token to the database. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthToken the we deploy auth token
	 * @return the we deploy auth token that was added
	 */
	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
		addWeDeployAuthToken(
			com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
				weDeployAuthToken) {

		return _weDeployAuthTokenLocalService.addWeDeployAuthToken(
			weDeployAuthToken);
	}

	/**
	 * Creates a new we deploy auth token with the primary key. Does not add the we deploy auth token to the database.
	 *
	 * @param weDeployAuthTokenId the primary key for the new we deploy auth token
	 * @return the new we deploy auth token
	 */
	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
		createWeDeployAuthToken(long weDeployAuthTokenId) {

		return _weDeployAuthTokenLocalService.createWeDeployAuthToken(
			weDeployAuthTokenId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthTokenLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the we deploy auth token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token that was removed
	 * @throws PortalException if a we deploy auth token with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
			deleteWeDeployAuthToken(long weDeployAuthTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthTokenLocalService.deleteWeDeployAuthToken(
			weDeployAuthTokenId);
	}

	/**
	 * Deletes the we deploy auth token from the database. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthToken the we deploy auth token
	 * @return the we deploy auth token that was removed
	 */
	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
		deleteWeDeployAuthToken(
			com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
				weDeployAuthToken) {

		return _weDeployAuthTokenLocalService.deleteWeDeployAuthToken(
			weDeployAuthToken);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _weDeployAuthTokenLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _weDeployAuthTokenLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _weDeployAuthTokenLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _weDeployAuthTokenLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _weDeployAuthTokenLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _weDeployAuthTokenLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
		fetchWeDeployAuthToken(long weDeployAuthTokenId) {

		return _weDeployAuthTokenLocalService.fetchWeDeployAuthToken(
			weDeployAuthTokenId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _weDeployAuthTokenLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _weDeployAuthTokenLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _weDeployAuthTokenLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthTokenLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the we deploy auth token with the primary key.
	 *
	 * @param weDeployAuthTokenId the primary key of the we deploy auth token
	 * @return the we deploy auth token
	 * @throws PortalException if a we deploy auth token with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
			getWeDeployAuthToken(long weDeployAuthTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthTokenLocalService.getWeDeployAuthToken(
			weDeployAuthTokenId);
	}

	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
			getWeDeployAuthToken(String token, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _weDeployAuthTokenLocalService.getWeDeployAuthToken(token, type);
	}

	/**
	 * Returns a range of all the we deploy auth tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of we deploy auth tokens
	 * @param end the upper bound of the range of we deploy auth tokens (not inclusive)
	 * @return the range of we deploy auth tokens
	 */
	@Override
	public java.util.List
		<com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken>
			getWeDeployAuthTokens(int start, int end) {

		return _weDeployAuthTokenLocalService.getWeDeployAuthTokens(start, end);
	}

	/**
	 * Returns the number of we deploy auth tokens.
	 *
	 * @return the number of we deploy auth tokens
	 */
	@Override
	public int getWeDeployAuthTokensCount() {
		return _weDeployAuthTokenLocalService.getWeDeployAuthTokensCount();
	}

	/**
	 * Updates the we deploy auth token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param weDeployAuthToken the we deploy auth token
	 * @return the we deploy auth token that was updated
	 */
	@Override
	public com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
		updateWeDeployAuthToken(
			com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthToken
				weDeployAuthToken) {

		return _weDeployAuthTokenLocalService.updateWeDeployAuthToken(
			weDeployAuthToken);
	}

	@Override
	public WeDeployAuthTokenLocalService getWrappedService() {
		return _weDeployAuthTokenLocalService;
	}

	@Override
	public void setWrappedService(
		WeDeployAuthTokenLocalService weDeployAuthTokenLocalService) {

		_weDeployAuthTokenLocalService = weDeployAuthTokenLocalService;
	}

	private WeDeployAuthTokenLocalService _weDeployAuthTokenLocalService;

}