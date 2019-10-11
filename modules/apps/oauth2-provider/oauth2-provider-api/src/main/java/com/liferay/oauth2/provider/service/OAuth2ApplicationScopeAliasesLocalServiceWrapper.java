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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuth2ApplicationScopeAliasesLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationScopeAliasesLocalService
 * @generated
 */
public class OAuth2ApplicationScopeAliasesLocalServiceWrapper
	implements OAuth2ApplicationScopeAliasesLocalService,
			   ServiceWrapper<OAuth2ApplicationScopeAliasesLocalService> {

	public OAuth2ApplicationScopeAliasesLocalServiceWrapper(
		OAuth2ApplicationScopeAliasesLocalService
			oAuth2ApplicationScopeAliasesLocalService) {

		_oAuth2ApplicationScopeAliasesLocalService =
			oAuth2ApplicationScopeAliasesLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2ApplicationScopeAliasesLocalServiceUtil} to access the o auth2 application scope aliases local service. Add custom service methods to <code>com.liferay.oauth2.provider.service.impl.OAuth2ApplicationScopeAliasesLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
			addOAuth2ApplicationScopeAliases(
				long companyId, long userId, String userName,
				long oAuth2ApplicationId,
				java.util.List<String> scopeAliasesList)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationScopeAliasesLocalService.
			addOAuth2ApplicationScopeAliases(
				companyId, userId, userName, oAuth2ApplicationId,
				scopeAliasesList);
	}

	/**
	 * Adds the o auth2 application scope aliases to the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ApplicationScopeAliases the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases that was added
	 */
	@Override
	public com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
		addOAuth2ApplicationScopeAliases(
			com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
				oAuth2ApplicationScopeAliases) {

		return _oAuth2ApplicationScopeAliasesLocalService.
			addOAuth2ApplicationScopeAliases(oAuth2ApplicationScopeAliases);
	}

	/**
	 * Creates a new o auth2 application scope aliases with the primary key. Does not add the o auth2 application scope aliases to the database.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the primary key for the new o auth2 application scope aliases
	 * @return the new o auth2 application scope aliases
	 */
	@Override
	public com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
		createOAuth2ApplicationScopeAliases(
			long oAuth2ApplicationScopeAliasesId) {

		return _oAuth2ApplicationScopeAliasesLocalService.
			createOAuth2ApplicationScopeAliases(
				oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Deletes the o auth2 application scope aliases with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases that was removed
	 * @throws PortalException if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
			deleteOAuth2ApplicationScopeAliases(
				long oAuth2ApplicationScopeAliasesId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationScopeAliasesLocalService.
			deleteOAuth2ApplicationScopeAliases(
				oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Deletes the o auth2 application scope aliases from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ApplicationScopeAliases the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases that was removed
	 */
	@Override
	public com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
		deleteOAuth2ApplicationScopeAliases(
			com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
				oAuth2ApplicationScopeAliases) {

		return _oAuth2ApplicationScopeAliasesLocalService.
			deleteOAuth2ApplicationScopeAliases(oAuth2ApplicationScopeAliases);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationScopeAliasesLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _oAuth2ApplicationScopeAliasesLocalService.dynamicQuery();
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

		return _oAuth2ApplicationScopeAliasesLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.oauth2.provider.model.impl.OAuth2ApplicationScopeAliasesModelImpl</code>.
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

		return _oAuth2ApplicationScopeAliasesLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.oauth2.provider.model.impl.OAuth2ApplicationScopeAliasesModelImpl</code>.
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

		return _oAuth2ApplicationScopeAliasesLocalService.dynamicQuery(
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

		return _oAuth2ApplicationScopeAliasesLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _oAuth2ApplicationScopeAliasesLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
		fetchOAuth2ApplicationScopeAliases(
			long oAuth2ApplicationScopeAliasesId) {

		return _oAuth2ApplicationScopeAliasesLocalService.
			fetchOAuth2ApplicationScopeAliases(oAuth2ApplicationScopeAliasesId);
	}

	@Override
	public com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
		fetchOAuth2ApplicationScopeAliases(
			long oAuth2ApplicationId, java.util.List<String> scopeAliasesList) {

		return _oAuth2ApplicationScopeAliasesLocalService.
			fetchOAuth2ApplicationScopeAliases(
				oAuth2ApplicationId, scopeAliasesList);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _oAuth2ApplicationScopeAliasesLocalService.
			getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _oAuth2ApplicationScopeAliasesLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the o auth2 application scope aliases with the primary key.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the primary key of the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases
	 * @throws PortalException if a o auth2 application scope aliases with the primary key could not be found
	 */
	@Override
	public com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
			getOAuth2ApplicationScopeAliases(
				long oAuth2ApplicationScopeAliasesId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationScopeAliasesLocalService.
			getOAuth2ApplicationScopeAliases(oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Returns a range of all the o auth2 application scope aliaseses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.oauth2.provider.model.impl.OAuth2ApplicationScopeAliasesModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 application scope aliaseses
	 * @param end the upper bound of the range of o auth2 application scope aliaseses (not inclusive)
	 * @return the range of o auth2 application scope aliaseses
	 */
	@Override
	public java.util.List
		<com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases>
			getOAuth2ApplicationScopeAliaseses(int start, int end) {

		return _oAuth2ApplicationScopeAliasesLocalService.
			getOAuth2ApplicationScopeAliaseses(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases>
			getOAuth2ApplicationScopeAliaseses(
				long oAuth2ApplicationId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.oauth2.provider.model.
						OAuth2ApplicationScopeAliases> orderByComparator) {

		return _oAuth2ApplicationScopeAliasesLocalService.
			getOAuth2ApplicationScopeAliaseses(
				oAuth2ApplicationId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of o auth2 application scope aliaseses.
	 *
	 * @return the number of o auth2 application scope aliaseses
	 */
	@Override
	public int getOAuth2ApplicationScopeAliasesesCount() {
		return _oAuth2ApplicationScopeAliasesLocalService.
			getOAuth2ApplicationScopeAliasesesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuth2ApplicationScopeAliasesLocalService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuth2ApplicationScopeAliasesLocalService.getPersistedModel(
			primaryKeyObj);
	}

	@Override
	public java.util.List<String> getScopeAliasesList(
		long oAuth2ApplicationScopeAliasesId) {

		return _oAuth2ApplicationScopeAliasesLocalService.getScopeAliasesList(
			oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Updates the o auth2 application scope aliases in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ApplicationScopeAliases the o auth2 application scope aliases
	 * @return the o auth2 application scope aliases that was updated
	 */
	@Override
	public com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
		updateOAuth2ApplicationScopeAliases(
			com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases
				oAuth2ApplicationScopeAliases) {

		return _oAuth2ApplicationScopeAliasesLocalService.
			updateOAuth2ApplicationScopeAliases(oAuth2ApplicationScopeAliases);
	}

	@Override
	public OAuth2ApplicationScopeAliasesLocalService getWrappedService() {
		return _oAuth2ApplicationScopeAliasesLocalService;
	}

	@Override
	public void setWrappedService(
		OAuth2ApplicationScopeAliasesLocalService
			oAuth2ApplicationScopeAliasesLocalService) {

		_oAuth2ApplicationScopeAliasesLocalService =
			oAuth2ApplicationScopeAliasesLocalService;
	}

	private OAuth2ApplicationScopeAliasesLocalService
		_oAuth2ApplicationScopeAliasesLocalService;

}