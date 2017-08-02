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

package com.liferay.commerce.address.web.internal.util;

import com.liferay.commerce.address.service.CommerceCountryService;
import com.liferay.commerce.address.web.internal.display.context.CommerceCountriesDisplayContext;
import com.liferay.commerce.address.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.admin.web.util.CommerceAdminModule;
import com.liferay.commerce.product.util.JSPRenderer;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.admin.module.key=" + CountriesCommerceAdminModule.KEY
)
public class CountriesCommerceAdminModule implements CommerceAdminModule {

	public static final String KEY = "countries";

	@Override
	public void deleteData(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void exportData(
			String namespace, PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public List<StagedModelType> getDeletionSystemEventStagedModelTypes() {
		return Collections.emptyList();
	}

	@Override
	public List<PortletDataHandlerControl> getExportControls(String namespace) {
		return Collections.emptyList();
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "countries");
	}

	@Override
	public void importData(
			String namespace, PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		CommerceCountriesDisplayContext commerceCountriesDisplayContext =
			new CommerceCountriesDisplayContext(
				_actionHelper, _commerceCountryService, renderRequest,
				renderResponse);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceCountriesDisplayContext);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/view.jsp");
	}

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.address.web)"
	)
	private ServletContext _servletContext;

}