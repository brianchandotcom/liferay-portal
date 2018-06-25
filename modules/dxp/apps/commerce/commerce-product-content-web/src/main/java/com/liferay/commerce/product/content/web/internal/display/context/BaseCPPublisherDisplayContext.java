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
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.render.list.CPContentListRenderer;
import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRenderer;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.web.internal.configuration.CPPublisherPortletInstanceConfiguration;
import com.liferay.commerce.product.content.web.internal.display.context.util.CPContentRequestHelper;
import com.liferay.commerce.product.content.web.internal.util.CPPublisherWebHelper;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class BaseCPPublisherDisplayContext {

	public BaseCPPublisherDisplayContext(
			CPContentListEntryRendererRegistry contentListEntryRendererRegistry,
			CPContentListRendererRegistry cpContentListRendererRegistry,
			CPPublisherWebHelper cpPublisherWebHelper,
			CPTypeServicesTracker cpTypeServicesTracker,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		this.contentListEntryRendererRegistry =
			contentListEntryRendererRegistry;
		this.cpContentListRendererRegistry = cpContentListRendererRegistry;
		this.cpPublisherWebHelper = cpPublisherWebHelper;
		this.cpTypeServicesTracker = cpTypeServicesTracker;

		cpContentRequestHelper = new CPContentRequestHelper(httpServletRequest);

		PortletDisplay portletDisplay =
			cpContentRequestHelper.getPortletDisplay();

		cpPublisherPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPPublisherPortletInstanceConfiguration.class);
	}

	public List<CPCatalogEntry> getCPCatalogEntries() throws Exception {
		return cpPublisherWebHelper.getCPCatalogEntries(
			cpContentRequestHelper.getPortletPreferences(),
			cpContentRequestHelper.getThemeDisplay());
	}

	public List<CPContentListEntryRenderer> getCPContentListEntryRenderers(
		String cpType) {

		return contentListEntryRendererRegistry.getCPContentListEntryRenderers(
			CPPortletKeys.CP_PUBLISHER_WEB, cpType);
	}

	public String getCPContentListRendererKey() {
		RenderRequest renderRequest = cpContentRequestHelper.getRenderRequest();

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
		return cpContentListRendererRegistry.getCPContentListRenderers(
			CPPortletKeys.CP_PUBLISHER_WEB);
	}

	public String getCPTypeListEntryRendererKey(String cpType) {
		RenderRequest renderRequest = cpContentRequestHelper.getRenderRequest();

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
		return cpTypeServicesTracker.getCPTypes();
	}

	public String getDataSource() {
		if (dataSource != null) {
			return dataSource;
		}

		PortletPreferences portletPreferences =
			cpContentRequestHelper.getPortletPreferences();

		dataSource = GetterUtil.getString(
			portletPreferences.getValue("dataSource", null));

		return dataSource;
	}

	public String getDisplayStyle() {
		return cpPublisherPortletInstanceConfiguration.displayStyle();
	}

	public long getDisplayStyleGroupId() {
		return cpPublisherPortletInstanceConfiguration.displayStyleGroupId();
	}

	public int getPaginationDelta() {
		return cpPublisherPortletInstanceConfiguration.paginationDelta();
	}

	public String getPaginationType() {
		return cpPublisherPortletInstanceConfiguration.paginationType();
	}

	public String getRenderSelection() {
		if (renderSelection != null) {
			return renderSelection;
		}

		PortletPreferences portletPreferences =
			cpContentRequestHelper.getPortletPreferences();

		renderSelection = GetterUtil.getString(
			portletPreferences.getValue("renderSelection", null), "custom");

		return renderSelection;
	}

	public String getSelectionStyle() {
		if (selectionStyle != null) {
			return selectionStyle;
		}

		PortletPreferences portletPreferences =
			cpContentRequestHelper.getPortletPreferences();

		selectionStyle = GetterUtil.getString(
			portletPreferences.getValue("selectionStyle", null), "dynamic");

		return selectionStyle;
	}

	public boolean isPaginate() {
		return cpPublisherPortletInstanceConfiguration.paginate();
	}

	public boolean isRenderSelectionADT() {
		String renderSelection = getRenderSelection();

		return renderSelection.equals("adt");
	}

	public boolean isRenderSelectionCustomRenderer() {
		String renderSelection = getRenderSelection();

		return renderSelection.equals("custom");
	}

	public boolean isSelectionStyleDataSource() {
		String selectionStyle = getSelectionStyle();

		return selectionStyle.equals("dataSource");
	}

	public boolean isSelectionStyleDynamic() {
		String selectionStyle = getSelectionStyle();

		return selectionStyle.equals("dynamic");
	}

	public boolean isSelectionStyleManual() {
		String selectionStyle = getSelectionStyle();

		return selectionStyle.equals("manual");
	}

	protected final CPContentListEntryRendererRegistry
		contentListEntryRendererRegistry;
	protected final CPContentListRendererRegistry cpContentListRendererRegistry;
	protected final CPContentRequestHelper cpContentRequestHelper;
	protected final CPPublisherPortletInstanceConfiguration
		cpPublisherPortletInstanceConfiguration;
	protected final CPPublisherWebHelper cpPublisherWebHelper;
	protected final CPTypeServicesTracker cpTypeServicesTracker;
	protected String dataSource;
	protected String renderSelection;
	protected String selectionStyle;

}