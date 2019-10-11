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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for OAuth2ScopeGrant. This utility wraps
 * <code>com.liferay.oauth2.provider.service.impl.OAuth2ScopeGrantLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrantLocalService
 * @generated
 */
public class OAuth2ScopeGrantLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.oauth2.provider.service.impl.OAuth2ScopeGrantLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuth2ScopeGrantLocalServiceUtil} to access the o auth2 scope grant local service. Add custom service methods to <code>com.liferay.oauth2.provider.service.impl.OAuth2ScopeGrantLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static void addOAuth2AuthorizationOAuth2ScopeGrant(
		long oAuth2AuthorizationId, long oAuth2ScopeGrantId) {

		getService().addOAuth2AuthorizationOAuth2ScopeGrant(
			oAuth2AuthorizationId, oAuth2ScopeGrantId);
	}

	public static void addOAuth2AuthorizationOAuth2ScopeGrant(
		long oAuth2AuthorizationId,
		com.liferay.oauth2.provider.model.OAuth2ScopeGrant oAuth2ScopeGrant) {

		getService().addOAuth2AuthorizationOAuth2ScopeGrant(
			oAuth2AuthorizationId, oAuth2ScopeGrant);
	}

	public static void addOAuth2AuthorizationOAuth2ScopeGrants(
		long oAuth2AuthorizationId,
		java.util.List<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			oAuth2ScopeGrants) {

		getService().addOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId, oAuth2ScopeGrants);
	}

	public static void addOAuth2AuthorizationOAuth2ScopeGrants(
		long oAuth2AuthorizationId, long[] oAuth2ScopeGrantIds) {

		getService().addOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId, oAuth2ScopeGrantIds);
	}

	/**
	 * Adds the o auth2 scope grant to the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ScopeGrant the o auth2 scope grant
	 * @return the o auth2 scope grant that was added
	 */
	public static com.liferay.oauth2.provider.model.OAuth2ScopeGrant
		addOAuth2ScopeGrant(
			com.liferay.oauth2.provider.model.OAuth2ScopeGrant
				oAuth2ScopeGrant) {

		return getService().addOAuth2ScopeGrant(oAuth2ScopeGrant);
	}

	public static void clearOAuth2AuthorizationOAuth2ScopeGrants(
		long oAuth2AuthorizationId) {

		getService().clearOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId);
	}

	/**
	 * Creates a new o auth2 scope grant with the primary key. Does not add the o auth2 scope grant to the database.
	 *
	 * @param oAuth2ScopeGrantId the primary key for the new o auth2 scope grant
	 * @return the new o auth2 scope grant
	 */
	public static com.liferay.oauth2.provider.model.OAuth2ScopeGrant
		createOAuth2ScopeGrant(long oAuth2ScopeGrantId) {

		return getService().createOAuth2ScopeGrant(oAuth2ScopeGrantId);
	}

	public static com.liferay.oauth2.provider.model.OAuth2ScopeGrant
			createOAuth2ScopeGrant(
				long companyId, long oAuth2ApplicationScopeAliasesId,
				String applicationName, String bundleSymbolicName, String scope)
		throws com.liferay.oauth2.provider.exception.
			DuplicateOAuth2ScopeGrantException {

		return getService().createOAuth2ScopeGrant(
			companyId, oAuth2ApplicationScopeAliasesId, applicationName,
			bundleSymbolicName, scope);
	}

	public static com.liferay.oauth2.provider.model.OAuth2ScopeGrant
			createOAuth2ScopeGrant(
				long companyId, long oAuth2ApplicationScopeAliasesId,
				String applicationName, String bundleSymbolicName, String scope,
				java.util.List<String> scopeAliases)
		throws com.liferay.oauth2.provider.exception.
			DuplicateOAuth2ScopeGrantException {

		return getService().createOAuth2ScopeGrant(
			companyId, oAuth2ApplicationScopeAliasesId, applicationName,
			bundleSymbolicName, scope, scopeAliases);
	}

	public static void deleteOAuth2AuthorizationOAuth2ScopeGrant(
		long oAuth2AuthorizationId, long oAuth2ScopeGrantId) {

		getService().deleteOAuth2AuthorizationOAuth2ScopeGrant(
			oAuth2AuthorizationId, oAuth2ScopeGrantId);
	}

	public static void deleteOAuth2AuthorizationOAuth2ScopeGrant(
		long oAuth2AuthorizationId,
		com.liferay.oauth2.provider.model.OAuth2ScopeGrant oAuth2ScopeGrant) {

		getService().deleteOAuth2AuthorizationOAuth2ScopeGrant(
			oAuth2AuthorizationId, oAuth2ScopeGrant);
	}

	public static void deleteOAuth2AuthorizationOAuth2ScopeGrants(
		long oAuth2AuthorizationId,
		java.util.List<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			oAuth2ScopeGrants) {

		getService().deleteOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId, oAuth2ScopeGrants);
	}

	public static void deleteOAuth2AuthorizationOAuth2ScopeGrants(
		long oAuth2AuthorizationId, long[] oAuth2ScopeGrantIds) {

		getService().deleteOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId, oAuth2ScopeGrantIds);
	}

	/**
	 * Deletes the o auth2 scope grant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant that was removed
	 * @throws PortalException if a o auth2 scope grant with the primary key could not be found
	 */
	public static com.liferay.oauth2.provider.model.OAuth2ScopeGrant
			deleteOAuth2ScopeGrant(long oAuth2ScopeGrantId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteOAuth2ScopeGrant(oAuth2ScopeGrantId);
	}

	/**
	 * Deletes the o auth2 scope grant from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ScopeGrant the o auth2 scope grant
	 * @return the o auth2 scope grant that was removed
	 */
	public static com.liferay.oauth2.provider.model.OAuth2ScopeGrant
		deleteOAuth2ScopeGrant(
			com.liferay.oauth2.provider.model.OAuth2ScopeGrant
				oAuth2ScopeGrant) {

		return getService().deleteOAuth2ScopeGrant(oAuth2ScopeGrant);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.oauth2.provider.model.OAuth2ScopeGrant
		fetchOAuth2ScopeGrant(long oAuth2ScopeGrantId) {

		return getService().fetchOAuth2ScopeGrant(oAuth2ScopeGrantId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static java.util.Collection
		<com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope>
			getFilteredLiferayOAuth2Scopes(
				long oAuth2ApplicationScopeAliasesId,
				java.util.Collection
					<com.liferay.oauth2.provider.scope.liferay.
						LiferayOAuth2Scope> liferayOAuth2Scopes) {

		return getService().getFilteredLiferayOAuth2Scopes(
			oAuth2ApplicationScopeAliasesId, liferayOAuth2Scopes);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	public static java.util.List
		<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			getOAuth2AuthorizationOAuth2ScopeGrants(
				long oAuth2AuthorizationId) {

		return getService().getOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId);
	}

	public static java.util.List
		<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			getOAuth2AuthorizationOAuth2ScopeGrants(
				long oAuth2AuthorizationId, int start, int end) {

		return getService().getOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId, start, end);
	}

	public static java.util.List
		<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			getOAuth2AuthorizationOAuth2ScopeGrants(
				long oAuth2AuthorizationId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
						orderByComparator) {

		return getService().getOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId, start, end, orderByComparator);
	}

	public static int getOAuth2AuthorizationOAuth2ScopeGrantsCount(
		long oAuth2AuthorizationId) {

		return getService().getOAuth2AuthorizationOAuth2ScopeGrantsCount(
			oAuth2AuthorizationId);
	}

	/**
	 * Returns the oAuth2AuthorizationIds of the o auth2 authorizations associated with the o auth2 scope grant.
	 *
	 * @param oAuth2ScopeGrantId the oAuth2ScopeGrantId of the o auth2 scope grant
	 * @return long[] the oAuth2AuthorizationIds of o auth2 authorizations associated with the o auth2 scope grant
	 */
	public static long[] getOAuth2AuthorizationPrimaryKeys(
		long oAuth2ScopeGrantId) {

		return getService().getOAuth2AuthorizationPrimaryKeys(
			oAuth2ScopeGrantId);
	}

	/**
	 * Returns the o auth2 scope grant with the primary key.
	 *
	 * @param oAuth2ScopeGrantId the primary key of the o auth2 scope grant
	 * @return the o auth2 scope grant
	 * @throws PortalException if a o auth2 scope grant with the primary key could not be found
	 */
	public static com.liferay.oauth2.provider.model.OAuth2ScopeGrant
			getOAuth2ScopeGrant(long oAuth2ScopeGrantId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getOAuth2ScopeGrant(oAuth2ScopeGrantId);
	}

	/**
	 * Returns a range of all the o auth2 scope grants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth2 scope grants
	 * @param end the upper bound of the range of o auth2 scope grants (not inclusive)
	 * @return the range of o auth2 scope grants
	 */
	public static java.util.List
		<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			getOAuth2ScopeGrants(int start, int end) {

		return getService().getOAuth2ScopeGrants(start, end);
	}

	public static java.util.Collection
		<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			getOAuth2ScopeGrants(
				long oAuth2ApplicationScopeAliasesId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
						orderByComparator) {

		return getService().getOAuth2ScopeGrants(
			oAuth2ApplicationScopeAliasesId, start, end, orderByComparator);
	}

	public static java.util.Collection
		<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
			getOAuth2ScopeGrants(
				long companyId, String applicationName,
				String bundleSymbolicName, String accessTokenContent) {

		return getService().getOAuth2ScopeGrants(
			companyId, applicationName, bundleSymbolicName, accessTokenContent);
	}

	/**
	 * Returns the number of o auth2 scope grants.
	 *
	 * @return the number of o auth2 scope grants
	 */
	public static int getOAuth2ScopeGrantsCount() {
		return getService().getOAuth2ScopeGrantsCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	public static java.util.Collection
		<com.liferay.oauth2.provider.model.OAuth2ScopeGrant>
				grantLiferayOAuth2Scopes(
					long oAuth2AuthorizationId,
					java.util.Collection
						<com.liferay.oauth2.provider.scope.liferay.
							LiferayOAuth2Scope> liferayOAuth2Scopes)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().grantLiferayOAuth2Scopes(
			oAuth2AuthorizationId, liferayOAuth2Scopes);
	}

	public static boolean hasOAuth2AuthorizationOAuth2ScopeGrant(
		long oAuth2AuthorizationId, long oAuth2ScopeGrantId) {

		return getService().hasOAuth2AuthorizationOAuth2ScopeGrant(
			oAuth2AuthorizationId, oAuth2ScopeGrantId);
	}

	public static boolean hasOAuth2AuthorizationOAuth2ScopeGrants(
		long oAuth2AuthorizationId) {

		return getService().hasOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId);
	}

	public static void setOAuth2AuthorizationOAuth2ScopeGrants(
		long oAuth2AuthorizationId, long[] oAuth2ScopeGrantIds) {

		getService().setOAuth2AuthorizationOAuth2ScopeGrants(
			oAuth2AuthorizationId, oAuth2ScopeGrantIds);
	}

	/**
	 * Updates the o auth2 scope grant in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param oAuth2ScopeGrant the o auth2 scope grant
	 * @return the o auth2 scope grant that was updated
	 */
	public static com.liferay.oauth2.provider.model.OAuth2ScopeGrant
		updateOAuth2ScopeGrant(
			com.liferay.oauth2.provider.model.OAuth2ScopeGrant
				oAuth2ScopeGrant) {

		return getService().updateOAuth2ScopeGrant(oAuth2ScopeGrant);
	}

	public static OAuth2ScopeGrantLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<OAuth2ScopeGrantLocalService, OAuth2ScopeGrantLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			OAuth2ScopeGrantLocalService.class);

		ServiceTracker
			<OAuth2ScopeGrantLocalService, OAuth2ScopeGrantLocalService>
				serviceTracker =
					new ServiceTracker
						<OAuth2ScopeGrantLocalService,
						 OAuth2ScopeGrantLocalService>(
							 bundle.getBundleContext(),
							 OAuth2ScopeGrantLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}