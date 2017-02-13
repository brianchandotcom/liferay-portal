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

package com.liferay.friendly.url.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FriendlyURLTitleLocalizationLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLTitleLocalizationLocalService
 * @generated
 */
@ProviderType
public class FriendlyURLTitleLocalizationLocalServiceWrapper
	implements FriendlyURLTitleLocalizationLocalService,
		ServiceWrapper<FriendlyURLTitleLocalizationLocalService> {
	public FriendlyURLTitleLocalizationLocalServiceWrapper(
		FriendlyURLTitleLocalizationLocalService friendlyURLTitleLocalizationLocalService) {
		_friendlyURLTitleLocalizationLocalService = friendlyURLTitleLocalizationLocalService;
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization addFriendlyURLTitleLocalization(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL,
		java.lang.String urlTitle, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLTitleLocalizationLocalService.addFriendlyURLTitleLocalization(friendlyURL,
			urlTitle, languageId);
	}

	/**
	* Adds the friendly url title localization to the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLTitleLocalization the friendly url title localization
	* @return the friendly url title localization that was added
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization addFriendlyURLTitleLocalization(
		com.liferay.friendly.url.model.FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		return _friendlyURLTitleLocalizationLocalService.addFriendlyURLTitleLocalization(friendlyURLTitleLocalization);
	}

	/**
	* Creates a new friendly url title localization with the primary key. Does not add the friendly url title localization to the database.
	*
	* @param friendlyURLTitleLocalizationId the primary key for the new friendly url title localization
	* @return the new friendly url title localization
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization createFriendlyURLTitleLocalization(
		long friendlyURLTitleLocalizationId) {
		return _friendlyURLTitleLocalizationLocalService.createFriendlyURLTitleLocalization(friendlyURLTitleLocalizationId);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization deleteFriendlyURLTitleLocalization(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLTitleLocalizationLocalService.deleteFriendlyURLTitleLocalization(friendlyURL,
			languageId);
	}

	/**
	* Deletes the friendly url title localization from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLTitleLocalization the friendly url title localization
	* @return the friendly url title localization that was removed
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization deleteFriendlyURLTitleLocalization(
		com.liferay.friendly.url.model.FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		return _friendlyURLTitleLocalizationLocalService.deleteFriendlyURLTitleLocalization(friendlyURLTitleLocalization);
	}

	/**
	* Deletes the friendly url title localization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLTitleLocalizationId the primary key of the friendly url title localization
	* @return the friendly url title localization that was removed
	* @throws PortalException if a friendly url title localization with the primary key could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization deleteFriendlyURLTitleLocalization(
		long friendlyURLTitleLocalizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLTitleLocalizationLocalService.deleteFriendlyURLTitleLocalization(friendlyURLTitleLocalizationId);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization fetchFriendlyURLTitleLocalization(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL,
		java.lang.String languageId) {
		return _friendlyURLTitleLocalizationLocalService.fetchFriendlyURLTitleLocalization(friendlyURL,
			languageId);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization fetchFriendlyURLTitleLocalization(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String languageId) {
		return _friendlyURLTitleLocalizationLocalService.fetchFriendlyURLTitleLocalization(companyId,
			groupId, classNameId, classPK, languageId);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization fetchFriendlyURLTitleLocalization(
		long friendlyURLTitleLocalizationId) {
		return _friendlyURLTitleLocalizationLocalService.fetchFriendlyURLTitleLocalization(friendlyURLTitleLocalizationId);
	}

	/**
	* Returns the friendly url title localization with the primary key.
	*
	* @param friendlyURLTitleLocalizationId the primary key of the friendly url title localization
	* @return the friendly url title localization
	* @throws PortalException if a friendly url title localization with the primary key could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization getFriendlyURLTitleLocalization(
		long friendlyURLTitleLocalizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLTitleLocalizationLocalService.getFriendlyURLTitleLocalization(friendlyURLTitleLocalizationId);
	}

	/**
	* Updates the friendly url title localization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLTitleLocalization the friendly url title localization
	* @return the friendly url title localization that was updated
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURLTitleLocalization updateFriendlyURLTitleLocalization(
		com.liferay.friendly.url.model.FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		return _friendlyURLTitleLocalizationLocalService.updateFriendlyURLTitleLocalization(friendlyURLTitleLocalization);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _friendlyURLTitleLocalizationLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _friendlyURLTitleLocalizationLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _friendlyURLTitleLocalizationLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLTitleLocalizationLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLTitleLocalizationLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public int getFriendlyURLTitleLocalizationCount(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL) {
		return _friendlyURLTitleLocalizationLocalService.getFriendlyURLTitleLocalizationCount(friendlyURL);
	}

	/**
	* Returns the number of friendly url title localizations.
	*
	* @return the number of friendly url title localizations
	*/
	@Override
	public int getFriendlyURLTitleLocalizationsCount() {
		return _friendlyURLTitleLocalizationLocalService.getFriendlyURLTitleLocalizationsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _friendlyURLTitleLocalizationLocalService.getOSGiServiceIdentifier();
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
		return _friendlyURLTitleLocalizationLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLTitleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _friendlyURLTitleLocalizationLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLTitleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _friendlyURLTitleLocalizationLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns a range of all the friendly url title localizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLTitleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url title localizations
	* @param end the upper bound of the range of friendly url title localizations (not inclusive)
	* @return the range of friendly url title localizations
	*/
	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURLTitleLocalization> getFriendlyURLTitleLocalizations(
		int start, int end) {
		return _friendlyURLTitleLocalizationLocalService.getFriendlyURLTitleLocalizations(start,
			end);
	}

	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURLTitleLocalization> updateFriendlyURLTitleLocalizations(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL,
		java.util.Map<java.util.Locale, java.lang.String> urlTitleMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLTitleLocalizationLocalService.updateFriendlyURLTitleLocalizations(friendlyURL,
			urlTitleMap);
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
		return _friendlyURLTitleLocalizationLocalService.dynamicQueryCount(dynamicQuery);
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
		return _friendlyURLTitleLocalizationLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteFriendlyURLTitleLocalizations(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException {
		_friendlyURLTitleLocalizationLocalService.deleteFriendlyURLTitleLocalizations(friendlyURL);
	}

	@Override
	public FriendlyURLTitleLocalizationLocalService getWrappedService() {
		return _friendlyURLTitleLocalizationLocalService;
	}

	@Override
	public void setWrappedService(
		FriendlyURLTitleLocalizationLocalService friendlyURLTitleLocalizationLocalService) {
		_friendlyURLTitleLocalizationLocalService = friendlyURLTitleLocalizationLocalService;
	}

	private FriendlyURLTitleLocalizationLocalService _friendlyURLTitleLocalizationLocalService;
}