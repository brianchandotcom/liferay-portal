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

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.commerce.account.exception.NoSuchAccountException;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.internal.helper.v1_0.OrderHelper;
import com.liferay.headless.commerce.admin.order.internal.odata.entity.v1_0.OrderEntityModel;
import com.liferay.headless.commerce.admin.order.internal.util.v1_0.BillingAddressUtil;
import com.liferay.headless.commerce.admin.order.internal.util.v1_0.OrderItemUtil;
import com.liferay.headless.commerce.admin.order.internal.util.v1_0.ShippingAddressUtil;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.ExpandoUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, properties = "OSGI-INF/liferay/rest/v1_0/order.properties",
	scope = ServiceScope.PROTOTYPE, service = OrderResource.class
)
public class OrderResourceImpl
	extends BaseOrderResourceImpl implements EntityModelResource {

	@Override
	public Response deleteOrder(Long id) throws Exception {
		_commerceOrderService.deleteCommerceOrder(id);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response deleteOrderByExternalReferenceCode(
			String externalReferenceCode)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find Order with externalReferenceCode: " +
					externalReferenceCode);
		}

		_commerceOrderService.deleteCommerceOrder(
			commerceOrder.getCommerceOrderId());

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Order getOrder(Long id) throws Exception {
		return _orderHelper.toOrder(
			GetterUtil.getLong(id), contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo,
			_getActions(
				_commerceOrderService.getCommerceOrder(
					GetterUtil.getLong(id))));
	}

	@Override
	public Order getOrderByExternalReferenceCode(String externalReferenceCode)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find Order with externalReferenceCode: " +
					externalReferenceCode);
		}

		return _orderHelper.toOrder(
			commerceOrder.getCommerceOrderId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo, _getActions(commerceOrder));
	}

	@Override
	public Page<Order> getOrdersPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return _orderHelper.getOrdersPage(
			contextCompany.getCompanyId(), filter, pagination, search, sorts,
			document -> _orderHelper.toOrder(
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)),
				contextAcceptLanguage.getPreferredLocale(),
				contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
				contextUriInfo,
				_getActions(
					_commerceOrderService.getCommerceOrder(
						GetterUtil.getLong(
							GetterUtil.getLong(
								document.get(Field.ENTRY_CLASS_PK)))))),
			true);
	}

	@Override
	public Response patchOrder(Long id, Order order) throws Exception {
		_updateOrder(_commerceOrderService.getCommerceOrder(id), order);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Response patchOrderByExternalReferenceCode(
			String externalReferenceCode, Order order)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find Order with externalReferenceCode: " +
					externalReferenceCode);
		}

		_updateOrder(commerceOrder, order);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Order postOrder(Order order) throws Exception {
		CommerceOrder commerceOrder = _upsertOrder(order);

		return _orderHelper.toOrder(
			commerceOrder.getCommerceOrderId(),
			contextAcceptLanguage.getPreferredLocale(),
			contextAcceptLanguage.isAcceptAllLanguages(), contextUser,
			contextUriInfo, _getActions(commerceOrder));
	}

	private Map<String, String> _addAction(
			String actionId, long commerceOrderId, UriInfo uriInfo,
			String methodName, Class<?> clazz)
		throws NoSuchMethodException, PortalException {

		if (!_commerceOrderModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), commerceOrderId,
				actionId)) {

			return null;
		}

		return HashMapBuilder.put(
			"href",
			() -> {
				UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();

				return uriBuilder.path(
					_getVersion(uriInfo)
				).path(
					clazz.getSuperclass(), methodName
				).toTemplate();
			}
		).put(
			"method", _getHttpMethodName(clazz, _getMethod(clazz, methodName))
		).build();
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceOrder commerceOrder)
		throws NoSuchMethodException, PortalException {

		if (contextUriInfo == null) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			_addAction(
				ActionKeys.DELETE, commerceOrder.getCommerceOrderId(),
				contextUriInfo, "deleteOrder", getClass())
		).put(
			"get",
			_addAction(
				ActionKeys.VIEW, commerceOrder.getCommerceOrderId(),
				contextUriInfo, "getOrder", getClass())
		).put(
			"update",
			_addAction(
				ActionKeys.UPDATE, commerceOrder.getCommerceOrderId(),
				contextUriInfo, "patchOrder", getClass())
		).build();
	}

	private String _getHttpMethodName(Class<?> clazz, Method method)
		throws NoSuchMethodException {

		Class<?> superClass = clazz.getSuperclass();

		Method superMethod = superClass.getMethod(
			method.getName(), method.getParameterTypes());

		for (Annotation annotation : superMethod.getAnnotations()) {
			Class<? extends Annotation> annotationType =
				annotation.annotationType();

			Annotation[] annotations = annotationType.getAnnotationsByType(
				HttpMethod.class);

			if (annotations.length > 0) {
				HttpMethod httpMethod = (HttpMethod)annotations[0];

				return httpMethod.value();
			}
		}

		return null;
	}

	private Method _getMethod(Class<?> clazz, String methodName) {
		for (Method method : clazz.getMethods()) {
			if (!methodName.equals(method.getName())) {
				continue;
			}

			return method;
		}

		return null;
	}

	private String _getVersion(UriInfo uriInfo) {
		String version = "";

		List<String> matchedURIs = uriInfo.getMatchedURIs();

		if (!matchedURIs.isEmpty()) {
			version = matchedURIs.get(matchedURIs.size() - 1);
		}

		return version;
	}

	private CommerceOrder _updateNestedResources(
			Order order, CommerceOrder commerceOrder,
			ServiceContext serviceContext)
		throws Exception {

		// Order items

		OrderItem[] orderItems = order.getOrderItems();

		if (orderItems != null) {
			_commerceOrderItemService.deleteCommerceOrderItems(
				commerceOrder.getCommerceOrderId());

			for (OrderItem orderItem : orderItems) {
				OrderItemUtil.upsertCommerceOrderItem(
					_cpInstanceService, _commerceOrderItemService,
					_commerceOrderModelResourcePermission, orderItem,
					commerceOrder,
					_commerceContextFactory.create(
						contextCompany.getCompanyId(),
						commerceOrder.getGroupId(), contextUser.getUserId(),
						commerceOrder.getCommerceOrderId(),
						commerceOrder.getCommerceAccountId()),
					serviceContext);
			}
		}

		// Billing Address

		BillingAddress billingAddress = order.getBillingAddress();

		if (billingAddress != null) {
			commerceOrder = BillingAddressUtil.upsertBillingAddress(
				_commerceAddressService, _commerceOrderService, commerceOrder,
				billingAddress, serviceContext);
		}

		// Shipping Address

		ShippingAddress shippingAddress = order.getShippingAddress();

		if (shippingAddress != null) {
			commerceOrder = ShippingAddressUtil.upsertShippingAddress(
				_commerceAddressService, _commerceOrderService, commerceOrder,
				shippingAddress, serviceContext);
		}

		return commerceOrder;
	}

	private CommerceOrder _updateOrder(CommerceOrder commerceOrder, Order order)
		throws Exception {

		long commerceShippingMethodId =
			commerceOrder.getCommerceShippingMethodId();

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.fetchCommerceShippingMethod(
				commerceOrder.getGroupId(), order.getShippingMethod());

		if (commerceShippingMethod != null) {
			commerceShippingMethodId =
				commerceShippingMethod.getCommerceShippingMethodId();
		}

		commerceOrder = _commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			GetterUtil.getLong(
				order.getBillingAddressId(),
				commerceOrder.getBillingAddressId()),
			GetterUtil.getLong(
				order.getShippingAddressId(),
				commerceOrder.getShippingAddressId()),
			GetterUtil.getString(
				order.getPaymentMethod(),
				commerceOrder.getCommercePaymentMethodKey()),
			commerceShippingMethodId,
			GetterUtil.getString(
				order.getShippingOption(),
				commerceOrder.getShippingOptionName()),
			GetterUtil.getString(
				order.getPurchaseOrderNumber(),
				commerceOrder.getPurchaseOrderNumber()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotal(), commerceOrder.getSubtotal()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingAmount(), commerceOrder.getShippingAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotal(), commerceOrder.getTotal()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalWithTaxAmount(),
				commerceOrder.getSubtotalWithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingWithTaxAmount(),
				commerceOrder.getShippingWithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalWithTaxAmount(),
				commerceOrder.getTotalWithTaxAmount()),
			GetterUtil.getString(
				order.getAdvanceStatus(), commerceOrder.getAdvanceStatus()),
			GetterUtil.getString(
				order.getExternalReferenceCode(),
				commerceOrder.getExternalReferenceCode()),
			_commerceContextFactory.create(
				contextCompany.getCompanyId(), commerceOrder.getGroupId(),
				contextUser.getUserId(), 0,
				GetterUtil.getLong(
					order.getAccountId(),
					commerceOrder.getCommerceAccountId())));

		// Expando

		Map<String, ?> customFields = order.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				contextCompany.getCompanyId(), CommerceOrder.class,
				commerceOrder.getPrimaryKey(), customFields);
		}

		// Update nested resources

		commerceOrder = _updateNestedResources(
			order, commerceOrder,
			_serviceContextHelper.getServiceContext(
				commerceOrder.getGroupId()));

		if (commerceOrder.getOrderStatus() != order.getOrderStatus()) {
			commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
				commerceOrder, order.getOrderStatus(), contextUser.getUserId());
		}

		return commerceOrder;
	}

	private CommerceOrder _upsertOrder(Order order) throws Exception {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				order.getChannelId());

		long commerceShippingMethodId = 0;

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.fetchCommerceShippingMethod(
				commerceChannel.getGroupId(), order.getShippingMethod());

		if (commerceShippingMethod != null) {
			commerceShippingMethodId =
				commerceShippingMethod.getCommerceShippingMethodId();
		}

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(
				commerceChannel.getCompanyId(), order.getCurrencyCode());

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commerceChannel.getGroupId());

		CommerceAccount commerceAccount = null;

		if (order.getAccountId() != null) {
			commerceAccount = _commerceAccountService.getCommerceAccount(
				order.getAccountId());
		}

		if ((commerceAccount == null) &&
			Validator.isNotNull(order.getAccountExternalReferenceCode())) {

			commerceAccount =
				_commerceAccountService.fetchByExternalReferenceCode(
					commerceChannel.getCompanyId(),
					order.getAccountExternalReferenceCode());
		}

		if (commerceAccount == null) {
			throw new NoSuchAccountException();
		}

		CommerceOrder commerceOrder = _commerceOrderService.upsertCommerceOrder(
			order.getExternalReferenceCode(), contextUser.getUserId(),
			commerceChannel.getGroupId(),
			commerceAccount.getCommerceAccountId(),
			commerceCurrency.getCommerceCurrencyId(),
			GetterUtil.getLong(order.getBillingAddressId()),
			GetterUtil.getLong(order.getShippingAddressId()),
			order.getPaymentMethod(), commerceShippingMethodId,
			order.getShippingOption(), order.getPurchaseOrderNumber(),
			order.getSubtotal(), order.getShippingAmount(), order.getTotal(),
			order.getSubtotalWithTaxAmount(), order.getShippingWithTaxAmount(),
			order.getTotalWithTaxAmount(),
			GetterUtil.getInteger(
				order.getPaymentStatus(),
				CommerceOrderConstants.PAYMENT_STATUS_PENDING),
			GetterUtil.getInteger(
				order.getOrderStatus(),
				CommerceOrderConstants.ORDER_STATUS_PENDING),
			order.getAdvanceStatus(),
			_commerceContextFactory.create(
				contextCompany.getCompanyId(), commerceChannel.getGroupId(),
				contextUser.getUserId(), 0,
				commerceAccount.getCommerceAccountId()),
			serviceContext);

		// Order date

		if (order.getOrderDate() != null) {
			Calendar orderDateCalendar = CalendarFactoryUtil.getCalendar(
				serviceContext.getTimeZone());

			orderDateCalendar.setTime(order.getOrderDate());

			DateConfig orderDate = new DateConfig(orderDateCalendar);

			_commerceOrderService.updateOrderDate(
				commerceOrder.getCommerceOrderId(), orderDate.getMonth(),
				orderDate.getDay(), orderDate.getYear(), orderDate.getHour(),
				orderDate.getMinute(), serviceContext);
		}

		// Printed note

		_commerceOrderService.updatePrintedNote(
			commerceOrder.getCommerceOrderId(), order.getPrintedNote());

		// Expando

		Map<String, ?> customFields = order.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				contextCompany.getCompanyId(), CommerceOrder.class,
				commerceOrder.getPrimaryKey(), customFields);
		}

		// Update nested resources

		return _updateNestedResources(order, commerceOrder, serviceContext);
	}

	private static final EntityModel _entityModel = new OrderEntityModel();

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	private ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private OrderHelper _orderHelper;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}