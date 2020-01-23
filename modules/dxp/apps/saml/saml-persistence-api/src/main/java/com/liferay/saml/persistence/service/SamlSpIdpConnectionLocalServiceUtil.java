/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SamlSpIdpConnection. This utility wraps
 * <code>com.liferay.saml.persistence.service.impl.SamlSpIdpConnectionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnectionLocalService
 * @generated
 */
public class SamlSpIdpConnectionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.saml.persistence.service.impl.SamlSpIdpConnectionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the saml sp idp connection to the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpIdpConnection the saml sp idp connection
	 * @return the saml sp idp connection that was added
	 */
	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
		addSamlSpIdpConnection(
			com.liferay.saml.persistence.model.SamlSpIdpConnection
				samlSpIdpConnection) {

		return getService().addSamlSpIdpConnection(samlSpIdpConnection);
	}

	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
			addSamlSpIdpConnection(
				String samlIdpEntityId, boolean assertionSignatureRequired,
				long clockSkew, boolean enabled, boolean forceAuthn,
				boolean ldapImportEnabled, boolean unknownUsersAreStrangers,
				String metadataUrl, java.io.InputStream metadataXmlInputStream,
				String name, String nameIdFormat, boolean signAuthnRequest,
				String userAttributeMappings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSamlSpIdpConnection(
			samlIdpEntityId, assertionSignatureRequired, clockSkew, enabled,
			forceAuthn, ldapImportEnabled, unknownUsersAreStrangers,
			metadataUrl, metadataXmlInputStream, name, nameIdFormat,
			signAuthnRequest, userAttributeMappings, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by
	 {@link #addSamlSpIdpConnection(String, boolean, long, boolean, boolean,
	 boolean, boolean, String, InputStream, String, String, boolean, String,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
			addSamlSpIdpConnection(
				String samlIdpEntityId, boolean assertionSignatureRequired,
				long clockSkew, boolean enabled, boolean forceAuthn,
				boolean ldapImportEnabled, String metadataUrl,
				java.io.InputStream metadataXmlInputStream, String name,
				String nameIdFormat, boolean signAuthnRequest,
				String userAttributeMappings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSamlSpIdpConnection(
			samlIdpEntityId, assertionSignatureRequired, clockSkew, enabled,
			forceAuthn, ldapImportEnabled, metadataUrl, metadataXmlInputStream,
			name, nameIdFormat, signAuthnRequest, userAttributeMappings,
			serviceContext);
	}

	/**
	 * Creates a new saml sp idp connection with the primary key. Does not add the saml sp idp connection to the database.
	 *
	 * @param samlSpIdpConnectionId the primary key for the new saml sp idp connection
	 * @return the new saml sp idp connection
	 */
	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
		createSamlSpIdpConnection(long samlSpIdpConnectionId) {

		return getService().createSamlSpIdpConnection(samlSpIdpConnectionId);
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

	/**
	 * Deletes the saml sp idp connection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	 * @return the saml sp idp connection that was removed
	 * @throws PortalException if a saml sp idp connection with the primary key could not be found
	 */
	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
			deleteSamlSpIdpConnection(long samlSpIdpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSamlSpIdpConnection(samlSpIdpConnectionId);
	}

	/**
	 * Deletes the saml sp idp connection from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpIdpConnection the saml sp idp connection
	 * @return the saml sp idp connection that was removed
	 */
	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
		deleteSamlSpIdpConnection(
			com.liferay.saml.persistence.model.SamlSpIdpConnection
				samlSpIdpConnection) {

		return getService().deleteSamlSpIdpConnection(samlSpIdpConnection);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl</code>.
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

	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
		fetchSamlSpIdpConnection(long samlSpIdpConnectionId) {

		return getService().fetchSamlSpIdpConnection(samlSpIdpConnectionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
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

	/**
	 * Returns the saml sp idp connection with the primary key.
	 *
	 * @param samlSpIdpConnectionId the primary key of the saml sp idp connection
	 * @return the saml sp idp connection
	 * @throws PortalException if a saml sp idp connection with the primary key could not be found
	 */
	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
			getSamlSpIdpConnection(long samlSpIdpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSamlSpIdpConnection(samlSpIdpConnectionId);
	}

	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
			getSamlSpIdpConnection(long companyId, String samlIdpEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSamlSpIdpConnection(companyId, samlIdpEntityId);
	}

	/**
	 * Returns a range of all the saml sp idp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp idp connections
	 * @param end the upper bound of the range of saml sp idp connections (not inclusive)
	 * @return the range of saml sp idp connections
	 */
	public static java.util.List
		<com.liferay.saml.persistence.model.SamlSpIdpConnection>
			getSamlSpIdpConnections(int start, int end) {

		return getService().getSamlSpIdpConnections(start, end);
	}

	public static java.util.List
		<com.liferay.saml.persistence.model.SamlSpIdpConnection>
			getSamlSpIdpConnections(long companyId) {

		return getService().getSamlSpIdpConnections(companyId);
	}

	public static java.util.List
		<com.liferay.saml.persistence.model.SamlSpIdpConnection>
			getSamlSpIdpConnections(long companyId, int start, int end) {

		return getService().getSamlSpIdpConnections(companyId, start, end);
	}

	public static java.util.List
		<com.liferay.saml.persistence.model.SamlSpIdpConnection>
			getSamlSpIdpConnections(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					orderByComparator) {

		return getService().getSamlSpIdpConnections(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of saml sp idp connections.
	 *
	 * @return the number of saml sp idp connections
	 */
	public static int getSamlSpIdpConnectionsCount() {
		return getService().getSamlSpIdpConnectionsCount();
	}

	public static int getSamlSpIdpConnectionsCount(long companyId) {
		return getService().getSamlSpIdpConnectionsCount(companyId);
	}

	public static void updateMetadata(long samlSpIdpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateMetadata(samlSpIdpConnectionId);
	}

	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
			updateSamlSpIdpConnection(
				long samlSpIdpConnectionId, String samlIdpEntityId,
				boolean assertionSignatureRequired, long clockSkew,
				boolean enabled, boolean forceAuthn, boolean ldapImportEnabled,
				boolean unknownUsersAreStrangers, String metadataUrl,
				java.io.InputStream metadataXmlInputStream, String name,
				String nameIdFormat, boolean signAuthnRequest,
				String userAttributeMappings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSamlSpIdpConnection(
			samlSpIdpConnectionId, samlIdpEntityId, assertionSignatureRequired,
			clockSkew, enabled, forceAuthn, ldapImportEnabled,
			unknownUsersAreStrangers, metadataUrl, metadataXmlInputStream, name,
			nameIdFormat, signAuthnRequest, userAttributeMappings,
			serviceContext);
	}

	@Deprecated
	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
			updateSamlSpIdpConnection(
				long samlSpIdpConnectionId, String samlIdpEntityId,
				boolean assertionSignatureRequired, long clockSkew,
				boolean enabled, boolean forceAuthn, boolean ldapImportEnabled,
				String metadataUrl, java.io.InputStream metadataXmlInputStream,
				String name, String nameIdFormat, boolean signAuthnRequest,
				String userAttributeMappings,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSamlSpIdpConnection(
			samlSpIdpConnectionId, samlIdpEntityId, assertionSignatureRequired,
			clockSkew, enabled, forceAuthn, ldapImportEnabled, metadataUrl,
			metadataXmlInputStream, name, nameIdFormat, signAuthnRequest,
			userAttributeMappings, serviceContext);
	}

	/**
	 * Updates the saml sp idp connection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpIdpConnection the saml sp idp connection
	 * @return the saml sp idp connection that was updated
	 */
	public static com.liferay.saml.persistence.model.SamlSpIdpConnection
		updateSamlSpIdpConnection(
			com.liferay.saml.persistence.model.SamlSpIdpConnection
				samlSpIdpConnection) {

		return getService().updateSamlSpIdpConnection(samlSpIdpConnection);
	}

	public static SamlSpIdpConnectionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SamlSpIdpConnectionLocalService, SamlSpIdpConnectionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SamlSpIdpConnectionLocalService.class);

		ServiceTracker
			<SamlSpIdpConnectionLocalService, SamlSpIdpConnectionLocalService>
				serviceTracker =
					new ServiceTracker
						<SamlSpIdpConnectionLocalService,
						 SamlSpIdpConnectionLocalService>(
							 bundle.getBundleContext(),
							 SamlSpIdpConnectionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}