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

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.product.catalog.CPCatalogEntryFactory;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.web.internal.configuration.CPCompareContentMiniPortletInstanceConfiguration;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPCompareUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPCompareContentMiniDisplayContext {

	public CPCompareContentMiniDisplayContext(
			CPCatalogEntryFactory cpCatalogEntryFactory,
			CPContentListEntryRendererRegistry
				cpContentListEntryRendererRegistry,
			CPContentListRendererRegistry cpContentListRendererRegistry,
			CPDefinitionService cpDefinitionService,
			CPTypeServicesTracker cpTypeServicesTracker,
			LayoutLocalService layoutLocalService,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_cpCatalogEntryFactory = cpCatalogEntryFactory;
		_cpContentListEntryRendererRegistry =
			cpContentListEntryRendererRegistry;
		_cpContentListRendererRegistry = cpContentListRendererRegistry;
		_cpDefinitionService = cpDefinitionService;
		_cpTypeServicesTracker = cpTypeServicesTracker;
		_layoutLocalService = layoutLocalService;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		ThemeDisplay themeDisplay = _cpRequestHelper.getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_cpCompareContentMiniPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPCompareContentMiniPortletInstanceConfiguration.class);

		_cpDefinitionIds = CPCompareUtil.getCPDefinitionIds(httpServletRequest);
	}

	public String getClearCompareProductsURL() {
		RenderResponse renderResponse = _cpRequestHelper.getRenderResponse();

		PortletURL portletURL = renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "clearCompareProducts");

		String redirect = _cpRequestHelper.getCurrentURL();

		portletURL.setParameter("redirect", redirect);

		return portletURL.toString();
	}

	public String getCompareProductsURL() throws PortalException {
		Layout curLayout = _cpRequestHelper.getLayout();

		long plid = PortalUtil.getPlidFromPortletId(
			_cpRequestHelper.getScopeGroupId(), curLayout.isPrivateLayout(),
			CPPortletKeys.CP_COMPARE_CONTENT_WEB);

		Layout layout = _layoutLocalService.fetchLayout(plid);

		if (layout == null) {
			return StringPool.BLANK;
		}

		return PortalUtil.getLayoutURL(
			layout, _cpRequestHelper.getThemeDisplay());
	}

	public List<CPCatalogEntry> getCPCatalogEntries() throws PortalException {
		List<CPCatalogEntry> cpCatalogEntries = new ArrayList<>();

		for (Long cpDefinitionId : _cpDefinitionIds) {
			cpCatalogEntries.add(
				_cpCatalogEntryFactory.create(
					cpDefinitionId, _cpRequestHelper.getLocale()));
		}

		if (cpCatalogEntries.size() > getProductsLimit()) {
			return cpCatalogEntries.subList(0, getProductsLimit());
		}

		return cpCatalogEntries;
	}

	public List<CPContentListEntryRenderer> getCPContentListEntryRenderers(
		String cpType) {

		return
			_cpContentListEntryRendererRegistry.getCPContentListEntryRenderers(
				getCPContentListRendererKey(), cpType);
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

	public String getDeleteCompareProductURL(long cpDefinitionId) {
		RenderResponse renderResponse = _cpRequestHelper.getRenderResponse();

		PortletURL portletURL = renderResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "deleteCompareProduct");

		String redirect = _cpRequestHelper.getCurrentURL();

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinitionId));

		return portletURL.toString();
	}

	public String getDisplayStyle() {
		return _cpCompareContentMiniPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		return _cpCompareContentMiniPortletInstanceConfiguration.
			displayStyleGroupId();
	}

	public int getProductsLimit() {
		return
			_cpCompareContentMiniPortletInstanceConfiguration.productsLimit();
	}

	public String getSelectionStyle() {
		return
			_cpCompareContentMiniPortletInstanceConfiguration.selectionStyle();
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

	public void renderCPContentList() throws Exception {
		CPContentListRenderer cpContentListRenderer =
			_cpContentListRendererRegistry.getCPContentListRenderer(
				getCPContentListRendererKey());

		if (cpContentListRenderer != null) {
			cpContentListRenderer.render(
				getCPCatalogEntries(), _cpRequestHelper.getRequest(),
				PortalUtil.getHttpServletResponse(
					_cpRequestHelper.getLiferayPortletResponse()));
		}
	}

	public void renderCPContentListEntry(CPCatalogEntry cpCatalogEntry)
		throws Exception {

		String cpType = cpCatalogEntry.getProductTypeName();

		CPContentListEntryRenderer cpContentListEntryRenderer =
			_cpContentListEntryRendererRegistry.getCPContentListEntryRenderer(
				getCPTypeListEntryRendererKey(cpType),
				getCPContentListRendererKey(), cpType);

		if (cpContentListEntryRenderer != null) {
			cpContentListEntryRenderer.render(
				cpCatalogEntry, _cpRequestHelper.getRequest(),
				PortalUtil.getHttpServletResponse(
					_cpRequestHelper.getLiferayPortletResponse()));
		}
	}

	private final CPCatalogEntryFactory _cpCatalogEntryFactory;
	private final CPCompareContentMiniPortletInstanceConfiguration
		_cpCompareContentMiniPortletInstanceConfiguration;
	private final CPContentListEntryRendererRegistry
		_cpContentListEntryRendererRegistry;
	private final CPContentListRendererRegistry _cpContentListRendererRegistry;
	private final List<Long> _cpDefinitionIds;
	private final CPDefinitionService _cpDefinitionService;
	private final CPRequestHelper _cpRequestHelper;
	private final CPTypeServicesTracker _cpTypeServicesTracker;
	private final LayoutLocalService _layoutLocalService;

}