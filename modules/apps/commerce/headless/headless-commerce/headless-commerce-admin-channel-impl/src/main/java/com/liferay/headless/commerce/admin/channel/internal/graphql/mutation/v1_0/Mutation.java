/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.channel.internal.graphql.mutation.v1_0;

import com.liferay.headless.commerce.admin.channel.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.PaymentMethodGroupRelOrderType;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.PaymentMethodGroupRelTerm;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingFixedOptionOrderType;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.ShippingFixedOptionTerm;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ChannelResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelOrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.PaymentMethodGroupRelTermResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingFixedOptionOrderTypeResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingFixedOptionTermResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.ShippingMethodResource;
import com.liferay.headless.commerce.admin.channel.resource.v1_0.TaxCategoryResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineExportTaskResource;
import com.liferay.portal.vulcan.batch.engine.resource.VulcanBatchEngineImportTaskResource;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;

import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public class Mutation {

	public static void setChannelResourceComponentServiceObjects(
		ComponentServiceObjects<ChannelResource>
			channelResourceComponentServiceObjects) {

		_channelResourceComponentServiceObjects =
			channelResourceComponentServiceObjects;
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

	@GraphQLField
	public Response createChannelsPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("sort") String sortsString,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.postChannelsPageExportBatch(
				search, _filterBiFunction.apply(channelResource, filterString),
				_sortsBiFunction.apply(channelResource, sortsString),
				callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public Channel createChannel(@GraphQLName("channel") Channel channel)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.postChannel(channel));
	}

	@GraphQLField
	public Response createChannelBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.postChannelBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean deleteChannelByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource ->
				channelResource.deleteChannelByExternalReferenceCode(
					externalReferenceCode));

		return true;
	}

	@GraphQLField
	public Channel patchChannelByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("channel") Channel channel)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource ->
				channelResource.patchChannelByExternalReferenceCode(
					externalReferenceCode, channel));
	}

	@GraphQLField
	public Channel updateChannelByExternalReferenceCode(
			@GraphQLName("externalReferenceCode") String externalReferenceCode,
			@GraphQLName("channel") Channel channel)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource ->
				channelResource.putChannelByExternalReferenceCode(
					externalReferenceCode, channel));
	}

	@GraphQLField
	public boolean deleteChannel(@GraphQLName("channelId") Long channelId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.deleteChannel(channelId));

		return true;
	}

	@GraphQLField
	public Response deleteChannelBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.deleteChannelBatch(
				callbackURL, object));
	}

	@GraphQLField
	public Channel patchChannel(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("channel") Channel channel)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.patchChannel(
				channelId, channel));
	}

	@GraphQLField
	public Channel updateChannel(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("channel") Channel channel)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.putChannel(channelId, channel));
	}

	@GraphQLField
	public Response updateChannelBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_channelResourceComponentServiceObjects,
			this::_populateResourceContext,
			channelResource -> channelResource.putChannelBatch(
				callbackURL, object));
	}

	@GraphQLField
	public boolean deletePaymentMethodGroupRelOrderType(
			@GraphQLName("paymentMethodGroupRelOrderTypeId") Long
				paymentMethodGroupRelOrderTypeId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_paymentMethodGroupRelOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodGroupRelOrderTypeResource ->
				paymentMethodGroupRelOrderTypeResource.
					deletePaymentMethodGroupRelOrderType(
						paymentMethodGroupRelOrderTypeId));

		return true;
	}

	@GraphQLField
	public Response deletePaymentMethodGroupRelOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_paymentMethodGroupRelOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodGroupRelOrderTypeResource ->
				paymentMethodGroupRelOrderTypeResource.
					deletePaymentMethodGroupRelOrderTypeBatch(
						callbackURL, object));
	}

	@GraphQLField
	public PaymentMethodGroupRelOrderType
			createPaymentMethodGroupRelIdPaymentMethodGroupRelOrderType(
				@GraphQLName("id") Long id,
				@GraphQLName("paymentMethodGroupRelOrderType")
					PaymentMethodGroupRelOrderType
						paymentMethodGroupRelOrderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_paymentMethodGroupRelOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodGroupRelOrderTypeResource ->
				paymentMethodGroupRelOrderTypeResource.
					postPaymentMethodGroupRelIdPaymentMethodGroupRelOrderType(
						id, paymentMethodGroupRelOrderType));
	}

	@GraphQLField
	public boolean deletePaymentMethodGroupRelTerm(
			@GraphQLName("paymentMethodGroupRelTermId") Long
				paymentMethodGroupRelTermId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_paymentMethodGroupRelTermResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodGroupRelTermResource ->
				paymentMethodGroupRelTermResource.
					deletePaymentMethodGroupRelTerm(
						paymentMethodGroupRelTermId));

		return true;
	}

	@GraphQLField
	public Response deletePaymentMethodGroupRelTermBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_paymentMethodGroupRelTermResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodGroupRelTermResource ->
				paymentMethodGroupRelTermResource.
					deletePaymentMethodGroupRelTermBatch(callbackURL, object));
	}

	@GraphQLField
	public PaymentMethodGroupRelTerm
			createPaymentMethodGroupRelIdPaymentMethodGroupRelTerm(
				@GraphQLName("id") Long id,
				@GraphQLName("paymentMethodGroupRelTerm")
					PaymentMethodGroupRelTerm paymentMethodGroupRelTerm)
		throws Exception {

		return _applyComponentServiceObjects(
			_paymentMethodGroupRelTermResourceComponentServiceObjects,
			this::_populateResourceContext,
			paymentMethodGroupRelTermResource ->
				paymentMethodGroupRelTermResource.
					postPaymentMethodGroupRelIdPaymentMethodGroupRelTerm(
						id, paymentMethodGroupRelTerm));
	}

	@GraphQLField
	public boolean deleteShippingFixedOptionOrderType(
			@GraphQLName("shippingFixedOptionOrderTypeId") Long
				shippingFixedOptionOrderTypeId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_shippingFixedOptionOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingFixedOptionOrderTypeResource ->
				shippingFixedOptionOrderTypeResource.
					deleteShippingFixedOptionOrderType(
						shippingFixedOptionOrderTypeId));

		return true;
	}

	@GraphQLField
	public Response deleteShippingFixedOptionOrderTypeBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingFixedOptionOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingFixedOptionOrderTypeResource ->
				shippingFixedOptionOrderTypeResource.
					deleteShippingFixedOptionOrderTypeBatch(
						callbackURL, object));
	}

	@GraphQLField
	public ShippingFixedOptionOrderType
			createShippingFixedOptionIdShippingFixedOptionOrderType(
				@GraphQLName("id") Long id,
				@GraphQLName("shippingFixedOptionOrderType")
					ShippingFixedOptionOrderType shippingFixedOptionOrderType)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingFixedOptionOrderTypeResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingFixedOptionOrderTypeResource ->
				shippingFixedOptionOrderTypeResource.
					postShippingFixedOptionIdShippingFixedOptionOrderType(
						id, shippingFixedOptionOrderType));
	}

	@GraphQLField
	public boolean deleteShippingFixedOptionTerm(
			@GraphQLName("shippingFixedOptionTermId") Long
				shippingFixedOptionTermId)
		throws Exception {

		_applyVoidComponentServiceObjects(
			_shippingFixedOptionTermResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingFixedOptionTermResource ->
				shippingFixedOptionTermResource.deleteShippingFixedOptionTerm(
					shippingFixedOptionTermId));

		return true;
	}

	@GraphQLField
	public Response deleteShippingFixedOptionTermBatch(
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("object") Object object)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingFixedOptionTermResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingFixedOptionTermResource ->
				shippingFixedOptionTermResource.
					deleteShippingFixedOptionTermBatch(callbackURL, object));
	}

	@GraphQLField
	public ShippingFixedOptionTerm
			createShippingFixedOptionIdShippingFixedOptionTerm(
				@GraphQLName("id") Long id,
				@GraphQLName("shippingFixedOptionTerm") ShippingFixedOptionTerm
					shippingFixedOptionTerm)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingFixedOptionTermResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingFixedOptionTermResource ->
				shippingFixedOptionTermResource.
					postShippingFixedOptionIdShippingFixedOptionTerm(
						id, shippingFixedOptionTerm));
	}

	@GraphQLField
	public Response createChannelShippingMethodsPageExportBatch(
			@GraphQLName("channelId") Long channelId,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_shippingMethodResourceComponentServiceObjects,
			this::_populateResourceContext,
			shippingMethodResource ->
				shippingMethodResource.
					postChannelShippingMethodsPageExportBatch(
						channelId, callbackURL, contentType, fieldNames));
	}

	@GraphQLField
	public Response createTaxCategoriesPageExportBatch(
			@GraphQLName("search") String search,
			@GraphQLName("callbackURL") String callbackURL,
			@GraphQLName("contentType") String contentType,
			@GraphQLName("fieldNames") String fieldNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_taxCategoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			taxCategoryResource ->
				taxCategoryResource.postTaxCategoriesPageExportBatch(
					search, callbackURL, contentType, fieldNames));
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

	private <T, E1 extends Throwable, E2 extends Throwable> void
			_applyVoidComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeConsumer<T, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			unsafeFunction.accept(resource);
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

		channelResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		channelResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		paymentMethodGroupRelOrderTypeResource.
			setVulcanBatchEngineExportTaskResource(
				_vulcanBatchEngineExportTaskResource);

		paymentMethodGroupRelOrderTypeResource.
			setVulcanBatchEngineImportTaskResource(
				_vulcanBatchEngineImportTaskResource);
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

		paymentMethodGroupRelTermResource.
			setVulcanBatchEngineExportTaskResource(
				_vulcanBatchEngineExportTaskResource);

		paymentMethodGroupRelTermResource.
			setVulcanBatchEngineImportTaskResource(
				_vulcanBatchEngineImportTaskResource);
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

		shippingFixedOptionOrderTypeResource.
			setVulcanBatchEngineExportTaskResource(
				_vulcanBatchEngineExportTaskResource);

		shippingFixedOptionOrderTypeResource.
			setVulcanBatchEngineImportTaskResource(
				_vulcanBatchEngineImportTaskResource);
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

		shippingFixedOptionTermResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		shippingFixedOptionTermResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		shippingMethodResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		shippingMethodResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
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

		taxCategoryResource.setVulcanBatchEngineExportTaskResource(
			_vulcanBatchEngineExportTaskResource);

		taxCategoryResource.setVulcanBatchEngineImportTaskResource(
			_vulcanBatchEngineImportTaskResource);
	}

	private static ComponentServiceObjects<ChannelResource>
		_channelResourceComponentServiceObjects;
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
	private VulcanBatchEngineExportTaskResource
		_vulcanBatchEngineExportTaskResource;
	private VulcanBatchEngineImportTaskResource
		_vulcanBatchEngineImportTaskResource;

}