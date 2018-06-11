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
import com.liferay.commerce.product.content.web.internal.display.context.util.CPContentRequestHelper;
import com.liferay.commerce.product.content.web.internal.util.CPPublisherWebHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class BaseCPPublisherDisplayContext {

	public BaseCPPublisherDisplayContext(
		CPPublisherWebHelper cpPublisherWebHelper,
		HttpServletRequest httpServletRequest) {

		this.cpPublisherWebHelper = cpPublisherWebHelper;

		cpContentRequestHelper = new CPContentRequestHelper(httpServletRequest);
	}

	public List<CPCatalogEntry> getCPCatalogEntries() throws Exception {
		return cpPublisherWebHelper.getCPCatalogEntries(
			cpContentRequestHelper.getPortletPreferences(),
			cpContentRequestHelper.getThemeDisplay());
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

	public String getSku(CPDefinition cpDefinition, Locale locale) {
		return cpPublisherWebHelper.getSku(cpDefinition, locale);
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

	protected final CPContentRequestHelper cpContentRequestHelper;
	protected final CPPublisherWebHelper cpPublisherWebHelper;
	protected String dataSource;
	protected String selectionStyle;

}