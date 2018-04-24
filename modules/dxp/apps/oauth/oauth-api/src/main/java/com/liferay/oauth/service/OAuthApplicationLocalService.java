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

import com.liferay.oauth.model.OAuthApplication;

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

import java.io.InputStream;
import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Provides the local service interface for OAuthApplication. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Ivica Cardic
 * @see OAuthApplicationLocalServiceUtil
 * @see com.liferay.oauth.service.base.OAuthApplicationLocalServiceBaseImpl
 * @see com.liferay.oauth.service.impl.OAuthApplicationLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface OAuthApplicationLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link OAuthApplicationLocalServiceUtil} to access the o auth application local service. Add custom service methods to {@link com.liferay.oauth.service.impl.OAuthApplicationLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public OAuthApplication addOAuthApplication(long userId,
		java.lang.String name, java.lang.String description, int accessLevel,
		boolean shareableAccessToken, java.lang.String callbackURI,
		java.lang.String websiteURL, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds the o auth application to the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthApplication the o auth application
	* @return the o auth application that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public OAuthApplication addOAuthApplication(
		OAuthApplication oAuthApplication);

	/**
	* Creates a new o auth application with the primary key. Does not add the o auth application to the database.
	*
	* @param oAuthApplicationId the primary key for the new o auth application
	* @return the new o auth application
	*/
	@Transactional(enabled = false)
	public OAuthApplication createOAuthApplication(long oAuthApplicationId);

	public void deleteLogo(long oAuthApplicationId) throws PortalException;

	/**
	* Deletes the o auth application with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthApplicationId the primary key of the o auth application
	* @return the o auth application that was removed
	* @throws PortalException if a o auth application with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public OAuthApplication deleteOAuthApplication(long oAuthApplicationId)
		throws PortalException;

	/**
	* Deletes the o auth application from the database. Also notifies the appropriate model listeners.
	*
	* @param oAuthApplication the o auth application
	* @return the o auth application that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	public OAuthApplication deleteOAuthApplication(
		OAuthApplication oAuthApplication) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public OAuthApplication fetchOAuthApplication(long oAuthApplicationId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OAuthApplication fetchOAuthApplication(java.lang.String consumerKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the o auth application with the primary key.
	*
	* @param oAuthApplicationId the primary key of the o auth application
	* @return the o auth application
	* @throws PortalException if a o auth application with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OAuthApplication getOAuthApplication(long oAuthApplicationId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public OAuthApplication getOAuthApplication(java.lang.String consumerKey)
		throws PortalException;

	/**
	* Returns a range of all the o auth applications.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.oauth.model.impl.OAuthApplicationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of o auth applications
	* @param end the upper bound of the range of o auth applications (not inclusive)
	* @return the range of o auth applications
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<OAuthApplication> getOAuthApplications(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<OAuthApplication> getOAuthApplications(long companyId,
		int start, int end, OrderByComparator orderByComparator);

	/**
	* Returns the number of o auth applications.
	*
	* @return the number of o auth applications
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOAuthApplicationsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOAuthApplicationsCount(long companyId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<OAuthApplication> search(long companyId,
		java.lang.String keywords,
		LinkedHashMap<java.lang.String, java.lang.Object> params, int start,
		int end, OrderByComparator orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, java.lang.String keywords,
		LinkedHashMap<java.lang.String, java.lang.Object> params);

	public OAuthApplication updateLogo(long oAuthApplicationId,
		InputStream inputStream) throws PortalException;

	public OAuthApplication updateOAuthApplication(long oAuthApplicationId,
		java.lang.String name, java.lang.String description,
		boolean shareableAccessToken, java.lang.String callbackURI,
		java.lang.String websiteURL, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the o auth application in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param oAuthApplication the o auth application
	* @return the o auth application that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public OAuthApplication updateOAuthApplication(
		OAuthApplication oAuthApplication);
}