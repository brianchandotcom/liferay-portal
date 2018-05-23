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

package com.liferay.commerce.product.type.virtual.order.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;

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

import java.io.File;
import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for CommerceVirtualOrderItem. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVirtualOrderItemLocalServiceUtil
 * @see com.liferay.commerce.product.type.virtual.order.service.base.CommerceVirtualOrderItemLocalServiceBaseImpl
 * @see com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceVirtualOrderItemLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceVirtualOrderItemLocalServiceUtil} to access the commerce virtual order item local service. Add custom service methods to {@link com.liferay.commerce.product.type.virtual.order.service.impl.CommerceVirtualOrderItemLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the commerce virtual order item to the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVirtualOrderItem the commerce virtual order item
	* @return the commerce virtual order item that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceVirtualOrderItem addCommerceVirtualOrderItem(
		CommerceVirtualOrderItem commerceVirtualOrderItem);

	public CommerceVirtualOrderItem addCommerceVirtualOrderItem(
		long commerceOrderItemId, long fileEntryId, String url,
		int activationStatus, long duration, int usages, int maxUsages,
		ServiceContext serviceContext) throws PortalException;

	public CommerceVirtualOrderItem addCommerceVirtualOrderItem(
		long commerceOrderItemId, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new commerce virtual order item with the primary key. Does not add the commerce virtual order item to the database.
	*
	* @param commerceVirtualOrderItemId the primary key for the new commerce virtual order item
	* @return the new commerce virtual order item
	*/
	@Transactional(enabled = false)
	public CommerceVirtualOrderItem createCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId);

	/**
	* Deletes the commerce virtual order item from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVirtualOrderItem the commerce virtual order item
	* @return the commerce virtual order item that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceVirtualOrderItem deleteCommerceVirtualOrderItem(
		CommerceVirtualOrderItem commerceVirtualOrderItem);

	/**
	* Deletes the commerce virtual order item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	* @return the commerce virtual order item that was removed
	* @throws PortalException if a commerce virtual order item with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CommerceVirtualOrderItem deleteCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId) throws PortalException;

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public CommerceVirtualOrderItem fetchCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceVirtualOrderItem fetchCommerceVirtualOrderItemByCommerceOrderItemId(
		long commerceOrderItemId);

	/**
	* Returns the commerce virtual order item matching the UUID and group.
	*
	* @param uuid the commerce virtual order item's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce virtual order item, or <code>null</code> if a matching commerce virtual order item could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceVirtualOrderItem fetchCommerceVirtualOrderItemByUuidAndGroupId(
		String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	* Returns the commerce virtual order item with the primary key.
	*
	* @param commerceVirtualOrderItemId the primary key of the commerce virtual order item
	* @return the commerce virtual order item
	* @throws PortalException if a commerce virtual order item with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceVirtualOrderItem getCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId) throws PortalException;

	/**
	* Returns the commerce virtual order item matching the UUID and group.
	*
	* @param uuid the commerce virtual order item's UUID
	* @param groupId the primary key of the group
	* @return the matching commerce virtual order item
	* @throws PortalException if a matching commerce virtual order item could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceVirtualOrderItem getCommerceVirtualOrderItemByUuidAndGroupId(
		String uuid, long groupId) throws PortalException;

	/**
	* Returns a range of all the commerce virtual order items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.commerce.product.type.virtual.order.model.impl.CommerceVirtualOrderItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce virtual order items
	* @param end the upper bound of the range of commerce virtual order items (not inclusive)
	* @return the range of commerce virtual order items
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceVirtualOrderItem> getCommerceVirtualOrderItems(
		int start, int end);

	/**
	* Returns all the commerce virtual order items matching the UUID and company.
	*
	* @param uuid the UUID of the commerce virtual order items
	* @param companyId the primary key of the company
	* @return the matching commerce virtual order items, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceVirtualOrderItem> getCommerceVirtualOrderItemsByUuidAndCompanyId(
		String uuid, long companyId);

	/**
	* Returns a range of commerce virtual order items matching the UUID and company.
	*
	* @param uuid the UUID of the commerce virtual order items
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of commerce virtual order items
	* @param end the upper bound of the range of commerce virtual order items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching commerce virtual order items, or an empty list if no matches were found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceVirtualOrderItem> getCommerceVirtualOrderItemsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator);

	/**
	* Returns the number of commerce virtual order items.
	*
	* @return the number of commerce virtual order items
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceVirtualOrderItemsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public File getFile(long commerceVirtualOrderItemId)
		throws Exception;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceVirtualOrderItem> getOrganizationCommerceVirtualOrderItems(
		long groupId, long organizationId, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getOrganizationCommerceVirtualOrderItemsCount(long groupId,
		long organizationId);

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceVirtualOrderItem> getUserCommerceVirtualOrderItems(
		long groupId, long user, int start, int end,
		OrderByComparator<CommerceVirtualOrderItem> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserCommerceVirtualOrderItemsCount(long groupId, long userId);

	public CommerceVirtualOrderItem incrementCommerceVirtualOrderItemUsages(
		long commerceVirtualOrderItemId) throws PortalException;

	public void setActive(long commerceVirtualOrderItemId, boolean active)
		throws PortalException;

	/**
	* Updates the commerce virtual order item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param commerceVirtualOrderItem the commerce virtual order item
	* @return the commerce virtual order item that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
		CommerceVirtualOrderItem commerceVirtualOrderItem);

	public CommerceVirtualOrderItem updateCommerceVirtualOrderItem(
		long commerceVirtualOrderItemId, long fileEntryId, String url,
		int activationStatus, long duration, int usages, int maxUsages,
		boolean active) throws PortalException;
}