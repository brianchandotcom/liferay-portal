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

package com.liferay.commerce.internal.object.system;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderTable;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionManager;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(service = SystemObjectDefinitionManager.class)
public class CommerceOrderSystemObjectDefinitionManager
	extends BaseSystemObjectDefinitionManager {

	@Override
	public long addBaseModel(User user, Map<String, Object> values)
		throws Exception {

		OrderResource orderResource = _buildOrderResource(user);

		Order order = orderResource.postOrder(_toOrder(values));

		setExtendedProperties(Order.class.getName(), order, user, values);

		return order.getId();
	}

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return _commerceOrderLocalService.deleteCommerceOrder(
			(CommerceOrder)baseModel);
	}

	public BaseModel<?> fetchBaseModelByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return _commerceOrderLocalService.
			fetchCommerceOrderByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return _commerceOrderLocalService.
			getCommerceOrderByExternalReferenceCode(
				externalReferenceCode, companyId);
	}

	@Override
	public String getExternalReferenceCode(long primaryKey)
		throws PortalException {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(primaryKey);

		return commerceOrder.getExternalReferenceCode();
	}

	@Override
	public JaxRsApplicationDescriptor getJaxRsApplicationDescriptor() {
		return new JaxRsApplicationDescriptor(
			"Liferay.Headless.Commerce.Admin.Order",
			"headless-commerce-admin-order", "orders", "v1.0");
	}

	@Override
	public Map<Locale, String> getLabelMap() {
		return createLabelMap("commerce-order");
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceOrder.class;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return Arrays.asList(
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
				ObjectFieldConstants.DB_TYPE_LONG, "account-id", "accountId",
				true, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER,
				ObjectFieldConstants.DB_TYPE_LONG, "channel-id", "channelId",
				true, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				ObjectFieldConstants.DB_TYPE_STRING, "currency-code",
				"currencyCode", true, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
				ObjectFieldConstants.DB_TYPE_INTEGER, "order-status",
				"orderStatus", true, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_TEXT,
				"orderTypeExternalReferenceCode",
				ObjectFieldConstants.DB_TYPE_STRING,
				"orderTypeExternalReferenceCode",
				"orderTypeExternalReferenceCode", false, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_LONG_INTEGER, "orderTypeId",
				ObjectFieldConstants.DB_TYPE_LONG, "order-type-id",
				"orderTypeId", false, true),
			createObjectField(
				ObjectFieldConstants.BUSINESS_TYPE_PRECISION_DECIMAL,
				ObjectFieldConstants.DB_TYPE_BIG_DECIMAL, "shipping-amount",
				"shippingAmount", true, true));
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return createLabelMap("commerce-orders");
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return CommerceOrderTable.INSTANCE.commerceOrderId;
	}

	@Override
	public String getScope() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public Table getTable() {
		return CommerceOrderTable.INSTANCE;
	}

	@Override
	public int getVersion() {
		return 3;
	}

	@Override
	public void updateBaseModel(
			long primaryKey, User user, Map<String, Object> values)
		throws Exception {

		OrderResource orderResource = _buildOrderResource(user);

		orderResource.patchOrder(primaryKey, _toOrder(values));

		setExtendedProperties(
			Order.class.getName(), JSONUtil.put("id", primaryKey), user,
			values);
	}

	private OrderResource _buildOrderResource(User user) {
		OrderResource.Builder builder = _orderResourceFactory.create();

		return builder.checkPermissions(
			false
		).preferredLocale(
			user.getLocale()
		).user(
			user
		).build();
	}

	private Order _toOrder(Map<String, Object> values) {
		return new Order() {
			{
				accountId = GetterUtil.getLong(values.get("accountId"));
				channelId = GetterUtil.getLong(values.get("channelId"));
				currencyCode = GetterUtil.getString(values.get("currencyCode"));
				externalReferenceCode = GetterUtil.getString(
					values.get("externalReferenceCode"));
				orderStatus = GetterUtil.getInteger(values.get("orderStatus"));
				orderTypeExternalReferenceCode = GetterUtil.getString(
					values.get("orderTypeExternalReferenceCode"));
				orderTypeId = GetterUtil.getLong(values.get("orderTypeId"));

				setShippingAmount(
					() -> {
						String shippingAmountString = GetterUtil.getString(
							values.get("shippingAmount"));

						if (Validator.isNull(shippingAmountString)) {
							return null;
						}

						return new BigDecimal(shippingAmountString);
					});
			}
		};
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private OrderResource.Factory _orderResourceFactory;

}