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

package com.liferay.saml.persistence.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SamlIdpSsoSession. This utility wraps
 * {@link com.liferay.saml.persistence.service.impl.SamlIdpSsoSessionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Mika Koivisto
 * @see SamlIdpSsoSessionLocalService
 * @see com.liferay.saml.persistence.service.base.SamlIdpSsoSessionLocalServiceBaseImpl
 * @see com.liferay.saml.persistence.service.impl.SamlIdpSsoSessionLocalServiceImpl
 * @generated
 */
@ProviderType
public class SamlIdpSsoSessionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.saml.persistence.service.impl.SamlIdpSsoSessionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the saml idp sso session to the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSession the saml idp sso session
	* @return the saml idp sso session that was added
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSsoSession addSamlIdpSsoSession(
		com.liferay.saml.persistence.model.SamlIdpSsoSession samlIdpSsoSession) {
		return getService().addSamlIdpSsoSession(samlIdpSsoSession);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSsoSession addSamlIdpSsoSession(
		java.lang.String samlIdpSsoSessionKey,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSamlIdpSsoSession(samlIdpSsoSessionKey, serviceContext);
	}

	/**
	* Creates a new saml idp sso session with the primary key. Does not add the saml idp sso session to the database.
	*
	* @param samlIdpSsoSessionId the primary key for the new saml idp sso session
	* @return the new saml idp sso session
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSsoSession createSamlIdpSsoSession(
		long samlIdpSsoSessionId) {
		return getService().createSamlIdpSsoSession(samlIdpSsoSessionId);
	}

	public static void deleteExpiredSamlIdpSsoSessions() {
		getService().deleteExpiredSamlIdpSsoSessions();
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
	* Deletes the saml idp sso session with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session that was removed
	* @throws PortalException if a saml idp sso session with the primary key could not be found
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSsoSession deleteSamlIdpSsoSession(
		long samlIdpSsoSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSamlIdpSsoSession(samlIdpSsoSessionId);
	}

	/**
	* Deletes the saml idp sso session from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSession the saml idp sso session
	* @return the saml idp sso session that was removed
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSsoSession deleteSamlIdpSsoSession(
		com.liferay.saml.persistence.model.SamlIdpSsoSession samlIdpSsoSession) {
		return getService().deleteSamlIdpSsoSession(samlIdpSsoSession);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.saml.persistence.model.SamlIdpSsoSession fetchSamlIdpSso(
		java.lang.String samlIdpSsoSessionKey) {
		return getService().fetchSamlIdpSso(samlIdpSsoSessionKey);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSsoSession fetchSamlIdpSsoSession(
		long samlIdpSsoSessionId) {
		return getService().fetchSamlIdpSsoSession(samlIdpSsoSessionId);
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

	public static com.liferay.saml.persistence.model.SamlIdpSsoSession getSamlIdpSso(
		java.lang.String samlIdpSsoSessionKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSamlIdpSso(samlIdpSsoSessionKey);
	}

	/**
	* Returns the saml idp sso session with the primary key.
	*
	* @param samlIdpSsoSessionId the primary key of the saml idp sso session
	* @return the saml idp sso session
	* @throws PortalException if a saml idp sso session with the primary key could not be found
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSsoSession getSamlIdpSsoSession(
		long samlIdpSsoSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSamlIdpSsoSession(samlIdpSsoSessionId);
	}

	/**
	* Returns a range of all the saml idp sso sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sso sessions
	* @param end the upper bound of the range of saml idp sso sessions (not inclusive)
	* @return the range of saml idp sso sessions
	*/
	public static java.util.List<com.liferay.saml.persistence.model.SamlIdpSsoSession> getSamlIdpSsoSessions(
		int start, int end) {
		return getService().getSamlIdpSsoSessions(start, end);
	}

	/**
	* Returns the number of saml idp sso sessions.
	*
	* @return the number of saml idp sso sessions
	*/
	public static int getSamlIdpSsoSessionsCount() {
		return getService().getSamlIdpSsoSessionsCount();
	}

	public static com.liferay.saml.persistence.model.SamlIdpSsoSession updateModifiedDate(
		java.lang.String samlIdpSsoSessionKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateModifiedDate(samlIdpSsoSessionKey);
	}

	/**
	* Updates the saml idp sso session in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSsoSession the saml idp sso session
	* @return the saml idp sso session that was updated
	*/
	public static com.liferay.saml.persistence.model.SamlIdpSsoSession updateSamlIdpSsoSession(
		com.liferay.saml.persistence.model.SamlIdpSsoSession samlIdpSsoSession) {
		return getService().updateSamlIdpSsoSession(samlIdpSsoSession);
	}

	public static SamlIdpSsoSessionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SamlIdpSsoSessionLocalService, SamlIdpSsoSessionLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(SamlIdpSsoSessionLocalService.class);

		ServiceTracker<SamlIdpSsoSessionLocalService, SamlIdpSsoSessionLocalService> serviceTracker =
			new ServiceTracker<SamlIdpSsoSessionLocalService, SamlIdpSsoSessionLocalService>(bundle.getBundleContext(),
				SamlIdpSsoSessionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}