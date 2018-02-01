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

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
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
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for CPDefinitionSpecificationOptionValue. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValueLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CPDefinitionSpecificationOptionValueLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPDefinitionSpecificationOptionValueLocalService
	extends BaseLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionSpecificationOptionValueLocalServiceUtil} to access the cp definition specification option value local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionSpecificationOptionValueLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp definition specification option value to the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	* @return the cp definition specification option value that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionSpecificationOptionValue addCPDefinitionSpecificationOptionValue(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue);

	public CPDefinitionSpecificationOptionValue addCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId,
		long cpOptionCategoryId, Map<Locale, java.lang.String> valueMap,
		double priority, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new cp definition specification option value with the primary key. Does not add the cp definition specification option value to the database.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key for the new cp definition specification option value
	* @return the new cp definition specification option value
	*/
	public CPDefinitionSpecificationOptionValue createCPDefinitionSpecificationOptionValue(
		long CPDefinitionSpecificationOptionValueId);

	/**
	* Deletes the cp definition specification option value from the database. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	* @return the cp definition specification option value that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionSpecificationOptionValue deleteCPDefinitionSpecificationOptionValue(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue);

	/**
	* Deletes the cp definition specification option value with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value that was removed
	* @throws PortalException if a cp definition specification option value with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPDefinitionSpecificationOptionValue deleteCPDefinitionSpecificationOptionValue(
		long CPDefinitionSpecificationOptionValueId) throws PortalException;

	public void deleteCPDefinitionSpecificationOptionValues(long cpDefinitionId);

	public void deleteCPSpecificationOptionDefinitionValues(
		long cpSpecificationOptionId);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		long CPDefinitionSpecificationOptionValueId);

	/**
	* Returns the cp definition specification option value matching the UUID and group.
	*
	* @param uuid the cp definition specification option value's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition specification option value, or <code>null</code> if a matching cp definition specification option value could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValueByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the cp definition specification option value with the primary key.
	*
	* @param CPDefinitionSpecificationOptionValueId the primary key of the cp definition specification option value
	* @return the cp definition specification option value
	* @throws PortalException if a cp definition specification option value with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionSpecificationOptionValue getCPDefinitionSpecificationOptionValue(
		long CPDefinitionSpecificationOptionValueId) throws PortalException;

	/**
	* Returns the cp definition specification option value matching the UUID and group.
	*
	* @param uuid the cp definition specification option value's UUID
	* @param groupId the primary key of the group
	* @return the matching cp definition specification option value
	* @throws PortalException if a matching cp definition specification option value could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPDefinitionSpecificationOptionValue getCPDefinitionSpecificationOptionValueByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the cp definition specification option values.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @return the range of cp definition specification option values
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId, long cpOptionCategoryId);

	/**
	* Returns all the cp definition specification option values matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition specification option values
	* @param companyId the primary key of the company
	* @return the matching cp definition specification option values, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValuesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of cp definition specification option values matching the UUID and company.
	*
	* @param uuid the UUID of the cp definition specification option values
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp definition specification option values
	* @param end the upper bound of the range of cp definition specification option values (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp definition specification option values, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValuesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinitionSpecificationOptionValue> orderByComparator);

	/**
	* Returns the number of cp definition specification option values.
	*
	* @return the number of cp definition specification option values
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionSpecificationOptionValuesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

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
	* Updates the cp definition specification option value in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpDefinitionSpecificationOptionValue the cp definition specification option value
	* @return the cp definition specification option value that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPDefinitionSpecificationOptionValue updateCPDefinitionSpecificationOptionValue(
		CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue);

	public CPDefinitionSpecificationOptionValue updateCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId,
		Map<Locale, java.lang.String> valueMap, double priority,
		ServiceContext serviceContext) throws PortalException;

	public CPDefinitionSpecificationOptionValue updateCPOptionCategoryId(
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId)
		throws PortalException;
}