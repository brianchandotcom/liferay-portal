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

import com.liferay.commerce.product.model.CPInstance;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service interface for CPInstance. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Marco Leo
 * @see CPInstanceLocalServiceUtil
 * @see com.liferay.commerce.product.service.base.CPInstanceLocalServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPInstanceLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CPInstanceLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPInstanceLocalServiceUtil} to access the cp instance local service. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPInstanceLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the cp instance to the database. Also notifies the appropriate model listeners.
	*
	* @param cpInstance the cp instance
	* @return the cp instance that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPInstance addCPInstance(CPInstance cpInstance);

	public CPInstance addCPInstance(long cpDefinitionId, java.lang.String sku,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		boolean purchasable, java.lang.String ddmContent, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CPInstance addCPInstance(long cpDefinitionId, java.lang.String sku,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		boolean purchasable, java.lang.String ddmContent, double width,
		double height, double depth, double weight, BigDecimal cost,
		BigDecimal price, boolean published, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public void buildCPInstances(long cpDefinitionId,
		ServiceContext serviceContext) throws PortalException;

	public void checkCPInstances() throws PortalException;

	public void checkCPInstancesByDisplayDate(long cpDefinitionId)
		throws PortalException;

	/**
	* Creates a new cp instance with the primary key. Does not add the cp instance to the database.
	*
	* @param CPInstanceId the primary key for the new cp instance
	* @return the new cp instance
	*/
	public CPInstance createCPInstance(long CPInstanceId);

	/**
	* Deletes the cp instance from the database. Also notifies the appropriate model listeners.
	*
	* @param cpInstance the cp instance
	* @return the cp instance that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPInstance deleteCPInstance(CPInstance cpInstance)
		throws PortalException;

	/**
	* Deletes the cp instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPInstanceId the primary key of the cp instance
	* @return the cp instance that was removed
	* @throws PortalException if a cp instance with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CPInstance deleteCPInstance(long CPInstanceId)
		throws PortalException;

	public void deleteCPInstances(long cpDefinitionId)
		throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CPInstance fetchCPInstance(long CPInstanceId);

	/**
	* Returns the cp instance matching the UUID and group.
	*
	* @param uuid the cp instance's UUID
	* @param groupId the primary key of the group
	* @return the matching cp instance, or <code>null</code> if a matching cp instance could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstance fetchCPInstanceByUuidAndGroupId(java.lang.String uuid,
		long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPDefinitionInstances(long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPDefinitionInstances(long cpDefinitionId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPDefinitionInstances(long cpDefinitionId,
		int status, int start, int end,
		OrderByComparator<CPInstance> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPDefinitionInstancesCount(long cpDefinitionId, int status);

	/**
	* Returns the cp instance with the primary key.
	*
	* @param CPInstanceId the primary key of the cp instance
	* @return the cp instance
	* @throws PortalException if a cp instance with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstance getCPInstance(long CPInstanceId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstance getCPInstance(long cpDefinitionId, java.lang.String sku)
		throws PortalException;

	/**
	* Returns the cp instance matching the UUID and group.
	*
	* @param uuid the cp instance's UUID
	* @param groupId the primary key of the group
	* @return the matching cp instance
	* @throws PortalException if a matching cp instance could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CPInstance getCPInstanceByUuidAndGroupId(java.lang.String uuid,
		long groupId) throws PortalException;

	/**
	* Returns a range of all the cp instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.model.impl.CPInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @return the range of cp instances
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPInstances(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPInstances(long groupId, int status, int start,
		int end, OrderByComparator<CPInstance> orderByComparator)
		throws PortalException;

	/**
	* Returns all the cp instances matching the UUID and company.
	*
	* @param uuid the UUID of the cp instances
	* @param companyId the primary key of the company
	* @return the matching cp instances, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of cp instances matching the UUID and company.
	*
	* @param uuid the UUID of the cp instances
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of cp instances
	* @param end the upper bound of the range of cp instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching cp instances, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CPInstance> getCPInstancesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CPInstance> orderByComparator);

	/**
	* Returns the number of cp instances.
	*
	* @return the number of cp instances
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPInstancesCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCPInstancesCount(long groupId, int status)
		throws PortalException;

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String[] getSKUs(long cpDefinitionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Hits search(SearchContext searchContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPInstance> searchCPDefinitionInstances(
		long companyId, long groupId, long cpDefinitionId,
		java.lang.String keywords, int status, int start, int end, Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPInstance> searchCPInstances(long companyId,
		long groupId, java.lang.String keywords, int status, int start,
		int end, Sort sort) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CPInstance> searchCPInstances(
		SearchContext searchContext) throws PortalException;

	/**
	* Updates the cp instance in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cpInstance the cp instance
	* @return the cp instance that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CPInstance updateCPInstance(CPInstance cpInstance);

	public CPInstance updateCPInstance(long cpInstanceId, java.lang.String sku,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		boolean purchasable, boolean published, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CPInstance updateCPInstance(long cpInstanceId, java.lang.String sku,
		java.lang.String gtin, java.lang.String manufacturerPartNumber,
		boolean purchasable, double width, double height, double depth,
		double weight, BigDecimal cost, BigDecimal price, boolean published,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		ServiceContext serviceContext) throws PortalException;

	public CPInstance updatePricingInfo(long cpInstanceId, BigDecimal cost,
		BigDecimal price, ServiceContext serviceContext)
		throws PortalException;

	public CPInstance updateShippingInfo(long cpInstanceId, double width,
		double height, double depth, double weight,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CPInstance updateStatus(long userId, long cpInstanceId, int status,
		ServiceContext serviceContext,
		Map<java.lang.String, Serializable> workflowContext)
		throws PortalException;
}