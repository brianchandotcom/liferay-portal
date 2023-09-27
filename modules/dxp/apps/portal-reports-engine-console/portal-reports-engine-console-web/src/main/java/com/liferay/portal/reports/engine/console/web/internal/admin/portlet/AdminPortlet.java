/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.web.internal.admin.portlet;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.reports.engine.console.constants.ReportsEngineConsolePortletKeys;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.model.Source;
import com.liferay.portal.reports.engine.console.service.DefinitionLocalService;
import com.liferay.portal.reports.engine.console.service.SourceLocalService;
import com.liferay.portal.reports.engine.console.web.internal.admin.configuration.ReportsEngineAdminWebConfiguration;
import com.liferay.portal.reports.engine.console.web.internal.admin.constants.ReportsEngineWebKeys;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gavin Wan
 */
@Component(
	configurationPid = "com.liferay.portal.reports.engine.console.web.internal.admin.configuration.ReportsEngineAdminWebConfiguration",
	property = {
		"com.liferay.portlet.css-class-wrapper=reports-portlet",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.footer-portlet-javascript=/admin/js/main.js",
		"com.liferay.portlet.header-portlet-css=/admin/css/main.css",
		"com.liferay.portlet.icon=/icons/admin.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Report Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/admin/configuration.jsp",
		"javax.portlet.init-param.copy-request-parameters=true",
		"javax.portlet.init-param.mvc-action-command-package-prefix=com.liferay.portal.reports.engine.console.web.admin.portlet.action",
		"javax.portlet.init-param.view-template=/admin/view.jsp",
		"javax.portlet.name=" + ReportsEngineConsolePortletKeys.REPORTS_ADMIN,
		"javax.portlet.portlet-info.keywords=Reports Admin",
		"javax.portlet.portlet-info.short-title=Reports Admin",
		"javax.portlet.portlet-info.title=Reports Admin",
		"javax.portlet.portlet-mode=text/html;config",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class AdminPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			_setDefinitionRequestAttribute(renderRequest);

			_setSourceRequestAttribute(renderRequest);

			_setReportsEngineAdminWebConfigurationRequestAttribute(
				renderRequest);
		}
		catch (Exception exception) {
			if (isSessionErrorException(exception)) {
				hideDefaultErrorMessage(renderRequest);

				SessionErrors.add(renderRequest, exception.getClass());
			}
			else {
				throw new PortletException(exception);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("download")) {
				_serveDownload(resourceRequest, resourceResponse);
			}
		}
		catch (IOException ioException) {
			throw ioException;
		}
		catch (PortletException portletException) {
			throw portletException;
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_reportsEngineAdminWebConfiguration =
			ConfigurableUtil.createConfigurable(
				ReportsEngineAdminWebConfiguration.class, properties);
	}

	@Reference
	protected DefinitionLocalService definitionLocalService;

	@Reference
	protected SourceLocalService sourceLocalService;

	@Reference(target = "(default=true)")
	protected Store store;

	private void _serveDownload(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String fileName = ParamUtil.getString(resourceRequest, "fileName");

		String shortFileName = StringUtil.extractLast(
			fileName, StringPool.SLASH);
		InputStream inputStream = store.getFileAsStream(
			themeDisplay.getCompanyId(), CompanyConstants.SYSTEM, fileName,
			StringPool.BLANK);

		PortletResponseUtil.sendFile(
			resourceRequest, resourceResponse, shortFileName, inputStream,
			MimeTypesUtil.getContentType(fileName));
	}

	private void _setDefinitionRequestAttribute(RenderRequest renderRequest)
		throws PortalException {

		long definitionId = ParamUtil.getLong(renderRequest, "definitionId");

		Definition definition = null;

		if (definitionId > 0) {
			definition = definitionLocalService.getDefinition(definitionId);
		}

		renderRequest.setAttribute(ReportsEngineWebKeys.DEFINITION, definition);
	}

	private void _setReportsEngineAdminWebConfigurationRequestAttribute(
		RenderRequest renderRequest) {

		renderRequest.setAttribute(
			ReportsEngineAdminWebConfiguration.class.getName(),
			_reportsEngineAdminWebConfiguration);
	}

	private void _setSourceRequestAttribute(RenderRequest renderRequest)
		throws PortalException {

		long sourceId = ParamUtil.getLong(renderRequest, "sourceId");

		Source source = null;

		if (sourceId > 0) {
			source = sourceLocalService.getSource(sourceId);
		}

		renderRequest.setAttribute(ReportsEngineWebKeys.SOURCE, source);
	}

	private volatile ReportsEngineAdminWebConfiguration
		_reportsEngineAdminWebConfiguration;

}