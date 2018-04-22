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

import com.liferay.commerce.model.CommerceOrder;

import com.liferay.exportimport.kernel.lar.PortletDataContext;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Provides the local service interface for CommerceOrder. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderLocalServiceUtil
 * @see com.liferay.commerce.service.base.CommerceOrderLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceOrderLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceOrderLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderLocalServiceUtil} to access the commerce order local service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceOrderLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce order to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrder the commerce order
	* @return the commerce order that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder addCommerceOrder(CommerceOrder commerceOrder);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder addOrganizationCommerceOrder(long groupId,
		long userId, long siteGroupId, long orderOrganizationId,
		long shippingAddressId, java.lang.String purchaseOrderNumber)
		throws PortalException;

	public CommerceOrder addUserCommerceOrder(long groupId, long userId)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder addUserCommerceOrder(long groupId, long userId,
		long orderUserId) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder approveCommerceOrder(long userId, long commerceOrderId)
		throws PortalException;

	public CommerceOrder cancelCommerceOrderPayment(long commerceOrderId,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder checkoutCommerceOrder(long commerceOrderId,
		ServiceContext serviceContext) throws PortalException;

	public CommerceOrder completeCommerceOrderPayment(long commerceOrderId,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Creates a new commerce order with the primary key. Does not add the commerce order to the database.
	*
	* @param commerceOrderId the primary key for the new commerce order
	* @return the new commerce order
	*/
	public CommerceOrder createCommerceOrder(long commerceOrderId);

	/**
	* Deletes the commerce order from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrder the commerce order
	* @return the commerce order that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceOrder deleteCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException;

	/**
	* Deletes the commerce order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order that was removed
	* @throws PortalException if a commerce order with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceOrder deleteCommerceOrder(long commerceOrderId)
		throws PortalException;

	public void deleteCommerceOrders(long groupId) throws PortalException;

	public void deleteCommerceOrders(long userId, Date date, int status);

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public CommerceOrder executeWorkflowTransition(long userId,
		long commerceOrderId, long workflowTaskId,
		java.lang.String transitionName, java.lang.String comment)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrder(long commerceOrderId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrder(long groupId, long userId,
		int orderStatus);

	/**
	* Returns the commerce order matching the UUID and group.
	*
	* @param uuid the commerce order's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce order, or <code>null</code> if a matching commerce order could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrderByUuidAndGroupId(
		java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int[] getAvailableOrderStatuses(long commerceOrderId)
		throws PortalException;

	/**
	* Returns the commerce order with the primary key.
	*
	* @param commerceOrderId the primary key of the commerce order
	* @return the commerce order
	* @throws PortalException if a commerce order with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder getCommerceOrder(long commerceOrderId)
		throws PortalException;

	/**
	* Returns the commerce order matching the UUID and group.
	*
	* @param uuid the commerce order's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce order
	* @throws PortalException if a matching commerce order could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder getCommerceOrderByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the commerce orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.model.impl.CommerceOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @return the range of commerce orders
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrders(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrders(long groupId,
		long orderUserId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrdersByBillingAddress(
		long billingAddressId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrdersByShippingAddress(
		long shippingAddressId);

	/**
	* Returns all the commerce orders matching the UUID and company.
	*
	* @param uuid the UUID of the commerce orders
	* @param companyId the primary key of the company
	* @return the matching commerce orders, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrdersByUuidAndCompanyId(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of commerce orders matching the UUID and company.
	*
	* @param uuid the UUID of the commerce orders
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce orders
	* @param end the upper bound of the range of commerce orders (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce orders, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrdersByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator);

	/**
	* Returns the number of commerce orders.
	*
	* @return the number of commerce orders
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceOrdersCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceOrdersCount(long groupId, long orderUserId);

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

	public void mergeGuestCommerceOrder(long guestCommerceOrderId,
		long userCommerceOrderId, ServiceContext serviceContext)
		throws PortalException;

	public CommerceOrder reorderCommerceOrder(long userId, long commerceOrderId)
		throws PortalException;

	public CommerceOrder resetCommerceOrderShipping(long commerceOrderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommerceOrder> searchCommerceOrders(
		SearchContext searchContext) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long searchCommerceOrdersCount(SearchContext searchContext)
		throws PortalException;

	public java.lang.String startCommerceOrderPayment(long commerceOrderId,
		ServiceContext serviceContext) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder submitCommerceOrder(long userId, long commerceOrderId)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder updateBillingAddress(long commerceOrderId,
		java.lang.String name, java.lang.String description,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId,
		java.lang.String phoneNumber, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the commerce order in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceOrder the commerce order
	* @return the commerce order that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder updateCommerceOrder(CommerceOrder commerceOrder);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder updateCommerceOrder(long commerceOrderId,
		long billingAddressId, long shippingAddressId,
		long commercePaymentMethodId, long commerceShippingMethodId,
		java.lang.String shippingOptionName,
		java.lang.String purchaseOrderNumber, BigDecimal subtotal,
		BigDecimal shippingPrice, BigDecimal total,
		java.lang.String advanceStatus) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder updateOrderStatus(long commerceOrderId, int orderStatus)
		throws PortalException;

	public CommerceOrder updatePaymentStatus(long commerceOrderId,
		int paymentStatus, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder updatePurchaseOrderNumber(long commerceOrderId,
		java.lang.String purchaseOrderNumber) throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder updateShippingAddress(long commerceOrderId,
		java.lang.String name, java.lang.String description,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId,
		java.lang.String phoneNumber, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceOrder updateStatus(long userId, long commerceOrderId,
		int status, ServiceContext serviceContext,
		Map<java.lang.String, Serializable> workflowContext)
		throws PortalException;

	public CommerceOrder updateUser(long commerceOrderId, long userId)
		throws PortalException;
}