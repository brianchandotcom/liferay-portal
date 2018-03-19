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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuth2RefreshTokenLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2RefreshTokenLocalService
 * @generated
 */
@ProviderType
public class OAuth2RefreshTokenLocalServiceWrapper
	implements OAuth2RefreshTokenLocalService,
		ServiceWrapper<OAuth2RefreshTokenLocalService> {
	public OAuth2RefreshTokenLocalServiceWrapper(
		OAuth2RefreshTokenLocalService oAuth2RefreshTokenLocalService) {
		_oAuth2RefreshTokenLocalService = oAuth2RefreshTokenLocalService;
	}

	/**
	* Adds the o auth2 refresh token to the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2RefreshToken the o auth2 refresh token
	* @return the o auth2 refresh token that was added
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2RefreshToken addOAuth2RefreshToken(
		com.liferay.oauth2.provider.model.OAuth2RefreshToken oAuth2RefreshToken) {
		return _oAuth2RefreshTokenLocalService.addOAuth2RefreshToken(oAuth2RefreshToken);
	}

	/**
	* Creates a new o auth2 refresh token with the primary key. Does not add the o auth2 refresh token to the database.
	*
	* @param oAuth2RefreshTokenId the primary key for the new o auth2 refresh token
	* @return the new o auth2 refresh token
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2RefreshToken createOAuth2RefreshToken(
		long oAuth2RefreshTokenId) {
		return _oAuth2RefreshTokenLocalService.createOAuth2RefreshToken(oAuth2RefreshTokenId);
	}

	/**
	* Deletes the o auth2 refresh token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2RefreshTokenId the primary key of the o auth2 refresh token
	* @return the o auth2 refresh token that was removed
	* @throws PortalException if a o auth2 refresh token with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2RefreshToken deleteOAuth2RefreshToken(
		long oAuth2RefreshTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2RefreshTokenLocalService.deleteOAuth2RefreshToken(oAuth2RefreshTokenId);
	}

	/**
	* Deletes the o auth2 refresh token from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2RefreshToken the o auth2 refresh token
	* @return the o auth2 refresh token that was removed
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2RefreshToken deleteOAuth2RefreshToken(
		com.liferay.oauth2.provider.model.OAuth2RefreshToken oAuth2RefreshToken) {
		return _oAuth2RefreshTokenLocalService.deleteOAuth2RefreshToken(oAuth2RefreshToken);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2RefreshTokenLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _oAuth2RefreshTokenLocalService.dynamicQuery();
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
		return _oAuth2RefreshTokenLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.model.impl.OAuth2RefreshTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _oAuth2RefreshTokenLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.model.impl.OAuth2RefreshTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _oAuth2RefreshTokenLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _oAuth2RefreshTokenLocalService.dynamicQueryCount(dynamicQuery);
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
		return _oAuth2RefreshTokenLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2RefreshToken fetchOAuth2RefreshToken(
		long oAuth2RefreshTokenId) {
		return _oAuth2RefreshTokenLocalService.fetchOAuth2RefreshToken(oAuth2RefreshTokenId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _oAuth2RefreshTokenLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _oAuth2RefreshTokenLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the o auth2 refresh token with the primary key.
	*
	* @param oAuth2RefreshTokenId the primary key of the o auth2 refresh token
	* @return the o auth2 refresh token
	* @throws PortalException if a o auth2 refresh token with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2RefreshToken getOAuth2RefreshToken(
		long oAuth2RefreshTokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2RefreshTokenLocalService.getOAuth2RefreshToken(oAuth2RefreshTokenId);
	}

	/**
	* Returns a range of all the o auth2 refresh tokens.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth2.provider.model.impl.OAuth2RefreshTokenModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth2 refresh tokens
	* @param end the upper bound of the range of o auth2 refresh tokens (not inclusive)
	* @return the range of o auth2 refresh tokens
	*/
	@Override
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2RefreshToken> getOAuth2RefreshTokens(
		int start, int end) {
		return _oAuth2RefreshTokenLocalService.getOAuth2RefreshTokens(start, end);
	}

	/**
	* Returns the number of o auth2 refresh tokens.
	*
	* @return the number of o auth2 refresh tokens
	*/
	@Override
	public int getOAuth2RefreshTokensCount() {
		return _oAuth2RefreshTokenLocalService.getOAuth2RefreshTokensCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _oAuth2RefreshTokenLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2RefreshTokenLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the o auth2 refresh token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param oAuth2RefreshToken the o auth2 refresh token
	* @return the o auth2 refresh token that was updated
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2RefreshToken updateOAuth2RefreshToken(
		com.liferay.oauth2.provider.model.OAuth2RefreshToken oAuth2RefreshToken) {
		return _oAuth2RefreshTokenLocalService.updateOAuth2RefreshToken(oAuth2RefreshToken);
	}

	@Override
	public OAuth2RefreshTokenLocalService getWrappedService() {
		return _oAuth2RefreshTokenLocalService;
	}

	@Override
	public void setWrappedService(
		OAuth2RefreshTokenLocalService oAuth2RefreshTokenLocalService) {
		_oAuth2RefreshTokenLocalService = oAuth2RefreshTokenLocalService;
	}

	private OAuth2RefreshTokenLocalService _oAuth2RefreshTokenLocalService;
}