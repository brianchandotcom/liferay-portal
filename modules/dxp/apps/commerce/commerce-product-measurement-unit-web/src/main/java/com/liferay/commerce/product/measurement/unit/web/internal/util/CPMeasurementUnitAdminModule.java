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

package com.liferay.commerce.product.measurement.unit.web.internal.util;

import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.commerce.product.measurement.unit.web.internal.display.context.CPMeasurementUnitsDisplayContext;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
import com.liferay.commerce.product.service.permission.CPMeasurementUnitPermission;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Element;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
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
	property = "commerce.admin.module.key=" + CPMeasurementUnitAdminModule.KEY
)
public class CPMeasurementUnitAdminModule implements CommerceAdminModule {

	public static final String KEY = "measurement-units";

	@Override
	public void deleteData(PortletDataContext portletDataContext)
		throws Exception {

		_cpMeasurementUnitStagedModelRepository.deleteStagedModels(
			portletDataContext);
	}

	@Override
	public void exportData(
			String namespace, PortletDataContext portletDataContext)
		throws Exception {

		portletDataContext.addPortletPermissions(
			CPMeasurementUnitPermission.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(
				namespace, "measurement-units")) {

			ActionableDynamicQuery actionableDynamicQuery =
				_cpMeasurementUnitStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			actionableDynamicQuery.performActions();
		}
	}

	@Override
	public List<StagedModelType> getDeletionSystemEventStagedModelTypes() {
		return Collections.singletonList(
			new StagedModelType(CPMeasurementUnit.class));
	}

	@Override
	public List<PortletDataHandlerControl> getExportControls(String namespace) {
		return Collections.<PortletDataHandlerControl>singletonList(
			new PortletDataHandlerBoolean(
				namespace, "measurement-units", true, false, null,
				CPMeasurementUnit.class.getName()));
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "measurement-units");
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

		if (portletDataContext.getBooleanParameter(
				namespace, "measurement-units")) {

			Element modelsElement =
				portletDataContext.getImportDataGroupElement(
					CPMeasurementUnit.class);

			List<Element> modelElements = modelsElement.elements();

			for (Element modelElement : modelElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, modelElement);
			}
		}
	}

	@Override
	public boolean isVisible(HttpServletRequest httpServletRequest)
		throws PortalException {

		return true;
	}

	@Override
	public void prepareManifestSummary(PortletDataContext portletDataContext)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_cpMeasurementUnitStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		actionableDynamicQuery.performCount();
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		CPMeasurementUnitsDisplayContext cpMeasurementUnitsDisplayContext =
			new CPMeasurementUnitsDisplayContext(
				_cpMeasurementUnitService, renderRequest, renderResponse);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, cpMeasurementUnitsDisplayContext);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/view.jsp");
	}

	@Reference
	private CPMeasurementUnitService _cpMeasurementUnitService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPMeasurementUnit)"
	)
	private StagedModelRepository<CPMeasurementUnit>
		_cpMeasurementUnitStagedModelRepository;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.measurement.unit.web)"
	)
	private ServletContext _servletContext;

}