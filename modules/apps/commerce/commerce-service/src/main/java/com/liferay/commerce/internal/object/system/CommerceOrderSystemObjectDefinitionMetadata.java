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
import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
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
@Component(service = SystemObjectDefinitionMetadata.class)
public class CommerceOrderSystemObjectDefinitionMetadata
	extends BaseSystemObjectDefinitionMetadata {

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
				"LongInteger", "Long", "account-id", "accountId", true, true),
			createObjectField(
				"LongInteger", "Long", "channel-id", "channelId", true, true),
			createObjectField(
				"Text", "String", "currency-code", "currencyCode", true, true),
			createObjectField(
				"Integer", "Integer", "order-status", "orderStatus", true,
				true),
			createObjectField(
				"PrecisionDecimal", "BigDecimal", "shipping-amount",
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
		return 2;
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

				String shippingAmountString = GetterUtil.getString(
					values.get("shippingAmount"));

				if (Validator.isNotNull(shippingAmountString)) {
					shippingAmount = new BigDecimal(shippingAmountString);
				}
			}
		};
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private OrderResource.Factory _orderResourceFactory;

}