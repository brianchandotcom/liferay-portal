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
 * Provides a wrapper for {@link OAuth2TokenLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2TokenLocalService
 * @generated
 */
@ProviderType
public class OAuth2TokenLocalServiceWrapper implements OAuth2TokenLocalService,
	ServiceWrapper<OAuth2TokenLocalService> {
	public OAuth2TokenLocalServiceWrapper(
		OAuth2TokenLocalService oAuth2TokenLocalService) {
		_oAuth2TokenLocalService = oAuth2TokenLocalService;
	}

	/**
	* Adds the o auth2 token to the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2Token the o auth2 token
	* @return the o auth2 token that was added
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token addOAuth2Token(
		com.liferay.oauth2.provider.model.OAuth2Token oAuth2Token) {
		return _oAuth2TokenLocalService.addOAuth2Token(oAuth2Token);
	}

	/**
	* Creates a new o auth2 token with the primary key. Does not add the o auth2 token to the database.
	*
	* @param oAuth2TokenId the primary key for the new o auth2 token
	* @return the new o auth2 token
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token createOAuth2Token(
		long oAuth2TokenId) {
		return _oAuth2TokenLocalService.createOAuth2Token(oAuth2TokenId);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token createOAuth2Token(
		java.lang.String tokenContent) {
		return _oAuth2TokenLocalService.createOAuth2Token(tokenContent);
	}

	/**
	* Deletes the o auth2 token with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token that was removed
	* @throws PortalException if a o auth2 token with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token deleteOAuth2Token(
		long oAuth2TokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2TokenLocalService.deleteOAuth2Token(oAuth2TokenId);
	}

	/**
	* Deletes the o auth2 token from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuth2Token the o auth2 token
	* @return the o auth2 token that was removed
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token deleteOAuth2Token(
		com.liferay.oauth2.provider.model.OAuth2Token oAuth2Token) {
		return _oAuth2TokenLocalService.deleteOAuth2Token(oAuth2Token);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2TokenLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _oAuth2TokenLocalService.dynamicQuery();
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
		return _oAuth2TokenLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _oAuth2TokenLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _oAuth2TokenLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _oAuth2TokenLocalService.dynamicQueryCount(dynamicQuery);
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
		return _oAuth2TokenLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token fetchByContent(
		java.lang.String oAuth2TokenContent) {
		return _oAuth2TokenLocalService.fetchByContent(oAuth2TokenContent);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token fetchOAuth2Token(
		long oAuth2TokenId) {
		return _oAuth2TokenLocalService.fetchOAuth2Token(oAuth2TokenId);
	}

	@Override
	public java.util.Collection<com.liferay.oauth2.provider.model.OAuth2Token> findByApplicationId(
		long applicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.oauth2.provider.model.OAuth2Token> orderByComparator) {
		return _oAuth2TokenLocalService.findByApplicationId(applicationId,
			start, end, orderByComparator);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token findByContent(
		java.lang.String oAuth2TokenContent)
		throws com.liferay.oauth2.provider.exception.NoSuchOAuth2TokenException {
		return _oAuth2TokenLocalService.findByContent(oAuth2TokenContent);
	}

	@Override
	public java.util.Collection<com.liferay.oauth2.provider.model.OAuth2Token> findByRefreshToken(
		long oAuth2RefreshTokenId) {
		return _oAuth2TokenLocalService.findByRefreshToken(oAuth2RefreshTokenId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _oAuth2TokenLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _oAuth2TokenLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the o auth2 token with the primary key.
	*
	* @param oAuth2TokenId the primary key of the o auth2 token
	* @return the o auth2 token
	* @throws PortalException if a o auth2 token with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token getOAuth2Token(
		long oAuth2TokenId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2TokenLocalService.getOAuth2Token(oAuth2TokenId);
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
	@Override
	public java.util.List<com.liferay.oauth2.provider.model.OAuth2Token> getOAuth2Tokens(
		int start, int end) {
		return _oAuth2TokenLocalService.getOAuth2Tokens(start, end);
	}

	/**
	* Returns the number of o auth2 tokens.
	*
	* @return the number of o auth2 tokens
	*/
	@Override
	public int getOAuth2TokensCount() {
		return _oAuth2TokenLocalService.getOAuth2TokensCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _oAuth2TokenLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuth2TokenLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the o auth2 token in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param oAuth2Token the o auth2 token
	* @return the o auth2 token that was updated
	*/
	@Override
	public com.liferay.oauth2.provider.model.OAuth2Token updateOAuth2Token(
		com.liferay.oauth2.provider.model.OAuth2Token oAuth2Token) {
		return _oAuth2TokenLocalService.updateOAuth2Token(oAuth2Token);
	}

	@Override
	public OAuth2TokenLocalService getWrappedService() {
		return _oAuth2TokenLocalService;
	}

	@Override
	public void setWrappedService(
		OAuth2TokenLocalService oAuth2TokenLocalService) {
		_oAuth2TokenLocalService = oAuth2TokenLocalService;
	}

	private OAuth2TokenLocalService _oAuth2TokenLocalService;
}