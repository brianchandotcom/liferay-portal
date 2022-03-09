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

package com.liferay.headless.commerce.admin.channel.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.admin.channel.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.admin.channel.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.OrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelOrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelTermResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingFixedOptionOrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingFixedOptionTermResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingMethodResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TaxCategoryResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TermResource;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Component(enabled = false, immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Mutation.
			setPaymentMethodGroupRelOrderTypeResourceComponentServiceObjects(
				_paymentMethodGroupRelOrderTypeResourceComponentServiceObjects);
		Mutation.setPaymentMethodGroupRelTermResourceComponentServiceObjects(
			_paymentMethodGroupRelTermResourceComponentServiceObjects);
		Mutation.setShippingFixedOptionOrderTypeResourceComponentServiceObjects(
			_shippingFixedOptionOrderTypeResourceComponentServiceObjects);
		Mutation.setShippingFixedOptionTermResourceComponentServiceObjects(
			_shippingFixedOptionTermResourceComponentServiceObjects);

		Query.setChannelResourceComponentServiceObjects(
			_channelResourceComponentServiceObjects);
		Query.setOrderTypeResourceComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects);
		Query.setPaymentMethodGroupRelOrderTypeResourceComponentServiceObjects(
			_paymentMethodGroupRelOrderTypeResourceComponentServiceObjects);
		Query.setPaymentMethodGroupRelTermResourceComponentServiceObjects(
			_paymentMethodGroupRelTermResourceComponentServiceObjects);
		Query.setShippingFixedOptionOrderTypeResourceComponentServiceObjects(
			_shippingFixedOptionOrderTypeResourceComponentServiceObjects);
		Query.setShippingFixedOptionTermResourceComponentServiceObjects(
			_shippingFixedOptionTermResourceComponentServiceObjects);
		Query.setShippingMethodResourceComponentServiceObjects(
			_shippingMethodResourceComponentServiceObjects);
		Query.setTaxCategoryResourceComponentServiceObjects(
			_taxCategoryResourceComponentServiceObjects);
		Query.setTermResourceComponentServiceObjects(
			_termResourceComponentServiceObjects);
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-admin-channel-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PaymentMethodGroupRelOrderTypeResource>
		_paymentMethodGroupRelOrderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PaymentMethodGroupRelTermResource>
		_paymentMethodGroupRelTermResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShippingFixedOptionOrderTypeResource>
		_shippingFixedOptionOrderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShippingFixedOptionTermResource>
		_shippingFixedOptionTermResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderTypeResource>
		_orderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<ShippingMethodResource>
		_shippingMethodResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TaxCategoryResource>
		_taxCategoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TermResource>
		_termResourceComponentServiceObjects;

}