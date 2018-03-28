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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SharepointOAuth2TokenEntry. This utility wraps
 * {@link com.liferay.sharepoint.rest.oauth2.service.impl.SharepointOAuth2TokenEntryLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Adolfo Pérez
 * @see SharepointOAuth2TokenEntryLocalService
 * @see com.liferay.sharepoint.rest.oauth2.service.base.SharepointOAuth2TokenEntryLocalServiceBaseImpl
 * @see com.liferay.sharepoint.rest.oauth2.service.impl.SharepointOAuth2TokenEntryLocalServiceImpl
 * @generated
 */
@ProviderType
public class SharepointOAuth2TokenEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.sharepoint.rest.oauth2.service.impl.SharepointOAuth2TokenEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry addSharepointOAuth2TokenEntry(
		long userId, java.lang.String configurationPid,
		java.lang.String accessToken, java.lang.String refreshToken,
		java.util.Date expirationDate)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSharepointOAuth2TokenEntry(userId, configurationPid,
			accessToken, refreshToken, expirationDate);
	}

	/**
	* Adds the sharepoint o auth2 token entry to the database. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntry the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was added
	*/
	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry addSharepointOAuth2TokenEntry(
		com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		return getService()
				   .addSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntry);
	}

	/**
	* Creates a new sharepoint o auth2 token entry with the primary key. Does not add the sharepoint o auth2 token entry to the database.
	*
	* @param sharepointOAuth2TokenEntryId the primary key for the new sharepoint o auth2 token entry
	* @return the new sharepoint o auth2 token entry
	*/
	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry createSharepointOAuth2TokenEntry(
		long sharepointOAuth2TokenEntryId) {
		return getService()
				   .createSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntryId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the sharepoint o auth2 token entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was removed
	* @throws PortalException if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry deleteSharepointOAuth2TokenEntry(
		long sharepointOAuth2TokenEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntryId);
	}

	public static void deleteSharepointOAuth2TokenEntry(long userId,
		java.lang.String configurationPid)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteSharepointOAuth2TokenEntry(userId, configurationPid);
	}

	/**
	* Deletes the sharepoint o auth2 token entry from the database. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntry the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was removed
	*/
	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry deleteSharepointOAuth2TokenEntry(
		com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		return getService()
				   .deleteSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntry);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.sharepoint.rest.oauth2.model.impl.SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry fetchSharepointOAuth2TokenEntry(
		long sharepointOAuth2TokenEntryId) {
		return getService()
				   .fetchSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntryId);
	}

	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry fetchSharepointOAuth2TokenEntry(
		long userId, java.lang.String configurationPid) {
		return getService()
				   .fetchSharepointOAuth2TokenEntry(userId, configurationPid);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
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
	public static java.util.List<com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry> getSharepointOAuth2TokenEntries(
		int start, int end) {
		return getService().getSharepointOAuth2TokenEntries(start, end);
	}

	/**
	* Returns the number of sharepoint o auth2 token entries.
	*
	* @return the number of sharepoint o auth2 token entries
	*/
	public static int getSharepointOAuth2TokenEntriesCount() {
		return getService().getSharepointOAuth2TokenEntriesCount();
	}

	/**
	* Returns the sharepoint o auth2 token entry with the primary key.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry
	* @throws PortalException if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry getSharepointOAuth2TokenEntry(
		long sharepointOAuth2TokenEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntryId);
	}

	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry getSharepointOAuth2TokenEntry(
		long userId, java.lang.String configurationPid)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getSharepointOAuth2TokenEntry(userId, configurationPid);
	}

	/**
	* Updates the sharepoint o auth2 token entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntry the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was updated
	*/
	public static com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry updateSharepointOAuth2TokenEntry(
		com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry) {
		return getService()
				   .updateSharepointOAuth2TokenEntry(sharepointOAuth2TokenEntry);
	}

	public static SharepointOAuth2TokenEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SharepointOAuth2TokenEntryLocalService, SharepointOAuth2TokenEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SharepointOAuth2TokenEntryLocalService.class);

		ServiceTracker<SharepointOAuth2TokenEntryLocalService, SharepointOAuth2TokenEntryLocalService> serviceTracker =
			new ServiceTracker<SharepointOAuth2TokenEntryLocalService, SharepointOAuth2TokenEntryLocalService>(bundle.getBundleContext(),
				SharepointOAuth2TokenEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}