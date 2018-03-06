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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderPaymentMethodException;
import com.liferay.commerce.exception.CommerceOrderPurchaseOrderNumberException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingMethodException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderConstants;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.commerce.service.base.CommerceOrderLocalServiceBaseImpl;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CommerceOrderLocalServiceImpl
	extends CommerceOrderLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder addOrganizationCommerceOrder(
			long groupId, long userId, long siteGroupId,
			long orderOrganizationId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		if (hasWorkflowDefinition(
				siteGroupId, CommerceOrderConstants.TYPE_PK_APPROVAL)) {

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		return addCommerceOrder(
			siteGroupId, orderOrganizationId, userId, 0, 0, 0, 0, null, null, 0,
			0, 0, CommerceOrderConstants.PAYMENT_STATUS_PENDING,
			CommerceOrderConstants.SHIPPING_STATUS_NOT_SHIPPED,
			CommerceOrderConstants.ORDER_STATUS_OPEN, serviceContext);
	}

	@Override
	public CommerceOrder addUserCommerceOrder(long groupId, long userId)
		throws PortalException {

		return commerceOrderLocalService.addUserCommerceOrder(
			groupId, userId, userId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder addUserCommerceOrder(
			long groupId, long userId, long orderUserId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		if (hasWorkflowDefinition(
				groupId, CommerceOrderConstants.TYPE_PK_APPROVAL)) {

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		return addCommerceOrder(
			groupId, 0, orderUserId, 0, 0, 0, 0, null, null, 0, 0, 0,
			CommerceOrderConstants.PAYMENT_STATUS_PENDING,
			CommerceOrderConstants.SHIPPING_STATUS_NOT_SHIPPED,
			CommerceOrderConstants.ORDER_STATUS_OPEN, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder approveCommerceOrder(long userId, long commerceOrderId)
		throws PortalException {

		// Commerce order

		CommerceOrder commerceOrder = commerceOrderLocalService.updateStatus(
			userId, commerceOrderId, WorkflowConstants.STATUS_APPROVED,
			new ServiceContext(), null);

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commerceOrder.getCompanyId(), commerceOrder.getGroupId(),
			CommerceOrder.class.getName(), commerceOrder.getCommerceOrderId());

		return commerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder checkoutCommerceOrder(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException {

		// Commerce order

		CommerceOrder commerceOrder =
			commerceOrderLocalService.approveCommerceOrder(
				serviceContext.getUserId(), commerceOrderId);

		validate(commerceOrder);

		serviceContext.setScopeGroupId(commerceOrder.getGroupId());

		double subtotal = commercePriceCalculationLocalService.getOrderSubtotal(
			commerceOrder);
		double shippingPrice = commerceOrder.getShippingPrice();

		double total = subtotal + shippingPrice;

		commerceOrder.setSubtotal(subtotal);
		commerceOrder.setTotal(total);
		commerceOrder.setOrderStatus(
			CommerceOrderConstants.ORDER_STATUS_TO_TRANSMIT);

		// Commerce addresses

		long billingAddressId = commerceOrder.getBillingAddressId();

		if (billingAddressId > 0) {
			CommerceAddress commerceAddress =
				commerceAddressLocalService.copyCommerceAddress(
					billingAddressId, commerceOrder.getModelClassName(),
					commerceOrder.getCommerceOrderId(), serviceContext);

			billingAddressId = commerceAddress.getCommerceAddressId();
		}

		long shippingAddressId = commerceOrder.getShippingAddressId();

		if (shippingAddressId > 0) {
			CommerceAddress commerceAddress =
				commerceAddressLocalService.copyCommerceAddress(
					shippingAddressId, commerceOrder.getModelClassName(),
					commerceOrder.getCommerceOrderId(), serviceContext);

			shippingAddressId = commerceAddress.getCommerceAddressId();
		}

		if ((billingAddressId > 0) || (shippingAddressId > 0)) {
			commerceOrder.setBillingAddressId(billingAddressId);
			commerceOrder.setShippingAddressId(shippingAddressId);
		}

		commerceOrderPersistence.update(commerceOrder);

		// Workflow

		return startWorkflowInstance(
			serviceContext.getUserId(), commerceOrder, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceOrder deleteCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException {

		// Commerce order

		commerceOrderPersistence.remove(commerceOrder);

		// Commerce order items

		commerceOrderItemLocalService.deleteCommerceOrderItems(
			commerceOrder.getCommerceOrderId());

		// Commerce order notes

		commerceOrderNoteLocalService.deleteCommerceOrderNotes(
			commerceOrder.getCommerceOrderId());

		// Commerce order payments

		commerceOrderPaymentLocalService.deleteCommerceOrderPayments(
			commerceOrder.getCommerceOrderId());

		// Commerce addresses

		commerceAddressLocalService.deleteCommerceAddresses(
			commerceOrder.getModelClassName(),
			commerceOrder.getCommerceOrderId());

		// Expando

		expandoRowLocalService.deleteRows(commerceOrder.getCommerceOrderId());

		// Workflow

		workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commerceOrder.getCompanyId(), commerceOrder.getGroupId(),
			CommerceOrder.class.getName(), commerceOrder.getCommerceOrderId());

		return commerceOrder;
	}

	@Override
	public CommerceOrder deleteCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		return commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
	}

	@Override
	public void deleteCommerceOrders(long groupId) throws PortalException {
		List<CommerceOrder> commerceOrders =
			commerceOrderPersistence.findByGroupId(groupId);

		for (CommerceOrder commerceOrder : commerceOrders) {
			commerceOrderLocalService.deleteCommerceOrder(commerceOrder);
		}
	}

	@Override
	public CommerceOrder fetchCommerceOrder(
		long groupId, long userId, int orderStatus) {

		return commerceOrderPersistence.fetchByG_U_O_First(
			groupId, userId, orderStatus, null);
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
		long groupId, long orderUserId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {

		return commerceOrderPersistence.findByG_O(
			groupId, orderUserId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceOrder> getCommerceOrdersByBillingAddress(
		long billingAddressId) {

		return commerceOrderPersistence.findByBillingAddressId(
			billingAddressId);
	}

	@Override
	public List<CommerceOrder> getCommerceOrdersByShippingAddress(
		long shippingAddressId) {

		return commerceOrderPersistence.findByShippingAddressId(
			shippingAddressId);
	}

	@Override
	public int getCommerceOrdersCount(long groupId, long orderUserId) {
		return commerceOrderPersistence.countByG_O(groupId, orderUserId);
	}

	@Override
	public void mergeGuestCommerceOrder(
			long guestCommerceOrderId, long userCommerceOrderId,
			ServiceContext serviceContext)
		throws PortalException {

		List<CommerceOrderItem> guestCommerceOrderItems =
			commerceOrderItemPersistence.findByCommerceOrderId(
				guestCommerceOrderId);

		for (CommerceOrderItem guestCommerceOrderItem :
				guestCommerceOrderItems) {

			List<CommerceOrderItem> userCommerceOrderItems =
				commerceOrderItemPersistence.findByC_I(
					userCommerceOrderId,
					guestCommerceOrderItem.getCPInstanceId());

			if (!userCommerceOrderItems.isEmpty()) {
				boolean found = false;

				for (CommerceOrderItem userCommerceOrderItem :
						userCommerceOrderItems) {

					if (_ddmFormValuesHelper.equals(
							guestCommerceOrderItem.getJson(),
							userCommerceOrderItem.getJson())) {

						found = true;

						break;
					}
				}

				if (found) {
					break;
				}
			}

			commerceOrderItemLocalService.addCommerceOrderItem(
				userCommerceOrderId, guestCommerceOrderItem.getCPInstanceId(),
				guestCommerceOrderItem.getQuantity(),
				guestCommerceOrderItem.getShippedQuantity(),
				guestCommerceOrderItem.getJson(),
				guestCommerceOrderItem.getPrice(), serviceContext);
		}

		commerceOrderLocalService.deleteCommerceOrder(guestCommerceOrderId);
	}

	@Override
	public CommerceOrder reorderCommerceOrder(long userId, long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(commerceOrder.getGroupId());
		serviceContext.setUserId(userId);

		long billingAddressId = 0;
		long shippingAddressId = 0;

		CommerceAddress billingAddress = getNewCommerceAddress(
			commerceOrder, commerceOrder.getBillingAddress(), serviceContext);
		CommerceAddress shippingAddress = getNewCommerceAddress(
			commerceOrder, commerceOrder.getShippingAddress(), serviceContext);

		if (billingAddress != null) {
			billingAddressId = billingAddress.getCommerceAddressId();
		}

		if (shippingAddress != null) {
			shippingAddressId = shippingAddress.getCommerceAddressId();
		}

		return addCommerceOrder(
			commerceOrder.getSiteGroupId(),
			commerceOrder.getOrderOrganizationId(),
			commerceOrder.getOrderUserId(), billingAddressId, shippingAddressId,
			commerceOrder.getCommercePaymentMethodId(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			commerceOrder.getShippingPrice(), commerceOrder.getTotal(),
			CommerceOrderConstants.PAYMENT_STATUS_PENDING,
			CommerceOrderConstants.SHIPPING_STATUS_NOT_SHIPPED,
			CommerceOrderConstants.ORDER_STATUS_OPEN, serviceContext);
	}

	@Override
	public CommerceOrder resetCommerceOrderShipping(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		return commerceOrderLocalService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getCommercePaymentMethodId(), 0, null,
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			0, commerceOrder.getTotal(), commerceOrder.getAdvanceStatus(),
			commerceOrder.getPaymentStatus(), commerceOrder.getOrderStatus());
	}

	@Override
	public BaseModelSearchResult<CommerceOrder> searchCommerceOrders(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceOrder> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CommerceOrder.class.getName());

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<CommerceOrder> commerceOrders = getCommerceOrders(hits);

			if (commerceOrders != null) {
				return new BaseModelSearchResult<>(
					commerceOrders, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder submitCommerceOrder(long userId, long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		if (!commerceOrder.isDraft() || commerceOrder.isEmpty()) {
			return commerceOrder;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(commerceOrder.getGroupId());
		serviceContext.setUserId(userId);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return startWorkflowInstance(
			serviceContext.getUserId(), commerceOrder, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateBillingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		return updateAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			CommerceOrder::getBillingAddressId,
			CommerceOrder::setBillingAddressId, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceOrder(
			long commerceOrderId, long billingAddressId, long shippingAddressId,
			long commercePaymentMethodId, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			double subtotal, double shippingPrice, double total,
			String advanceStatus, int paymentStatus, int orderStatus)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setBillingAddressId(billingAddressId);
		commerceOrder.setShippingAddressId(shippingAddressId);
		commerceOrder.setCommercePaymentMethodId(commercePaymentMethodId);
		commerceOrder.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceOrder.setShippingOptionName(shippingOptionName);
		commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);
		commerceOrder.setSubtotal(subtotal);
		commerceOrder.setShippingPrice(shippingPrice);
		commerceOrder.setTotal(total);
		commerceOrder.setAdvanceStatus(advanceStatus);
		commerceOrder.setPaymentStatus(paymentStatus);
		commerceOrder.setOrderStatus(orderStatus);

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updatePaymentStatus(
			long commerceOrderId, int paymentStatus, int orderStatus)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setPaymentStatus(paymentStatus);
		commerceOrder.setOrderStatus(orderStatus);

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updatePurchaseOrderNumber(
			long commerceOrderId, String purchaseOrderNumber)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		validatePurchaseOrderNumber(purchaseOrderNumber);

		commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateShippingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		return updateAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			CommerceOrder::getShippingAddressId,
			CommerceOrder::setShippingAddressId, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateStatus(
			long userId, long commerceOrderId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setStatus(status);
		commerceOrder.setStatusByUserId(user.getUserId());
		commerceOrder.setStatusByUserName(user.getFullName());
		commerceOrder.setStatusDate(serviceContext.getModifiedDate(now));

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrder;
	}

	@Override
	public CommerceOrder updateUser(long commerceOrderId, long userId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		User user = userLocalService.getUser(userId);

		commerceOrder.setUserId(user.getUserId());
		commerceOrder.setUserName(user.getFullName());

		return commerceOrderPersistence.update(commerceOrder);
	}

	protected CommerceOrder addCommerceOrder(
			long siteGroupId, long orderOrganizationId, long orderUserId,
			long billingAddressId, long shippingAddressId,
			long commercePaymentMethodId, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			double subtotal, double shippingPrice, double total,
			int paymentStatus, int shippingStatus, int orderStatus,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce order

		long scopeGroupId = serviceContext.getScopeGroupId();
		long userId = serviceContext.getUserId();

		User user = userLocalService.getUser(userId);

		long commerceOrderId = counterLocalService.increment();

		CommerceOrder commerceOrder = commerceOrderPersistence.create(
			commerceOrderId);

		commerceOrder.setUuid(serviceContext.getUuid());
		commerceOrder.setGroupId(scopeGroupId);
		commerceOrder.setCompanyId(user.getCompanyId());
		commerceOrder.setUserId(user.getUserId());
		commerceOrder.setUserName(user.getFullName());
		commerceOrder.setSiteGroupId(siteGroupId);
		commerceOrder.setOrderOrganizationId(orderOrganizationId);
		commerceOrder.setOrderUserId(orderUserId);
		commerceOrder.setBillingAddressId(billingAddressId);
		commerceOrder.setShippingAddressId(shippingAddressId);
		commerceOrder.setCommercePaymentMethodId(commercePaymentMethodId);
		commerceOrder.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceOrder.setShippingOptionName(shippingOptionName);
		commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);
		commerceOrder.setSubtotal(subtotal);
		commerceOrder.setShippingPrice(shippingPrice);
		commerceOrder.setTotal(total);
		commerceOrder.setPaymentStatus(paymentStatus);
		commerceOrder.setShippingStatus(shippingStatus);
		commerceOrder.setOrderStatus(orderStatus);
		commerceOrder.setStatus(WorkflowConstants.STATUS_DRAFT);
		commerceOrder.setStatusByUserId(user.getUserId());
		commerceOrder.setStatusByUserName(user.getFullName());
		commerceOrder.setStatusDate(serviceContext.getModifiedDate(null));
		commerceOrder.setExpandoBridgeAttributes(serviceContext);

		commerceOrderPersistence.update(commerceOrder);

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), commerceOrder, serviceContext);
	}

	protected List<CommerceOrder> getCommerceOrders(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceOrder> commerceOrders = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long commerceOrderId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceOrder commerceOrder = fetchCommerceOrder(commerceOrderId);

			if (commerceOrder == null) {
				commerceOrders = null;

				Indexer<CommerceOrder> indexer = IndexerRegistryUtil.getIndexer(
					CommerceOrder.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceOrders != null) {
				commerceOrders.add(commerceOrder);
			}
		}

		return commerceOrders;
	}

	protected CommerceAddress getNewCommerceAddress(
			CommerceOrder commerceOrder, CommerceAddress commerceAddress,
			ServiceContext serviceContext)
		throws PortalException {

		if (commerceAddress == null) {
			return commerceAddress;
		}

		long groupId = serviceContext.getScopeGroupId();
		String className = commerceOrder.getClassName();
		long classPK = commerceOrder.getClassPK();

		List<CommerceAddress> commerceAddresses =
			commerceAddressLocalService.getCommerceAddresses(
				groupId, className, classPK);

		for (CommerceAddress newCommerceAddress : commerceAddresses) {
			if (commerceAddress.isSameAddress(newCommerceAddress)) {
				return newCommerceAddress;
			}
		}

		return commerceAddressLocalService.copyCommerceAddress(
			commerceAddress.getCommerceAddressId(), className, classPK,
			serviceContext);
	}

	protected boolean hasWorkflowDefinition(long siteGroupId, long typePK)
		throws PortalException {

		Group group = groupLocalService.getGroup(siteGroupId);

		return workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
			group.getCompanyId(), group.getGroupId(),
			CommerceOrder.class.getName(), 0, typePK);
	}

	protected CommerceOrder startWorkflowInstance(
			long userId, CommerceOrder commerceOrder,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceOrder.getCompanyId(), commerceOrder.getGroupId(), userId,
			CommerceOrder.class.getName(), commerceOrder.getCommerceOrderId(),
			commerceOrder, serviceContext, workflowContext);
	}

	protected CommerceOrder updateAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber,
			Function<CommerceOrder, Long> commerceAddressIdGetter,
			BiConsumer<CommerceOrder, Long> commerceAddressIdSetter,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		CommerceAddress commerceAddress = null;

		long commerceAddressId = commerceAddressIdGetter.apply(commerceOrder);

		if (commerceAddressId > 0) {
			commerceAddress = commerceAddressLocalService.updateCommerceAddress(
				commerceAddressId, name, description, street1, street2, street3,
				city, zip, commerceRegionId, commerceCountryId, phoneNumber,
				false, false, serviceContext);
		}
		else {
			commerceAddress = commerceAddressLocalService.addCommerceAddress(
				commerceOrder.getModelClassName(),
				commerceOrder.getCommerceOrderId(), name, description, street1,
				street2, street3, city, zip, commerceRegionId,
				commerceCountryId, phoneNumber, false, false, serviceContext);
		}

		commerceAddressIdSetter.accept(
			commerceOrder, commerceAddress.getCommerceAddressId());

		return commerceOrderPersistence.update(commerceOrder);
	}

	protected void validate(CommerceOrder commerceOrder)
		throws PortalException {

		CommercePaymentMethod commercePaymentMethod = null;

		long commercePaymentMethodId =
			commerceOrder.getCommercePaymentMethodId();

		if (commercePaymentMethodId > 0) {
			commercePaymentMethod =
				commercePaymentMethodLocalService.getCommercePaymentMethod(
					commercePaymentMethodId);

			if (!commercePaymentMethod.isActive()) {
				commercePaymentMethod = null;
			}
			else if (!commerceOrder.isB2B() &&
					 (commerceOrder.getBillingAddressId() <= 0)) {

				throw new CommerceOrderBillingAddressException();
			}
		}

		if ((commercePaymentMethod == null) &&
			(commercePaymentMethodLocalService.getCommercePaymentMethodsCount(
				commerceOrder.getGroupId(), true) > 0)) {

			throw new CommerceOrderPaymentMethodException();
		}

		CommerceShippingMethod commerceShippingMethod = null;

		long commerceShippingMethodId =
			commerceOrder.getCommerceShippingMethodId();

		if (commerceShippingMethodId > 0) {
			commerceShippingMethod =
				commerceShippingMethodLocalService.getCommerceShippingMethod(
					commerceShippingMethodId);

			if (!commerceShippingMethod.isActive()) {
				commerceShippingMethod = null;
			}
			else if (commerceOrder.getShippingAddressId() <= 0) {
				throw new CommerceOrderShippingAddressException();
			}
		}

		if ((commerceShippingMethod == null) &&
			(commerceShippingMethodLocalService.getCommerceShippingMethodsCount(
				commerceOrder.getGroupId(), true) > 0) &&
			_commerceShippingHelper.isShippable(commerceOrder)) {

			throw new CommerceOrderShippingMethodException();
		}
	}

	protected void validatePurchaseOrderNumber(String purchaseOrderNumber)
		throws PortalException {

		if (Validator.isNull(purchaseOrderNumber)) {
			throw new CommerceOrderPurchaseOrderNumberException();
		}
	}

	@ServiceReference(type = CommerceOrganizationLocalService.class)
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@ServiceReference(type = CommerceShippingHelper.class)
	private CommerceShippingHelper _commerceShippingHelper;

	@ServiceReference(type = DDMFormValuesHelper.class)
	private DDMFormValuesHelper _ddmFormValuesHelper;

	@ServiceReference(type = Portal.class)
	private Portal _portal;

}