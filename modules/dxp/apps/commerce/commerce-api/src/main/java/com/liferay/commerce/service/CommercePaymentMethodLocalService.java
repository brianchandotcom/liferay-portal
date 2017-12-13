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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommercePaymentMethod;

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

import java.io.File;
import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CommercePaymentMethod. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethodLocalServiceUtil
 * @see com.liferay.commerce.service.base.CommercePaymentMethodLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePaymentMethodLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommercePaymentMethodLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePaymentMethodLocalServiceUtil} to access the commerce payment method local service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePaymentMethodLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce payment method to the database. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethod the commerce payment method
	* @return the commerce payment method that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommercePaymentMethod addCommercePaymentMethod(
		CommercePaymentMethod commercePaymentMethod);

	public CommercePaymentMethod addCommercePaymentMethod(
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, File imageFile,
		java.lang.String engineKey,
		Map<java.lang.String, java.lang.String> engineParameterMap,
		double priority, boolean active, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new commerce payment method with the primary key. Does not add the commerce payment method to the database.
	*
	* @param commercePaymentMethodId the primary key for the new commerce payment method
	* @return the new commerce payment method
	*/
	public CommercePaymentMethod createCommercePaymentMethod(
		long commercePaymentMethodId);

	/**
	* Deletes the commerce payment method from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethod the commerce payment method
	* @return the commerce payment method that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommercePaymentMethod deleteCommercePaymentMethod(
		CommercePaymentMethod commercePaymentMethod);

	/**
	* Deletes the commerce payment method with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method that was removed
	* @throws PortalException if a commerce payment method with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommercePaymentMethod deleteCommercePaymentMethod(
		long commercePaymentMethodId) throws PortalException;

	public void deleteCommercePaymentMethods(long groupId);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommercePaymentMethod fetchCommercePaymentMethod(
		long commercePaymentMethodId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the commerce payment method with the primary key.
	*
	* @param commercePaymentMethodId the primary key of the commerce payment method
	* @return the commerce payment method
	* @throws PortalException if a commerce payment method with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePaymentMethod getCommercePaymentMethod(
		long commercePaymentMethodId) throws PortalException;

	/**
	* Returns a range of all the commerce payment methods.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommercePaymentMethodModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce payment methods
	* @param end the upper bound of the range of commerce payment methods (not inclusive)
	* @return the range of commerce payment methods
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethod> getCommercePaymentMethods(int start,
		int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethod> getCommercePaymentMethods(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethod> getCommercePaymentMethods(long groupId,
		boolean active);

	/**
	* Returns the number of commerce payment methods.
	*
	* @return the number of commerce payment methods
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePaymentMethodsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePaymentMethodsCount(long groupId, boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

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

	/**
	* Updates the commerce payment method in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commercePaymentMethod the commerce payment method
	* @return the commerce payment method that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommercePaymentMethod updateCommercePaymentMethod(
		CommercePaymentMethod commercePaymentMethod);

	public CommercePaymentMethod updateCommercePaymentMethod(
		long commercePaymentMethodId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, File imageFile,
		Map<java.lang.String, java.lang.String> engineParameterMap,
		double priority, boolean active, ServiceContext serviceContext)
		throws PortalException;
}