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

package com.liferay.commerce.vat.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.vat.model.CommerceVatNumber;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for CommerceVatNumber. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumberLocalServiceUtil
 * @see com.liferay.commerce.vat.service.base.CommerceVatNumberLocalServiceBaseImpl
 * @see com.liferay.commerce.vat.service.impl.CommerceVatNumberLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceVatNumberLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceVatNumberLocalServiceUtil} to access the commerce vat number local service. Add custom service methods to {@link com.liferay.commerce.vat.service.impl.CommerceVatNumberLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce vat number to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumber the commerce vat number
	* @return the commerce vat number that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceVatNumber addCommerceVatNumber(
		CommerceVatNumber commerceVatNumber);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceVatNumber addCommerceVatNumber(java.lang.String className,
		long classPK, java.lang.String value, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new commerce vat number with the primary key. Does not add the commerce vat number to the database.
	*
	* @param commerceVatNumberId the primary key for the new commerce vat number
	* @return the new commerce vat number
	*/
	public CommerceVatNumber createCommerceVatNumber(long commerceVatNumberId);

	/**
	* Deletes the commerce vat number from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumber the commerce vat number
	* @return the commerce vat number that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceVatNumber deleteCommerceVatNumber(
		CommerceVatNumber commerceVatNumber);

	/**
	* Deletes the commerce vat number with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number that was removed
	* @throws PortalException if a commerce vat number with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceVatNumber deleteCommerceVatNumber(long commerceVatNumberId)
		throws PortalException;

	public void deleteCommerceVatNumbers(java.lang.String className,
		long classPK);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.vat.model.impl.CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.vat.model.impl.CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommerceVatNumber fetchCommerceVatNumber(long commerceVatNumberId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceVatNumber fetchCommerceVatNumber(long groupId,
		java.lang.String className, long classPK);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the commerce vat number with the primary key.
	*
	* @param commerceVatNumberId the primary key of the commerce vat number
	* @return the commerce vat number
	* @throws PortalException if a commerce vat number with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceVatNumber getCommerceVatNumber(long commerceVatNumberId)
		throws PortalException;

	/**
	* Returns a range of all the commerce vat numbers.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.vat.model.impl.CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce vat numbers
	* @param end the upper bound of the range of commerce vat numbers (not inclusive)
	* @return the range of commerce vat numbers
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceVatNumber> getCommerceVatNumbers(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceVatNumber> getCommerceVatNumbers(long groupId,
		int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator);

	/**
	* Returns the number of commerce vat numbers.
	*
	* @return the number of commerce vat numbers
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceVatNumbersCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceVatNumbersCount(long groupId);

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommerceVatNumber> searchCommerceVatNumbers(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, Sort sort) throws PortalException;

	/**
	* Updates the commerce vat number in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceVatNumber the commerce vat number
	* @return the commerce vat number that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceVatNumber updateCommerceVatNumber(
		CommerceVatNumber commerceVatNumber);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceVatNumber updateCommerceVatNumber(long commerceVatNumberId,
		java.lang.String value) throws PortalException;
}