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

package com.liferay.commerce.currency.web.internal.util;

import com.liferay.commerce.admin.CommerceAdminModule;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.permission.CommerceCurrencyPermission;
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
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "commerce.admin.module.key=" + CurrenciesCommerceAdminModule.KEY
)
public class CurrenciesCommerceAdminModule implements CommerceAdminModule {

	public static final String KEY = "currencies";

	@Override
	public void deleteData(PortletDataContext portletDataContext)
		throws Exception {

		_commerceCurrencyStagedModelRepository.deleteStagedModels(
			portletDataContext);
	}

	@Override
	public void exportData(
			String namespace, PortletDataContext portletDataContext)
		throws Exception {

		portletDataContext.addPortletPermissions(
			CommerceCurrencyPermission.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(namespace, "currencies")) {
			ActionableDynamicQuery actionableDynamicQuery =
				_commerceCurrencyStagedModelRepository.
					getExportActionableDynamicQuery(portletDataContext);

			actionableDynamicQuery.performActions();
		}
	}

	@Override
	public List<StagedModelType> getDeletionSystemEventStagedModelTypes() {
		return Collections.singletonList(
			new StagedModelType(CommerceCurrency.class));
	}

	@Override
	public List<PortletDataHandlerControl> getExportControls(String namespace) {
		return Collections.<PortletDataHandlerControl>singletonList(
			new PortletDataHandlerBoolean(
				namespace, "currencies", true, false, null,
				CommerceCurrency.class.getName()));
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "currencies");
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

		if (portletDataContext.getBooleanParameter(namespace, "currencies")) {
			Element modelsElement =
				portletDataContext.getImportDataGroupElement(
					CommerceCurrency.class);

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
			_commerceCurrencyStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		actionableDynamicQuery.performCount();
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);
		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/view.jsp");
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.currency.model.CommerceCurrency)"
	)
	private StagedModelRepository<CommerceCurrency>
		_commerceCurrencyStagedModelRepository;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.currency.web)"
	)
	private ServletContext _servletContext;

}