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

import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.petra.sql.dsl.query.DSLQuery;
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

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for CommerceShipmentItem. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItemLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceShipmentItemLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShipmentItemLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce shipment item local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceShipmentItemLocalServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Adds the commerce shipment item to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipmentItem the commerce shipment item
	 * @return the commerce shipment item that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CommerceShipmentItem addCommerceShipmentItem(
		CommerceShipmentItem commerceShipmentItem);

	@Indexable(type = IndexableType.REINDEX)
	public CommerceShipmentItem addCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			int quantity, ServiceContext serviceContext)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceShipmentItem addDeliverySubscriptionCommerceShipmentItem(
			long groupId, long userId, long commerceShipmentId,
			long commerceOrderItemId)
		throws PortalException;

	public CommerceShipmentItem addOrUpdateCommerceShipmentItem(
			String externalReferenceCode, long commerceShipmentId,
			long commerceOrderItemId, long commerceInventoryWarehouseId,
			int quantity, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Creates a new commerce shipment item with the primary key. Does not add the commerce shipment item to the database.
	 *
	 * @param commerceShipmentItemId the primary key for the new commerce shipment item
	 * @return the new commerce shipment item
	 */
	@Transactional(enabled = false)
	public CommerceShipmentItem createCommerceShipmentItem(
		long commerceShipmentItemId);

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Deletes the commerce shipment item from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipmentItem the commerce shipment item
	 * @return the commerce shipment item that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public CommerceShipmentItem deleteCommerceShipmentItem(
		CommerceShipmentItem commerceShipmentItem);

	@Indexable(type = IndexableType.DELETE)
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceShipmentItem deleteCommerceShipmentItem(
			CommerceShipmentItem commerceShipmentItem,
			boolean restoreStockQuantity)
		throws PortalException;

	/**
	 * Deletes the commerce shipment item with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item that was removed
	 * @throws PortalException if a commerce shipment item with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public CommerceShipmentItem deleteCommerceShipmentItem(
			long commerceShipmentItemId)
		throws PortalException;

	public void deleteCommerceShipmentItem(
			long commerceShipmentItemId, boolean restoreStockQuantity)
		throws PortalException;

	public void deleteCommerceShipmentItems(
			long commerceShipmentId, boolean restoreStockQuantity)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> T dslQuery(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int dslQueryCount(DSLQuery dslQuery);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentItemId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipmentItem fetchCommerceShipmentItem(
		long commerceShipmentId, long commerceOrderItemId,
		long commerceInventoryWarehouseId);

	/**
	 * Returns the commerce shipment item with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce shipment item's external reference code
	 * @return the matching commerce shipment item, or <code>null</code> if a matching commerce shipment item could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipmentItem
		fetchCommerceShipmentItemByExternalReferenceCode(
			long companyId, String externalReferenceCode);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchCommerceShipmentItemByExternalReferenceCode(long, String)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipmentItem fetchCommerceShipmentItemByReferenceCode(
		long companyId, String externalReferenceCode);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the commerce shipment item with the primary key.
	 *
	 * @param commerceShipmentItemId the primary key of the commerce shipment item
	 * @return the commerce shipment item
	 * @throws PortalException if a commerce shipment item with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipmentItem getCommerceShipmentItem(
			long commerceShipmentItemId)
		throws PortalException;

	/**
	 * Returns the commerce shipment item with the matching external reference code and company.
	 *
	 * @param companyId the primary key of the company
	 * @param externalReferenceCode the commerce shipment item's external reference code
	 * @return the matching commerce shipment item
	 * @throws PortalException if a matching commerce shipment item could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShipmentItem getCommerceShipmentItemByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException;

	/**
	 * Returns a range of all the commerce shipment items.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.model.impl.CommerceShipmentItemModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipment items
	 * @param end the upper bound of the range of commerce shipment items (not inclusive)
	 * @return the range of commerce shipment items
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		int start, int end);

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceOrderItemId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipmentItem> getCommerceShipmentItems(
		long commerceShipmentId, long commerceOrderItemId, int start, int end,
		OrderByComparator<CommerceShipmentItem> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShipmentItem>
		getCommerceShipmentItemsByCommerceOrderItemId(long commerceOrderItemId);

	/**
	 * Returns the number of commerce shipment items.
	 *
	 * @return the number of commerce shipment items
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentItemsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentItemsCount(long commerceShipmentId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentItemsCountByCommerceOrderItemId(
		long commerceOrderItemId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShipmentOrderItemsQuantity(
		long commerceShipmentId, long commerceOrderItemId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Updates the commerce shipment item in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceShipmentItemLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceShipmentItem the commerce shipment item
	 * @return the commerce shipment item that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public CommerceShipmentItem updateCommerceShipmentItem(
		CommerceShipmentItem commerceShipmentItem);

	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, int quantity)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, long commerceInventoryWarehouseId,
			int quantity)
		throws PortalException;

	@Indexable(type = IndexableType.REINDEX)
	public CommerceShipmentItem updateCommerceShipmentItemExternalReferenceCode(
			String externalReferenceCode, long commerceShipmentItemId)
		throws PortalException;

}