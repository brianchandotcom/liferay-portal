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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.saml.persistence.model.SamlIdpSpSession;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for SamlIdpSpSession. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpSessionLocalServiceUtil
 * @see com.liferay.saml.persistence.service.base.SamlIdpSpSessionLocalServiceBaseImpl
 * @see com.liferay.saml.persistence.service.impl.SamlIdpSpSessionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SamlIdpSpSessionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SamlIdpSpSessionLocalServiceUtil} to access the saml idp sp session local service. Add custom service methods to {@link com.liferay.saml.persistence.service.impl.SamlIdpSpSessionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public SamlIdpSpSession addSamlIdpSpSession(long samlIdpSsoSessionId,
		String samlSpEntityId, String nameIdFormat, String nameIdValue,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Adds the saml idp sp session to the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpSession the saml idp sp session
	* @return the saml idp sp session that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public SamlIdpSpSession addSamlIdpSpSession(
		SamlIdpSpSession samlIdpSpSession);

	/**
	* Creates a new saml idp sp session with the primary key. Does not add the saml idp sp session to the database.
	*
	* @param samlIdpSpSessionId the primary key for the new saml idp sp session
	* @return the new saml idp sp session
	*/
	@Transactional(enabled = false)
	public SamlIdpSpSession createSamlIdpSpSession(long samlIdpSpSessionId);

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	* Deletes the saml idp sp session with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpSessionId the primary key of the saml idp sp session
	* @return the saml idp sp session that was removed
	* @throws PortalException if a saml idp sp session with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public SamlIdpSpSession deleteSamlIdpSpSession(long samlIdpSpSessionId)
		throws PortalException;

	/**
	* Deletes the saml idp sp session from the database. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpSession the saml idp sp session
	* @return the saml idp sp session that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public SamlIdpSpSession deleteSamlIdpSpSession(
		SamlIdpSpSession samlIdpSpSession);

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SamlIdpSpSession fetchSamlIdpSpSession(long samlIdpSpSessionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Returns the saml idp sp session with the primary key.
	*
	* @param samlIdpSpSessionId the primary key of the saml idp sp session
	* @return the saml idp sp session
	* @throws PortalException if a saml idp sp session with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SamlIdpSpSession getSamlIdpSpSession(long samlIdpSpSessionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SamlIdpSpSession getSamlIdpSpSession(long samlIdpSsoSessionId,
		String samlSpEntityId) throws PortalException;

	/**
	* Returns a range of all the saml idp sp sessions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.saml.persistence.model.impl.SamlIdpSpSessionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of saml idp sp sessions
	* @param end the upper bound of the range of saml idp sp sessions (not inclusive)
	* @return the range of saml idp sp sessions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SamlIdpSpSession> getSamlIdpSpSessions(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SamlIdpSpSession> getSamlIdpSpSessions(long samlIdpSsoSessionId);

	/**
	* Returns the number of saml idp sp sessions.
	*
	* @return the number of saml idp sp sessions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSamlIdpSpSessionsCount();

	public SamlIdpSpSession updateModifiedDate(long samlIdpSsoSessionId,
		String samlSpEntityId) throws PortalException;

	/**
	* Updates the saml idp sp session in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param samlIdpSpSession the saml idp sp session
	* @return the saml idp sp session that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public SamlIdpSpSession updateSamlIdpSpSession(
		SamlIdpSpSession samlIdpSpSession);
}