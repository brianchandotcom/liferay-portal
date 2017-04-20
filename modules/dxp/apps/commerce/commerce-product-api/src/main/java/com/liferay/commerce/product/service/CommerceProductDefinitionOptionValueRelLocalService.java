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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CommerceProductDefinitionOptionValueRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRelLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CommerceProductDefinitionOptionValueRelLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionValueRelLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceProductDefinitionOptionValueRelLocalService
	extends BaseLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceProductDefinitionOptionValueRelLocalServiceUtil} to access the commerce product definition option value rel local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductDefinitionOptionValueRelLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce product definition option value rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	* @return the commerce product definition option value rel that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel);

	public CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionRelId,
		Map<Locale, java.lang.String> titleMap, int priority,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new commerce product definition option value rel with the primary key. Does not add the commerce product definition option value rel to the database.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key for the new commerce product definition option value rel
	* @return the new commerce product definition option value rel
	*/
	public CommerceProductDefinitionOptionValueRel createCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId);

	/**
	* Deletes the commerce product definition option value rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	* @return the commerce product definition option value rel that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel)
		throws PortalException;

	/**
	* Deletes the commerce product definition option value rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel that was removed
	* @throws PortalException if a commerce product definition option value rel with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceProductDefinitionOptionValueRel fetchCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId);

	/**
	* Returns the commerce product definition option value rel with the primary key.
	*
	* @param commerceProductDefinitionOptionValueRelId the primary key of the commerce product definition option value rel
	* @return the commerce product definition option value rel
	* @throws PortalException if a commerce product definition option value rel with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceProductDefinitionOptionValueRel getCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws PortalException;

	/**
	* Updates the commerce product definition option value rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceProductDefinitionOptionValueRel the commerce product definition option value rel
	* @return the commerce product definition option value rel that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceProductDefinitionOptionValueRel updateCommerceProductDefinitionOptionValueRel(
		CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel);

	public CommerceProductDefinitionOptionValueRel updateCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId,
		Map<Locale, java.lang.String> titleMap, int priority,
		ServiceContext serviceContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Returns the number of commerce product definition option value rels.
	*
	* @return the number of commerce product definition option value rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceProductDefinitionOptionValueRelsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceProductDefinitionOptionValueRelsCount(
		long commerceProductDefinitionOptionRelId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the commerce product definition option value rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CommerceProductDefinitionOptionValueRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce product definition option value rels
	* @param end the upper bound of the range of commerce product definition option value rels (not inclusive)
	* @return the range of commerce product definition option value rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end,
		OrderByComparator<CommerceProductDefinitionOptionValueRel> orderByComparator);

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
}