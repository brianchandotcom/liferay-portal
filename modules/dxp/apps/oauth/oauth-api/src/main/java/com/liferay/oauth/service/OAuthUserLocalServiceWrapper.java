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

package com.liferay.oauth.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuthUserLocalService}.
 *
 * @author Ivica Cardic
 * @see OAuthUserLocalService
 * @generated
 */
@ProviderType
public class OAuthUserLocalServiceWrapper implements OAuthUserLocalService,
	ServiceWrapper<OAuthUserLocalService> {
	public OAuthUserLocalServiceWrapper(
		OAuthUserLocalService oAuthUserLocalService) {
		_oAuthUserLocalService = oAuthUserLocalService;
	}

	@Override
	public com.liferay.oauth.model.OAuthUser addOAuthUser(long userId,
		long oAuthApplicationId, String accessToken, String accessSecret,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.addOAuthUser(userId, oAuthApplicationId,
			accessToken, accessSecret, serviceContext);
	}

	/**
	* Adds the o auth user to the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthUser the o auth user
	* @return the o auth user that was added
	*/
	@Override
	public com.liferay.oauth.model.OAuthUser addOAuthUser(
		com.liferay.oauth.model.OAuthUser oAuthUser) {
		return _oAuthUserLocalService.addOAuthUser(oAuthUser);
	}

	/**
	* Creates a new o auth user with the primary key. Does not add the o auth user to the database.
	*
	* @param oAuthUserId the primary key for the new o auth user
	* @return the new o auth user
	*/
	@Override
	public com.liferay.oauth.model.OAuthUser createOAuthUser(long oAuthUserId) {
		return _oAuthUserLocalService.createOAuthUser(oAuthUserId);
	}

	/**
	* Deletes the o auth user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthUserId the primary key of the o auth user
	* @return the o auth user that was removed
	* @throws PortalException if a o auth user with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth.model.OAuthUser deleteOAuthUser(long oAuthUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.deleteOAuthUser(oAuthUserId);
	}

	@Override
	public com.liferay.oauth.model.OAuthUser deleteOAuthUser(long userId,
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.deleteOAuthUser(userId, oAuthApplicationId);
	}

	/**
	* Deletes the o auth user from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthUser the o auth user
	* @return the o auth user that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.oauth.model.OAuthUser deleteOAuthUser(
		com.liferay.oauth.model.OAuthUser oAuthUser)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.deleteOAuthUser(oAuthUser);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _oAuthUserLocalService.dynamicQuery();
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
		return _oAuthUserLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _oAuthUserLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _oAuthUserLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
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
		return _oAuthUserLocalService.dynamicQueryCount(dynamicQuery);
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
		return _oAuthUserLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.oauth.model.OAuthUser fetchOAuthUser(long oAuthUserId) {
		return _oAuthUserLocalService.fetchOAuthUser(oAuthUserId);
	}

	@Override
	public com.liferay.oauth.model.OAuthUser fetchOAuthUser(long userId,
		long oAuthApplicationId) {
		return _oAuthUserLocalService.fetchOAuthUser(userId, oAuthApplicationId);
	}

	@Override
	public com.liferay.oauth.model.OAuthUser fetchOAuthUser(String accessToken) {
		return _oAuthUserLocalService.fetchOAuthUser(accessToken);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _oAuthUserLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _oAuthUserLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.oauth.model.OAuthUser> getOAuthApplicationOAuthUsers(
		long oAuthApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _oAuthUserLocalService.getOAuthApplicationOAuthUsers(oAuthApplicationId,
			start, end, orderByComparator);
	}

	@Override
	public int getOAuthApplicationOAuthUsersCount(long oAuthApplicationId) {
		return _oAuthUserLocalService.getOAuthApplicationOAuthUsersCount(oAuthApplicationId);
	}

	/**
	* Returns the o auth user with the primary key.
	*
	* @param oAuthUserId the primary key of the o auth user
	* @return the o auth user
	* @throws PortalException if a o auth user with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth.model.OAuthUser getOAuthUser(long oAuthUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.getOAuthUser(oAuthUserId);
	}

	@Override
	public com.liferay.oauth.model.OAuthUser getOAuthUser(long userId,
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.getOAuthUser(userId, oAuthApplicationId);
	}

	@Override
	public com.liferay.oauth.model.OAuthUser getOAuthUser(String accessToken)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.getOAuthUser(accessToken);
	}

	/**
	* Returns a range of all the o auth users.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth users
	* @param end the upper bound of the range of o auth users (not inclusive)
	* @return the range of o auth users
	*/
	@Override
	public java.util.List<com.liferay.oauth.model.OAuthUser> getOAuthUsers(
		int start, int end) {
		return _oAuthUserLocalService.getOAuthUsers(start, end);
	}

	/**
	* Returns the number of o auth users.
	*
	* @return the number of o auth users
	*/
	@Override
	public int getOAuthUsersCount() {
		return _oAuthUserLocalService.getOAuthUsersCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.oauth.model.OAuthUser> getUserOAuthUsers(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _oAuthUserLocalService.getUserOAuthUsers(userId, start, end,
			orderByComparator);
	}

	@Override
	public int getUserOAuthUsersCount(long userId) {
		return _oAuthUserLocalService.getUserOAuthUsersCount(userId);
	}

	@Override
	public com.liferay.oauth.model.OAuthUser updateOAuthUser(long userId,
		long oAuthApplicationId, String accessToken, String accessSecret,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthUserLocalService.updateOAuthUser(userId,
			oAuthApplicationId, accessToken, accessSecret, serviceContext);
	}

	/**
	* Updates the o auth user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param oAuthUser the o auth user
	* @return the o auth user that was updated
	*/
	@Override
	public com.liferay.oauth.model.OAuthUser updateOAuthUser(
		com.liferay.oauth.model.OAuthUser oAuthUser) {
		return _oAuthUserLocalService.updateOAuthUser(oAuthUser);
	}

	@Override
	public OAuthUserLocalService getWrappedService() {
		return _oAuthUserLocalService;
	}

	@Override
	public void setWrappedService(OAuthUserLocalService oAuthUserLocalService) {
		_oAuthUserLocalService = oAuthUserLocalService;
	}

	private OAuthUserLocalService _oAuthUserLocalService;
}