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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for OAuthUser. This utility wraps
 * {@link com.liferay.oauth.service.impl.OAuthUserLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Ivica Cardic
 * @see OAuthUserLocalService
 * @see com.liferay.oauth.service.base.OAuthUserLocalServiceBaseImpl
 * @see com.liferay.oauth.service.impl.OAuthUserLocalServiceImpl
 * @generated
 */
@ProviderType
public class OAuthUserLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.oauth.service.impl.OAuthUserLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.oauth.model.OAuthUser addOAuthUser(long userId,
		long oAuthApplicationId, java.lang.String accessToken,
		java.lang.String accessSecret,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOAuthUser(userId, oAuthApplicationId, accessToken,
			accessSecret, serviceContext);
	}

	/**
	* Adds the o auth user to the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthUser the o auth user
	* @return the o auth user that was added
	*/
	public static com.liferay.oauth.model.OAuthUser addOAuthUser(
		com.liferay.oauth.model.OAuthUser oAuthUser) {
		return getService().addOAuthUser(oAuthUser);
	}

	/**
	* Creates a new o auth user with the primary key. Does not add the o auth user to the database.
	*
	* @param oAuthUserId the primary key for the new o auth user
	* @return the new o auth user
	*/
	public static com.liferay.oauth.model.OAuthUser createOAuthUser(
		long oAuthUserId) {
		return getService().createOAuthUser(oAuthUserId);
	}

	/**
	* Deletes the o auth user with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthUserId the primary key of the o auth user
	* @return the o auth user that was removed
	* @throws PortalException if a o auth user with the primary key could not be found
	*/
	public static com.liferay.oauth.model.OAuthUser deleteOAuthUser(
		long oAuthUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOAuthUser(oAuthUserId);
	}

	public static com.liferay.oauth.model.OAuthUser deleteOAuthUser(
		long userId, long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOAuthUser(userId, oAuthApplicationId);
	}

	/**
	* Deletes the o auth user from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthUser the o auth user
	* @return the o auth user that was removed
	* @throws PortalException
	*/
	public static com.liferay.oauth.model.OAuthUser deleteOAuthUser(
		com.liferay.oauth.model.OAuthUser oAuthUser)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteOAuthUser(oAuthUser);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthUserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.oauth.model.OAuthUser fetchOAuthUser(
		long oAuthUserId) {
		return getService().fetchOAuthUser(oAuthUserId);
	}

	public static com.liferay.oauth.model.OAuthUser fetchOAuthUser(
		long userId, long oAuthApplicationId) {
		return getService().fetchOAuthUser(userId, oAuthApplicationId);
	}

	public static com.liferay.oauth.model.OAuthUser fetchOAuthUser(
		java.lang.String accessToken) {
		return getService().fetchOAuthUser(accessToken);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.List<com.liferay.oauth.model.OAuthUser> getOAuthApplicationOAuthUsers(
		long oAuthApplicationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getService()
				   .getOAuthApplicationOAuthUsers(oAuthApplicationId, start,
			end, orderByComparator);
	}

	public static int getOAuthApplicationOAuthUsersCount(
		long oAuthApplicationId) {
		return getService()
				   .getOAuthApplicationOAuthUsersCount(oAuthApplicationId);
	}

	/**
	* Returns the o auth user with the primary key.
	*
	* @param oAuthUserId the primary key of the o auth user
	* @return the o auth user
	* @throws PortalException if a o auth user with the primary key could not be found
	*/
	public static com.liferay.oauth.model.OAuthUser getOAuthUser(
		long oAuthUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOAuthUser(oAuthUserId);
	}

	public static com.liferay.oauth.model.OAuthUser getOAuthUser(long userId,
		long oAuthApplicationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOAuthUser(userId, oAuthApplicationId);
	}

	public static com.liferay.oauth.model.OAuthUser getOAuthUser(
		java.lang.String accessToken)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOAuthUser(accessToken);
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
	public static java.util.List<com.liferay.oauth.model.OAuthUser> getOAuthUsers(
		int start, int end) {
		return getService().getOAuthUsers(start, end);
	}

	/**
	* Returns the number of o auth users.
	*
	* @return the number of o auth users
	*/
	public static int getOAuthUsersCount() {
		return getService().getOAuthUsersCount();
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

	public static java.util.List<com.liferay.oauth.model.OAuthUser> getUserOAuthUsers(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getService()
				   .getUserOAuthUsers(userId, start, end, orderByComparator);
	}

	public static int getUserOAuthUsersCount(long userId) {
		return getService().getUserOAuthUsersCount(userId);
	}

	public static com.liferay.oauth.model.OAuthUser updateOAuthUser(
		long userId, long oAuthApplicationId, java.lang.String accessToken,
		java.lang.String accessSecret,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOAuthUser(userId, oAuthApplicationId, accessToken,
			accessSecret, serviceContext);
	}

	/**
	* Updates the o auth user in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param oAuthUser the o auth user
	* @return the o auth user that was updated
	*/
	public static com.liferay.oauth.model.OAuthUser updateOAuthUser(
		com.liferay.oauth.model.OAuthUser oAuthUser) {
		return getService().updateOAuthUser(oAuthUser);
	}

	public static OAuthUserLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<OAuthUserLocalService, OAuthUserLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(OAuthUserLocalService.class);

		ServiceTracker<OAuthUserLocalService, OAuthUserLocalService> serviceTracker =
			new ServiceTracker<OAuthUserLocalService, OAuthUserLocalService>(bundle.getBundleContext(),
				OAuthUserLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}