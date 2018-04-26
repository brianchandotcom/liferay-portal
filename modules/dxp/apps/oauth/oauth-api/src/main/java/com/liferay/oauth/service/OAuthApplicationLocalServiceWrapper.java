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
 * Provides a wrapper for {@link OAuthApplicationLocalService}.
 *
 * @author Ivica Cardic
 * @see OAuthApplicationLocalService
 * @generated
 */
@ProviderType
public class OAuthApplicationLocalServiceWrapper
	implements OAuthApplicationLocalService,
		ServiceWrapper<OAuthApplicationLocalService> {
	public OAuthApplicationLocalServiceWrapper(
		OAuthApplicationLocalService oAuthApplicationLocalService) {
		_oAuthApplicationLocalService = oAuthApplicationLocalService;
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication addOAuthApplication(
		long userId, String name, String description, int accessLevel,
		boolean shareableAccessToken, String callbackURI, String websiteURL,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationLocalService.addOAuthApplication(userId, name,
			description, accessLevel, shareableAccessToken, callbackURI,
			websiteURL, serviceContext);
	}

	/**
	* Adds the o auth application to the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthApplication the o auth application
	* @return the o auth application that was added
	*/
	@Override
	public com.liferay.oauth.model.OAuthApplication addOAuthApplication(
		com.liferay.oauth.model.OAuthApplication oAuthApplication) {
		return _oAuthApplicationLocalService.addOAuthApplication(oAuthApplication);
	}

	/**
	* Creates a new o auth application with the primary key. Does not add the o auth application to the database.
	*
	* @param oAuthApplicationId the primary key for the new o auth application
	* @return the new o auth application
	*/
	@Override
	public com.liferay.oauth.model.OAuthApplication createOAuthApplication(
		long oAuthApplicationId) {
		return _oAuthApplicationLocalService.createOAuthApplication(oAuthApplicationId);
	}

	@Override
	public void deleteLogo(long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_oAuthApplicationLocalService.deleteLogo(oAuthApplicationId);
	}

	/**
	* Deletes the o auth application with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthApplicationId the primary key of the o auth application
	* @return the o auth application that was removed
	* @throws PortalException if a o auth application with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth.model.OAuthApplication deleteOAuthApplication(
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationLocalService.deleteOAuthApplication(oAuthApplicationId);
	}

	/**
	* Deletes the o auth application from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthApplication the o auth application
	* @return the o auth application that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.oauth.model.OAuthApplication deleteOAuthApplication(
		com.liferay.oauth.model.OAuthApplication oAuthApplication)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationLocalService.deleteOAuthApplication(oAuthApplication);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _oAuthApplicationLocalService.dynamicQuery();
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
		return _oAuthApplicationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _oAuthApplicationLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _oAuthApplicationLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _oAuthApplicationLocalService.dynamicQueryCount(dynamicQuery);
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
		return _oAuthApplicationLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication fetchOAuthApplication(
		long oAuthApplicationId) {
		return _oAuthApplicationLocalService.fetchOAuthApplication(oAuthApplicationId);
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication fetchOAuthApplication(
		String consumerKey) {
		return _oAuthApplicationLocalService.fetchOAuthApplication(consumerKey);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _oAuthApplicationLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _oAuthApplicationLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the o auth application with the primary key.
	*
	* @param oAuthApplicationId the primary key of the o auth application
	* @return the o auth application
	* @throws PortalException if a o auth application with the primary key could not be found
	*/
	@Override
	public com.liferay.oauth.model.OAuthApplication getOAuthApplication(
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationLocalService.getOAuthApplication(oAuthApplicationId);
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication getOAuthApplication(
		String consumerKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationLocalService.getOAuthApplication(consumerKey);
	}

	/**
	* Returns a range of all the o auth applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth applications
	* @param end the upper bound of the range of o auth applications (not inclusive)
	* @return the range of o auth applications
	*/
	@Override
	public java.util.List<com.liferay.oauth.model.OAuthApplication> getOAuthApplications(
		int start, int end) {
		return _oAuthApplicationLocalService.getOAuthApplications(start, end);
	}

	@Override
	public java.util.List<com.liferay.oauth.model.OAuthApplication> getOAuthApplications(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _oAuthApplicationLocalService.getOAuthApplications(companyId,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of o auth applications.
	*
	* @return the number of o auth applications
	*/
	@Override
	public int getOAuthApplicationsCount() {
		return _oAuthApplicationLocalService.getOAuthApplicationsCount();
	}

	@Override
	public int getOAuthApplicationsCount(long companyId) {
		return _oAuthApplicationLocalService.getOAuthApplicationsCount(companyId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthApplicationLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.oauth.model.OAuthApplication> search(
		long companyId, String keywords,
		java.util.LinkedHashMap<String, Object> params, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _oAuthApplicationLocalService.search(companyId, keywords,
			params, start, end, orderByComparator);
	}

	@Override
	public int searchCount(long companyId, String keywords,
		java.util.LinkedHashMap<String, Object> params) {
		return _oAuthApplicationLocalService.searchCount(companyId, keywords,
			params);
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication updateLogo(
		long oAuthApplicationId, java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationLocalService.updateLogo(oAuthApplicationId,
			inputStream);
	}

	@Override
	public com.liferay.oauth.model.OAuthApplication updateOAuthApplication(
		long oAuthApplicationId, String name, String description,
		boolean shareableAccessToken, String callbackURI, String websiteURL,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _oAuthApplicationLocalService.updateOAuthApplication(oAuthApplicationId,
			name, description, shareableAccessToken, callbackURI, websiteURL,
			serviceContext);
	}

	/**
	* Updates the o auth application in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param oAuthApplication the o auth application
	* @return the o auth application that was updated
	*/
	@Override
	public com.liferay.oauth.model.OAuthApplication updateOAuthApplication(
		com.liferay.oauth.model.OAuthApplication oAuthApplication) {
		return _oAuthApplicationLocalService.updateOAuthApplication(oAuthApplication);
	}

	@Override
	public OAuthApplicationLocalService getWrappedService() {
		return _oAuthApplicationLocalService;
	}

	@Override
	public void setWrappedService(
		OAuthApplicationLocalService oAuthApplicationLocalService) {
		_oAuthApplicationLocalService = oAuthApplicationLocalService;
	}

	private OAuthApplicationLocalService _oAuthApplicationLocalService;
}