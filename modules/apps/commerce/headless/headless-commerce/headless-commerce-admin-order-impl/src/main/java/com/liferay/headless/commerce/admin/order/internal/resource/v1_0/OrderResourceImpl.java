/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.order.internal.resource.v1_0;

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommerceOrderPaymentConstants;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.dto.v1_0.ShippingAddress;
import com.liferay.headless.commerce.admin.order.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.commerce.admin.order.internal.helper.v1_0.OrderHelper;
import com.liferay.headless.commerce.admin.order.internal.odata.entity.v1_0.OrderEntityModel;
import com.liferay.headless.commerce.admin.order.internal.util.v1_0.BillingAddressUtil;
import com.liferay.headless.commerce.admin.order.internal.util.v1_0.OrderItemUtil;
import com.liferay.headless.commerce.admin.order.internal.util.v1_0.ShippingAddressUtil;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.ExpandoUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
	properties = "OSGI-INF/liferay/rest/v1_0/order.properties",
	scope = ServiceScope.PROTOTYPE, service = OrderResource.class
)
public class OrderResourceImpl extends BaseOrderResourceImpl {

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
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
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
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
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
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceOrder == null) {
			throw new NoSuchOrderException(
				"Unable to find order with external reference code " +
					externalReferenceCode);
		}

		_updateOrder(commerceOrder, order);

		Response.ResponseBuilder responseBuilder = Response.noContent();

		return responseBuilder.build();
	}

	@Override
	public Order postOrder(Order order) throws Exception {
		CommerceOrder commerceOrder = _addOrUpdateOrder(order);

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

	private CommerceOrder _addOrUpdateOrder(Order order) throws Exception {
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

		AccountEntry accountEntry = null;

		if (order.getAccountId() != null) {
			accountEntry = _accountEntryService.getAccountEntry(
				order.getAccountId());
		}

		if ((accountEntry == null) &&
			Validator.isNotNull(order.getAccountExternalReferenceCode())) {

			accountEntry =
				_accountEntryService.fetchAccountEntryByExternalReferenceCode(
					commerceChannel.getCompanyId(),
					order.getAccountExternalReferenceCode());
		}

		if (accountEntry == null) {
			throw new NoSuchEntryException();
		}

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(
				commerceChannel.getCompanyId(), order.getCurrencyCode());

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			commerceChannel.getGroupId());

		CommerceOrder commerceOrder =
			_commerceOrderService.addOrUpdateCommerceOrder(
				order.getExternalReferenceCode(), commerceChannel.getGroupId(),
				GetterUtil.getLong(order.getBillingAddressId()),
				accountEntry.getAccountEntryId(),
				commerceCurrency.getCommerceCurrencyId(),
				_getCommerceOrderTypeId(order), commerceShippingMethodId,
				GetterUtil.getLong(order.getShippingAddressId()),
				order.getAdvanceStatus(), order.getPaymentMethod(),
				GetterUtil.getInteger(
					order.getOrderStatus(),
					CommerceOrderConstants.ORDER_STATUS_PENDING),
				GetterUtil.getInteger(
					order.getPaymentStatus(),
					CommerceOrderPaymentConstants.STATUS_PENDING),
				order.getPurchaseOrderNumber(), order.getShippingAmount(),
				order.getShippingOption(), order.getShippingWithTaxAmount(),
				order.getSubtotal(), order.getSubtotalWithTaxAmount(),
				order.getTaxAmount(), order.getTotal(),
				order.getTotalWithTaxAmount(),
				_commerceContextFactory.create(
					contextCompany.getCompanyId(), commerceChannel.getGroupId(),
					contextUser.getUserId(), 0,
					accountEntry.getAccountEntryId()),
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

		// Requested delivery date

		if (order.getRequestedDeliveryDate() != null) {
			Calendar requestedDeliveryDateCalendar =
				CalendarFactoryUtil.getCalendar(serviceContext.getTimeZone());

			requestedDeliveryDateCalendar.setTime(
				order.getRequestedDeliveryDate());

			DateConfig requestedDeliveryDate = new DateConfig(
				requestedDeliveryDateCalendar);

			_commerceOrderService.updateInfo(
				commerceOrder.getCommerceOrderId(),
				GetterUtil.getString(
					order.getPrintedNote(), commerceOrder.getPrintedNote()),
				requestedDeliveryDate.getMonth(),
				requestedDeliveryDate.getDay(), requestedDeliveryDate.getYear(),
				requestedDeliveryDate.getHour(),
				requestedDeliveryDate.getMinute(), serviceContext);
		}
		else {

			// Printed note

			_commerceOrderService.updatePrintedNote(
				commerceOrder.getCommerceOrderId(),
				GetterUtil.getString(
					order.getPrintedNote(), commerceOrder.getPrintedNote()));
		}

		// Terms and Conditions

		if ((order.getDeliveryTermId() != null) ||
			(order.getPaymentTermId() != null)) {

			_commerceOrderService.updateTermsAndConditions(
				commerceOrder.getCommerceOrderId(),
				GetterUtil.getLong(order.getDeliveryTermId()),
				GetterUtil.getLong(order.getPaymentTermId()),
				contextAcceptLanguage.getPreferredLanguageId());
		}

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

	private long _getCommerceOrderTypeId(Order order) throws Exception {
		if (order.getOrderTypeId() != null) {
			return order.getOrderTypeId();
		}

		CommerceOrderType commerceOrderType =
			_commerceOrderTypeService.fetchByExternalReferenceCode(
				order.getOrderTypeExternalReferenceCode(),
				contextCompany.getCompanyId());

		if (commerceOrderType == null) {
			return 0;
		}

		return commerceOrderType.getCommerceOrderTypeId();
	}

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		OrderItem orderItem) {

		return CustomFieldsUtil.toMap(
			CommerceOrderItem.class.getName(), contextCompany.getCompanyId(),
			orderItem.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
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
			if (methodName.equals(method.getName())) {
				return method;
			}
		}

		return null;
	}

	private String[] _getOrderItemExternalReferenceCodes(
		OrderItem[] orderItems) {

		Set<String> orderItemExternalReferenceCodes = new HashSet<>();

		for (OrderItem orderItem : orderItems) {
			String externalReferenceCode = orderItem.getExternalReferenceCode();

			if (Objects.nonNull(externalReferenceCode)) {
				orderItemExternalReferenceCodes.add(externalReferenceCode);
			}
		}

		if (orderItemExternalReferenceCodes.isEmpty()) {
			return null;
		}

		return transformToArray(
			orderItemExternalReferenceCodes,
			orderItemExternalReferenceCode -> orderItemExternalReferenceCode,
			String.class);
	}

	private Long[] _getOrderItemIds(OrderItem[] orderItems) {
		Set<Long> orderItemIds = new HashSet<>();

		for (OrderItem orderItem : orderItems) {
			Long id = orderItem.getId();

			if (Objects.nonNull(id)) {
				orderItemIds.add(id);
			}
		}

		if (orderItemIds.isEmpty()) {
			return new Long[] {0L};
		}

		return transformToArray(
			orderItemIds, orderItemId -> orderItemId, Long.class);
	}

	private String _getVersion(UriInfo uriInfo) {
		List<String> matchedURIs = uriInfo.getMatchedURIs();

		if (matchedURIs.isEmpty()) {
			return "";
		}

		return matchedURIs.get(matchedURIs.size() - 1);
	}

	private CommerceOrder _updateNestedResources(
			Order order, CommerceOrder commerceOrder,
			ServiceContext serviceContext)
		throws Exception {

		// Order items

		OrderItem[] orderItems = order.getOrderItems();

		if (orderItems != null) {
			_commerceOrderItemService.deleteMissingCommerceOrderItems(
				commerceOrder.getCommerceOrderId(),
				_getOrderItemIds(orderItems),
				_getOrderItemExternalReferenceCodes(orderItems));

			for (OrderItem orderItem : orderItems) {
				CommerceOrderItem commerceOrderItem =
					OrderItemUtil.addOrUpdateCommerceOrderItem(
						_cpInstanceService, _commerceOrderItemService,
						_commerceOrderModelResourcePermission, orderItem,
						commerceOrder,
						_commerceContextFactory.create(
							contextCompany.getCompanyId(),
							commerceOrder.getGroupId(), contextUser.getUserId(),
							commerceOrder.getCommerceOrderId(),
							commerceOrder.getCommerceAccountId()),
						_serviceContextHelper.getServiceContext(
							commerceOrder.getGroupId()));

				Map<String, ?> expandoAttributes = _getExpandoBridgeAttributes(
					orderItem);

				if (MapUtil.isNotEmpty(expandoAttributes)) {
					ExpandoUtil.updateExpando(
						contextCompany.getCompanyId(), CommerceOrderItem.class,
						commerceOrderItem.getPrimaryKey(), expandoAttributes);
				}
			}
		}

		// Billing Address

		BillingAddress billingAddress = order.getBillingAddress();

		if (billingAddress != null) {
			commerceOrder = BillingAddressUtil.addOrUpdateBillingAddress(
				_commerceAddressService, _commerceOrderService, commerceOrder,
				billingAddress, serviceContext);
		}

		// Shipping Address

		ShippingAddress shippingAddress = order.getShippingAddress();

		if (shippingAddress != null) {
			commerceOrder = ShippingAddressUtil.addOrUpdateShippingAddress(
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

		commerceOrder = _commerceOrderEngine.updateCommerceOrder(
			GetterUtil.getString(
				order.getExternalReferenceCode(),
				commerceOrder.getExternalReferenceCode()),
			commerceOrder.getCommerceOrderId(),
			GetterUtil.getLong(
				order.getBillingAddressId(),
				commerceOrder.getBillingAddressId()),
			commerceShippingMethodId,
			GetterUtil.getLong(
				order.getShippingAddressId(),
				commerceOrder.getShippingAddressId()),
			GetterUtil.getString(
				order.getAdvanceStatus(), commerceOrder.getAdvanceStatus()),
			GetterUtil.getString(
				order.getPaymentMethod(),
				commerceOrder.getCommercePaymentMethodKey()),
			GetterUtil.getString(
				order.getPurchaseOrderNumber(),
				commerceOrder.getPurchaseOrderNumber()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingAmount(), commerceOrder.getShippingAmount()),
			GetterUtil.getString(
				order.getShippingOption(),
				commerceOrder.getShippingOptionName()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingWithTaxAmount(),
				commerceOrder.getShippingWithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotal(), commerceOrder.getSubtotal()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalWithTaxAmount(),
				commerceOrder.getSubtotalWithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTaxAmount(), commerceOrder.getTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotal(), commerceOrder.getTotal()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountAmount(),
				commerceOrder.getTotalDiscountAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalWithTaxAmount(),
				commerceOrder.getTotalWithTaxAmount()),
			_commerceContextFactory.create(
				contextCompany.getCompanyId(), commerceOrder.getGroupId(),
				contextUser.getUserId(), 0,
				GetterUtil.getLong(
					order.getAccountId(),
					commerceOrder.getCommerceAccountId())),
			false);

		_commerceOrderService.updateCommerceOrderPrices(
			commerceOrder.getCommerceOrderId(),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingAmount(), commerceOrder.getShippingAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountAmount(),
				commerceOrder.getShippingDiscountAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountPercentageLevel1(),
				commerceOrder.getShippingDiscountPercentageLevel1()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountPercentageLevel2(),
				commerceOrder.getShippingDiscountPercentageLevel2()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountPercentageLevel3(),
				commerceOrder.getShippingDiscountPercentageLevel3()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountPercentageLevel4(),
				commerceOrder.getShippingDiscountPercentageLevel4()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountPercentageLevel1WithTaxAmount(),
				commerceOrder.
					getShippingDiscountPercentageLevel1WithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountPercentageLevel2WithTaxAmount(),
				commerceOrder.
					getShippingDiscountPercentageLevel2WithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountPercentageLevel3WithTaxAmount(),
				commerceOrder.
					getShippingDiscountPercentageLevel3WithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountPercentageLevel4WithTaxAmount(),
				commerceOrder.
					getShippingDiscountPercentageLevel4WithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingDiscountWithTaxAmount(),
				commerceOrder.getShippingDiscountWithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getShippingWithTaxAmount(),
				commerceOrder.getShippingWithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotal(), commerceOrder.getSubtotal()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountAmount(),
				commerceOrder.getSubtotalDiscountAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountPercentageLevel1(),
				commerceOrder.getSubtotalDiscountPercentageLevel1()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountPercentageLevel2(),
				commerceOrder.getSubtotalDiscountPercentageLevel2()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountPercentageLevel3(),
				commerceOrder.getTotalDiscountPercentageLevel3()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountPercentageLevel4(),
				commerceOrder.getSubtotalDiscountPercentageLevel4()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountPercentageLevel1WithTaxAmount(),
				commerceOrder.
					getSubtotalDiscountPercentageLevel1WithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountPercentageLevel2WithTaxAmount(),
				commerceOrder.
					getSubtotalDiscountPercentageLevel2WithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountPercentageLevel3WithTaxAmount(),
				commerceOrder.
					getSubtotalDiscountPercentageLevel3WithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountPercentageLevel4WithTaxAmount(),
				commerceOrder.
					getSubtotalDiscountPercentageLevel4WithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalDiscountWithTaxAmount(),
				commerceOrder.getSubtotalDiscountWithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getSubtotalWithTaxAmount(),
				commerceOrder.getSubtotalWithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTaxAmount(), commerceOrder.getTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotal(), commerceOrder.getTotal()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountAmount(),
				commerceOrder.getTotalDiscountAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountPercentageLevel1(),
				commerceOrder.getTotalDiscountPercentageLevel1()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountPercentageLevel2(),
				commerceOrder.getTotalDiscountPercentageLevel2()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountPercentageLevel3(),
				commerceOrder.getTotalDiscountPercentageLevel3()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountPercentageLevel4(),
				commerceOrder.getTotalDiscountPercentageLevel4()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountPercentageLevel1(),
				commerceOrder.getTotalDiscountPercentageLevel1()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountPercentageLevel2(),
				commerceOrder.getTotalDiscountPercentageLevel2()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountPercentageLevel3(),
				commerceOrder.getTotalDiscountPercentageLevel3()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountPercentageLevel4(),
				commerceOrder.getTotalDiscountPercentageLevel4()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalDiscountWithTaxAmount(),
				commerceOrder.getTotalDiscountWithTaxAmount()),
			(BigDecimal)GetterUtil.getNumber(
				order.getTotalWithTaxAmount(),
				commerceOrder.getTotalWithTaxAmount()));

		if (order.getRequestedDeliveryDate() != null) {
			ServiceContext serviceContext =
				_serviceContextHelper.getServiceContext(
					commerceOrder.getGroupId());

			Calendar requestedDeliveryDateCalendar =
				CalendarFactoryUtil.getCalendar(serviceContext.getTimeZone());

			requestedDeliveryDateCalendar.setTime(
				order.getRequestedDeliveryDate());

			DateConfig requestedDeliveryDate = new DateConfig(
				requestedDeliveryDateCalendar);

			commerceOrder = _commerceOrderService.updateInfo(
				commerceOrder.getCommerceOrderId(),
				GetterUtil.getString(
					order.getPrintedNote(), commerceOrder.getPrintedNote()),
				requestedDeliveryDate.getMonth(),
				requestedDeliveryDate.getDay(), requestedDeliveryDate.getYear(),
				requestedDeliveryDate.getHour(),
				requestedDeliveryDate.getMinute(), serviceContext);
		}
		else {
			commerceOrder = _commerceOrderService.updatePrintedNote(
				commerceOrder.getCommerceOrderId(),
				GetterUtil.getString(
					order.getPrintedNote(), commerceOrder.getPrintedNote()));
		}

		commerceOrder = _commercePaymentEngine.updateOrderPaymentStatus(
			commerceOrder.getCommerceOrderId(),
			GetterUtil.getInteger(
				order.getPaymentStatus(), commerceOrder.getPaymentStatus()),
			commerceOrder.getTransactionId(), StringPool.BLANK);

		Map<String, ?> customFields = order.getCustomFields();

		if ((customFields != null) && !customFields.isEmpty()) {
			ExpandoUtil.updateExpando(
				contextCompany.getCompanyId(), CommerceOrder.class,
				commerceOrder.getPrimaryKey(), customFields);
		}

		commerceOrder = _updateNestedResources(
			order, commerceOrder,
			_serviceContextHelper.getServiceContext(
				commerceOrder.getGroupId()));

		if (Validator.isNotNull(order.getOrderStatus()) &&
			(commerceOrder.getOrderStatus() != order.getOrderStatus())) {

			commerceOrder = _commerceOrderEngine.transitionCommerceOrder(
				commerceOrder, order.getOrderStatus(), contextUser.getUserId(),
				true);
		}

		return commerceOrder;
	}

	private static final EntityModel _entityModel = new OrderEntityModel();

	@Reference
	private AccountEntryService _accountEntryService;

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
	private CommerceOrderTypeService _commerceOrderTypeService;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private OrderHelper _orderHelper;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}