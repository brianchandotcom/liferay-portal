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

package com.liferay.sharepoint.rest.oauth2.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SharepointOAuth2TokenEntryLocalService}.
 *
 * @author Adolfo Pérez
 * @see SharepointOAuth2TokenEntryLocalService
 * @generated
 */
@ProviderType
public class SharepointOAuth2TokenEntryLocalServiceWrapper
	implements SharepointOAuth2TokenEntryLocalService,
		ServiceWrapper<SharepointOAuth2TokenEntryLocalService> {
	public SharepointOAuth2TokenEntryLocalServiceWrapper(
		SharepointOAuth2TokenEntryLocalService sharepointOAuth2TokenEntryLocalService) {
		_sharepointOAuth2TokenEntryLocalService = sharepointOAuth2TokenEntryLocalService;
	}

	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry addSharepointOAuth2TokenEntry(
		long userId, java.lang.String configurationPid,
		java.lang.String accessToken, java.lang.String refreshToken,
		java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharepointOAuth2TokenEntryLocalService.addSharepointOAuth2TokenEntry(userId,
			configurationPid, accessToken, refreshToken, expirationDate);
	}

	/**
	* Adds the sharepoint o auth2 token entry to the database. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntry the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was added
	*/
	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry addSharepointOAuth2TokenEntry(
		com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		return _sharepointOAuth2TokenEntryLocalService.addSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntry);
	}

	/**
	* Creates a new sharepoint o auth2 token entry with the primary key. Does not add the sharepoint o auth2 token entry to the database.
	*
	* @param sharepointOAuth2TokenEntryId the primary key for the new sharepoint o auth2 token entry
	* @return the new sharepoint o auth2 token entry
	*/
	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry createSharepointOAuth2TokenEntry(
		long sharepointOAuth2TokenEntryId) {
		return _sharepointOAuth2TokenEntryLocalService.createSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharepointOAuth2TokenEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the sharepoint o auth2 token entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was removed
	* @throws PortalException if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry deleteSharepointOAuth2TokenEntry(
		long sharepointOAuth2TokenEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharepointOAuth2TokenEntryLocalService.deleteSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntryId);
	}

	@Override
	public void deleteSharepointOAuth2TokenEntry(long userId,
		java.lang.String configurationPid)
		throws com.liferay.portal.kernel.exception.PortalException {
		_sharepointOAuth2TokenEntryLocalService.deleteSharepointOAuth2TokenEntry(userId,
			configurationPid);
	}

	/**
	* Deletes the sharepoint o auth2 token entry from the database. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntry the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was removed
	*/
	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry deleteSharepointOAuth2TokenEntry(
		com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		return _sharepointOAuth2TokenEntryLocalService.deleteSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntry);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _sharepointOAuth2TokenEntryLocalService.dynamicQuery();
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
		return _sharepointOAuth2TokenEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _sharepointOAuth2TokenEntryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _sharepointOAuth2TokenEntryLocalService.dynamicQuery(dynamicQuery,
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
		return _sharepointOAuth2TokenEntryLocalService.dynamicQueryCount(dynamicQuery);
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
		return _sharepointOAuth2TokenEntryLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry fetchSharepointOAuth2TokenEntry(
		long sharepointOAuth2TokenEntryId) {
		return _sharepointOAuth2TokenEntryLocalService.fetchSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntryId);
	}

	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry fetchSharepointOAuth2TokenEntry(
		long userId, java.lang.String configurationPid) {
		return _sharepointOAuth2TokenEntryLocalService.fetchSharepointOAuth2TokenEntry(userId,
			configurationPid);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _sharepointOAuth2TokenEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _sharepointOAuth2TokenEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _sharepointOAuth2TokenEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharepointOAuth2TokenEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the sharepoint o auth2 token entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharepoint o auth2 token entries
	* @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	* @return the range of sharepoint o auth2 token entries
	*/
	@Override
	public java.util.List<com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry> getSharepointOAuth2TokenEntries(
		int start, int end) {
		return _sharepointOAuth2TokenEntryLocalService.getSharepointOAuth2TokenEntries(start,
			end);
	}

	/**
	* Returns the number of sharepoint o auth2 token entries.
	*
	* @return the number of sharepoint o auth2 token entries
	*/
	@Override
	public int getSharepointOAuth2TokenEntriesCount() {
		return _sharepointOAuth2TokenEntryLocalService.getSharepointOAuth2TokenEntriesCount();
	}

	/**
	* Returns the sharepoint o auth2 token entry with the primary key.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry
	* @throws PortalException if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry getSharepointOAuth2TokenEntry(
		long sharepointOAuth2TokenEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharepointOAuth2TokenEntryLocalService.getSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntryId);
	}

	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry getSharepointOAuth2TokenEntry(
		long userId, java.lang.String configurationPid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sharepointOAuth2TokenEntryLocalService.getSharepointOAuth2TokenEntry(userId,
			configurationPid);
	}

	/**
	* Updates the sharepoint o auth2 token entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntry the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was updated
	*/
	@Override
	public com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry updateSharepointOAuth2TokenEntry(
		com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		return _sharepointOAuth2TokenEntryLocalService.updateSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntry);
	}

	@Override
	public SharepointOAuth2TokenEntryLocalService getWrappedService() {
		return _sharepointOAuth2TokenEntryLocalService;
	}

	@Override
	public void setWrappedService(
		SharepointOAuth2TokenEntryLocalService sharepointOAuth2TokenEntryLocalService) {
		_sharepointOAuth2TokenEntryLocalService = sharepointOAuth2TokenEntryLocalService;
	}

	private SharepointOAuth2TokenEntryLocalService _sharepointOAuth2TokenEntryLocalService;
}