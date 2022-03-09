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

package com.liferay.headless.commerce.admin.channel.internal.graphql.query.v1_0;

import com.liferay.headless.commerce.admin.channel.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.OrderType;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.PaymentMethodGroupRelOrderType;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.PaymentMethodGroupRelTerm;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingFixedOptionOrderType;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingFixedOptionTerm;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingMethod;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.TaxCategory;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.Term;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.OrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelOrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelTermResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingFixedOptionOrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingFixedOptionTermResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingMethodResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TaxCategoryResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TermResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLTypeExtension;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class Query {

	public static void setChannelResourceComponentServiceObjects(
		ComponentServiceObjects<ChannelResource>
			channelResourceComponentServiceObjects) {

		_channelResourceComponentServiceObjects =
			channelResourceComponentServiceObjects;
	}

	public static void setOrderTypeResourceComponentServiceObjects(
		ComponentServiceObjects<OrderTypeResource>
			orderTypeResourceComponentServiceObjects) {

		_orderTypeResourceComponentServiceObjects =
			orderTypeResourceComponentServiceObjects;
	}

	public static void
		setPaymentMethodGroupRelOrderTypeResourceComponentServiceObjects(
			ComponentServiceObjects<PaymentMethodGroupRelOrderTypeResource>
				paymentMethodGroupRelOrderTypeResourceComponentServiceObjects) {

		_paymentMethodGroupRelOrderTypeResourceComponentServiceObjects =
			paymentMethodGroupRelOrderTypeResourceComponentServiceObjects;
	}

	public static void
		setPaymentMethodGroupRelTermResourceComponentServiceObjects(
			ComponentServiceObjects<PaymentMethodGroupRelTermResource>
				paymentMethodGroupRelTermResourceComponentServiceObjects) {

		_paymentMethodGroupRelTermResourceComponentServiceObjects =
			paymentMethodGroupRelTermResourceComponentServiceObjects;
	}

	public static void
		setShippingFixedOptionOrderTypeResourceComponentServiceObjects(
			ComponentServiceObjects<ShippingFixedOptionOrderTypeResource>
				shippingFixedOptionOrderTypeResourceComponentServiceObjects) {

		_shippingFixedOptionOrderTypeResourceComponentServiceObjects =
			shippingFixedOptionOrderTypeResourceComponentServiceObjects;
	}

	public static void
		setShippingFixedOptionTermResourceComponentServiceObjects(
			ComponentServiceObjects<ShippingFixedOptionTermResource>
				shippingFixedOptionTermResourceComponentServiceObjects) {

		_shippingFixedOptionTermResourceComponentServiceObjects =
			shippingFixedOptionTermResourceComponentServiceObjects;
	}

	public static void setShippingMethodResourceComponentServiceObjects(
		ComponentServiceObjects<ShippingMethodResource>
			shippingMethodResourceComponentServiceObjects) {

		_shippingMethodResourceComponentServiceObjects =
			shippingMethodResourceComponentServiceObjects;
	}

	public static void setTaxCategoryResourceComponentServiceObjects(
		ComponentServiceObjects<TaxCategoryResource>
			taxCategoryResourceComponentServiceObjects) {

		_taxCategoryResourceComponentServiceObjects =
			taxCategoryResourceComponentServiceObjects;
	}

	public static void setTermResourceComponentServiceObjects(
		ComponentServiceObjects<TermResource>
			termResourceComponentServiceObjects) {

		_termResourceComponentServiceObjects =
			termResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channels(filter: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves channels.")
	public ChannelPage channels(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> new ChannelPage(
				channelResource.getChannelsPage(
					search,
					_filterBiFunction.apply(channelResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(channelResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelByExternalReferenceCode(externalReferenceCode: ___){currencyCode, externalReferenceCode, id, name, siteGroupId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrive information of the given Channel.")
	public Channel channelByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource ->
				channelResource.getChannelByExternalReferenceCode(
					externalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channel(channelId: ___){currencyCode, externalReferenceCode, id, name, siteGroupId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrive information of the given Channel.")
	public Channel channel(@GraphQLName("channelId") Long channelId)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.getChannel(channelId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {paymentMethodGroupRelOrderTypeOrderType(paymentMethodGroupRelOrderTypeId: ___){id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderType paymentMethodGroupRelOrderTypeOrderType(
			@GraphQLName("paymentMethodGroupRelOrderTypeId") Long
				paymentMethodGroupRelOrderTypeId)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.getPaymentMethodGroupRelOrderTypeOrderType(
					paymentMethodGroupRelOrderTypeId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {shippingFixedOptionOrderTypeOrderType(shippingFixedOptionOrderTypeId: ___){id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public OrderType shippingFixedOptionOrderTypeOrderType(
			@GraphQLName("shippingFixedOptionOrderTypeId") Long
				shippingFixedOptionOrderTypeId)
		throws Exception {

		return _applyComponentServiceObjects(
			_orderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			orderTypeResource ->
				orderTypeResource.getShippingFixedOptionOrderTypeOrderType(
					shippingFixedOptionOrderTypeId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {paymentMethodGroupRelIdPaymentMethodGroupRelOrderTypes(filter: ___, id: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PaymentMethodGroupRelOrderTypePage
			paymentMethodGroupRelIdPaymentMethodGroupRelOrderTypes(
				@GraphQLName("id") Long id,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_paymentMethodGroupRelOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodGroupRelOrderTypeResource ->
				new PaymentMethodGroupRelOrderTypePage(
					paymentMethodGroupRelOrderTypeResource.
						getPaymentMethodGroupRelIdPaymentMethodGroupRelOrderTypesPage(
							id, search,
							_filterBiFunction.apply(
								paymentMethodGroupRelOrderTypeResource,
								filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								paymentMethodGroupRelOrderTypeResource,
								sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {paymentMethodGroupRelIdPaymentMethodGroupRelTerms(filter: ___, id: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PaymentMethodGroupRelTermPage
			paymentMethodGroupRelIdPaymentMethodGroupRelTerms(
				@GraphQLName("id") Long id,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_paymentMethodGroupRelTermResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodGroupRelTermResource ->
				new PaymentMethodGroupRelTermPage(
					paymentMethodGroupRelTermResource.
						getPaymentMethodGroupRelIdPaymentMethodGroupRelTermsPage(
							id, search,
							_filterBiFunction.apply(
								paymentMethodGroupRelTermResource,
								filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								paymentMethodGroupRelTermResource,
								sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {shippingFixedOptionIdShippingFixedOptionOrderTypes(filter: ___, id: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ShippingFixedOptionOrderTypePage
			shippingFixedOptionIdShippingFixedOptionOrderTypes(
				@GraphQLName("id") Long id,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingFixedOptionOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingFixedOptionOrderTypeResource ->
				new ShippingFixedOptionOrderTypePage(
					shippingFixedOptionOrderTypeResource.
						getShippingFixedOptionIdShippingFixedOptionOrderTypesPage(
							id, search,
							_filterBiFunction.apply(
								shippingFixedOptionOrderTypeResource,
								filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								shippingFixedOptionOrderTypeResource,
								sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {shippingFixedOptionIdShippingFixedOptionTerms(filter: ___, id: ___, page: ___, pageSize: ___, search: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public ShippingFixedOptionTermPage
			shippingFixedOptionIdShippingFixedOptionTerms(
				@GraphQLName("id") Long id,
				@GraphQLName("search") String search,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingFixedOptionTermResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingFixedOptionTermResource -> new ShippingFixedOptionTermPage(
				shippingFixedOptionTermResource.
					getShippingFixedOptionIdShippingFixedOptionTermsPage(
						id, search,
						_filterBiFunction.apply(
							shippingFixedOptionTermResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							shippingFixedOptionTermResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {channelShippingMethods(channelId: ___, page: ___, pageSize: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves channel shipping methods.")
	public ShippingMethodPage channelShippingMethods(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingMethodResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingMethodResource -> new ShippingMethodPage(
				shippingMethodResource.getChannelShippingMethodsPage(
					channelId, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxCategories(page: ___, pageSize: ___, search: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaxCategoryPage taxCategories(
			@GraphQLName("search") String search,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxCategoryResource -> new TaxCategoryPage(
				taxCategoryResource.getTaxCategoriesPage(
					search, Pagination.of(page, pageSize))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {taxCategory(id: ___){description, groupId, id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public TaxCategory taxCategory(@GraphQLName("id") Long id)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxCategoryResource -> taxCategoryResource.getTaxCategory(id));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {paymentMethodGroupRelTermTerm(paymentMethodGroupRelTermId: ___){id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Term paymentMethodGroupRelTermTerm(
			@GraphQLName("paymentMethodGroupRelTermId") Long
				paymentMethodGroupRelTermId)
		throws Exception {

		return _applyComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.getPaymentMethodGroupRelTermTerm(
				paymentMethodGroupRelTermId));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {shippingFixedOptionTermTerm(shippingFixedOptionTermId: ___){id, name}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public Term shippingFixedOptionTermTerm(
			@GraphQLName("shippingFixedOptionTermId") Long
				shippingFixedOptionTermId)
		throws Exception {

		return _applyComponentServiceObjects(
			_termResourceComponentServiceObjects,
			this::_populateResourceContext,
			termResource -> termResource.getShippingFixedOptionTermTerm(
				shippingFixedOptionTermId));
	}

	@GraphQLTypeExtension(Channel.class)
	public class GetChannelShippingMethodsPageTypeExtension {

		public GetChannelShippingMethodsPageTypeExtension(Channel channel) {
			_channel = channel;
		}

		@GraphQLField(description = "Retrieves channel shipping methods.")
		public ShippingMethodPage shippingMethods(
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page)
			throws Exception {

			return _applyComponentServiceObjects(
				_shippingMethodResourceComponentServiceObjects,
				Query.this::_populateResourceContext,
				shippingMethodResource -> new ShippingMethodPage(
					shippingMethodResource.getChannelShippingMethodsPage(
						_channel.getId(), Pagination.of(page, pageSize))));
		}

		private Channel _channel;

	}

	@GraphQLName("ChannelPage")
	public class ChannelPage {

		public ChannelPage(Page channelPage) {
			actions = channelPage.getActions();

			items = channelPage.getItems();
			lastPage = channelPage.getLastPage();
			page = channelPage.getPage();
			pageSize = channelPage.getPageSize();
			totalCount = channelPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Channel> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("OrderTypePage")
	public class OrderTypePage {

		public OrderTypePage(Page orderTypePage) {
			actions = orderTypePage.getActions();

			items = orderTypePage.getItems();
			lastPage = orderTypePage.getLastPage();
			page = orderTypePage.getPage();
			pageSize = orderTypePage.getPageSize();
			totalCount = orderTypePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<OrderType> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PaymentMethodGroupRelOrderTypePage")
	public class PaymentMethodGroupRelOrderTypePage {

		public PaymentMethodGroupRelOrderTypePage(
			Page paymentMethodGroupRelOrderTypePage) {

			actions = paymentMethodGroupRelOrderTypePage.getActions();

			items = paymentMethodGroupRelOrderTypePage.getItems();
			lastPage = paymentMethodGroupRelOrderTypePage.getLastPage();
			page = paymentMethodGroupRelOrderTypePage.getPage();
			pageSize = paymentMethodGroupRelOrderTypePage.getPageSize();
			totalCount = paymentMethodGroupRelOrderTypePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<PaymentMethodGroupRelOrderType> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PaymentMethodGroupRelTermPage")
	public class PaymentMethodGroupRelTermPage {

		public PaymentMethodGroupRelTermPage(
			Page paymentMethodGroupRelTermPage) {

			actions = paymentMethodGroupRelTermPage.getActions();

			items = paymentMethodGroupRelTermPage.getItems();
			lastPage = paymentMethodGroupRelTermPage.getLastPage();
			page = paymentMethodGroupRelTermPage.getPage();
			pageSize = paymentMethodGroupRelTermPage.getPageSize();
			totalCount = paymentMethodGroupRelTermPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<PaymentMethodGroupRelTerm> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ShippingFixedOptionOrderTypePage")
	public class ShippingFixedOptionOrderTypePage {

		public ShippingFixedOptionOrderTypePage(
			Page shippingFixedOptionOrderTypePage) {

			actions = shippingFixedOptionOrderTypePage.getActions();

			items = shippingFixedOptionOrderTypePage.getItems();
			lastPage = shippingFixedOptionOrderTypePage.getLastPage();
			page = shippingFixedOptionOrderTypePage.getPage();
			pageSize = shippingFixedOptionOrderTypePage.getPageSize();
			totalCount = shippingFixedOptionOrderTypePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ShippingFixedOptionOrderType> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ShippingFixedOptionTermPage")
	public class ShippingFixedOptionTermPage {

		public ShippingFixedOptionTermPage(Page shippingFixedOptionTermPage) {
			actions = shippingFixedOptionTermPage.getActions();

			items = shippingFixedOptionTermPage.getItems();
			lastPage = shippingFixedOptionTermPage.getLastPage();
			page = shippingFixedOptionTermPage.getPage();
			pageSize = shippingFixedOptionTermPage.getPageSize();
			totalCount = shippingFixedOptionTermPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ShippingFixedOptionTerm> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("ShippingMethodPage")
	public class ShippingMethodPage {

		public ShippingMethodPage(Page shippingMethodPage) {
			actions = shippingMethodPage.getActions();

			items = shippingMethodPage.getItems();
			lastPage = shippingMethodPage.getLastPage();
			page = shippingMethodPage.getPage();
			pageSize = shippingMethodPage.getPageSize();
			totalCount = shippingMethodPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<ShippingMethod> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TaxCategoryPage")
	public class TaxCategoryPage {

		public TaxCategoryPage(Page taxCategoryPage) {
			actions = taxCategoryPage.getActions();

			items = taxCategoryPage.getItems();
			lastPage = taxCategoryPage.getLastPage();
			page = taxCategoryPage.getPage();
			pageSize = taxCategoryPage.getPageSize();
			totalCount = taxCategoryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<TaxCategory> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("TermPage")
	public class TermPage {

		public TermPage(Page termPage) {
			actions = termPage.getActions();

			items = termPage.getItems();
			lastPage = termPage.getLastPage();
			page = termPage.getPage();
			pageSize = termPage.getPageSize();
			totalCount = termPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map> actions;

		@GraphQLField
		protected java.util.Collection<Term> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(ChannelResource channelResource)
		throws Exception {

		channelResource.setContextAcceptLanguage(_acceptLanguage);
		channelResource.setContextCompany(_company);
		channelResource.setContextHttpServletRequest(_httpServletRequest);
		channelResource.setContextHttpServletResponse(_httpServletResponse);
		channelResource.setContextUriInfo(_uriInfo);
		channelResource.setContextUser(_user);
		channelResource.setGroupLocalService(_groupLocalService);
		channelResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(OrderTypeResource orderTypeResource)
		throws Exception {

		orderTypeResource.setContextAcceptLanguage(_acceptLanguage);
		orderTypeResource.setContextCompany(_company);
		orderTypeResource.setContextHttpServletRequest(_httpServletRequest);
		orderTypeResource.setContextHttpServletResponse(_httpServletResponse);
		orderTypeResource.setContextUriInfo(_uriInfo);
		orderTypeResource.setContextUser(_user);
		orderTypeResource.setGroupLocalService(_groupLocalService);
		orderTypeResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PaymentMethodGroupRelOrderTypeResource
				paymentMethodGroupRelOrderTypeResource)
		throws Exception {

		paymentMethodGroupRelOrderTypeResource.setContextAcceptLanguage(
			_acceptLanguage);
		paymentMethodGroupRelOrderTypeResource.setContextCompany(_company);
		paymentMethodGroupRelOrderTypeResource.setContextHttpServletRequest(
			_httpServletRequest);
		paymentMethodGroupRelOrderTypeResource.setContextHttpServletResponse(
			_httpServletResponse);
		paymentMethodGroupRelOrderTypeResource.setContextUriInfo(_uriInfo);
		paymentMethodGroupRelOrderTypeResource.setContextUser(_user);
		paymentMethodGroupRelOrderTypeResource.setGroupLocalService(
			_groupLocalService);
		paymentMethodGroupRelOrderTypeResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			PaymentMethodGroupRelTermResource paymentMethodGroupRelTermResource)
		throws Exception {

		paymentMethodGroupRelTermResource.setContextAcceptLanguage(
			_acceptLanguage);
		paymentMethodGroupRelTermResource.setContextCompany(_company);
		paymentMethodGroupRelTermResource.setContextHttpServletRequest(
			_httpServletRequest);
		paymentMethodGroupRelTermResource.setContextHttpServletResponse(
			_httpServletResponse);
		paymentMethodGroupRelTermResource.setContextUriInfo(_uriInfo);
		paymentMethodGroupRelTermResource.setContextUser(_user);
		paymentMethodGroupRelTermResource.setGroupLocalService(
			_groupLocalService);
		paymentMethodGroupRelTermResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			ShippingFixedOptionOrderTypeResource
				shippingFixedOptionOrderTypeResource)
		throws Exception {

		shippingFixedOptionOrderTypeResource.setContextAcceptLanguage(
			_acceptLanguage);
		shippingFixedOptionOrderTypeResource.setContextCompany(_company);
		shippingFixedOptionOrderTypeResource.setContextHttpServletRequest(
			_httpServletRequest);
		shippingFixedOptionOrderTypeResource.setContextHttpServletResponse(
			_httpServletResponse);
		shippingFixedOptionOrderTypeResource.setContextUriInfo(_uriInfo);
		shippingFixedOptionOrderTypeResource.setContextUser(_user);
		shippingFixedOptionOrderTypeResource.setGroupLocalService(
			_groupLocalService);
		shippingFixedOptionOrderTypeResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			ShippingFixedOptionTermResource shippingFixedOptionTermResource)
		throws Exception {

		shippingFixedOptionTermResource.setContextAcceptLanguage(
			_acceptLanguage);
		shippingFixedOptionTermResource.setContextCompany(_company);
		shippingFixedOptionTermResource.setContextHttpServletRequest(
			_httpServletRequest);
		shippingFixedOptionTermResource.setContextHttpServletResponse(
			_httpServletResponse);
		shippingFixedOptionTermResource.setContextUriInfo(_uriInfo);
		shippingFixedOptionTermResource.setContextUser(_user);
		shippingFixedOptionTermResource.setGroupLocalService(
			_groupLocalService);
		shippingFixedOptionTermResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			ShippingMethodResource shippingMethodResource)
		throws Exception {

		shippingMethodResource.setContextAcceptLanguage(_acceptLanguage);
		shippingMethodResource.setContextCompany(_company);
		shippingMethodResource.setContextHttpServletRequest(
			_httpServletRequest);
		shippingMethodResource.setContextHttpServletResponse(
			_httpServletResponse);
		shippingMethodResource.setContextUriInfo(_uriInfo);
		shippingMethodResource.setContextUser(_user);
		shippingMethodResource.setGroupLocalService(_groupLocalService);
		shippingMethodResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			TaxCategoryResource taxCategoryResource)
		throws Exception {

		taxCategoryResource.setContextAcceptLanguage(_acceptLanguage);
		taxCategoryResource.setContextCompany(_company);
		taxCategoryResource.setContextHttpServletRequest(_httpServletRequest);
		taxCategoryResource.setContextHttpServletResponse(_httpServletResponse);
		taxCategoryResource.setContextUriInfo(_uriInfo);
		taxCategoryResource.setContextUser(_user);
		taxCategoryResource.setGroupLocalService(_groupLocalService);
		taxCategoryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(TermResource termResource)
		throws Exception {

		termResource.setContextAcceptLanguage(_acceptLanguage);
		termResource.setContextCompany(_company);
		termResource.setContextHttpServletRequest(_httpServletRequest);
		termResource.setContextHttpServletResponse(_httpServletResponse);
		termResource.setContextUriInfo(_uriInfo);
		termResource.setContextUser(_user);
		termResource.setGroupLocalService(_groupLocalService);
		termResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;
	private static ComponentServiceObjects<OrderTypeResource>
		_orderTypeResourceComponentServiceObjects;
	private static ComponentServiceObjects
		<PaymentMethodGroupRelOrderTypeResource>
			_paymentMethodGroupRelOrderTypeResourceComponentServiceObjects;
	private static ComponentServiceObjects<PaymentMethodGroupRelTermResource>
		_paymentMethodGroupRelTermResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShippingFixedOptionOrderTypeResource>
		_shippingFixedOptionOrderTypeResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShippingFixedOptionTermResource>
		_shippingFixedOptionTermResourceComponentServiceObjects;
	private static ComponentServiceObjects<ShippingMethodResource>
		_shippingMethodResourceComponentServiceObjects;
	private static ComponentServiceObjects<TaxCategoryResource>
		_taxCategoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<TermResource>
		_termResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}