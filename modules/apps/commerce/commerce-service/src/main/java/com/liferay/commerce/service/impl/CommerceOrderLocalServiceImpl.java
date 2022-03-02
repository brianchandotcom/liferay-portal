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

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.configuration.CommerceOrderConfiguration;
import com.liferay.commerce.configuration.CommerceOrderFieldsConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.discount.exception.CommerceDiscountCouponCodeException;
import com.liferay.commerce.discount.exception.CommerceDiscountLimitationTimesException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountLocalService;
import com.liferay.commerce.discount.service.CommerceDiscountUsageEntryLocalService;
import com.liferay.commerce.discount.validator.helper.CommerceDiscountValidatorHelper;
import com.liferay.commerce.exception.CommerceOrderAccountLimitException;
import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderDateException;
import com.liferay.commerce.exception.CommerceOrderPaymentMethodException;
import com.liferay.commerce.exception.CommerceOrderPurchaseOrderNumberException;
import com.liferay.commerce.exception.CommerceOrderRequestedDeliveryDateException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingMethodException;
import com.liferay.commerce.exception.CommerceOrderStatusException;
import com.liferay.commerce.exception.CommercePaymentEngineException;
import com.liferay.commerce.exception.GuestCartMaxAllowedException;
import com.liferay.commerce.internal.order.comparator.CommerceOrderModifiedDateComparator;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.price.CommerceOrderPriceCalculationFactory;
import com.liferay.commerce.product.util.JsonHelper;
import com.liferay.commerce.search.facet.NegatableMultiValueFacet;
import com.liferay.commerce.service.base.CommerceOrderLocalServiceBaseImpl;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CommerceOrderLocalServiceImpl
	extends CommerceOrderLocalServiceBaseImpl {

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CommerceOrder addCommerceOrder(
			long userId, long groupId, long commerceAccountId,
			long commerceCurrencyId)
		throws PortalException {

		return commerceOrderLocalService.addCommerceOrder(
			userId, groupId, commerceAccountId, commerceCurrencyId, 0);
	}

	@Override
	public CommerceOrder addCommerceOrder(
			long userId, long groupId, long commerceAccountId,
			long commerceCurrencyId, long commerceOrderTypeId)
		throws PortalException {

		return commerceOrderLocalService.addCommerceOrder(
			userId, groupId, commerceAccountId, commerceCurrencyId,
			commerceOrderTypeId, 0, 0, null, 0, null, null, BigDecimal.ZERO,
			BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
			BigDecimal.ZERO, BigDecimal.ZERO,
			CommerceOrderConstants.PAYMENT_STATUS_PENDING, 0, 0, 0, 0, 0,
			CommerceOrderConstants.ORDER_STATUS_OPEN, new ServiceContext());
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder addCommerceOrder(
			long userId, long groupId, long commerceAccountId,
			long commerceCurrencyId, long commerceOrderTypeId,
			long billingAddressId, long shippingAddressId,
			String commercePaymentMethodKey, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			BigDecimal subtotal, BigDecimal shippingAmount,
			BigDecimal taxAmount, BigDecimal total,
			BigDecimal subtotalWithTaxAmount, BigDecimal shippingWithTaxAmount,
			BigDecimal totalWithTaxAmount, int paymentStatus,
			int orderDateMonth, int orderDateDay, int orderDateYear,
			int orderDateHour, int orderDateMinute, int orderStatus,
			ServiceContext serviceContext)
		throws PortalException {

		// Check guest user

		if (userId == 0) {
			Group group = _groupLocalService.getGroup(groupId);

			User defaultUser = _userLocalService.getDefaultUser(
				group.getCompanyId());

			userId = defaultUser.getUserId();
		}

		serviceContext.setUserId(userId);

		User user = userLocalService.getUser(userId);

		// Check approval workflow

		if (hasWorkflowDefinition(
				groupId, CommerceOrderConstants.TYPE_PK_APPROVAL)) {

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		serviceContext.setScopeGroupId(groupId);

		// Commerce order

		validateAccountOrdersLimit(groupId, commerceAccountId);
		validateGuestOrders();

		if (commerceCurrencyId <= 0) {
			CommerceCurrency commerceCurrency =
				_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
					serviceContext.getCompanyId());

			if (commerceCurrency != null) {
				commerceCurrencyId = commerceCurrency.getCommerceCurrencyId();
			}
		}

		long commerceOrderId = counterLocalService.increment();

		CommerceOrder commerceOrder = commerceOrderPersistence.create(
			commerceOrderId);

		commerceOrder.setGroupId(groupId);
		commerceOrder.setCompanyId(user.getCompanyId());
		commerceOrder.setUserId(userId);
		commerceOrder.setUserName(user.getFullName());
		commerceOrder.setCommerceAccountId(commerceAccountId);
		commerceOrder.setCommerceCurrencyId(commerceCurrencyId);
		commerceOrder.setCommerceOrderTypeId(commerceOrderTypeId);
		commerceOrder.setBillingAddressId(billingAddressId);
		commerceOrder.setShippingAddressId(shippingAddressId);
		commerceOrder.setCommercePaymentMethodKey(commercePaymentMethodKey);
		commerceOrder.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceOrder.setShippingOptionName(shippingOptionName);
		commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);

		_setCommerceOrderPrices(
			commerceOrder, subtotal, shippingAmount, taxAmount, total,
			subtotalWithTaxAmount, shippingWithTaxAmount, totalWithTaxAmount);

		_setCommerceOrderShippingDiscountValue(commerceOrder, null, true);
		_setCommerceOrderShippingDiscountValue(commerceOrder, null, false);
		_setCommerceOrderSubtotalDiscountValue(commerceOrder, null, true);
		_setCommerceOrderSubtotalDiscountValue(commerceOrder, null, false);
		_setCommerceOrderTotalDiscountValue(commerceOrder, null, true);
		_setCommerceOrderTotalDiscountValue(commerceOrder, null, false);

		commerceOrder.setPaymentStatus(paymentStatus);

		Date orderDate = PortalUtil.getDate(
			orderDateMonth, orderDateDay, orderDateYear, orderDateHour,
			orderDateMinute, user.getTimeZone(), null);

		if (orderDate != null) {
			commerceOrder.setOrderDate(orderDate);
		}
		else {
			commerceOrder.setOrderDate(new Date());
		}

		commerceOrder.setOrderStatus(orderStatus);
		commerceOrder.setManuallyAdjusted(false);
		commerceOrder.setStatus(WorkflowConstants.STATUS_DRAFT);
		commerceOrder.setStatusByUserId(user.getUserId());
		commerceOrder.setStatusByUserName(user.getFullName());
		commerceOrder.setStatusDate(serviceContext.getModifiedDate(null));
		commerceOrder.setExpandoBridgeAttributes(serviceContext);

		commerceOrder = commerceOrderPersistence.update(commerceOrder);

		// Workflow

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceOrder.getCompanyId(), commerceOrder.getScopeGroupId(),
			userId, CommerceOrder.class.getName(),
			commerceOrder.getCommerceOrderId(), commerceOrder, serviceContext,
			new HashMap<>());
	}

	@Override
	public CommerceOrder addOrUpdateCommerceOrder(
			String externalReferenceCode, long userId, long groupId,
			long commerceAccountId, long commerceCurrencyId,
			long commerceOrderTypeId, long billingAddressId,
			long shippingAddressId, String commercePaymentMethodKey,
			long commerceShippingMethodId, String shippingOptionName,
			String purchaseOrderNumber, BigDecimal subtotal,
			BigDecimal shippingAmount, BigDecimal taxAmount, BigDecimal total,
			BigDecimal subtotalWithTaxAmount, BigDecimal shippingWithTaxAmount,
			BigDecimal totalWithTaxAmount, int paymentStatus,
			int orderDateMonth, int orderDateDay, int orderDateYear,
			int orderDateHour, int orderDateMinute, int orderStatus,
			String advanceStatus, CommerceContext commerceContext,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		// Update

		CommerceOrder commerceOrder = null;

		if (Validator.isNotNull(externalReferenceCode)) {
			commerceOrder = commerceOrderPersistence.fetchByC_ERC(
				serviceContext.getCompanyId(), externalReferenceCode);
		}

		if (commerceOrder != null) {
			commerceOrderLocalService.updateCommerceOrder(
				externalReferenceCode, commerceOrder.getCommerceOrderId(),
				billingAddressId, shippingAddressId, commercePaymentMethodKey,
				commerceShippingMethodId, shippingOptionName,
				purchaseOrderNumber, subtotal, shippingAmount, taxAmount, total,
				subtotalWithTaxAmount, shippingWithTaxAmount,
				totalWithTaxAmount, advanceStatus, commerceContext);

			commerceOrderLocalService.updateOrderDate(
				commerceOrder.getCommerceOrderId(), orderDateMonth,
				orderDateDay, orderDateYear, orderDateHour, orderDateMinute,
				serviceContext);

			commerceOrderLocalService.updatePaymentStatus(
				userId, commerceOrder.getCommerceOrderId(), paymentStatus);

			return commerceOrderLocalService.updateOrderStatus(
				commerceOrder.getCommerceOrderId(), paymentStatus);
		}

		// Add

		commerceOrder = commerceOrderLocalService.addCommerceOrder(
			userId, groupId, commerceAccountId, commerceCurrencyId,
			commerceOrderTypeId, billingAddressId, shippingAddressId,
			commercePaymentMethodKey, commerceShippingMethodId,
			shippingOptionName, purchaseOrderNumber, subtotal, shippingAmount,
			taxAmount, total, subtotalWithTaxAmount, shippingWithTaxAmount,
			totalWithTaxAmount, paymentStatus, orderDateMonth, orderDateDay,
			orderDateYear, orderDateHour, orderDateMinute, orderStatus,
			serviceContext);

		commerceOrder.setExternalReferenceCode(externalReferenceCode);

		return commerceOrderPersistence.update(commerceOrder);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CommerceOrder addOrUpdateCommerceOrder(
			String externalReferenceCode, long userId, long groupId,
			long commerceAccountId, long commerceCurrencyId,
			long billingAddressId, long shippingAddressId,
			String commercePaymentMethodKey, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			BigDecimal subtotal, BigDecimal shippingAmount,
			BigDecimal taxAmount, BigDecimal total,
			BigDecimal subtotalWithTaxAmount, BigDecimal shippingWithTaxAmount,
			BigDecimal totalWithTaxAmount, int paymentStatus,
			int orderDateMonth, int orderDateDay, int orderDateYear,
			int orderDateHour, int orderDateMinute, int orderStatus,
			String advanceStatus, CommerceContext commerceContext,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceOrderLocalService.addOrUpdateCommerceOrder(
			externalReferenceCode, userId, groupId, commerceAccountId,
			commerceCurrencyId, 0, billingAddressId, shippingAddressId,
			commercePaymentMethodKey, commerceShippingMethodId,
			shippingOptionName, purchaseOrderNumber, subtotal, shippingAmount,
			taxAmount, total, subtotalWithTaxAmount, shippingWithTaxAmount,
			totalWithTaxAmount, paymentStatus, orderDateMonth, orderDateDay,
			orderDateYear, orderDateHour, orderDateMinute, orderStatus,
			advanceStatus, commerceContext, serviceContext);
	}

	@Override
	public CommerceOrder applyCouponCode(
			long commerceOrderId, String couponCode,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		boolean hasDiscounts = false;

		int count =
			_commerceDiscountLocalService.getActiveCommerceDiscountsCount(
				commerceOrder.getCompanyId(), couponCode, true);

		if (count == 0) {
			hasDiscounts = true;
		}

		if (hasDiscounts && Validator.isNotNull(couponCode)) {
			throw new CommerceDiscountCouponCodeException();
		}

		if (Validator.isNotNull(couponCode)) {
			CommerceDiscount commerceDiscount =
				_commerceDiscountLocalService.getActiveCommerceDiscount(
					commerceOrder.getCompanyId(), couponCode, true);

			_commerceDiscountValidatorHelper.checkValid(
				commerceContext, commerceDiscount);

			if (!_commerceDiscountUsageEntryLocalService.
					validateDiscountLimitationUsage(
						CommerceUtil.getCommerceAccountId(commerceContext),
						commerceDiscount.getCommerceDiscountId())) {

				throw new CommerceDiscountLimitationTimesException();
			}
		}

		commerceOrder.setCouponCode(couponCode);

		commerceOrderPersistence.update(commerceOrder);

		return commerceOrderLocalService.recalculatePrice(
			commerceOrderId, commerceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceOrder deleteCommerceOrder(CommerceOrder commerceOrder)
		throws PortalException {

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

		// Commerce order

		commerceOrderPersistence.remove(commerceOrder);

		// Expando

		_expandoRowLocalService.deleteRows(commerceOrder.getCommerceOrderId());

		// Workflow

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			commerceOrder.getCompanyId(), commerceOrder.getScopeGroupId(),
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

	/**
	 * @deprecated As of Athanasius (7.3.x), delete by commerceAccountId
	 */
	@Deprecated
	@Override
	public void deleteCommerceOrders(long userId, Date date, int status) {
		commerceOrderPersistence.removeByU_LtC_O(userId, date, status);
	}

	@Override
	public void deleteCommerceOrdersByAccountId(
		long commerceAccountId, Date date, int status) {

		commerceOrderPersistence.removeByC_LtC_O(
			date, commerceAccountId, status);
	}

	@Override
	public CommerceOrder executeWorkflowTransition(
			long userId, long commerceOrderId, long workflowTaskId,
			String transitionName, String comment)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		long companyId = commerceOrder.getCompanyId();

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowTaskId);

		if (!workflowTask.isAssignedToSingleUser()) {
			workflowTask = _workflowTaskManager.assignWorkflowTaskToUser(
				companyId, userId, workflowTask.getWorkflowTaskId(), userId,
				comment, null, null);
		}

		_workflowTaskManager.completeWorkflowTask(
			companyId, userId, workflowTask.getWorkflowTaskId(), transitionName,
			comment, null, true);

		return commerceOrder;
	}

	@Override
	public CommerceOrder fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commerceOrderPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceOrder fetchCommerceOrder(
		long commerceAccountId, long groupId, int orderStatus) {

		return commerceOrderPersistence.fetchByG_C_O_First(
			groupId, commerceAccountId, orderStatus,
			new CommerceOrderModifiedDateComparator());
	}

	@Override
	public CommerceOrder fetchCommerceOrder(
		long commerceAccountId, long groupId, long userId, int orderStatus) {

		return commerceOrderFinder.fetchByG_U_C_O_S_First(
			groupId, userId, commerceAccountId, orderStatus);
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
		long groupId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {

		return commerceOrderPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
		long groupId, int[] orderStatuses) {

		return commerceOrderFinder.findByG_O(groupId, orderStatuses);
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
		long groupId, int[] orderStatuses, int start, int end) {

		return commerceOrderFinder.findByG_O(
			groupId, orderStatuses, start, end);
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
		long groupId, long commerceAccountId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {

		return commerceOrderPersistence.findByG_C(
			groupId, commerceAccountId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
			long companyId, long groupId, long[] commerceAccountIds,
			String keywords, int[] orderStatuses, boolean excludeOrderStatus,
			int start, int end)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, commerceAccountIds, keywords,
			excludeOrderStatus, orderStatuses, start, end);

		BaseModelSearchResult<CommerceOrder> baseModelSearchResult =
			commerceOrderLocalService.searchCommerceOrders(searchContext);

		return baseModelSearchResult.getBaseModels();
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
		long groupId, String commercePaymentMethodKey) {

		return commerceOrderPersistence.findByG_CP(
			groupId, commercePaymentMethodKey);
	}

	@Override
	public List<CommerceOrder> getCommerceOrdersByBillingAddress(
		long billingAddressId) {

		return commerceOrderPersistence.findByBillingAddressId(
			billingAddressId);
	}

	@Override
	public List<CommerceOrder> getCommerceOrdersByCommerceAccountId(
		long commerceAccountId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator) {

		return commerceOrderPersistence.findByCommerceAccountId(
			commerceAccountId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceOrder> getCommerceOrdersByShippingAddress(
		long shippingAddressId) {

		return commerceOrderPersistence.findByShippingAddressId(
			shippingAddressId);
	}

	@Override
	public int getCommerceOrdersCount(long groupId) {
		return commerceOrderPersistence.countByGroupId(groupId);
	}

	@Override
	public int getCommerceOrdersCount(long groupId, long commerceAccountId) {
		return commerceOrderPersistence.countByG_C(groupId, commerceAccountId);
	}

	@Override
	public long getCommerceOrdersCount(
			long companyId, long groupId, long[] commerceAccountIds,
			String keywords, int[] orderStatuses, boolean excludeOrderStatus)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, commerceAccountIds, keywords,
			excludeOrderStatus, orderStatuses, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		return commerceOrderLocalService.searchCommerceOrdersCount(
			searchContext);
	}

	@Override
	public int getCommerceOrdersCountByCommerceAccountId(
		long commerceAccountId) {

		return commerceOrderPersistence.countByCommerceAccountId(
			commerceAccountId);
	}

	@Override
	public List<CommerceOrder> getShippedCommerceOrdersByCommerceShipmentId(
		long commerceShipmentId, int start, int end) {

		return commerceOrderFinder.getShippedCommerceOrdersByCommerceShipmentId(
			commerceShipmentId, start, end);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public List<CommerceOrder> getUserCommerceOrders(
		long groupId, long userId, long commerceAccountId, Integer orderStatus,
		boolean excludeOrderStatus, String keywords, int start, int end) {

		try {
			Group group = _groupLocalService.getGroup(groupId);

			return commerceOrderLocalService.getCommerceOrders(
				group.getCompanyId(), groupId, new long[] {commerceAccountId},
				keywords, new int[] {CommerceOrderConstants.ORDER_STATUS_OPEN},
				false, start, end);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return Collections.emptyList();
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public int getUserCommerceOrdersCount(
		long groupId, long userId, long commerceAccountId, Integer orderStatus,
		boolean excludeOrderStatus, String keywords) {

		try {
			Group group = _groupLocalService.getGroup(groupId);

			return (int)commerceOrderLocalService.getCommerceOrdersCount(
				group.getCompanyId(), groupId, new long[] {commerceAccountId},
				keywords, new int[] {CommerceOrderConstants.ORDER_STATUS_OPEN},
				false);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return 0;
	}

	@Override
	public void mergeGuestCommerceOrder(
			long guestCommerceOrderId, long userCommerceOrderId,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws PortalException {

		List<CommerceOrderItem> guestCommerceOrderItems =
			commerceOrderItemPersistence.findByCommerceOrderId(
				guestCommerceOrderId);

		for (CommerceOrderItem guestCommerceOrderItem :
				guestCommerceOrderItems) {

			List<CommerceOrderItem> userCommerceOrderItems =
				commerceOrderItemPersistence.findByC_CPI(
					userCommerceOrderId,
					guestCommerceOrderItem.getCPInstanceId());

			if (!userCommerceOrderItems.isEmpty()) {
				boolean found = false;

				for (CommerceOrderItem userCommerceOrderItem :
						userCommerceOrderItems) {

					if (Objects.equals(
							guestCommerceOrderItem.getJson(),
							userCommerceOrderItem.getJson())) {

						found = true;

						break;
					}
				}

				if (found) {
					continue;
				}
			}

			commerceOrderItemLocalService.addCommerceOrderItem(
				userCommerceOrderId, guestCommerceOrderItem.getCPInstanceId(),
				guestCommerceOrderItem.getJson(),
				guestCommerceOrderItem.getQuantity(),
				guestCommerceOrderItem.getShippedQuantity(), commerceContext,
				serviceContext);
		}

		commerceOrderLocalService.deleteCommerceOrder(guestCommerceOrderId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder recalculatePrice(
			long commerceOrderId, CommerceContext commerceContext)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		if (commerceOrder.getOrderStatus() !=
				CommerceOrderConstants.ORDER_STATUS_OPEN) {

			return commerceOrder;
		}

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			commerceOrderItemLocalService.updateCommerceOrderItemPrice(
				commerceOrderItem.getCommerceOrderItemId(), commerceContext);
		}

		CommerceOrderPriceCalculation commerceOrderPriceCalculation =
			_commerceOrderPriceCalculationFactory.
				getCommerceOrderPriceCalculation();

		CommerceOrderPrice commerceOrderPrice =
			commerceOrderPriceCalculation.getCommerceOrderPrice(
				commerceOrder, false, commerceContext);

		CommerceMoney subtotalCommerceMoney = commerceOrderPrice.getSubtotal();
		CommerceMoney shippingValueCommerceMoney =
			commerceOrderPrice.getShippingValue();
		CommerceMoney taxValueCommerceMoney = commerceOrderPrice.getTaxValue();
		CommerceMoney totalCommerceMoney = commerceOrderPrice.getTotal();
		CommerceMoney subtotalWithTaxAmountCommerceMoney =
			commerceOrderPrice.getSubtotalWithTaxAmount();
		CommerceMoney shippingValueWithTaxAmountCommerceMoney =
			commerceOrderPrice.getShippingValueWithTaxAmount();
		CommerceMoney totalWithTaxAmountCommerceMoney =
			commerceOrderPrice.getTotalWithTaxAmount();

		commerceOrder.setSubtotal(subtotalCommerceMoney.getPrice());
		commerceOrder.setShippingAmount(shippingValueCommerceMoney.getPrice());
		commerceOrder.setTaxAmount(taxValueCommerceMoney.getPrice());
		commerceOrder.setTotal(totalCommerceMoney.getPrice());

		if (subtotalWithTaxAmountCommerceMoney != null) {
			commerceOrder.setSubtotalWithTaxAmount(
				subtotalWithTaxAmountCommerceMoney.getPrice());
		}

		if (shippingValueWithTaxAmountCommerceMoney != null) {
			commerceOrder.setShippingWithTaxAmount(
				shippingValueWithTaxAmountCommerceMoney.getPrice());
		}

		if (totalWithTaxAmountCommerceMoney != null) {
			commerceOrder.setTotalWithTaxAmount(
				totalWithTaxAmountCommerceMoney.getPrice());
		}

		if (!commerceOrder.isManuallyAdjusted()) {
			_setCommerceOrderSubtotalDiscountValue(
				commerceOrder, commerceOrderPrice.getSubtotalDiscountValue(),
				false);
			_setCommerceOrderShippingDiscountValue(
				commerceOrder, commerceOrderPrice.getShippingDiscountValue(),
				false);
			_setCommerceOrderTotalDiscountValue(
				commerceOrder, commerceOrderPrice.getTotalDiscountValue(),
				false);
			_setCommerceOrderSubtotalDiscountValue(
				commerceOrder,
				commerceOrderPrice.getSubtotalDiscountValueWithTaxAmount(),
				true);
			_setCommerceOrderShippingDiscountValue(
				commerceOrder,
				commerceOrderPrice.getShippingDiscountValueWithTaxAmount(),
				true);
			_setCommerceOrderTotalDiscountValue(
				commerceOrder,
				commerceOrderPrice.getTotalDiscountValueWithTaxAmount(), true);
		}

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Override
	public CommerceOrder reorderCommerceOrder(
			long userId, long commerceOrderId, CommerceContext commerceContext)
		throws PortalException {

		// Commerce order

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(commerceOrder.getGroupId());
		serviceContext.setUserId(userId);

		long billingAddressId = 0;
		long shippingAddressId = 0;

		CommerceAddress billingAddress = getNewCommerceAddress(
			commerceOrder, commerceOrder.getBillingAddress(), serviceContext);

		CommerceAddress shippingAddress = billingAddress;

		if (commerceOrder.getBillingAddressId() !=
				commerceOrder.getShippingAddressId()) {

			shippingAddress = getNewCommerceAddress(
				commerceOrder, commerceOrder.getShippingAddress(),
				serviceContext);
		}

		if (billingAddress != null) {
			billingAddressId = billingAddress.getCommerceAddressId();
		}

		if (shippingAddress != null) {
			shippingAddressId = shippingAddress.getCommerceAddressId();
		}

		CommerceOrder newCommerceOrder =
			commerceOrderLocalService.addCommerceOrder(
				userId, commerceOrder.getGroupId(),
				commerceOrder.getCommerceAccountId(),
				commerceOrder.getCommerceCurrencyId(),
				commerceOrder.getCommerceOrderTypeId(), billingAddressId,
				shippingAddressId, commerceOrder.getCommercePaymentMethodKey(),
				commerceOrder.getCommerceShippingMethodId(),
				commerceOrder.getShippingOptionName(), StringPool.BLANK,
				commerceOrder.getSubtotal(), commerceOrder.getShippingAmount(),
				commerceOrder.getTaxAmount(), commerceOrder.getTotal(),
				commerceOrder.getSubtotalWithTaxAmount(),
				commerceOrder.getShippingWithTaxAmount(),
				commerceOrder.getTotalWithTaxAmount(),
				CommerceOrderConstants.PAYMENT_STATUS_PENDING, 0, 0, 0, 0, 0,
				CommerceOrderConstants.ORDER_STATUS_OPEN, serviceContext);

		// Commerce order items

		List<CommerceOrderItem> commerceOrderItems =
			commerceOrderItemLocalService.getCommerceOrderItems(
				commerceOrder.getCommerceOrderId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (CommerceOrderItem commerceOrderItem : commerceOrderItems) {
			if (commerceOrderItem.getParentCommerceOrderItemId() != 0) {
				continue;
			}

			commerceOrderItemLocalService.addCommerceOrderItem(
				newCommerceOrder.getCommerceOrderId(),
				commerceOrderItem.getCPInstanceId(),
				commerceOrderItem.getJson(), commerceOrderItem.getQuantity(), 0,
				commerceContext, serviceContext);
		}

		return newCommerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder resetCommerceOrderShipping(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		commerceOrder.setCommerceShippingMethodId(0);
		commerceOrder.setShippingOptionName(null);
		commerceOrder.setShippingAmount(BigDecimal.ZERO);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Override
	public CommerceOrder resetTermsAndConditions(
			long commerceOrderId, boolean resetDeliveryCommerceTerm,
			boolean resetPaymentCommerceTermEntry)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		if (resetDeliveryCommerceTerm) {
			commerceOrder.setDeliveryCommerceTermEntryId(0);
			commerceOrder.setDeliveryCommerceTermEntryDescription(null);
			commerceOrder.setDeliveryCommerceTermEntryName(null);
		}

		if (resetPaymentCommerceTermEntry) {
			commerceOrder.setPaymentCommerceTermEntryId(0);
			commerceOrder.setPaymentCommerceTermEntryDescription(null);
			commerceOrder.setPaymentCommerceTermEntryName(null);
		}

		return commerceOrderPersistence.update(commerceOrder);
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

	@Override
	public long searchCommerceOrdersCount(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceOrder> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CommerceOrder.class.getName());

		return indexer.searchCount(searchContext);
	}

	@Override
	public CommerceOrder updateAccount(
			long commerceOrderId, long userId, long commerceAccountId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		User user = userLocalService.getUser(userId);

		commerceOrder.setUserId(user.getUserId());
		commerceOrder.setUserName(user.getFullName());

		commerceOrder.setCommerceAccountId(commerceAccountId);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateBillingAddress(
			long commerceOrderId, long billingAddressId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setBillingAddressId(billingAddressId);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateBillingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			ServiceContext serviceContext)
		throws PortalException {

		return updateAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, regionId, countryId, phoneNumber,
			CommerceOrder::getBillingAddressId,
			CommerceOrder::setBillingAddressId, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceOrder(
			String externalReferenceCode, long commerceOrderId,
			long billingAddressId, long shippingAddressId,
			String commercePaymentMethodKey, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			BigDecimal subtotal, BigDecimal shippingAmount,
			BigDecimal taxAmount, BigDecimal total,
			BigDecimal subtotalWithTaxAmount, BigDecimal shippingWithTaxAmount,
			BigDecimal totalWithTaxAmount, BigDecimal totalDiscountAmount,
			String advanceStatus, CommerceContext commerceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setExternalReferenceCode(externalReferenceCode);
		commerceOrder.setBillingAddressId(billingAddressId);
		commerceOrder.setShippingAddressId(shippingAddressId);
		commerceOrder.setCommercePaymentMethodKey(commercePaymentMethodKey);
		commerceOrder.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceOrder.setShippingOptionName(shippingOptionName);
		commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);
		commerceOrder.setTotalDiscountAmount(totalDiscountAmount);

		_setCommerceOrderPrices(
			commerceOrder, subtotal, shippingAmount, taxAmount, total,
			subtotalWithTaxAmount, shippingWithTaxAmount, totalWithTaxAmount);

		if (commerceContext != null) {
			CommerceOrderPriceCalculation commerceOrderPriceCalculation =
				_commerceOrderPriceCalculationFactory.
					getCommerceOrderPriceCalculation();

			CommerceOrderPrice commerceOrderPrice =
				commerceOrderPriceCalculation.getCommerceOrderPrice(
					commerceOrder, false, commerceContext);

			CommerceDiscountValue shippingDiscountValue =
				commerceOrderPrice.getShippingDiscountValue();

			if (shippingDiscountValue != null) {
				CommerceMoney shippingDiscountAmountCommerceMoney =
					shippingDiscountValue.getDiscountAmount();

				commerceOrder.setShippingDiscountAmount(
					shippingDiscountAmountCommerceMoney.getPrice());
			}

			CommerceDiscountValue shippingWithTaxAmountCommerceDiscountValue =
				commerceOrderPrice.getShippingDiscountValueWithTaxAmount();

			if (shippingWithTaxAmountCommerceDiscountValue != null) {
				CommerceMoney shippingDiscountWithTaxAmountCommerceMoney =
					shippingWithTaxAmountCommerceDiscountValue.
						getDiscountAmount();

				commerceOrder.setShippingDiscountWithTaxAmount(
					shippingDiscountWithTaxAmountCommerceMoney.getPrice());
			}
		}

		commerceOrder.setAdvanceStatus(advanceStatus);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceOrder(
			String externalReferenceCode, long commerceOrderId,
			long billingAddressId, long shippingAddressId,
			String commercePaymentMethodKey, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			BigDecimal subtotal, BigDecimal shippingAmount,
			BigDecimal taxAmount, BigDecimal total,
			BigDecimal subtotalWithTaxAmount, BigDecimal shippingWithTaxAmount,
			BigDecimal totalWithTaxAmount, String advanceStatus,
			CommerceContext commerceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setExternalReferenceCode(externalReferenceCode);
		commerceOrder.setBillingAddressId(billingAddressId);
		commerceOrder.setShippingAddressId(shippingAddressId);
		commerceOrder.setCommercePaymentMethodKey(commercePaymentMethodKey);
		commerceOrder.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceOrder.setShippingOptionName(shippingOptionName);
		commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);

		_setCommerceOrderPrices(
			commerceOrder, subtotal, shippingAmount, taxAmount, total,
			subtotalWithTaxAmount, shippingWithTaxAmount, totalWithTaxAmount);

		if (commerceContext != null) {
			CommerceOrderPriceCalculation commerceOrderPriceCalculation =
				_commerceOrderPriceCalculationFactory.
					getCommerceOrderPriceCalculation();

			CommerceOrderPrice commerceOrderPrice =
				commerceOrderPriceCalculation.getCommerceOrderPrice(
					commerceOrder, false, commerceContext);

			CommerceDiscountValue shippingDiscountValue =
				commerceOrderPrice.getShippingDiscountValue();

			if (shippingDiscountValue != null) {
				CommerceMoney shippingDiscountAmountCommerceMoney =
					shippingDiscountValue.getDiscountAmount();

				commerceOrder.setShippingDiscountAmount(
					shippingDiscountAmountCommerceMoney.getPrice());
			}

			CommerceDiscountValue shippingWithTaxAmountCommerceDiscountValue =
				commerceOrderPrice.getShippingDiscountValueWithTaxAmount();

			if (shippingWithTaxAmountCommerceDiscountValue != null) {
				CommerceMoney shippingDiscountWithTaxAmountCommerceMoney =
					shippingWithTaxAmountCommerceDiscountValue.
						getDiscountAmount();

				commerceOrder.setShippingDiscountWithTaxAmount(
					shippingDiscountWithTaxAmountCommerceMoney.getPrice());
			}
		}

		commerceOrder.setAdvanceStatus(advanceStatus);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceOrder(
			String externalReferenceCode, long commerceOrderId,
			long billingAddressId, long shippingAddressId,
			String commercePaymentMethodKey, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			BigDecimal subtotal, BigDecimal shippingAmount, BigDecimal total,
			String advanceStatus, CommerceContext commerceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setExternalReferenceCode(externalReferenceCode);
		commerceOrder.setBillingAddressId(billingAddressId);
		commerceOrder.setShippingAddressId(shippingAddressId);
		commerceOrder.setCommercePaymentMethodKey(commercePaymentMethodKey);
		commerceOrder.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceOrder.setShippingOptionName(shippingOptionName);
		commerceOrder.setPurchaseOrderNumber(purchaseOrderNumber);

		_setCommerceOrderPrices(commerceOrder, subtotal, shippingAmount, total);

		if (commerceContext != null) {
			CommerceOrderPriceCalculation commerceOrderPriceCalculation =
				_commerceOrderPriceCalculationFactory.
					getCommerceOrderPriceCalculation();

			CommerceOrderPrice commerceOrderPrice =
				commerceOrderPriceCalculation.getCommerceOrderPrice(
					commerceOrder, false, commerceContext);

			CommerceDiscountValue shippingDiscountValue =
				commerceOrderPrice.getShippingDiscountValue();

			if (shippingDiscountValue != null) {
				CommerceMoney shippingDiscountAmountCommerceMoney =
					shippingDiscountValue.getDiscountAmount();

				commerceOrder.setShippingDiscountAmount(
					shippingDiscountAmountCommerceMoney.getPrice());
			}
		}

		commerceOrder.setAdvanceStatus(advanceStatus);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceOrderExternalReferenceCode(
			String externalReferenceCode, long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setExternalReferenceCode(externalReferenceCode);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceOrderPrices(
			long commerceOrderId, BigDecimal subtotal,
			BigDecimal subtotalDiscountAmount,
			BigDecimal subtotalDiscountPercentageLevel1,
			BigDecimal subtotalDiscountPercentageLevel2,
			BigDecimal subtotalDiscountPercentageLevel3,
			BigDecimal subtotalDiscountPercentageLevel4,
			BigDecimal shippingAmount, BigDecimal shippingDiscountAmount,
			BigDecimal shippingDiscountPercentageLevel1,
			BigDecimal shippingDiscountPercentageLevel2,
			BigDecimal shippingDiscountPercentageLevel3,
			BigDecimal shippingDiscountPercentageLevel4, BigDecimal taxAmount,
			BigDecimal total, BigDecimal totalDiscountAmount,
			BigDecimal totalDiscountPercentageLevel1,
			BigDecimal totalDiscountPercentageLevel2,
			BigDecimal totalDiscountPercentageLevel3,
			BigDecimal totalDiscountPercentageLevel4)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setLastPriceUpdateDate(new Date());
		commerceOrder.setSubtotal(subtotal);
		commerceOrder.setSubtotalDiscountAmount(subtotalDiscountAmount);
		commerceOrder.setSubtotalDiscountPercentageLevel1(
			subtotalDiscountPercentageLevel1);
		commerceOrder.setSubtotalDiscountPercentageLevel2(
			subtotalDiscountPercentageLevel2);
		commerceOrder.setSubtotalDiscountPercentageLevel3(
			subtotalDiscountPercentageLevel3);
		commerceOrder.setSubtotalDiscountPercentageLevel4(
			subtotalDiscountPercentageLevel4);
		commerceOrder.setShippingAmount(shippingAmount);
		commerceOrder.setShippingDiscountAmount(shippingDiscountAmount);
		commerceOrder.setShippingDiscountPercentageLevel1(
			shippingDiscountPercentageLevel1);
		commerceOrder.setShippingDiscountPercentageLevel2(
			shippingDiscountPercentageLevel2);
		commerceOrder.setShippingDiscountPercentageLevel3(
			shippingDiscountPercentageLevel3);
		commerceOrder.setShippingDiscountPercentageLevel4(
			shippingDiscountPercentageLevel4);
		commerceOrder.setTaxAmount(taxAmount);
		commerceOrder.setTotal(total);
		commerceOrder.setTotalDiscountAmount(totalDiscountAmount);
		commerceOrder.setTotalDiscountPercentageLevel1(
			totalDiscountPercentageLevel1);
		commerceOrder.setTotalDiscountPercentageLevel2(
			totalDiscountPercentageLevel2);
		commerceOrder.setTotalDiscountPercentageLevel3(
			totalDiscountPercentageLevel3);
		commerceOrder.setTotalDiscountPercentageLevel4(
			totalDiscountPercentageLevel4);
		commerceOrder.setManuallyAdjusted(true);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceOrderPrices(
			long commerceOrderId, BigDecimal subtotal,
			BigDecimal subtotalDiscountAmount,
			BigDecimal subtotalDiscountPercentageLevel1,
			BigDecimal subtotalDiscountPercentageLevel2,
			BigDecimal subtotalDiscountPercentageLevel3,
			BigDecimal subtotalDiscountPercentageLevel4,
			BigDecimal shippingAmount, BigDecimal shippingDiscountAmount,
			BigDecimal shippingDiscountPercentageLevel1,
			BigDecimal shippingDiscountPercentageLevel2,
			BigDecimal shippingDiscountPercentageLevel3,
			BigDecimal shippingDiscountPercentageLevel4, BigDecimal taxAmount,
			BigDecimal total, BigDecimal totalDiscountAmount,
			BigDecimal totalDiscountPercentageLevel1,
			BigDecimal totalDiscountPercentageLevel2,
			BigDecimal totalDiscountPercentageLevel3,
			BigDecimal totalDiscountPercentageLevel4,
			BigDecimal subtotalWithTaxAmount,
			BigDecimal subtotalDiscountWithTaxAmount,
			BigDecimal subtotalDiscountPercentageLevel1WithTaxAmount,
			BigDecimal subtotalDiscountPercentageLevel2WithTaxAmount,
			BigDecimal subtotalDiscountPercentageLevel3WithTaxAmount,
			BigDecimal subtotalDiscountPercentageLevel4WithTaxAmount,
			BigDecimal shippingWithTaxAmount,
			BigDecimal shippingDiscountWithTaxAmount,
			BigDecimal shippingDiscountPercentageLevel1WithTaxAmount,
			BigDecimal shippingDiscountPercentageLevel2WithTaxAmount,
			BigDecimal shippingDiscountPercentageLevel3WithTaxAmount,
			BigDecimal shippingDiscountPercentageLevel4WithTaxAmount,
			BigDecimal totalWithTaxAmount,
			BigDecimal totalDiscountWithTaxAmount,
			BigDecimal totalDiscountPercentageLevel1WithTaxAmount,
			BigDecimal totalDiscountPercentageLevel2WithTaxAmount,
			BigDecimal totalDiscountPercentageLevel3WithTaxAmount,
			BigDecimal totalDiscountPercentageLevel4WithTaxAmount)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setLastPriceUpdateDate(new Date());
		commerceOrder.setSubtotal(subtotal);
		commerceOrder.setSubtotalDiscountAmount(subtotalDiscountAmount);
		commerceOrder.setSubtotalDiscountPercentageLevel1(
			subtotalDiscountPercentageLevel1);
		commerceOrder.setSubtotalDiscountPercentageLevel2(
			subtotalDiscountPercentageLevel2);
		commerceOrder.setSubtotalDiscountPercentageLevel3(
			subtotalDiscountPercentageLevel3);
		commerceOrder.setSubtotalDiscountPercentageLevel4(
			subtotalDiscountPercentageLevel4);
		commerceOrder.setShippingAmount(shippingAmount);
		commerceOrder.setShippingDiscountAmount(shippingDiscountAmount);
		commerceOrder.setShippingDiscountPercentageLevel1(
			shippingDiscountPercentageLevel1);
		commerceOrder.setShippingDiscountPercentageLevel2(
			shippingDiscountPercentageLevel2);
		commerceOrder.setShippingDiscountPercentageLevel3(
			shippingDiscountPercentageLevel3);
		commerceOrder.setShippingDiscountPercentageLevel4(
			shippingDiscountPercentageLevel4);
		commerceOrder.setTaxAmount(taxAmount);
		commerceOrder.setTotal(total);
		commerceOrder.setTotalDiscountAmount(totalDiscountAmount);
		commerceOrder.setTotalDiscountPercentageLevel1(
			totalDiscountPercentageLevel1);
		commerceOrder.setTotalDiscountPercentageLevel2(
			totalDiscountPercentageLevel2);
		commerceOrder.setTotalDiscountPercentageLevel3(
			totalDiscountPercentageLevel3);
		commerceOrder.setTotalDiscountPercentageLevel4(
			totalDiscountPercentageLevel4);
		commerceOrder.setSubtotalWithTaxAmount(subtotalWithTaxAmount);
		commerceOrder.setSubtotalDiscountWithTaxAmount(
			subtotalDiscountWithTaxAmount);
		commerceOrder.setSubtotalDiscountPercentageLevel1WithTaxAmount(
			subtotalDiscountPercentageLevel1WithTaxAmount);
		commerceOrder.setSubtotalDiscountPercentageLevel2WithTaxAmount(
			subtotalDiscountPercentageLevel2WithTaxAmount);
		commerceOrder.setSubtotalDiscountPercentageLevel3WithTaxAmount(
			subtotalDiscountPercentageLevel3WithTaxAmount);
		commerceOrder.setSubtotalDiscountPercentageLevel4WithTaxAmount(
			subtotalDiscountPercentageLevel4WithTaxAmount);
		commerceOrder.setShippingWithTaxAmount(shippingWithTaxAmount);
		commerceOrder.setShippingDiscountWithTaxAmount(
			shippingDiscountWithTaxAmount);
		commerceOrder.setShippingDiscountPercentageLevel1WithTaxAmount(
			shippingDiscountPercentageLevel1WithTaxAmount);
		commerceOrder.setShippingDiscountPercentageLevel2WithTaxAmount(
			shippingDiscountPercentageLevel2WithTaxAmount);
		commerceOrder.setShippingDiscountPercentageLevel3WithTaxAmount(
			shippingDiscountPercentageLevel3WithTaxAmount);
		commerceOrder.setShippingDiscountPercentageLevel4WithTaxAmount(
			shippingDiscountPercentageLevel4WithTaxAmount);
		commerceOrder.setTotalWithTaxAmount(totalWithTaxAmount);
		commerceOrder.setTotalDiscountWithTaxAmount(totalDiscountWithTaxAmount);
		commerceOrder.setTotalDiscountPercentageLevel1WithTaxAmount(
			totalDiscountPercentageLevel1WithTaxAmount);
		commerceOrder.setTotalDiscountPercentageLevel2WithTaxAmount(
			totalDiscountPercentageLevel2WithTaxAmount);
		commerceOrder.setTotalDiscountPercentageLevel3WithTaxAmount(
			totalDiscountPercentageLevel3WithTaxAmount);
		commerceOrder.setTotalDiscountPercentageLevel4WithTaxAmount(
			totalDiscountPercentageLevel4WithTaxAmount);
		commerceOrder.setManuallyAdjusted(true);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Override
	public CommerceOrder updateCommercePaymentMethodKey(
			long commerceOrderId, String commercePaymentMethodKey)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		commerceOrder.setCommercePaymentMethodKey(commercePaymentMethodKey);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceShippingMethod(
			long commerceOrderId, long commerceShippingMethodId,
			String commerceShippingOptionName, BigDecimal shippingAmount,
			CommerceContext commerceContext)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setCommerceShippingMethodId(commerceShippingMethodId);
		commerceOrder.setShippingOptionName(commerceShippingOptionName);
		commerceOrder.setShippingAmount(shippingAmount);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCommerceShippingMethod(
			long commerceOrderId, long commerceShippingMethodId,
			String commerceShippingOptionName, CommerceContext commerceContext,
			Locale locale)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		CommerceShippingMethod commerceShippingMethod =
			commerceShippingMethodLocalService.getCommerceShippingMethod(
				commerceShippingMethodId);

		commerceOrder.setCommerceShippingMethodId(
			commerceShippingMethod.getCommerceShippingMethodId());

		commerceOrder.setShippingOptionName(commerceShippingOptionName);

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		List<CommerceShippingOption> commerceShippingOptions =
			commerceShippingEngine.getCommerceShippingOptions(
				commerceContext, commerceOrder, locale);

		for (CommerceShippingOption commerceShippingOption :
				commerceShippingOptions) {

			if (Validator.isNotNull(commerceShippingOptionName) &&
				commerceShippingOptionName.equals(
					commerceShippingOption.getName())) {

				commerceOrder.setShippingAmount(
					commerceShippingOption.getAmount());

				break;
			}
		}

		commerceOrder = commerceOrderPersistence.update(commerceOrder);

		return commerceOrderLocalService.recalculatePrice(
			commerceOrder.getCommerceOrderId(), commerceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateCustomFields(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		commerceOrder.setExpandoBridgeAttributes(serviceContext);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateInfo(
			long commerceOrderId, String printedNote,
			int requestedDeliveryDateMonth, int requestedDeliveryDateDay,
			int requestedDeliveryDateYear, int requestedDeliveryDateHour,
			int requestedDeliveryDateMinute, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		Date requestedDeliveryDate = PortalUtil.getDate(
			requestedDeliveryDateMonth, requestedDeliveryDateDay,
			requestedDeliveryDateYear, requestedDeliveryDateHour,
			requestedDeliveryDateMinute, user.getTimeZone(),
			CommerceOrderRequestedDeliveryDateException.class);

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setPrintedNote(printedNote);
		commerceOrder.setRequestedDeliveryDate(requestedDeliveryDate);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateOrderDate(
			long commerceOrderId, int orderDateMonth, int orderDateDay,
			int orderDateYear, int orderDateHour, int orderDateMinute,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		Date orderDate = PortalUtil.getDate(
			orderDateMonth, orderDateDay, orderDateYear, orderDateHour,
			orderDateMinute, user.getTimeZone(),
			CommerceOrderDateException.class);

		commerceOrder.setOrderDate(orderDate);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateOrderStatus(
			long commerceOrderId, int orderStatus)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setOrderStatus(orderStatus);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updatePaymentStatus(
			long userId, long commerceOrderId, int paymentStatus)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		int previousPaymentStatus = commerceOrder.getPaymentStatus();

		commerceOrder.setPaymentStatus(paymentStatus);

		commerceOrder = commerceOrderPersistence.update(commerceOrder);

		// Messaging

		sendPaymentStatusMessage(commerceOrder, previousPaymentStatus);

		return commerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updatePaymentStatusAndTransactionId(
			long userId, long commerceOrderId, int paymentStatus,
			String transactionId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		int previousPaymentStatus = commerceOrder.getPaymentStatus();

		commerceOrder.setTransactionId(transactionId);
		commerceOrder.setPaymentStatus(paymentStatus);

		commerceOrder = commerceOrderPersistence.update(commerceOrder);

		// Messaging

		sendPaymentStatusMessage(commerceOrder, previousPaymentStatus);

		return commerceOrder;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updatePrintedNote(
			long commerceOrderId, String printedNote)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setPrintedNote(printedNote);

		return commerceOrderPersistence.update(commerceOrder);
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

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateShippingAddress(
			long commerceOrderId, long shippingAddressId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setShippingAddressId(shippingAddressId);

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateShippingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			ServiceContext serviceContext)
		throws PortalException {

		return updateAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, regionId, countryId, phoneNumber,
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

		if (userId == 0) {
			userId = serviceContext.getUserId();
		}

		User user = userLocalService.getUser(userId);
		Date date = new Date();

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setStatus(status);
		commerceOrder.setStatusByUserId(user.getUserId());
		commerceOrder.setStatusByUserName(user.getFullName());
		commerceOrder.setStatusDate(serviceContext.getModifiedDate(date));

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Override
	public CommerceOrder updateTermsAndConditions(
			long commerceOrderId, long deliveryCommerceTermEntryId,
			long paymentCommerceTermEntryId, String languageId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		CommerceTermEntry deliveryCommerceTermEntry =
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				deliveryCommerceTermEntryId);

		CommerceTermEntry paymentCommerceTermEntry =
			_commerceTermEntryLocalService.fetchCommerceTermEntry(
				paymentCommerceTermEntryId);

		if ((deliveryCommerceTermEntry == null) &&
			(paymentCommerceTermEntry == null)) {

			return commerceOrder;
		}

		if (deliveryCommerceTermEntry != null) {
			commerceOrder.setDeliveryCommerceTermEntryId(
				deliveryCommerceTermEntry.getCommerceTermEntryId());
			commerceOrder.setDeliveryCommerceTermEntryDescription(
				deliveryCommerceTermEntry.getDescription(languageId));
			commerceOrder.setDeliveryCommerceTermEntryName(
				deliveryCommerceTermEntry.getLabel(languageId));
		}

		if (paymentCommerceTermEntry != null) {
			commerceOrder.setPaymentCommerceTermEntryId(
				paymentCommerceTermEntry.getCommerceTermEntryId());
			commerceOrder.setPaymentCommerceTermEntryDescription(
				paymentCommerceTermEntry.getDescription(languageId));
			commerceOrder.setPaymentCommerceTermEntryName(
				paymentCommerceTermEntry.getLabel(languageId));
		}

		return commerceOrderPersistence.update(commerceOrder);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceOrder updateTransactionId(
			long commerceOrderId, String transactionId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		commerceOrder.setTransactionId(transactionId);

		return commerceOrderPersistence.update(commerceOrder);
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

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceOrder upsertCommerceOrder(
			String externalReferenceCode, long userId, long groupId,
			long commerceAccountId, long commerceCurrencyId,
			long billingAddressId, long shippingAddressId,
			String commercePaymentMethodKey, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			BigDecimal subtotal, BigDecimal shippingAmount, BigDecimal total,
			int paymentStatus, int orderStatus, String advanceStatus,
			CommerceContext commerceContext, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		// Update

		CommerceOrder commerceOrder = null;

		if (Validator.isNotNull(externalReferenceCode)) {
			commerceOrder = commerceOrderPersistence.fetchByC_ERC(
				serviceContext.getCompanyId(), externalReferenceCode);
		}

		if (commerceOrder != null) {
			commerceOrderLocalService.updateCommerceOrder(
				externalReferenceCode, commerceOrder.getCommerceOrderId(),
				billingAddressId, shippingAddressId, commercePaymentMethodKey,
				commerceShippingMethodId, shippingOptionName,
				purchaseOrderNumber, subtotal, shippingAmount, total,
				advanceStatus, commerceContext);

			commerceOrderLocalService.updatePaymentStatus(
				userId, commerceOrder.getCommerceOrderId(), paymentStatus);

			return commerceOrderLocalService.updateOrderStatus(
				commerceOrder.getCommerceOrderId(), paymentStatus);
		}

		// Add

		commerceOrder = commerceOrderLocalService.addCommerceOrder(
			userId, groupId, commerceAccountId, commerceCurrencyId, 0,
			billingAddressId, shippingAddressId, commercePaymentMethodKey,
			commerceShippingMethodId, shippingOptionName, purchaseOrderNumber,
			subtotal, shippingAmount, BigDecimal.ZERO, total, BigDecimal.ZERO,
			BigDecimal.ZERO, BigDecimal.ZERO, paymentStatus, 0, 0, 0, 0, 0,
			orderStatus, serviceContext);

		commerceOrder.setExternalReferenceCode(externalReferenceCode);

		return commerceOrderPersistence.update(commerceOrder);
	}

	protected SearchContext addFacetOrderStatus(
		boolean negated, int[] orderStatuses, SearchContext searchContext) {

		NegatableMultiValueFacet negatableMultiValueFacet =
			new NegatableMultiValueFacet(searchContext);

		negatableMultiValueFacet.setFieldName("orderStatus");

		searchContext.addFacet(negatableMultiValueFacet);

		negatableMultiValueFacet.setNegated(negated);

		searchContext.setAttribute(
			negatableMultiValueFacet.getFieldId(),
			StringUtil.merge(orderStatuses));

		return searchContext;
	}

	protected SearchContext buildSearchContext(
			long companyId, long commerceChannelGroupId,
			long[] commerceAccountIds, String keywords, boolean negated,
			int[] orderStatuses, int start, int end)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		if (orderStatuses != null) {
			searchContext.setAttribute("negateOrderStatuses", negated);
			searchContext.setAttribute("orderStatuses", orderStatuses);
		}

		if (commerceAccountIds != null) {
			searchContext.setAttribute(
				"commerceAccountIds", commerceAccountIds);
		}

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {commerceChannelGroupId});
		searchContext.setKeywords(keywords);

		Sort sort = SortFactoryUtil.getSort(
			CommerceOrder.class, Sort.LONG_TYPE, Field.CREATE_DATE, "DESC");

		searchContext.setSorts(sort);

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected String getCommerceOrderPaymentContent(
		CommercePaymentEngineException commercePaymentEngineException) {

		return StackTraceUtil.getStackTrace(commercePaymentEngineException);
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

		List<CommerceAddress> commerceAddresses =
			commerceAddressLocalService.getCommerceAddressesByCompanyId(
				serviceContext.getCompanyId(), AccountEntry.class.getName(),
				commerceOrder.getCommerceAccountId());

		for (CommerceAddress newCommerceAddress : commerceAddresses) {
			if (commerceAddress.isSameAddress(newCommerceAddress)) {
				return newCommerceAddress;
			}
		}

		return commerceAddressLocalService.copyCommerceAddress(
			commerceAddress.getCommerceAddressId(),
			CommerceOrder.class.getName(), commerceOrder.getCommerceOrderId(),
			serviceContext);
	}

	protected boolean hasWorkflowDefinition(long groupId, long typePK)
		throws PortalException {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return false;
		}

		return _workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
			group.getCompanyId(), group.getGroupId(),
			CommerceOrder.class.getName(), 0, typePK);
	}

	protected void sendPaymentStatusMessage(
		CommerceOrder commerceOrder, int previousPaymentStatus) {

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Message message = new Message();

				message.setPayload(
					JSONUtil.put(
						"commerceOrder",
						() -> {
							DTOConverter<?, ?> dtoConverter =
								_dtoConverterRegistry.getDTOConverter(
									CommerceOrder.class.getName());

							Object object = dtoConverter.toDTO(
								new DefaultDTOConverterContext(
									_dtoConverterRegistry,
									commerceOrder.getCommerceOrderId(),
									LocaleUtil.getSiteDefault(), null, null));

							return JSONFactoryUtil.createJSONObject(
								object.toString());
						}
					).put(
						"commerceOrderId", commerceOrder.getCommerceOrderId()
					).put(
						"paymentStatus", commerceOrder.getPaymentStatus()
					).put(
						"previousPaymentStatus", previousPaymentStatus
					));

				MessageBusUtil.sendMessage(
					DestinationNames.COMMERCE_PAYMENT_STATUS, message);

				return null;
			});
	}

	protected CommerceOrder updateAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
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
				city, zip, regionId, countryId, phoneNumber, false, false,
				serviceContext);
		}
		else {
			commerceAddress = commerceAddressLocalService.addCommerceAddress(
				commerceOrder.getModelClassName(),
				commerceOrder.getCommerceOrderId(), name, description, street1,
				street2, street3, city, zip, regionId, countryId, phoneNumber,
				false, false, serviceContext);
		}

		commerceAddressIdSetter.accept(
			commerceOrder, commerceAddress.getCommerceAddressId());

		return commerceOrderPersistence.update(commerceOrder);
	}

	protected void validateAccountOrdersLimit(
			long commerceChannelGroupId, long commerceAccountId)
		throws PortalException {

		Group group = _groupLocalService.getGroup(commerceChannelGroupId);

		int pendingCommerceOrdersCount =
			(int)commerceOrderLocalService.getCommerceOrdersCount(
				group.getCompanyId(), commerceChannelGroupId,
				new long[] {commerceAccountId}, StringPool.BLANK,
				new int[] {CommerceOrderConstants.ORDER_STATUS_OPEN}, false);

		CommerceOrderFieldsConfiguration commerceOrderFieldsConfiguration =
			_configurationProvider.getConfiguration(
				CommerceOrderFieldsConfiguration.class,
				new GroupServiceSettingsLocator(
					commerceChannelGroupId,
					CommerceConstants.SERVICE_NAME_COMMERCE_ORDER_FIELDS));

		if ((commerceOrderFieldsConfiguration.accountCartMaxAllowed() > 0) &&
			(pendingCommerceOrdersCount >=
				commerceOrderFieldsConfiguration.accountCartMaxAllowed())) {

			throw new CommerceOrderAccountLimitException(
				"The account carts limit was reached");
		}
	}

	protected void validateCheckout(CommerceOrder commerceOrder)
		throws PortalException {

		if (commerceOrder.isDraft() ||
			(!commerceOrder.isOpen() && !commerceOrder.isSubscription())) {

			throw new CommerceOrderStatusException();
		}

		if (commerceOrder.isB2B() &&
			(commerceOrder.getBillingAddressId() <= 0)) {

			throw new CommerceOrderBillingAddressException();
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

		int count =
			commerceShippingMethodLocalService.getCommerceShippingMethodsCount(
				commerceOrder.getGroupId(), true);

		if ((commerceShippingMethod == null) && (count > 0) &&
			_commerceShippingHelper.isShippable(commerceOrder)) {

			throw new CommerceOrderShippingMethodException();
		}

		BigDecimal subtotal = commerceOrder.getSubtotal();

		if (commerceOrder.isSubscriptionOrder() &&
			Validator.isNull(commerceOrder.getCommercePaymentMethodKey()) &&
			(subtotal.compareTo(BigDecimal.ZERO) > 0)) {

			throw new CommerceOrderPaymentMethodException();
		}
	}

	protected void validateGuestOrders() throws PortalException {
		int count = commerceOrderPersistence.countByUserId(
			UserConstants.USER_ID_DEFAULT);

		if (count >= _commerceOrderConfiguration.guestCartMaxAllowed()) {
			throw new GuestCartMaxAllowedException();
		}
	}

	protected void validatePurchaseOrderNumber(String purchaseOrderNumber)
		throws PortalException {

		if (Validator.isNull(purchaseOrderNumber)) {
			throw new CommerceOrderPurchaseOrderNumberException();
		}
	}

	private void _setCommerceOrderPrices(
		CommerceOrder commerceOrder, BigDecimal subtotal,
		BigDecimal shippingAmount, BigDecimal total) {

		if (subtotal == null) {
			subtotal = BigDecimal.ZERO;
		}

		if (shippingAmount == null) {
			shippingAmount = BigDecimal.ZERO;
		}

		if (total == null) {
			total = BigDecimal.ZERO;
		}

		commerceOrder.setSubtotal(subtotal);
		commerceOrder.setShippingAmount(shippingAmount);
		commerceOrder.setTotal(total);
	}

	private void _setCommerceOrderPrices(
		CommerceOrder commerceOrder, BigDecimal subtotal,
		BigDecimal shippingAmount, BigDecimal taxAmount, BigDecimal total,
		BigDecimal subtotalWithTaxAmount, BigDecimal shippingWithTaxAmount,
		BigDecimal totalWithTaxAmount) {

		if (subtotal == null) {
			subtotal = BigDecimal.ZERO;
		}

		if (shippingAmount == null) {
			shippingAmount = BigDecimal.ZERO;
		}

		if (taxAmount == null) {
			taxAmount = BigDecimal.ZERO;
		}

		if (total == null) {
			total = BigDecimal.ZERO;
		}

		if (subtotalWithTaxAmount == null) {
			subtotalWithTaxAmount = BigDecimal.ZERO;
		}

		if (shippingWithTaxAmount == null) {
			shippingWithTaxAmount = BigDecimal.ZERO;
		}

		if (totalWithTaxAmount == null) {
			totalWithTaxAmount = BigDecimal.ZERO;
		}

		commerceOrder.setSubtotal(subtotal);
		commerceOrder.setShippingAmount(shippingAmount);
		commerceOrder.setTaxAmount(taxAmount);
		commerceOrder.setTotal(total);
		commerceOrder.setSubtotalWithTaxAmount(subtotalWithTaxAmount);
		commerceOrder.setShippingWithTaxAmount(shippingWithTaxAmount);
		commerceOrder.setTotalWithTaxAmount(totalWithTaxAmount);
	}

	private void _setCommerceOrderShippingDiscountValue(
		CommerceOrder commerceOrder,
		CommerceDiscountValue commerceDiscountValue, boolean withTaxAmount) {

		BigDecimal discountAmount = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel1 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel2 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel3 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel4 = BigDecimal.ZERO;

		if (commerceDiscountValue != null) {
			CommerceMoney discountAmountCommerceMoney =
				commerceDiscountValue.getDiscountAmount();

			discountAmount = discountAmountCommerceMoney.getPrice();

			BigDecimal[] percentages = commerceDiscountValue.getPercentages();

			if (percentages.length >= 1) {
				discountPercentageLevel1 = percentages[0];
			}

			if (percentages.length >= 2) {
				discountPercentageLevel2 = percentages[1];
			}

			if (percentages.length >= 3) {
				discountPercentageLevel3 = percentages[2];
			}

			if (percentages.length >= 4) {
				discountPercentageLevel4 = percentages[3];
			}
		}

		if (withTaxAmount) {
			commerceOrder.setShippingDiscountWithTaxAmount(discountAmount);
			commerceOrder.setShippingDiscountPercentageLevel1WithTaxAmount(
				discountPercentageLevel1);
			commerceOrder.setShippingDiscountPercentageLevel2WithTaxAmount(
				discountPercentageLevel2);
			commerceOrder.setShippingDiscountPercentageLevel3WithTaxAmount(
				discountPercentageLevel3);
			commerceOrder.setShippingDiscountPercentageLevel4WithTaxAmount(
				discountPercentageLevel4);
		}
		else {
			commerceOrder.setShippingDiscountAmount(discountAmount);
			commerceOrder.setShippingDiscountPercentageLevel1(
				discountPercentageLevel1);
			commerceOrder.setShippingDiscountPercentageLevel2(
				discountPercentageLevel2);
			commerceOrder.setShippingDiscountPercentageLevel3(
				discountPercentageLevel3);
			commerceOrder.setShippingDiscountPercentageLevel4(
				discountPercentageLevel4);
		}
	}

	private void _setCommerceOrderSubtotalDiscountValue(
		CommerceOrder commerceOrder,
		CommerceDiscountValue commerceDiscountValue, boolean withTaxAmount) {

		BigDecimal discountAmount = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel1 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel2 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel3 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel4 = BigDecimal.ZERO;

		if (commerceDiscountValue != null) {
			CommerceMoney discountAmountCommerceMoney =
				commerceDiscountValue.getDiscountAmount();

			discountAmount = discountAmountCommerceMoney.getPrice();

			BigDecimal[] percentages = commerceDiscountValue.getPercentages();

			if ((percentages.length >= 1) && (percentages[0] != null)) {
				discountPercentageLevel1 = percentages[0];
			}

			if ((percentages.length >= 2) && (percentages[1] != null)) {
				discountPercentageLevel2 = percentages[1];
			}

			if ((percentages.length >= 3) && (percentages[2] != null)) {
				discountPercentageLevel3 = percentages[2];
			}

			if ((percentages.length >= 4) && (percentages[3] != null)) {
				discountPercentageLevel4 = percentages[3];
			}
		}

		if (withTaxAmount) {
			commerceOrder.setSubtotalDiscountWithTaxAmount(discountAmount);
			commerceOrder.setSubtotalDiscountPercentageLevel1WithTaxAmount(
				discountPercentageLevel1);
			commerceOrder.setSubtotalDiscountPercentageLevel2WithTaxAmount(
				discountPercentageLevel2);
			commerceOrder.setSubtotalDiscountPercentageLevel3WithTaxAmount(
				discountPercentageLevel3);
			commerceOrder.setSubtotalDiscountPercentageLevel4WithTaxAmount(
				discountPercentageLevel4);
		}
		else {
			commerceOrder.setSubtotalDiscountAmount(discountAmount);
			commerceOrder.setSubtotalDiscountPercentageLevel1(
				discountPercentageLevel1);
			commerceOrder.setSubtotalDiscountPercentageLevel2(
				discountPercentageLevel2);
			commerceOrder.setSubtotalDiscountPercentageLevel3(
				discountPercentageLevel3);
			commerceOrder.setSubtotalDiscountPercentageLevel4(
				discountPercentageLevel4);
		}
	}

	private void _setCommerceOrderTotalDiscountValue(
		CommerceOrder commerceOrder,
		CommerceDiscountValue commerceDiscountValue, boolean withTaxAmount) {

		BigDecimal discountAmount = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel1 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel2 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel3 = BigDecimal.ZERO;
		BigDecimal discountPercentageLevel4 = BigDecimal.ZERO;

		if (commerceDiscountValue != null) {
			CommerceMoney discountAmountCommerceMoney =
				commerceDiscountValue.getDiscountAmount();

			discountAmount = discountAmountCommerceMoney.getPrice();

			BigDecimal[] percentages = commerceDiscountValue.getPercentages();

			if (percentages.length >= 1) {
				discountPercentageLevel1 = percentages[0];
			}

			if (percentages.length >= 2) {
				discountPercentageLevel2 = percentages[1];
			}

			if (percentages.length >= 3) {
				discountPercentageLevel3 = percentages[2];
			}

			if (percentages.length >= 4) {
				discountPercentageLevel4 = percentages[3];
			}
		}

		if (withTaxAmount) {
			commerceOrder.setTotalDiscountWithTaxAmount(discountAmount);
			commerceOrder.setTotalDiscountPercentageLevel1WithTaxAmount(
				discountPercentageLevel1);
			commerceOrder.setTotalDiscountPercentageLevel2WithTaxAmount(
				discountPercentageLevel2);
			commerceOrder.setTotalDiscountPercentageLevel3WithTaxAmount(
				discountPercentageLevel3);
			commerceOrder.setTotalDiscountPercentageLevel4WithTaxAmount(
				discountPercentageLevel4);
		}
		else {
			commerceOrder.setTotalDiscountAmount(discountAmount);
			commerceOrder.setTotalDiscountPercentageLevel1(
				discountPercentageLevel1);
			commerceOrder.setTotalDiscountPercentageLevel2(
				discountPercentageLevel2);
			commerceOrder.setTotalDiscountPercentageLevel3(
				discountPercentageLevel3);
			commerceOrder.setTotalDiscountPercentageLevel4(
				discountPercentageLevel4);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderLocalServiceImpl.class);

	@ServiceReference(type = CommerceCurrencyLocalService.class)
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@ServiceReference(type = CommerceDiscountLocalService.class)
	private CommerceDiscountLocalService _commerceDiscountLocalService;

	@ServiceReference(type = CommerceDiscountUsageEntryLocalService.class)
	private CommerceDiscountUsageEntryLocalService
		_commerceDiscountUsageEntryLocalService;

	@ServiceReference(type = CommerceDiscountValidatorHelper.class)
	private CommerceDiscountValidatorHelper _commerceDiscountValidatorHelper;

	@ServiceReference(type = CommerceOrderConfiguration.class)
	private CommerceOrderConfiguration _commerceOrderConfiguration;

	@ServiceReference(type = CommerceOrderPriceCalculationFactory.class)
	private CommerceOrderPriceCalculationFactory
		_commerceOrderPriceCalculationFactory;

	@ServiceReference(type = CommerceShippingEngineRegistry.class)
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@ServiceReference(type = CommerceShippingHelper.class)
	private CommerceShippingHelper _commerceShippingHelper;

	@ServiceReference(type = CommerceTermEntryLocalService.class)
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	@ServiceReference(type = ConfigurationProvider.class)
	private ConfigurationProvider _configurationProvider;

	@ServiceReference(type = DTOConverterRegistry.class)
	private DTOConverterRegistry _dtoConverterRegistry;

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

	@ServiceReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

	@ServiceReference(type = JsonHelper.class)
	private JsonHelper _jsonHelper;

	@ServiceReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

	@ServiceReference(type = WorkflowDefinitionLinkLocalService.class)
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@ServiceReference(type = WorkflowInstanceLinkLocalService.class)
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@ServiceReference(type = WorkflowTaskManager.class)
	private WorkflowTaskManager _workflowTaskManager;

}