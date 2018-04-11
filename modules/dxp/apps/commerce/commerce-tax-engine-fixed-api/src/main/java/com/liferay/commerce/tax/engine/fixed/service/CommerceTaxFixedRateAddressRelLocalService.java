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

package com.liferay.commerce.tax.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;

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

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for CommerceTaxFixedRateAddressRel. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxFixedRateAddressRelLocalServiceUtil
 * @see com.liferay.commerce.tax.engine.fixed.service.base.CommerceTaxFixedRateAddressRelLocalServiceBaseImpl
 * @see com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceTaxFixedRateAddressRelLocalService
	extends BaseLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTaxFixedRateAddressRelLocalServiceUtil} to access the commerce tax fixed rate address rel local service. Add custom service methods to {@link com.liferay.commerce.tax.engine.fixed.service.impl.CommerceTaxFixedRateAddressRelLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce tax fixed rate address rel to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel);

	public CommerceTaxFixedRateAddressRel addCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long cpTaxCategoryId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double rate,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new commerce tax fixed rate address rel with the primary key. Does not add the commerce tax fixed rate address rel to the database.
	*
	* @param commerceTaxFixedRateAddressRelId the primary key for the new commerce tax fixed rate address rel
	* @return the new commerce tax fixed rate address rel
	*/
	public CommerceTaxFixedRateAddressRel createCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId);

	/**
	* Deletes the commerce tax fixed rate address rel from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceTaxFixedRateAddressRel deleteCommerceTaxFixedRateAddressRel(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel);

	/**
	* Deletes the commerce tax fixed rate address rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRelId the primary key of the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was removed
	* @throws PortalException if a commerce tax fixed rate address rel with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceTaxFixedRateAddressRel deleteCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) throws PortalException;

	public void deleteCommerceTaxFixedRateAddressRelsByCommerceTaxMethodId(
		long commerceTaxMethodId);

	public void deleteCommerceTaxFixedRateAddressRelsByCPTaxCategoryId(
		long cpTaxCategoryId);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTaxFixedRateAddressRel fetchCommerceTaxFixedRateAddressRel(
		long commerceTaxMethodId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the commerce tax fixed rate address rel with the primary key.
	*
	* @param commerceTaxFixedRateAddressRelId the primary key of the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel
	* @throws PortalException if a commerce tax fixed rate address rel with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceTaxFixedRateAddressRel getCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId) throws PortalException;

	/**
	* Returns a range of all the commerce tax fixed rate address rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.tax.engine.fixed.model.impl.CommerceTaxFixedRateAddressRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tax fixed rate address rels
	* @param end the upper bound of the range of commerce tax fixed rate address rels (not inclusive)
	* @return the range of commerce tax fixed rate address rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTaxFixedRateAddressRel> getCommerceTaxFixedRateAddressRels(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTaxFixedRateAddressRel> getCommerceTaxFixedRateAddressRels(
		long cpTaxCategoryId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTaxFixedRateAddressRel> getCommerceTaxFixedRateAddressRels(
		long cpTaxCategoryId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator);

	/**
	* Returns the number of commerce tax fixed rate address rels.
	*
	* @return the number of commerce tax fixed rate address rels
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTaxFixedRateAddressRelsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTaxFixedRateAddressRelsCount(long cpTaxCategoryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTaxFixedRateAddressRel> getCommerceTaxMethodFixedRateAddressRels(
		long commerceTaxMethodId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceTaxFixedRateAddressRel> getCommerceTaxMethodFixedRateAddressRels(
		long commerceTaxMethodId, int start, int end,
		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTaxMethodFixedRateAddressRelsCount(
		long commerceTaxMethodId);

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
	* Updates the commerce tax fixed rate address rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceTaxFixedRateAddressRel the commerce tax fixed rate address rel
	* @return the commerce tax fixed rate address rel that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel);

	public CommerceTaxFixedRateAddressRel updateCommerceTaxFixedRateAddressRel(
		long commerceTaxFixedRateAddressRelId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double rate)
		throws PortalException;
}