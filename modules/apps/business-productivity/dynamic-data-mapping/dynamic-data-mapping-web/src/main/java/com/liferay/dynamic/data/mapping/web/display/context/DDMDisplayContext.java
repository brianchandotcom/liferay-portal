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

package com.liferay.dynamic.data.mapping.web.display.context;

import com.liferay.dynamic.data.mapping.configuration.DDMGroupServiceConfiguration;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.web.configuration.DDMWebConfiguration;
import com.liferay.dynamic.data.mapping.web.context.util.DDMWebRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class DDMDisplayContext {

	public DDMDisplayContext(
			RenderRequest renderRequest,
			DDMWebConfiguration ddmWebConfiguration)
		throws PortalException {

		_renderRequest = renderRequest;
		_ddmWebConfiguration = ddmWebConfiguration;

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		_ddmWebRequestHelper = new DDMWebRequestHelper(httpServletRequest);
	}

	public boolean autogenerateStructureKey() {
		return _ddmWebConfiguration.autogenerateStructureKey();
	}

	public boolean autogenerateTemplateKey() {
		return _ddmWebConfiguration.autogenerateTemplateKey();
	}

	public boolean changeableDefaultLanguage() {
		return _ddmWebConfiguration.changeableDefaultLanguage();
	}

	public DDMGroupServiceConfiguration getDDMGroupServiceConfiguration() {
		return _ddmWebRequestHelper.getDDMGroupServiceConfiguration();
	}

	public String getOrderByCol() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		String orderByCol = ParamUtil.getString(_renderRequest, "orderByCol");

		if (Validator.isNull(orderByCol)) {
			orderByCol = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col",
				"modified-date");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-col",
				orderByCol);
		}

		return orderByCol;
	}

	public String getOrderByType() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		String orderByType = ParamUtil.getString(_renderRequest, "orderByType");

		if (Validator.isNull(orderByType)) {
			orderByType = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type",
				"asc");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING, "entries-order-by-type",
				orderByType);
		}

		return orderByType;
	}

	public String[] smallImageExtensions() {
		DDMGroupServiceConfiguration ddmGroupServiceConfiguration =
			_ddmWebRequestHelper.getDDMGroupServiceConfiguration();

		return ddmGroupServiceConfiguration.smallImageExtensions();
	}

	public int smallImageMaxSize() {
		DDMGroupServiceConfiguration ddmGroupServiceConfiguration =
			_ddmWebRequestHelper.getDDMGroupServiceConfiguration();

		return ddmGroupServiceConfiguration.smallImageMaxSize();
	}

	private final DDMWebConfiguration _ddmWebConfiguration;
	private final DDMWebRequestHelper _ddmWebRequestHelper;
	private final RenderRequest _renderRequest;

}