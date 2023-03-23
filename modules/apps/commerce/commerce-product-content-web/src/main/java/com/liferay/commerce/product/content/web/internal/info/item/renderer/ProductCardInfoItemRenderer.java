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

package com.liferay.commerce.product.content.web.internal.info.item.renderer;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.content.constants.CPContentWebKeys;
import com.liferay.commerce.product.content.info.item.renderer.CPContentInfoItemRendererRegistry;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	service = {InfoItemRenderer.class, ProductCardInfoItemRenderer.class}
)
public class ProductCardInfoItemRenderer
	implements InfoItemRenderer<CPDefinition> {

	@Override
	public String getKey() {
		return "cpDefinition-product-card";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "product-card");
	}

	@Override
	public void render(
		CPDefinition cpDefinition, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		if (cpDefinition == null) {
			return;
		}

		try {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/fragment/renderer/product_card/page.jsp");

			long groupId = _portal.getScopeGroupId(httpServletRequest);

			httpServletRequest.setAttribute(
				CPWebKeys.CP_CATALOG_ENTRY,
				_cpDefinitionHelper.getCPCatalogEntry(
					_getAccountEntryId(groupId, httpServletRequest), groupId,
					cpDefinition.getCPDefinitionId(),
					_portal.getLocale(httpServletRequest)));

			httpServletRequest.setAttribute(
				CPContentWebKeys.CP_CONTENT_HELPER, _cpContentHelper);
			httpServletRequest.setAttribute(
				CPContentWebKeys.CP_CONTENT_INFO_ITEM_RENDERER,
				_cpContentInfoItemRendererRegistry.getCPContentInfoItemRenderer(
					cpDefinition.getProductTypeName()));
			httpServletRequest.setAttribute(
				"liferay-commerce:product-card:showAddToCartButton",
				Boolean.TRUE);
			httpServletRequest.setAttribute(
				"liferay-commerce:product-card:showAddToWishListButton",
				Boolean.TRUE);
			httpServletRequest.setAttribute(
				"liferay-commerce:product-card:showAvailabilityLabel",
				Boolean.TRUE);
			httpServletRequest.setAttribute(
				"liferay-commerce:product-card:showCompareCheckbox",
				Boolean.TRUE);
			httpServletRequest.setAttribute(
				"liferay-commerce:product-card:showDiscontinuedLabel",
				Boolean.TRUE);
			httpServletRequest.setAttribute(
				"liferay-commerce:product-card:showImage", Boolean.TRUE);
			httpServletRequest.setAttribute(
				"liferay-commerce:product-card:showName", Boolean.TRUE);
			httpServletRequest.setAttribute(
				"liferay-commerce:product-card:showPrice", Boolean.TRUE);
			httpServletRequest.setAttribute(
				"liferay-commerce:product-card:showSku", Boolean.TRUE);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private long _getAccountEntryId(
			long groupId, HttpServletRequest httpServletRequest)
		throws PortalException {

		AccountEntry accountEntry =
			_commerceAccountHelper.getCurrentAccountEntry(
				_commerceChannelLocalService.
					getCommerceChannelGroupIdBySiteGroupId(groupId),
				httpServletRequest);

		long accountEntryId = 0;

		if (accountEntry != null) {
			accountEntryId = accountEntry.getAccountEntryId();
		}

		return accountEntryId;
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CPContentHelper _cpContentHelper;

	@Reference
	private CPContentInfoItemRendererRegistry
		_cpContentInfoItemRendererRegistry;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.content.web)"
	)
	private ServletContext _servletContext;

}