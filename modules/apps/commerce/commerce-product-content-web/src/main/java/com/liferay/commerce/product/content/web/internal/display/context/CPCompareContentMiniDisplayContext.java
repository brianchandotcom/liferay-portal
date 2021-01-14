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

package com.liferay.commerce.product.content.web.internal.display.context;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.web.internal.configuration.CPCompareContentMiniPortletInstanceConfiguration;
import com.liferay.commerce.product.data.source.CPDataSourceResult;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPCompareHelper;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPCompareContentMiniDisplayContext {

	public CPCompareContentMiniDisplayContext(
			CPCompareHelper cpCompareHelper,
			CPContentListEntryRendererRegistry
				cpContentListEntryRendererRegistry,
			CPContentListRendererRegistry cpContentListRendererRegistry,
			CPDefinitionHelper cpDefinitionHelper,
			CPTypeServicesTracker cpTypeServicesTracker,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_cpCompareHelper = cpCompareHelper;
		_cpContentListEntryRendererRegistry =
			cpContentListEntryRendererRegistry;
		_cpContentListRendererRegistry = cpContentListRendererRegistry;
		_cpDefinitionHelper = cpDefinitionHelper;
		_cpTypeServicesTracker = cpTypeServicesTracker;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpCompareContentMiniPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPCompareContentMiniPortletInstanceConfiguration.class);

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		if (commerceAccount != null) {
			HttpServletRequest originalHttpServletRequest =
				PortalUtil.getOriginalServletRequest(httpServletRequest);

			_cpDefinitionIds = new ArrayList<>(
				_cpCompareHelper.getCPDefinitionIds(
					commerceContext.getCommerceChannelGroupId(),
					commerceAccount.getCommerceAccountId(),
					CookieKeys.getCookie(
						originalHttpServletRequest,
						_cpCompareHelper.getCPDefinitionIdsCookieKey(
							commerceContext.getCommerceChannelGroupId()))));
		}
		else {
			_cpDefinitionIds = new ArrayList<>();
		}
	}

	public Map<String, String> getCPContentListEntryRendererKeys() {
		Map<String, String> cpContentListEntryRendererKeys = new HashMap<>();

		for (CPType cpType : getCPTypes()) {
			String cpTypeName = cpType.getName();

			cpContentListEntryRendererKeys.put(
				cpTypeName, getCPTypeListEntryRendererKey(cpTypeName));
		}

		return cpContentListEntryRendererKeys;
	}

	public List<CPContentListEntryRenderer> getCPContentListEntryRenderers(
		String cpType) {

		return _cpContentListEntryRendererRegistry.
			getCPContentListEntryRenderers(
				CPPortletKeys.CP_COMPARE_CONTENT_MINI_WEB, cpType);
	}

	public String getCPContentListRendererKey() {
		RenderRequest renderRequest = _cpRequestHelper.getRenderRequest();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String value = portletPreferences.getValue(
			"cpContentListRendererKey", null);

		if (Validator.isNotNull(value)) {
			return value;
		}

		List<CPContentListRenderer> cpContentListRenderers =
			getCPContentListRenderers();

		if (cpContentListRenderers.isEmpty()) {
			return StringPool.BLANK;
		}

		CPContentListRenderer cpContentListRenderer =
			cpContentListRenderers.get(0);

		if (cpContentListRenderer == null) {
			return StringPool.BLANK;
		}

		return cpContentListRenderer.getKey();
	}

	public List<CPContentListRenderer> getCPContentListRenderers() {
		return _cpContentListRendererRegistry.getCPContentListRenderers(
			CPPortletKeys.CP_COMPARE_CONTENT_MINI_WEB);
	}

	public CPDataSourceResult getCPDataSourceResult() throws PortalException {
		List<CPCatalogEntry> cpCatalogEntries = new ArrayList<>();

		HttpServletRequest httpServletRequest = _cpRequestHelper.getRequest();

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceAccount commerceAccount = commerceContext.getCommerceAccount();

		long commerceAccountId = 0;

		if (commerceAccount != null) {
			commerceAccountId = commerceAccount.getCommerceAccountId();
		}

		for (Long cpDefinitionId : _cpDefinitionIds) {
			cpCatalogEntries.add(
				_cpDefinitionHelper.getCPCatalogEntry(
					commerceAccountId,
					commerceContext.getCommerceChannelGroupId(), cpDefinitionId,
					_cpRequestHelper.getLocale()));
		}

		if (cpCatalogEntries.size() > getProductsLimit()) {
			cpCatalogEntries = cpCatalogEntries.subList(0, getProductsLimit());
		}

		return new CPDataSourceResult(
			cpCatalogEntries, cpCatalogEntries.size());
	}

	public String getCPTypeListEntryRendererKey(String cpType) {
		RenderRequest renderRequest = _cpRequestHelper.getRenderRequest();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String value = portletPreferences.getValue(
			cpType + "--cpTypeListEntryRendererKey", null);

		if (Validator.isNotNull(value)) {
			return value;
		}

		List<CPContentListEntryRenderer> cpContentListEntryRenderers =
			getCPContentListEntryRenderers(cpType);

		if (cpContentListEntryRenderers.isEmpty()) {
			return StringPool.BLANK;
		}

		CPContentListEntryRenderer cpContentListEntryRenderer =
			cpContentListEntryRenderers.get(0);

		if (cpContentListEntryRenderer == null) {
			return StringPool.BLANK;
		}

		return cpContentListEntryRenderer.getKey();
	}

	public List<CPType> getCPTypes() {
		return _cpTypeServicesTracker.getCPTypes();
	}

	public String getDisplayStyle() {
		return _cpCompareContentMiniPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		return _cpCompareContentMiniPortletInstanceConfiguration.
			displayStyleGroupId();
	}

	public int getProductsLimit() {
		return _cpCompareContentMiniPortletInstanceConfiguration.
			productsLimit();
	}

	public String getSelectionStyle() {
		return _cpCompareContentMiniPortletInstanceConfiguration.
			selectionStyle();
	}

	public boolean hasCommerceChannel() throws PortalException {
		HttpServletRequest httpServletRequest = _cpRequestHelper.getRequest();

		CommerceContext commerceContext =
			(CommerceContext)httpServletRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceChannelId = commerceContext.getCommerceChannelId();

		if (commerceChannelId > 0) {
			return true;
		}

		return false;
	}

	public boolean isSelectionStyleADT() {
		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("adt")) {
			return true;
		}

		return false;
	}

	public boolean isSelectionStyleCustomRenderer() {
		String selectionStyle = getSelectionStyle();

		if (selectionStyle.equals("custom")) {
			return true;
		}

		return false;
	}

	private final CPCompareContentMiniPortletInstanceConfiguration
		_cpCompareContentMiniPortletInstanceConfiguration;
	private final CPCompareHelper _cpCompareHelper;
	private final CPContentListEntryRendererRegistry
		_cpContentListEntryRendererRegistry;
	private final CPContentListRendererRegistry _cpContentListRendererRegistry;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private final List<Long> _cpDefinitionIds;
	private final CPRequestHelper _cpRequestHelper;
	private final CPTypeServicesTracker _cpTypeServicesTracker;

}