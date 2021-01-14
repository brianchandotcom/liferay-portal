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

package com.liferay.commerce.theme.minium.internal.product.renderer.list.entry;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.model.PriceModel;
import com.liferay.commerce.frontend.model.ProductSettingsModel;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPSku;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.constants.CPContentWebKeys;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.util.CPCompareHelperUtil;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.wish.list.model.CommerceWishList;
import com.liferay.commerce.wish.list.service.CommerceWishListItemService;
import com.liferay.commerce.wish.list.service.CommerceWishListService;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.soy.renderer.ComponentDescriptor;
import com.liferay.portal.template.soy.renderer.SoyComponentRenderer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.product.content.list.entry.renderer.key=" + MiniumCPContentListEntryRenderer.KEY,
		"commerce.product.content.list.entry.renderer.portlet.name=" + CPPortletKeys.CP_COMPARE_CONTENT_WEB,
		"commerce.product.content.list.entry.renderer.portlet.name=" + CPPortletKeys.CP_PUBLISHER_WEB,
		"commerce.product.content.list.entry.renderer.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS,
		"commerce.product.content.list.entry.renderer.type=grouped",
		"commerce.product.content.list.entry.renderer.type=simple",
		"commerce.product.content.list.entry.renderer.type=virtual"
	},
	service = CPContentListEntryRenderer.class
)
public class MiniumCPContentListEntryRenderer
	implements CPContentListEntryRenderer {

	public static final String KEY = "list-entry-minium";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "minium");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CPContentHelper cpContentHelper =
			(CPContentHelper)httpServletRequest.getAttribute(
				CPContentWebKeys.CP_CONTENT_HELPER);

		CPCatalogEntry cpCatalogEntry = cpContentHelper.getCPCatalogEntry(
			httpServletRequest);

		List<CPSku> cpSkus = cpCatalogEntry.getCPSkus();

		CPSku cpSku = null;

		if (cpSkus.size() == 1) {
			cpSku = cpSkus.get(0);
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Map<String, Object> context = new HashMap<>();

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (portletName.equals(CPPortletKeys.CP_COMPARE_CONTENT_WEB)) {
			context.put("compareCheckboxVisible", false);
			context.put(
				"compareContentNamespace",
				_portal.getPortletNamespace(
					CPPortletKeys.CP_COMPARE_CONTENT_WEB));
			context.put("deleteButtonVisible", true);
		}
		else {
			long commerceAccountId = 0;

			if (commerceAccount != null) {
				commerceAccountId = commerceAccount.getCommerceAccountId();
			}

			HttpServletRequest originalHttpServletRequest =
				_portal.getOriginalServletRequest(httpServletRequest);

			List<Long> cpDefinitionIds = CPCompareHelperUtil.getCPDefinitionIds(
				commerceContext.getCommerceChannelGroupId(), commerceAccountId,
				originalHttpServletRequest.getSession());

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put(
				"checkboxVisible", true
			).put(
				"compareAvailable", true
			).put(
				"inCompare",
				cpDefinitionIds.contains(cpCatalogEntry.getCPDefinitionId())
			);

			context.put("compareCheckboxVisible", true);
			context.put("compareState", jsonObject);
			context.put("deleteButtonVisible", false);
		}

		context.put("orderQuantity", 0);

		if (commerceAccount != null) {
			context.put("accountId", commerceAccount.getCommerceAccountId());
		}

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		if (commerceOrder != null) {
			context.put("orderId", commerceOrder.getCommerceOrderId());
		}

		context.put("addToCartButtonVisible", true);

		context.put(
			"cartAPI",
			_portal.getPortalURL(httpServletRequest) +
				"/o/commerce-ui/cart-item");
		context.put("categories", null);
		context.put("description", cpCatalogEntry.getShortDescription());
		context.put(
			"detailsLink",
			cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay));
		context.put("name", cpCatalogEntry.getName());
		context.put("pictureUrl", cpCatalogEntry.getDefaultImageFileUrl());
		context.put("productId", cpCatalogEntry.getCPDefinitionId());

		boolean hasChildCPDefinitions = cpContentHelper.hasChildCPDefinitions(
			cpCatalogEntry.getCPDefinitionId());

		if ((cpSku != null) && !hasChildCPDefinitions) {
			context.put("sku", cpSku.getSku());
			context.put("skuId", cpSku.getCPInstanceId());

			ProductSettingsModel productSettingsModel =
				_productHelper.getProductSettingsModel(cpSku.getCPInstanceId());

			PriceModel priceModel = _productHelper.getPriceModel(
				cpSku.getCPInstanceId(), productSettingsModel.getMinQuantity(),
				commerceContext, StringPool.BLANK, themeDisplay.getLocale());

			context.put("prices", priceModel);

			context.put("settings", productSettingsModel);

			if (commerceOrder != null) {
				List<CommerceOrderItem> commerceOrderItems =
					_commerceOrderItemLocalService.getCommerceOrderItems(
						commerceOrder.getCommerceOrderId(),
						cpSku.getCPInstanceId(), 0, 1);

				if (!commerceOrderItems.isEmpty()) {
					context.put("orderQuantity", 1);
				}
			}

			if (productSettingsModel.isShowAvailabilityDot()) {
				Map<String, Integer> stockQuantities =
					(Map<String, Integer>)httpServletRequest.getAttribute(
						"stockQuantities");

				int stockQuantity = 0;

				if (MapUtil.isNotEmpty(stockQuantities)) {
					stockQuantity = MapUtil.getInteger(
						stockQuantities, cpSku.getSku());
				}

				String status = "inStock";

				if (stockQuantity <= 0) {
					status = "notAvailable";
				}
				else if (stockQuantity <=
							productSettingsModel.getLowStockQuantity()) {

					status = "available";
				}

				context.put("availability", status);
			}
		}
		else if (hasChildCPDefinitions) {
			PriceModel priceModel = _productHelper.getMinPrice(
				cpCatalogEntry.getCPDefinitionId(), commerceContext,
				themeDisplay.getLocale());

			context.put("prices", priceModel);
		}

		CommerceWishList commerceWishList =
			_commerceWishListService.getDefaultCommerceWishList(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId());

		if (commerceWishList != null) {
			boolean addedToWishlist = false;

			if (cpSku != null) {
				int count =
					_commerceWishListItemService.
						getCommerceWishListItemByContainsCPInstanceCount(
							commerceWishList.getCommerceWishListId(),
							cpSku.getCPInstanceUuid());

				if (count > 0) {
					addedToWishlist = true;
				}
			}
			else {
				int count =
					_commerceWishListItemService.
						getCommerceWishListItemByContainsCProductCount(
							commerceWishList.getCommerceWishListId(),
							cpCatalogEntry.getCProductId());

				if (count > 0) {
					addedToWishlist = true;
				}
			}

			context.put("addedToWishlist", addedToWishlist);

			context.put("addToWishlistButtonVisible", true);
			context.put(
				"wishlistAPI",
				_portal.getPortalURL(httpServletRequest) +
					"/o/commerce-ui/wish-list-item");
		}
		else {
			context.put("addToWishlistButtonVisible", false);
		}

		context.put(
			"spritemap", themeDisplay.getPathThemeImages() + "/icons.svg");

		Set<String> dependencies = new HashSet<>();

		dependencies.add(
			"commerce-frontend-taglib/add_to_cart/AddToCartButton.es");
		dependencies.add(
			"commerce-frontend-taglib/compare_checkbox/CompareCheckbox.es");
		dependencies.add("commerce-frontend-taglib/price/Price.es");

		ComponentDescriptor componentDescriptor = new ComponentDescriptor(
			"ProductCard.render",
			"commerce-frontend-taglib/product_card/ProductCard.es", null,
			dependencies, false, true, false);

		_soyComponentRenderer.renderSoyComponent(
			httpServletRequest, httpServletResponse, componentDescriptor,
			context);
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceWishListItemService _commerceWishListItemService;

	@Reference
	private CommerceWishListService _commerceWishListService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	@Reference
	private ProductHelper _productHelper;

	@Reference
	private SoyComponentRenderer _soyComponentRenderer;

}