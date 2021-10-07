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

package com.liferay.headless.commerce.admin.order.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.order.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.order.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.order.resource.v1_0.AccountGroupResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.AccountResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.BillingAddressResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderNoteResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleAccountGroupResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleAccountResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleChannelResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleOrderTypeResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderRuleResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderTypeChannelResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.OrderTypeResource;
import com.liferay.headless.commerce.admin.order.resource.v1_0.ShippingAddressResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(enabled = false, immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setBillingAddressResourceComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects);
		Mutation.setOrderResourceComponentServiceObjects(
			_orderResourceComponentServiceObjects);
		Mutation.setOrderItemResourceComponentServiceObjects(
			_orderItemResourceComponentServiceObjects);
		Mutation.setOrderNoteResourceComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects);
		Mutation.setOrderRuleResourceComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects);
		Mutation.setOrderRuleAccountResourceComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects);
		Mutation.setOrderRuleAccountGroupResourceComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects);
		Mutation.setOrderRuleChannelResourceComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects);
		Mutation.setOrderRuleOrderTypeResourceComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects);
		Mutation.setOrderTypeResourceComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects);
		Mutation.setOrderTypeChannelResourceComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects);
		Mutation.setShippingAddressResourceComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects);

		Query.setAccountResourceComponentServiceObjects(
			_accountResourceComponentServiceObjects);
		Query.setAccountGroupResourceComponentServiceObjects(
			_accountGroupResourceComponentServiceObjects);
		Query.setBillingAddressResourceComponentServiceObjects(
			_billingAddressResourceComponentServiceObjects);
		Query.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Query.setOrderResourceComponentServiceObjects(
			_orderResourceComponentServiceObjects);
		Query.setOrderItemResourceComponentServiceObjects(
			_orderItemResourceComponentServiceObjects);
		Query.setOrderNoteResourceComponentServiceObjects(
			_orderNoteResourceComponentServiceObjects);
		Query.setOrderRuleResourceComponentServiceObjects(
			_orderRuleResourceComponentServiceObjects);
		Query.setOrderRuleAccountResourceComponentServiceObjects(
			_orderRuleAccountResourceComponentServiceObjects);
		Query.setOrderRuleAccountGroupResourceComponentServiceObjects(
			_orderRuleAccountGroupResourceComponentServiceObjects);
		Query.setOrderRuleChannelResourceComponentServiceObjects(
			_orderRuleChannelResourceComponentServiceObjects);
		Query.setOrderRuleOrderTypeResourceComponentServiceObjects(
			_orderRuleOrderTypeResourceComponentServiceObjects);
		Query.setOrderTypeResourceComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects);
		Query.setOrderTypeChannelResourceComponentServiceObjects(
			_orderTypeChannelResourceComponentServiceObjects);
		Query.setShippingAddressResourceComponentServiceObjects(
			_shippingAddressResourceComponentServiceObjects);
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-admin-order-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<BillingAddressResource>
		_billingAddressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderResource>
		_orderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderItemResource>
		_orderItemResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderNoteResource>
		_orderNoteResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleResource>
		_orderRuleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleAccountResource>
		_orderRuleAccountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleAccountGroupResource>
		_orderRuleAccountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleChannelResource>
		_orderRuleChannelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderRuleOrderTypeResource>
		_orderRuleOrderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderTypeResource>
		_orderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderTypeChannelResource>
		_orderTypeChannelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShippingAddressResource>
		_shippingAddressResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountResource>
		_accountResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountGroupResource>
		_accountGroupResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;

}