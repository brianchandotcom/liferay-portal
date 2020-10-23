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

package com.liferay.commerce.frontend.internal.search;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.frontend.internal.account.CommerceAccountResource;
import com.liferay.commerce.frontend.internal.account.model.Account;
import com.liferay.commerce.frontend.internal.account.model.AccountList;
import com.liferay.commerce.frontend.internal.account.model.Order;
import com.liferay.commerce.frontend.internal.account.model.OrderList;
import com.liferay.commerce.frontend.internal.order.CommerceOrderResource;
import com.liferay.commerce.frontend.internal.search.model.SearchItemModel;
import com.liferay.commerce.frontend.internal.search.util.CommerceSearchUtil;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPQuery;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CommerceSearchResource.class)
public class CommerceSearchResource {

	@GET
	@Path("/search/{plid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(
		@PathParam("plid") long plid, @QueryParam("q") String queryString,
		@Context ThemeDisplay themeDisplay,
		@Context HttpServletRequest httpServletRequest) {

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			themeDisplay.setScopeGroupId(layout.getGroupId());
			themeDisplay.setLayout(layout);
			themeDisplay.setLayoutSet(layout.getLayoutSet());

			CommerceAccount commerceAccount =
				_commerceAccountHelper.getCurrentCommerceAccount(
					_commerceChannelLocalService.
						getCommerceChannelGroupIdBySiteGroupId(
							themeDisplay.getScopeGroupId()),
					httpServletRequest);

			List<SearchItemModel> searchItemModels = new ArrayList<>();

			searchItemModels.addAll(
				searchProducts(
					themeDisplay.getCompanyId(), layout.getGroupId(),
					queryString, themeDisplay));

			if (themeDisplay.isSignedIn()) {
				searchItemModels.addAll(
					searchAccounts(queryString, themeDisplay));
				searchItemModels.addAll(
					searchOrders(queryString, themeDisplay, commerceAccount));
			}

			String url = _commerceSearchUtil.getSearchFriendlyURL(themeDisplay);

			if (Validator.isNotNull(url)) {
				url = _http.addParameter(url, "q", queryString);

				SearchItemModel searchItemModel = new SearchItemModel(
					"category",
					LanguageUtil.get(themeDisplay.getLocale(), "all-content"));

				searchItemModel.setUrl(url);

				searchItemModels.add(searchItemModel);
			}

			String json = _OBJECT_MAPPER.writeValueAsString(searchItemModels);

			return Response.ok(
				json, MediaType.APPLICATION_JSON
			).build();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return Response.status(
			Response.Status.SERVICE_UNAVAILABLE
		).build();
	}

	protected List<SearchItemModel> searchAccounts(
			String queryString, ThemeDisplay themeDisplay)
		throws PortalException {

		List<SearchItemModel> searchItemModels = new ArrayList<>();

		CommerceContext commerceContext = _commerceContextFactory.create(
			themeDisplay.getCompanyId(),
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				themeDisplay.getScopeGroupId()),
			themeDisplay.getUserId(), 0, 0);

		AccountList accountList = _commerceAccountResource.getAccountList(
			themeDisplay.getUserId(),
			CommerceAccountConstants.DEFAULT_PARENT_ACCOUNT_ID,
			commerceContext.getCommerceSiteType(), queryString, 1, 5,
			themeDisplay.getPathImage());

		if (accountList.getCount() > 0) {
			searchItemModels.add(
				new SearchItemModel(
					"label",
					LanguageUtil.get(themeDisplay.getLocale(), "accounts")));
		}

		for (Account account : accountList.getAccounts()) {
			SearchItemModel searchItemModel = new SearchItemModel(
				"item", account.getName());

			searchItemModel.setImage(account.getThumbnail());

			searchItemModel.setUrl(
				_getAccountManagementPortletEditURL(
					GetterUtil.getLong(account.getAccountId()), themeDisplay));

			searchItemModels.add(searchItemModel);
		}

		String url = _commerceSearchUtil.getAccountManagementFriendlyURL(
			themeDisplay);

		if (Validator.isNotNull(url)) {
			url = _http.addParameter(url, "q", queryString);

			SearchItemModel searchItemModel = new SearchItemModel(
				"category",
				LanguageUtil.get(themeDisplay.getLocale(), "accounts"));

			searchItemModel.setUrl(url);

			searchItemModels.add(searchItemModel);
		}

		return searchItemModels;
	}

	protected List<SearchItemModel> searchOrders(
			String queryString, ThemeDisplay themeDisplay,
			CommerceAccount commerceAccount)
		throws PortalException {

		List<SearchItemModel> searchItemModels = new ArrayList<>();

		OrderList orderList = _commerceOrderResource.getOrderList(
			themeDisplay.getScopeGroupId(), queryString, 1, 5,
			themeDisplay.getRequest(), commerceAccount);

		if (orderList.getCount() > 0) {
			searchItemModels.add(
				new SearchItemModel(
					"label",
					LanguageUtil.get(themeDisplay.getLocale(), "orders")));
		}

		for (Order order : orderList.getOrders()) {
			SearchItemModel searchItemModel = new SearchItemModel(
				"item", String.valueOf(order.getId()));

			searchItemModel.setIcon("document");

			searchItemModel.setSubtitle(order.getAccountName());

			searchItemModel.setUrl(
				String.valueOf(
					_commerceOrderHttpHelper.getCommerceCartPortletURL(
						themeDisplay.getScopeGroupId(),
						themeDisplay.getRequest(),
						_commerceOrderService.getCommerceOrder(
							order.getId()))));

			searchItemModels.add(searchItemModel);
		}

		String url = _commerceSearchUtil.getOrdersFriendlyURL(themeDisplay);

		if (Validator.isNotNull(url)) {
			url = _http.addParameter(url, "q", queryString);

			SearchItemModel searchItemModel = new SearchItemModel(
				"category",
				LanguageUtil.get(themeDisplay.getLocale(), "orders"));

			searchItemModel.setUrl(url);

			searchItemModels.add(searchItemModel);
		}

		return searchItemModels;
	}

	protected List<SearchItemModel> searchProducts(
			long companyId, long groupId, String queryString,
			ThemeDisplay themeDisplay)
		throws PortalException {

		List<SearchItemModel> searchItemModels = new ArrayList<>();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, WorkflowConstants.STATUS_APPROVED
			).build();

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.fetchCommerceChannelBySiteGroupId(
				themeDisplay.getScopeGroupId());

		if (commerceChannel != null) {
			attributes.put(
				"commerceChannelGroupId", commerceChannel.getGroupId());
		}

		CommerceAccount commerceAccount =
			_commerceAccountHelper.getCurrentCommerceAccount(
				commerceChannel.getGroupId(), themeDisplay.getRequest());

		long[] commerceAccountGroupIds = null;

		if (commerceAccount != null) {
			commerceAccountGroupIds =
				_commerceAccountHelper.getCommerceAccountGroupIds(
					commerceAccount.getCommerceAccountId());
		}

		searchContext.setAttribute(
			"commerceAccountGroupIds", commerceAccountGroupIds);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);

		searchContext.setKeywords(queryString);

		CPQuery cpQuery = new CPQuery();

		cpQuery.setOrderByCol1("title");
		cpQuery.setOrderByCol2("modifiedDate");
		cpQuery.setOrderByType1("ASC");
		cpQuery.setOrderByType2("DESC");

		CPDataSourceResult cpDataSourceResult = _cpDefinitionHelper.search(
			groupId, searchContext, cpQuery, 0, 5);

		if (cpDataSourceResult.getLength() > 0) {
			searchItemModels.add(
				new SearchItemModel(
					"label", LanguageUtil.get(resourceBundle, "catalog")));
		}

		for (CPCatalogEntry cpCatalogEntry :
				cpDataSourceResult.getCPCatalogEntries()) {

			searchItemModels.add(
				_getSearchItemModel(cpCatalogEntry, themeDisplay));
		}

		String url = _commerceSearchUtil.getCatalogFriendlyURL(themeDisplay);

		if (Validator.isNotNull(url)) {
			url = _http.addParameter(url, "q", queryString);

			SearchItemModel searchItemModel = new SearchItemModel(
				"category", LanguageUtil.get(resourceBundle, "catalog"));

			searchItemModel.setUrl(url);

			searchItemModels.add(searchItemModel);
		}

		return searchItemModels;
	}

	private String _getAccountManagementPortletEditURL(
			long accountId, ThemeDisplay themeDisplay)
		throws PortalException {

		PortletURL editURL = PortletProviderUtil.getPortletURL(
			themeDisplay.getRequest(), CommerceAccount.class.getName(),
			PortletProvider.Action.VIEW);

		if (editURL == null) {
			return StringPool.BLANK;
		}

		editURL.setParameter("commerceAccountId", String.valueOf(accountId));

		return editURL.toString();
	}

	private SearchItemModel _getSearchItemModel(
			CPCatalogEntry cpCatalogEntry, ThemeDisplay themeDisplay)
		throws PortalException {

		SearchItemModel searchItemModel = new SearchItemModel(
			"item", HtmlUtil.escape(cpCatalogEntry.getName()));

		String subtitle = cpCatalogEntry.getShortDescription();

		if (Validator.isNull(subtitle)) {
			subtitle = HtmlUtil.extractText(cpCatalogEntry.getDescription());
		}

		searchItemModel.setSubtitle(subtitle);

		searchItemModel.setImage(cpCatalogEntry.getDefaultImageFileUrl());

		String url = _cpDefinitionHelper.getFriendlyURL(
			cpCatalogEntry.getCPDefinitionId(), themeDisplay);

		searchItemModel.setUrl(url);

		return searchItemModel;
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			disable(SerializationFeature.INDENT_OUTPUT);
		}
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceSearchResource.class);

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceAccountResource _commerceAccountResource;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderResource _commerceOrderResource;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceSearchUtil _commerceSearchUtil;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private Http _http;

	@Reference
	private LayoutLocalService _layoutLocalService;

}