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

package com.liferay.commerce.order.web.internal.admin;

import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.internal.display.context.CommerceOrderSettingsDisplayContext;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "commerce.admin.module.key=" + OrderSettingsCommerceAdminModule.KEY
)
public class OrderSettingsCommerceAdminModule implements CommerceAdminModule {

	public static final String KEY = "order";

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
		return LanguageUtil.get(locale, "order");
	}

	@Override
	public PortletURL getSearchURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return null;
	}

	@Override
	public void importData(
			String namespace, PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public boolean isVisible(HttpServletRequest httpServletRequest)
		throws PortalException {

		if (_workflowEngineManager.isDeployed() &&
			(WorkflowHandlerRegistryUtil.getWorkflowHandler(
				CommerceOrder.class.getName()) != null)) {

			return true;
		}

		return false;
	}

	@Override
	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		CommerceOrderSettingsDisplayContext
			commerceOrderSettingsDisplayContext =
				new CommerceOrderSettingsDisplayContext(
					renderRequest, _workflowDefinitionLinkLocalService,
					_workflowDefinitionManager);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceOrderSettingsDisplayContext);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/edit_order_settings.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.order.web)"
	)
	private ServletContext _servletContext;

	@Reference
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Reference
	private WorkflowEngineManager _workflowEngineManager;

}