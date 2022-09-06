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
import com.liferay.headless.commerce.admin.channel.internal.resource.v1_0.ChannelResourceImpl;
import com.liferay.headless.commerce.admin.channel.internal.resource.v1_0.OrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.channel.internal.resource.v1_0.PaymentMethodGroupRelOrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.channel.internal.resource.v1_0.PaymentMethodGroupRelTermResourceImpl;
import com.liferay.headless.commerce.admin.channel.internal.resource.v1_0.ShippingFixedOptionOrderTypeResourceImpl;
import com.liferay.headless.commerce.admin.channel.internal.resource.v1_0.ShippingFixedOptionTermResourceImpl;
import com.liferay.headless.commerce.admin.channel.internal.resource.v1_0.ShippingMethodResourceImpl;
import com.liferay.headless.commerce.admin.channel.internal.resource.v1_0.TaxCategoryResourceImpl;
import com.liferay.headless.commerce.admin.channel.internal.resource.v1_0.TermResourceImpl;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.OrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelOrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelTermResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingFixedOptionOrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingFixedOptionTermResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingMethodResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TaxCategoryResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TermResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

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
@Component(service = ServletData.class)
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

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Admin.Channel";
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

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"mutation#createChannel",
			new ObjectValuePair<>(ChannelResourceImpl.class, "postChannel"));
		_resourceMethodPairs.put(
			"mutation#createChannelBatch",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "postChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#deleteChannelByExternalReferenceCode",
			new ObjectValuePair<>(
				ChannelResourceImpl.class,
				"deleteChannelByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#patchChannelByExternalReferenceCode",
			new ObjectValuePair<>(
				ChannelResourceImpl.class,
				"patchChannelByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#updateChannelByExternalReferenceCode",
			new ObjectValuePair<>(
				ChannelResourceImpl.class,
				"putChannelByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"mutation#deleteChannel",
			new ObjectValuePair<>(ChannelResourceImpl.class, "deleteChannel"));
		_resourceMethodPairs.put(
			"mutation#deleteChannelBatch",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "deleteChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#patchChannel",
			new ObjectValuePair<>(ChannelResourceImpl.class, "patchChannel"));
		_resourceMethodPairs.put(
			"mutation#updateChannel",
			new ObjectValuePair<>(ChannelResourceImpl.class, "putChannel"));
		_resourceMethodPairs.put(
			"mutation#updateChannelBatch",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "putChannelBatch"));
		_resourceMethodPairs.put(
			"mutation#deletePaymentMethodGroupRelOrderType",
			new ObjectValuePair<>(
				PaymentMethodGroupRelOrderTypeResourceImpl.class,
				"deletePaymentMethodGroupRelOrderType"));
		_resourceMethodPairs.put(
			"mutation#deletePaymentMethodGroupRelOrderTypeBatch",
			new ObjectValuePair<>(
				PaymentMethodGroupRelOrderTypeResourceImpl.class,
				"deletePaymentMethodGroupRelOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#createPaymentMethodGroupRelIdPaymentMethodGroupRelOrderType",
			new ObjectValuePair<>(
				PaymentMethodGroupRelOrderTypeResourceImpl.class,
				"postPaymentMethodGroupRelIdPaymentMethodGroupRelOrderType"));
		_resourceMethodPairs.put(
			"mutation#deletePaymentMethodGroupRelTerm",
			new ObjectValuePair<>(
				PaymentMethodGroupRelTermResourceImpl.class,
				"deletePaymentMethodGroupRelTerm"));
		_resourceMethodPairs.put(
			"mutation#deletePaymentMethodGroupRelTermBatch",
			new ObjectValuePair<>(
				PaymentMethodGroupRelTermResourceImpl.class,
				"deletePaymentMethodGroupRelTermBatch"));
		_resourceMethodPairs.put(
			"mutation#createPaymentMethodGroupRelIdPaymentMethodGroupRelTerm",
			new ObjectValuePair<>(
				PaymentMethodGroupRelTermResourceImpl.class,
				"postPaymentMethodGroupRelIdPaymentMethodGroupRelTerm"));
		_resourceMethodPairs.put(
			"mutation#deleteShippingFixedOptionOrderType",
			new ObjectValuePair<>(
				ShippingFixedOptionOrderTypeResourceImpl.class,
				"deleteShippingFixedOptionOrderType"));
		_resourceMethodPairs.put(
			"mutation#deleteShippingFixedOptionOrderTypeBatch",
			new ObjectValuePair<>(
				ShippingFixedOptionOrderTypeResourceImpl.class,
				"deleteShippingFixedOptionOrderTypeBatch"));
		_resourceMethodPairs.put(
			"mutation#createShippingFixedOptionIdShippingFixedOptionOrderType",
			new ObjectValuePair<>(
				ShippingFixedOptionOrderTypeResourceImpl.class,
				"postShippingFixedOptionIdShippingFixedOptionOrderType"));
		_resourceMethodPairs.put(
			"mutation#deleteShippingFixedOptionTerm",
			new ObjectValuePair<>(
				ShippingFixedOptionTermResourceImpl.class,
				"deleteShippingFixedOptionTerm"));
		_resourceMethodPairs.put(
			"mutation#deleteShippingFixedOptionTermBatch",
			new ObjectValuePair<>(
				ShippingFixedOptionTermResourceImpl.class,
				"deleteShippingFixedOptionTermBatch"));
		_resourceMethodPairs.put(
			"mutation#createShippingFixedOptionIdShippingFixedOptionTerm",
			new ObjectValuePair<>(
				ShippingFixedOptionTermResourceImpl.class,
				"postShippingFixedOptionIdShippingFixedOptionTerm"));
		_resourceMethodPairs.put(
			"query#channels",
			new ObjectValuePair<>(
				ChannelResourceImpl.class, "getChannelsPage"));
		_resourceMethodPairs.put(
			"query#channelByExternalReferenceCode",
			new ObjectValuePair<>(
				ChannelResourceImpl.class,
				"getChannelByExternalReferenceCode"));
		_resourceMethodPairs.put(
			"query#channel",
			new ObjectValuePair<>(ChannelResourceImpl.class, "getChannel"));
		_resourceMethodPairs.put(
			"query#paymentMethodGroupRelOrderTypeOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class,
				"getPaymentMethodGroupRelOrderTypeOrderType"));
		_resourceMethodPairs.put(
			"query#shippingFixedOptionOrderTypeOrderType",
			new ObjectValuePair<>(
				OrderTypeResourceImpl.class,
				"getShippingFixedOptionOrderTypeOrderType"));
		_resourceMethodPairs.put(
			"query#paymentMethodGroupRelIdPaymentMethodGroupRelOrderTypes",
			new ObjectValuePair<>(
				PaymentMethodGroupRelOrderTypeResourceImpl.class,
				"getPaymentMethodGroupRelIdPaymentMethodGroupRelOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#paymentMethodGroupRelIdPaymentMethodGroupRelTerms",
			new ObjectValuePair<>(
				PaymentMethodGroupRelTermResourceImpl.class,
				"getPaymentMethodGroupRelIdPaymentMethodGroupRelTermsPage"));
		_resourceMethodPairs.put(
			"query#shippingFixedOptionIdShippingFixedOptionOrderTypes",
			new ObjectValuePair<>(
				ShippingFixedOptionOrderTypeResourceImpl.class,
				"getShippingFixedOptionIdShippingFixedOptionOrderTypesPage"));
		_resourceMethodPairs.put(
			"query#shippingFixedOptionIdShippingFixedOptionTerms",
			new ObjectValuePair<>(
				ShippingFixedOptionTermResourceImpl.class,
				"getShippingFixedOptionIdShippingFixedOptionTermsPage"));
		_resourceMethodPairs.put(
			"query#channelShippingMethods",
			new ObjectValuePair<>(
				ShippingMethodResourceImpl.class,
				"getChannelShippingMethodsPage"));
		_resourceMethodPairs.put(
			"query#taxCategories",
			new ObjectValuePair<>(
				TaxCategoryResourceImpl.class, "getTaxCategoriesPage"));
		_resourceMethodPairs.put(
			"query#taxCategory",
			new ObjectValuePair<>(
				TaxCategoryResourceImpl.class, "getTaxCategory"));
		_resourceMethodPairs.put(
			"query#paymentMethodGroupRelTermTerm",
			new ObjectValuePair<>(
				TermResourceImpl.class, "getPaymentMethodGroupRelTermTerm"));
		_resourceMethodPairs.put(
			"query#shippingFixedOptionTermTerm",
			new ObjectValuePair<>(
				TermResourceImpl.class, "getShippingFixedOptionTermTerm"));
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