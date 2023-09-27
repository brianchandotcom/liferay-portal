/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		Mutation.setShippingMethodResourceComponentServiceObjects(
			_shippingMethodResourceComponentServiceObjects);
		Mutation.setTaxCategoryResourceComponentServiceObjects(
			_taxCategoryResourceComponentServiceObjects);

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

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createChannelsPageExportBatch",
						new ObjectValuePair<>(
							ChannelResourceImpl.class,
							"postChannelsPageExportBatch"));
					put(
						"mutation#createChannel",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "postChannel"));
					put(
						"mutation#createChannelBatch",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "postChannelBatch"));
					put(
						"mutation#deleteChannelByExternalReferenceCode",
						new ObjectValuePair<>(
							ChannelResourceImpl.class,
							"deleteChannelByExternalReferenceCode"));
					put(
						"mutation#patchChannelByExternalReferenceCode",
						new ObjectValuePair<>(
							ChannelResourceImpl.class,
							"patchChannelByExternalReferenceCode"));
					put(
						"mutation#updateChannelByExternalReferenceCode",
						new ObjectValuePair<>(
							ChannelResourceImpl.class,
							"putChannelByExternalReferenceCode"));
					put(
						"mutation#deleteChannel",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "deleteChannel"));
					put(
						"mutation#deleteChannelBatch",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "deleteChannelBatch"));
					put(
						"mutation#patchChannel",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "patchChannel"));
					put(
						"mutation#updateChannel",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "putChannel"));
					put(
						"mutation#updateChannelBatch",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "putChannelBatch"));
					put(
						"mutation#deletePaymentMethodGroupRelOrderType",
						new ObjectValuePair<>(
							PaymentMethodGroupRelOrderTypeResourceImpl.class,
							"deletePaymentMethodGroupRelOrderType"));
					put(
						"mutation#deletePaymentMethodGroupRelOrderTypeBatch",
						new ObjectValuePair<>(
							PaymentMethodGroupRelOrderTypeResourceImpl.class,
							"deletePaymentMethodGroupRelOrderTypeBatch"));
					put(
						"mutation#createPaymentMethodGroupRelIdPaymentMethodGroupRelOrderType",
						new ObjectValuePair<>(
							PaymentMethodGroupRelOrderTypeResourceImpl.class,
							"postPaymentMethodGroupRelIdPaymentMethodGroupRelOrderType"));
					put(
						"mutation#deletePaymentMethodGroupRelTerm",
						new ObjectValuePair<>(
							PaymentMethodGroupRelTermResourceImpl.class,
							"deletePaymentMethodGroupRelTerm"));
					put(
						"mutation#deletePaymentMethodGroupRelTermBatch",
						new ObjectValuePair<>(
							PaymentMethodGroupRelTermResourceImpl.class,
							"deletePaymentMethodGroupRelTermBatch"));
					put(
						"mutation#createPaymentMethodGroupRelIdPaymentMethodGroupRelTerm",
						new ObjectValuePair<>(
							PaymentMethodGroupRelTermResourceImpl.class,
							"postPaymentMethodGroupRelIdPaymentMethodGroupRelTerm"));
					put(
						"mutation#deleteShippingFixedOptionOrderType",
						new ObjectValuePair<>(
							ShippingFixedOptionOrderTypeResourceImpl.class,
							"deleteShippingFixedOptionOrderType"));
					put(
						"mutation#deleteShippingFixedOptionOrderTypeBatch",
						new ObjectValuePair<>(
							ShippingFixedOptionOrderTypeResourceImpl.class,
							"deleteShippingFixedOptionOrderTypeBatch"));
					put(
						"mutation#createShippingFixedOptionIdShippingFixedOptionOrderType",
						new ObjectValuePair<>(
							ShippingFixedOptionOrderTypeResourceImpl.class,
							"postShippingFixedOptionIdShippingFixedOptionOrderType"));
					put(
						"mutation#deleteShippingFixedOptionTerm",
						new ObjectValuePair<>(
							ShippingFixedOptionTermResourceImpl.class,
							"deleteShippingFixedOptionTerm"));
					put(
						"mutation#deleteShippingFixedOptionTermBatch",
						new ObjectValuePair<>(
							ShippingFixedOptionTermResourceImpl.class,
							"deleteShippingFixedOptionTermBatch"));
					put(
						"mutation#createShippingFixedOptionIdShippingFixedOptionTerm",
						new ObjectValuePair<>(
							ShippingFixedOptionTermResourceImpl.class,
							"postShippingFixedOptionIdShippingFixedOptionTerm"));
					put(
						"mutation#createChannelShippingMethodsPageExportBatch",
						new ObjectValuePair<>(
							ShippingMethodResourceImpl.class,
							"postChannelShippingMethodsPageExportBatch"));
					put(
						"mutation#createTaxCategoriesPageExportBatch",
						new ObjectValuePair<>(
							TaxCategoryResourceImpl.class,
							"postTaxCategoriesPageExportBatch"));

					put(
						"query#channels",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "getChannelsPage"));
					put(
						"query#channelByExternalReferenceCode",
						new ObjectValuePair<>(
							ChannelResourceImpl.class,
							"getChannelByExternalReferenceCode"));
					put(
						"query#channel",
						new ObjectValuePair<>(
							ChannelResourceImpl.class, "getChannel"));
					put(
						"query#paymentMethodGroupRelOrderTypeOrderType",
						new ObjectValuePair<>(
							OrderTypeResourceImpl.class,
							"getPaymentMethodGroupRelOrderTypeOrderType"));
					put(
						"query#shippingFixedOptionOrderTypeOrderType",
						new ObjectValuePair<>(
							OrderTypeResourceImpl.class,
							"getShippingFixedOptionOrderTypeOrderType"));
					put(
						"query#paymentMethodGroupRelIdPaymentMethodGroupRelOrderTypes",
						new ObjectValuePair<>(
							PaymentMethodGroupRelOrderTypeResourceImpl.class,
							"getPaymentMethodGroupRelIdPaymentMethodGroupRelOrderTypesPage"));
					put(
						"query#paymentMethodGroupRelIdPaymentMethodGroupRelTerms",
						new ObjectValuePair<>(
							PaymentMethodGroupRelTermResourceImpl.class,
							"getPaymentMethodGroupRelIdPaymentMethodGroupRelTermsPage"));
					put(
						"query#shippingFixedOptionIdShippingFixedOptionOrderTypes",
						new ObjectValuePair<>(
							ShippingFixedOptionOrderTypeResourceImpl.class,
							"getShippingFixedOptionIdShippingFixedOptionOrderTypesPage"));
					put(
						"query#shippingFixedOptionIdShippingFixedOptionTerms",
						new ObjectValuePair<>(
							ShippingFixedOptionTermResourceImpl.class,
							"getShippingFixedOptionIdShippingFixedOptionTermsPage"));
					put(
						"query#channelShippingMethods",
						new ObjectValuePair<>(
							ShippingMethodResourceImpl.class,
							"getChannelShippingMethodsPage"));
					put(
						"query#taxCategories",
						new ObjectValuePair<>(
							TaxCategoryResourceImpl.class,
							"getTaxCategoriesPage"));
					put(
						"query#taxCategory",
						new ObjectValuePair<>(
							TaxCategoryResourceImpl.class, "getTaxCategory"));
					put(
						"query#paymentMethodGroupRelTermTerm",
						new ObjectValuePair<>(
							TermResourceImpl.class,
							"getPaymentMethodGroupRelTermTerm"));
					put(
						"query#shippingFixedOptionTermTerm",
						new ObjectValuePair<>(
							TermResourceImpl.class,
							"getShippingFixedOptionTermTerm"));

					put(
						"query#Channel.shippingMethods",
						new ObjectValuePair<>(
							ShippingMethodResourceImpl.class,
							"getChannelShippingMethodsPage"));
				}
			};

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
	private ComponentServiceObjects<ShippingMethodResource>
		_shippingMethodResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TaxCategoryResource>
		_taxCategoryResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<OrderTypeResource>
		_orderTypeResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<TermResource>
		_termResourceComponentServiceObjects;

}