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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SamlSpSessionLocalService}.
 *
 * @author Mika Koivisto
 * @see SamlSpSessionLocalService
 * @generated
 */
@ProviderType
public class SamlSpSessionLocalServiceWrapper
	implements SamlSpSessionLocalService,
		ServiceWrapper<SamlSpSessionLocalService> {
	public SamlSpSessionLocalServiceWrapper(
		SamlSpSessionLocalService samlSpSessionLocalService) {
		_samlSpSessionLocalService = samlSpSessionLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _samlSpSessionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _samlSpSessionLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _samlSpSessionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the saml sp session to the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpSession the saml sp session
	* @return the saml sp session that was added
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpSession addSamlSpSession(
		com.liferay.saml.persistence.model.SamlSpSession samlSpSession) {
		return _samlSpSessionLocalService.addSamlSpSession(samlSpSession);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession addSamlSpSession(
		java.lang.String samlSpSessionKey, java.lang.String assertionXml,
		java.lang.String jSessionId, java.lang.String nameIdFormat,
		java.lang.String nameIdNameQualifier,
		java.lang.String nameIdSPNameQualifier, java.lang.String nameIdValue,
		java.lang.String sessionIndex,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.addSamlSpSession(samlSpSessionKey,
			assertionXml, jSessionId, nameIdFormat, nameIdNameQualifier,
			nameIdSPNameQualifier, nameIdValue, sessionIndex, serviceContext);
	}

	/**
	* Creates a new saml sp session with the primary key. Does not add the saml sp session to the database.
	*
	* @param samlSpSessionId the primary key for the new saml sp session
	* @return the new saml sp session
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpSession createSamlSpSession(
		long samlSpSessionId) {
		return _samlSpSessionLocalService.createSamlSpSession(samlSpSessionId);
	}

	/**
	* Deletes the saml sp session from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpSession the saml sp session
	* @return the saml sp session that was removed
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpSession deleteSamlSpSession(
		com.liferay.saml.persistence.model.SamlSpSession samlSpSession) {
		return _samlSpSessionLocalService.deleteSamlSpSession(samlSpSession);
	}

	/**
	* Deletes the saml sp session with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlSpSessionId the primary key of the saml sp session
	* @return the saml sp session that was removed
	* @throws PortalException if a saml sp session with the primary key could not be found
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpSession deleteSamlSpSession(
		long samlSpSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.deleteSamlSpSession(samlSpSessionId);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession fetchSamlSpSession(
		long samlSpSessionId) {
		return _samlSpSessionLocalService.fetchSamlSpSession(samlSpSessionId);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession fetchSamlSpSessionByJSessionId(
		java.lang.String jSessionId) {
		return _samlSpSessionLocalService.fetchSamlSpSessionByJSessionId(jSessionId);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession fetchSamlSpSessionBySamlSpSessionKey(
		java.lang.String samlSpSessionKey) {
		return _samlSpSessionLocalService.fetchSamlSpSessionBySamlSpSessionKey(samlSpSessionKey);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession fetchSamlSpSessionBySessionIndex(
		java.lang.String sessionIndex) {
		return _samlSpSessionLocalService.fetchSamlSpSessionBySessionIndex(sessionIndex);
	}

	/**
	* Returns the saml sp session with the primary key.
	*
	* @param samlSpSessionId the primary key of the saml sp session
	* @return the saml sp session
	* @throws PortalException if a saml sp session with the primary key could not be found
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpSession getSamlSpSession(
		long samlSpSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.getSamlSpSession(samlSpSessionId);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession getSamlSpSessionByJSessionId(
		java.lang.String jSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.getSamlSpSessionByJSessionId(jSessionId);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession getSamlSpSessionBySamlSpSessionKey(
		java.lang.String samlSpSessionKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.getSamlSpSessionBySamlSpSessionKey(samlSpSessionKey);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession getSamlSpSessionBySessionIndex(
		java.lang.String sessionIndex)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.getSamlSpSessionBySessionIndex(sessionIndex);
	}

	/**
	* Updates the saml sp session in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlSpSession the saml sp session
	* @return the saml sp session that was updated
	*/
	@Override
	public com.liferay.saml.persistence.model.SamlSpSession updateSamlSpSession(
		com.liferay.saml.persistence.model.SamlSpSession samlSpSession) {
		return _samlSpSessionLocalService.updateSamlSpSession(samlSpSession);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession updateSamlSpSession(
		long samlSpSessionId, java.lang.String jSessionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.updateSamlSpSession(samlSpSessionId,
			jSessionId);
	}

	@Override
	public com.liferay.saml.persistence.model.SamlSpSession updateSamlSpSession(
		long samlSpSessionId, java.lang.String samlSpSessionKey,
		java.lang.String assertionXml, java.lang.String jSessionId,
		java.lang.String nameIdFormat, java.lang.String nameIdNameQualifier,
		java.lang.String nameIdSPNameQualifier, java.lang.String nameIdValue,
		java.lang.String sessionIndex,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _samlSpSessionLocalService.updateSamlSpSession(samlSpSessionId,
			samlSpSessionKey, assertionXml, jSessionId, nameIdFormat,
			nameIdNameQualifier, nameIdSPNameQualifier, nameIdValue,
			sessionIndex, serviceContext);
	}

	/**
	* Returns the number of saml sp sessions.
	*
	* @return the number of saml sp sessions
	*/
	@Override
	public int getSamlSpSessionsCount() {
		return _samlSpSessionLocalService.getSamlSpSessionsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _samlSpSessionLocalService.getOSGiServiceIdentifier();
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
		return _samlSpSessionLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _samlSpSessionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _samlSpSessionLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns a range of all the saml sp sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlSpSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml sp sessions
	* @param end the upper bound of the range of saml sp sessions (not inclusive)
	* @return the range of saml sp sessions
	*/
	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlSpSession> getSamlSpSessions(
		int start, int end) {
		return _samlSpSessionLocalService.getSamlSpSessions(start, end);
	}

	@Override
	public java.util.List<com.liferay.saml.persistence.model.SamlSpSession> getSamlSpSessions(
		java.lang.String nameIdValue) {
		return _samlSpSessionLocalService.getSamlSpSessions(nameIdValue);
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
		return _samlSpSessionLocalService.dynamicQueryCount(dynamicQuery);
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
		return _samlSpSessionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public SamlSpSessionLocalService getWrappedService() {
		return _samlSpSessionLocalService;
	}

	@Override
	public void setWrappedService(
		SamlSpSessionLocalService samlSpSessionLocalService) {
		_samlSpSessionLocalService = samlSpSessionLocalService;
	}

	private SamlSpSessionLocalService _samlSpSessionLocalService;
}