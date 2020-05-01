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
 * Provides the local service utility for SamlIdpSpConnection. This utility wraps
 * <code>com.liferay.saml.persistence.service.impl.SamlIdpSpConnectionLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpConnectionLocalService
 * @generated
 */
public class SamlIdpSpConnectionLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.saml.persistence.service.impl.SamlIdpSpConnectionLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the saml idp sp connection to the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpConnection the saml idp sp connection
	 * @return the saml idp sp connection that was added
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
		addSamlIdpSpConnection(
			com.liferay.saml.persistence.model.SamlIdpSpConnection
				samlIdpSpConnection) {

		return getService().addSamlIdpSpConnection(samlIdpSpConnection);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
			addSamlIdpSpConnection(
				String samlSpEntityId, int assertionLifetime,
				String attributeNames, boolean attributesEnabled,
				boolean attributesNamespaceEnabled, boolean enabled,
				boolean encryptionForced, String metadataUrl,
				java.io.InputStream metadataXmlInputStream, String name,
				String nameIdAttribute, String nameIdFormat,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addSamlIdpSpConnection(
			samlSpEntityId, assertionLifetime, attributeNames,
			attributesEnabled, attributesNamespaceEnabled, enabled,
			encryptionForced, metadataUrl, metadataXmlInputStream, name,
			nameIdAttribute, nameIdFormat, serviceContext);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new saml idp sp connection with the primary key. Does not add the saml idp sp connection to the database.
	 *
	 * @param samlIdpSpConnectionId the primary key for the new saml idp sp connection
	 * @return the new saml idp sp connection
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
		createSamlIdpSpConnection(long samlIdpSpConnectionId) {

		return getService().createSamlIdpSpConnection(samlIdpSpConnectionId);
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
	 * Deletes the saml idp sp connection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	 * @return the saml idp sp connection that was removed
	 * @throws PortalException if a saml idp sp connection with the primary key could not be found
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
			deleteSamlIdpSpConnection(long samlIdpSpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSamlIdpSpConnection(samlIdpSpConnectionId);
	}

	/**
	 * Deletes the saml idp sp connection from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpConnection the saml idp sp connection
	 * @return the saml idp sp connection that was removed
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
		deleteSamlIdpSpConnection(
			com.liferay.saml.persistence.model.SamlIdpSpConnection
				samlIdpSpConnection) {

		return getService().deleteSamlIdpSpConnection(samlIdpSpConnection);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionModelImpl</code>.
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

	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
		fetchSamlIdpSpConnection(long samlIdpSpConnectionId) {

		return getService().fetchSamlIdpSpConnection(samlIdpSpConnectionId);
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

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the saml idp sp connection with the primary key.
	 *
	 * @param samlIdpSpConnectionId the primary key of the saml idp sp connection
	 * @return the saml idp sp connection
	 * @throws PortalException if a saml idp sp connection with the primary key could not be found
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
			getSamlIdpSpConnection(long samlIdpSpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSamlIdpSpConnection(samlIdpSpConnectionId);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
			getSamlIdpSpConnection(long companyId, String samlSpEntityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSamlIdpSpConnection(companyId, samlSpEntityId);
	}

	/**
	 * Returns a range of all the saml idp sp connections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml idp sp connections
	 * @param end the upper bound of the range of saml idp sp connections (not inclusive)
	 * @return the range of saml idp sp connections
	 */
	public static java.util.List
		<com.liferay.saml.persistence.model.SamlIdpSpConnection>
			getSamlIdpSpConnections(int start, int end) {

		return getService().getSamlIdpSpConnections(start, end);
	}

	public static java.util.List
		<com.liferay.saml.persistence.model.SamlIdpSpConnection>
			getSamlIdpSpConnections(long companyId) {

		return getService().getSamlIdpSpConnections(companyId);
	}

	public static java.util.List
		<com.liferay.saml.persistence.model.SamlIdpSpConnection>
			getSamlIdpSpConnections(long companyId, int start, int end) {

		return getService().getSamlIdpSpConnections(companyId, start, end);
	}

	public static java.util.List
		<com.liferay.saml.persistence.model.SamlIdpSpConnection>
			getSamlIdpSpConnections(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					orderByComparator) {

		return getService().getSamlIdpSpConnections(
			companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of saml idp sp connections.
	 *
	 * @return the number of saml idp sp connections
	 */
	public static int getSamlIdpSpConnectionsCount() {
		return getService().getSamlIdpSpConnectionsCount();
	}

	public static int getSamlIdpSpConnectionsCount(long companyId) {
		return getService().getSamlIdpSpConnectionsCount(companyId);
	}

	public static void updateMetadata(long samlIdpSpConnectionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateMetadata(samlIdpSpConnectionId);
	}

	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
			updateSamlIdpSpConnection(
				long samlIdpSpConnectionId, String samlSpEntityId,
				int assertionLifetime, String attributeNames,
				boolean attributesEnabled, boolean attributesNamespaceEnabled,
				boolean enabled, boolean encryptionForced, String metadataUrl,
				java.io.InputStream metadataXmlInputStream, String name,
				String nameIdAttribute, String nameIdFormat,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateSamlIdpSpConnection(
			samlIdpSpConnectionId, samlSpEntityId, assertionLifetime,
			attributeNames, attributesEnabled, attributesNamespaceEnabled,
			enabled, encryptionForced, metadataUrl, metadataXmlInputStream,
			name, nameIdAttribute, nameIdFormat, serviceContext);
	}

	/**
	 * Updates the saml idp sp connection in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param samlIdpSpConnection the saml idp sp connection
	 * @return the saml idp sp connection that was updated
	 */
	public static com.liferay.saml.persistence.model.SamlIdpSpConnection
		updateSamlIdpSpConnection(
			com.liferay.saml.persistence.model.SamlIdpSpConnection
				samlIdpSpConnection) {

		return getService().updateSamlIdpSpConnection(samlIdpSpConnection);
	}

	public static SamlIdpSpConnectionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<SamlIdpSpConnectionLocalService, SamlIdpSpConnectionLocalService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			SamlIdpSpConnectionLocalService.class);

		ServiceTracker
			<SamlIdpSpConnectionLocalService, SamlIdpSpConnectionLocalService>
				serviceTracker =
					new ServiceTracker
						<SamlIdpSpConnectionLocalService,
						 SamlIdpSpConnectionLocalService>(
							 bundle.getBundleContext(),
							 SamlIdpSpConnectionLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}