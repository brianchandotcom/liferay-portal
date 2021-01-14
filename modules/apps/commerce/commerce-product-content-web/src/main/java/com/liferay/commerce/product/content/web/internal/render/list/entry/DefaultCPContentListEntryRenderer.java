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

package com.liferay.commerce.product.content.web.internal.render.list.entry;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.frontend.model.CPContentListEntryModel;
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
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Gianmarco Brunialti Masera
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"commerce.product.content.list.entry.renderer.key=" + DefaultCPContentListEntryRenderer.KEY,
		"commerce.product.content.list.entry.renderer.order=" + Integer.MIN_VALUE,
		"commerce.product.content.list.entry.renderer.portlet.name=" + CPPortletKeys.CP_COMPARE_CONTENT_WEB,
		"commerce.product.content.list.entry.renderer.portlet.name=" + CPPortletKeys.CP_PUBLISHER_WEB,
		"commerce.product.content.list.entry.renderer.portlet.name=" + CPPortletKeys.CP_SEARCH_RESULTS,
		"commerce.product.content.list.entry.renderer.type=grouped",
		"commerce.product.content.list.entry.renderer.type=simple",
		"commerce.product.content.list.entry.renderer.type=virtual"
	},
	service = CPContentListEntryRenderer.class
)
public class DefaultCPContentListEntryRenderer
	implements CPContentListEntryRenderer {

	public static final String KEY = "list-entry-default";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "default");
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

		long accountId = 0;
		boolean compareCheckboxVisible = true;
		boolean compareDeleteButtonVisible = false;
		JSONObject compareStateJSONObject = _jsonFactory.createJSONObject();
		boolean inCart = false;
		boolean lowStock = false;
		long orderId = 0;
		PriceModel prices = null;
		ProductSettingsModel productSettingsModel = null;
		String sku = StringPool.BLANK;
		long skuId = 0;
		int stockQuantity = 0;

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		if (portletName.equals(CPPortletKeys.CP_COMPARE_CONTENT_WEB)) {
			compareCheckboxVisible = false;
			compareDeleteButtonVisible = true;
		}
		else {
			if (commerceAccount != null) {
				accountId = commerceAccount.getCommerceAccountId();
			}

			HttpServletRequest originalHttpServletRequest =
				_portal.getOriginalServletRequest(httpServletRequest);

			List<Long> cpDefinitionIds = CPCompareHelperUtil.getCPDefinitionIds(
				commerceContext.getCommerceChannelGroupId(), accountId,
				CookieKeys.getCookie(
					originalHttpServletRequest,
					CPCompareHelperUtil.getCPDefinitionIdsCookieKey(
						commerceContext.getCommerceChannelGroupId())));

			compareStateJSONObject.put(
				"checkboxVisible", true
			).put(
				"compareAvailable", true
			).put(
				"inCompare",
				cpDefinitionIds.contains(cpCatalogEntry.getCPDefinitionId())
			);
		}

		CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

		if (commerceOrder != null) {
			orderId = commerceOrder.getCommerceOrderId();
		}

		boolean hasChildCPDefinitions = cpContentHelper.hasChildCPDefinitions(
			cpCatalogEntry.getCPDefinitionId());

		if ((cpSku != null) && !hasChildCPDefinitions) {
			sku = cpSku.getSku();
			skuId = cpSku.getCPInstanceId();

			productSettingsModel = _productHelper.getProductSettingsModel(
				cpSku.getCPInstanceId());

			prices = _productHelper.getPriceModel(
				cpSku.getCPInstanceId(), productSettingsModel.getMinQuantity(),
				commerceContext, StringPool.BLANK, themeDisplay.getLocale());

			if (commerceOrder != null) {
				List<CommerceOrderItem> commerceOrderItems =
					_commerceOrderItemLocalService.getCommerceOrderItems(
						commerceOrder.getCommerceOrderId(),
						cpSku.getCPInstanceId(), 0, 1);

				if (!commerceOrderItems.isEmpty()) {
					inCart = true;
				}
			}

			Map<String, Integer> stockQuantities =
				(Map<String, Integer>)httpServletRequest.getAttribute(
					"stockQuantities");

			if (MapUtil.isNotEmpty(stockQuantities)) {
				stockQuantity = MapUtil.getInteger(
					stockQuantities, cpSku.getSku());
			}

			lowStock =
				(stockQuantity > 0) &&
				(stockQuantity <= productSettingsModel.getLowStockQuantity());
		}
		else if (hasChildCPDefinitions) {
			prices = _productHelper.getMinPrice(
				cpCatalogEntry.getCPDefinitionId(), commerceContext,
				themeDisplay.getLocale());
		}

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		String pathThemeImages = themeDisplay.getPathThemeImages();

		String spritemap = pathThemeImages + "/icons.svg";

		if (pathThemeImages.contains("classic")) {
			spritemap = pathThemeImages + "/lexicon/icons.svg";
		}

		CPContentListEntryModel cpContentListEntryModel =
			new CPContentListEntryModel(
				accountId, commerceContext.getCommerceChannelId(),
				compareCheckboxVisible, compareDeleteButtonVisible,
				compareStateJSONObject, cpCatalogEntry.getCPDefinitionId(),
				commerceCurrency.getCode(),
				cpCatalogEntry.getShortDescription(), inCart,
				cpContentHelper.isInWishList(
					cpSku, cpCatalogEntry, themeDisplay),
				lowStock, cpCatalogEntry.getName(), orderId, prices,
				cpContentHelper.getFriendlyURL(cpCatalogEntry, themeDisplay),
				cpCatalogEntry.getDefaultImageFileUrl(), productSettingsModel,
				sku, skuId, spritemap, stockQuantity);

		httpServletRequest.setAttribute(
			"cpContentListEntryModel", cpContentListEntryModel);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/product_publisher/render/list/entry/view.jsp");
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference
	private ProductHelper _productHelper;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.content.web)"
	)
	private ServletContext _servletContext;

}