/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.object.system;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderTable;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.headless.commerce.admin.order.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.IntegerObjectFieldBuilder;
import com.liferay.object.field.builder.LongIntegerObjectFieldBuilder;
import com.liferay.object.field.builder.PrecisionDecimalObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
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
	public String getBaseModelExternalReferenceCode(long primaryKey)
		throws PortalException {

		CommerceOrder commerceOrder =
			_commerceOrderLocalService.getCommerceOrder(primaryKey);

		return commerceOrder.getExternalReferenceCode();
	}

	@Override
	public String getExternalReferenceCode() {
		return "L_COMMERCE_ORDER";
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
			new LongIntegerObjectFieldBuilder(
			).labelMap(
				createLabelMap("account-id")
			).name(
				"accountId"
			).required(
				true
			).system(
				true
			).build(),
			new LongIntegerObjectFieldBuilder(
			).labelMap(
				createLabelMap("channel-id")
			).name(
				"channelId"
			).required(
				true
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).labelMap(
				createLabelMap("currency-code")
			).name(
				"currencyCode"
			).required(
				true
			).system(
				true
			).build(),
			new IntegerObjectFieldBuilder(
			).labelMap(
				createLabelMap("order-status")
			).name(
				"orderStatus"
			).required(
				true
			).system(
				true
			).build(),
			new TextObjectFieldBuilder(
			).dbColumnName(
				"orderTypeExternalReferenceCode"
			).labelMap(
				createLabelMap("orderTypeExternalReferenceCode")
			).name(
				"orderTypeExternalReferenceCode"
			).system(
				true
			).build(),
			new LongIntegerObjectFieldBuilder(
			).dbColumnName(
				"orderTypeId"
			).labelMap(
				createLabelMap("order-type-id")
			).name(
				"orderTypeId"
			).system(
				true
			).build(),
			new PrecisionDecimalObjectFieldBuilder(
			).labelMap(
				createLabelMap("shipping-amount")
			).name(
				"shippingAmount"
			).required(
				true
			).system(
				true
			).build());
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